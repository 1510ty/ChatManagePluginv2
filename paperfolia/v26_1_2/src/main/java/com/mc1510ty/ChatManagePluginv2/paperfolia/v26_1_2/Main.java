package com.mc1510ty.ChatManagePluginv2.paperfolia.v26_1_2;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.UnifiedJedis;

public class Main extends JavaPlugin implements Listener {

    private boolean useRedis;
    private UnifiedJedis jedis;
    private String redisChannel;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        this.useRedis = getConfig().getBoolean("redis.enable", false);
        this.redisChannel = getConfig().getString("redis.channel", "chat_relay");

        if (useRedis) {
            String host = getConfig().getString("redis.host", "localhost");
            int port = getConfig().getInt("redis.port", 6379);
            // ここで代入！これだけで接続準備完了
            this.jedis = new redis.clients.jedis.UnifiedJedis(new redis.clients.jedis.HostAndPort(host, port));
            startSubscriber();
            getLogger().info("Redis Mode: ON");
        }


        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        if (jedis != null) {
            jedis.close();
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent e) {
        String original = PlainTextComponentSerializer.plainText().serialize(e.message());

        // 1. かな変換を試みる（日本語や大文字があればスキップ）
        String kanaResult = original;
        boolean isConverted = false;

        if (!Henkan.containsJapanese(original) && !Henkan.containsUpperCase(original)) {
            kanaResult = Henkan.romajikarakana(original);
            // 元の文と変わっていれば「変換された」とみなす
            if (!original.equals(kanaResult)) {
                isConverted = true;
            }
        }

        // 2. 常にフィルターを適用
        String finalContent = Henkan.applyFilters(kanaResult);

        // 3. 表示文字列の構築
        String displayString;
        if (isConverted) {
            // 変換された場合のみ () をつける。フィルター済みの文字 (原文)
            displayString = finalContent + " (" + original + ")";
        } else {
            // 変換されていない（最初から日本語、大文字入り、または変換不要）ならそのまま
            displayString = finalContent;
        }

        // 以降、Redis送信 or broadcast
        if (useRedis) {
            getServer().getScheduler().runTaskAsynchronously(this, () -> {
                jedis.publish(redisChannel, e.getPlayer().getName() + "::" + displayString);
            });
            e.setCancelled(true);
        } else {
            e.message(Component.text(displayString));
        }
    }

    private void startSubscriber() {
        new Thread(() -> {
            try {
                // JedisPubSub のインスタンス作成
                redis.clients.jedis.JedisPubSub pubSub = new redis.clients.jedis.JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        // "名前::内容" を分割
                        String[] data = message.split("::", 2);
                        if (data.length < 2) return;

                        String senderName = data[0];
                        String content = data[1];

                        getServer().getScheduler().runTask(Main.this, () -> {
                            // バニラ風の Component を組み立て
                            Component vanillaStyle = Component.text()
                                    .append(Component.text("<" + senderName + "> "))
                                    .append(Component.text(content))
                                    .build();

                            getServer().broadcast(vanillaStyle);
                        });
                    }
                };

                // ここで待機状態に入る（無限ループになるので必ず別スレッドで！）
                // UnifiedJedisから中身のコネクションを借りてsubscribe
                jedis.subscribe(pubSub, redisChannel);

            } catch (Exception e) {
                getLogger().severe("Redis Subscriber でエラーが発生しました: " + e.getMessage());
            }
        }, "ChatManage-Subscriber").start();
    }

}
