//        ChatManagePluginv2
//        Copyright (C) 2026  1510ty
//
//        This program is free software: you can redistribute it and/or modify
//        it under the terms of the GNU General Public License as published by
//        the Free Software Foundation, either version 3 of the License, or
//        (at your option) any later version.
//
//        This program is distributed in the hope that it will be useful,
//        but WITHOUT ANY WARRANTY; without even the implied warranty of
//        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//        GNU General Public License for more details.
//
//        You should have received a copy of the GNU General Public License
//        along with this program.  If not, see <https://www.gnu.org/licenses/>.
package com.mc1510ty.ChatManagePluginv2.paperfolia.v26_1_2;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import redis.clients.jedis.UnifiedJedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main extends JavaPlugin implements Listener {

    private boolean useRedis;
    private UnifiedJedis pubJedis; // 送信用
    private UnifiedJedis subJedis; // 受信用
    private String redisChannel;
    private boolean enableromazitohiragana;
    private final Map<String, String> playerTags = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigValues();

        if (useRedis) {
            String host = getConfig().getString("redis.host", "localhost");
            int port = getConfig().getInt("redis.port", 6379);
            redis.clients.jedis.HostAndPort address = new redis.clients.jedis.HostAndPort(host, port);

            // インスタンスを2つ作る（これで土管が2本になる）
            this.pubJedis = new UnifiedJedis(address);
            this.subJedis = new UnifiedJedis(address);

            startSubscriber();
            getLogger().info("Redis Mode: ON (Two-way Connection Established)");
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        if (pubJedis != null) pubJedis.close();
        if (subJedis != null) subJedis.close();
    }

    private void loadConfigValues() {
        this.useRedis = getConfig().getBoolean("redis.enable", false);
        this.redisChannel = getConfig().getString("redis.channel", "chat_relay");
        this.enableromazitohiragana = getConfig().getBoolean("romazitohiragana.enable", false);

        // タグ設定の読み込み
        playerTags.clear();
        ConfigurationSection tagSection = getConfig().getConfigurationSection("tag");
        if (tagSection != null) {
            for (String tagName : tagSection.getKeys(false)) {
                List<String> players = tagSection.getStringList(tagName);
                for (String playerName : players) {
                    playerTags.put(playerName, tagName);
                }
            }
        }
    }

    // チャットのComponentを組み立てる共通メソッド
    private Component createChatComponent(String senderName, String content) {
        String tag = playerTags.get(senderName);
        String prefix = (tag != null && !tag.isEmpty()) ? "[" + tag + "] " : "";

        return Component.text(prefix + "<" + senderName + "> " + content);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerChat(AsyncChatEvent e) {
        String original = PlainTextComponentSerializer.plainText().serialize(e.message());

        // 1. かな変換を試みる（日本語や大文字があればスキップ）
        String kanaResult = original;
        boolean isConverted = false;

        if (enableromazitohiragana && !Henkan.containsJapanese(original) && !Henkan.containsUpperCase(original)) {
            kanaResult = Henkan.romajikarakana(original);
            if (!original.equals(kanaResult)) {
                isConverted = true;
            }
        }

        // 2. 常にフィルターを適用
        String finalContent = Henkan.applyFilters(kanaResult);

        // 3. 表示文字列の構築
        String displayString;
        if (isConverted) {
            displayString = finalContent + " (" + original + ")";
        } else {
            displayString = finalContent;
        }

        String senderName = e.getPlayer().getName();

        // 4. 以降、Redis送信 or ローカル配信
        if (useRedis) {
            getServer().getGlobalRegionScheduler().run(this, (task) -> {
                pubJedis.publish(redisChannel, senderName + "::" + displayString);
            });
            e.setCancelled(true);
        } else {
            e.setCancelled(true);
            Component chatComponent = createChatComponent(senderName, displayString);

            // ローカルでも全員に安全に配信
            getServer().getGlobalRegionScheduler().execute(this, () -> {
                for (Player player : getServer().getOnlinePlayers()) {
                    player.getScheduler().execute(this, () -> {
                        player.sendMessage(chatComponent);
                    }, null, 0);
                }
                getServer().getConsoleSender().sendMessage(chatComponent);
            });
        }
    }

    private void startSubscriber() {
        new Thread(() -> {
            try {
                redis.clients.jedis.JedisPubSub pubSub = new redis.clients.jedis.JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        String[] data = message.split("::", 2);
                        if (data.length < 2) return;

                        String senderName = data[0];
                        String content = data[1];

                        getServer().getGlobalRegionScheduler().execute(Main.this, () -> {
                            Component chatComponent = createChatComponent(senderName, content);

                            for (Player player : getServer().getOnlinePlayers()) {
                                player.getScheduler().execute(Main.this, () -> {
                                    player.sendMessage(chatComponent);
                                }, null, 0);
                            }

                            getServer().getConsoleSender().sendMessage(chatComponent);
                        });
                    }
                };

                subJedis.subscribe(pubSub, redisChannel);

            } catch (Exception e) {
                getLogger().severe("Redis Subscriber でエラーが発生しました: " + e.getMessage());
            }
        }, "ChatManage-Subscriber").start();
    }
}