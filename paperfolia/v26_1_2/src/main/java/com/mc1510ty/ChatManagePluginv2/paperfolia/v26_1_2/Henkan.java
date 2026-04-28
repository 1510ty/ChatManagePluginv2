package com.mc1510ty.ChatManagePluginv2.paperfolia.v26_1_2;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Henkan {
    private static Map<String, String> table = new LinkedHashMap<>();
    private static final Map<String, String> filterTable = new LinkedHashMap<>();

    static {
        table.put("a", "あ");
        table.put("i", "い");
        table.put("u", "う");
        table.put("e", "え");
        table.put("o", "お");
        table.put("ka", "か");
        table.put("ki", "き");
        table.put("ku", "く");
        table.put("ke", "け");
        table.put("ko", "こ");
        table.put("kka", "っか");
        table.put("kki", "っき");
        table.put("kku", "っく");
        table.put("kke", "っけ");
        table.put("kko", "っこ");
        table.put("sa", "さ");
        table.put("si", "し");
        table.put("shi", "し");
        table.put("su", "す");
        table.put("se", "せ");
        table.put("so", "そ");
        table.put("ssa", "っさ");
        table.put("ssi", "っし");
        table.put("sshi", "っし");
        table.put("ssu", "っす");
        table.put("sse", "っせ");
        table.put("sso", "っそ");
        table.put("ta", "た");
        table.put("ti", "ち");
        table.put("chi", "ち");
        table.put("tu", "つ");
        table.put("tsu", "つ");
        table.put("te", "て");
        table.put("to", "と");
        table.put("tta", "った");
        table.put("tti", "っち");
        table.put("cchi", "っち");
        table.put("ttu", "っつ");
        table.put("ttsu", "っつ");
        table.put("tte", "って");
        table.put("tto", "っと");
        table.put("na", "な");
        table.put("ni", "に");
        table.put("nu", "ぬ");
        table.put("ne", "ね");
        table.put("no", "の");
        table.put("ha", "は");
        table.put("hi", "ひ");
        table.put("hu", "ふ");
        table.put("fu", "ふ");
        table.put("he", "へ");
        table.put("ho", "ほ");
        table.put("hha", "っは");
        table.put("hhi", "っひ");
        table.put("hhu", "っふ");
        table.put("ffu", "っふ");
        table.put("hhe", "っへ");
        table.put("hho", "っほ");
        table.put("ma", "ま");
        table.put("mi", "み");
        table.put("mu", "む");
        table.put("me", "め");
        table.put("mo", "も");
        table.put("mma", "っま");
        table.put("mmi", "っみ");
        table.put("mmu", "っむ");
        table.put("mme", "っめ");
        table.put("mmo", "っも");
        table.put("ya", "や");
        table.put("yu", "ゆ");
        table.put("yo", "よ");
        table.put("yya", "っや");
        table.put("yyu", "っゆ");
        table.put("yyo", "っよ");
        table.put("ra", "ら");
        table.put("ri", "り");
        table.put("ru", "る");
        table.put("re", "れ");
        table.put("ro", "ろ");
        table.put("rra", "っら");
        table.put("rri", "っり");
        table.put("rru", "っる");
        table.put("rre", "っれ");
        table.put("rro", "っろ");
        table.put("wa", "わ");
        table.put("wo", "を");
        table.put("nn", "ん");
        table.put("wwa", "っわ");
        table.put("wwo", "っを");
        table.put("ga", "が");
        table.put("gi", "ぎ");
        table.put("gu", "ぐ");
        table.put("ge", "げ");
        table.put("go", "ご");
        table.put("gga", "っが");
        table.put("ggi", "っぎ");
        table.put("ggu", "っぐ");
        table.put("gge", "っげ");
        table.put("ggo", "っご");
        table.put("za", "ざ");
        table.put("zi", "じ");
        table.put("ji", "じ");
        table.put("zu", "ず");
        table.put("ze", "ぜ");
        table.put("zo", "ぞ");
        table.put("zza", "っざ");
        table.put("zzi", "っじ");
        table.put("jji", "っじ");
        table.put("zzu", "っず");
        table.put("zze", "っぜ");
        table.put("zzo", "っぞ");
        table.put("da", "だ");
        table.put("di", "ぢ");
        table.put("du", "づ");
        table.put("de", "で");
        table.put("do", "ど");
        table.put("dda", "っだ");
        table.put("ddi", "っぢ");
        table.put("ddu", "っづ");
        table.put("っで", "っで");
        table.put("ddo", "っど");
        table.put("ba", "ば");
        table.put("bi", "び");
        table.put("bu", "ぶ");
        table.put("be", "べ");
        table.put("bo", "ぼ");
        table.put("bba", "っば");
        table.put("bbi", "っび");
        table.put("bbu", "っぶ");
        table.put("bbe", "っべ");
        table.put("bbo", "っぼ");
        table.put("pa", "ぱ");
        table.put("pi", "ぴ");
        table.put("pu", "ぷ");
        table.put("pe", "ぺ");
        table.put("po", "ぽ");
        table.put("ppa", "っぱ");
        table.put("ppi", "っぴ");
        table.put("ppu", "っぷ");
        table.put("ppe", "っぺ");
        table.put("ppo", "っぽ");
        table.put("fa", "ふぁ");
        table.put("fi", "ふぃ");
        table.put("fe", "ふぇ");
        table.put("fo", "ふぉ");
        table.put("ffa", "っふぁ");
        table.put("ffi", "っふぃ");
        table.put("ffe", "っふぇ");
        table.put("ffo", "っふぉ");
        table.put("kya", "きゃ");
        table.put("kyi", "きぃ");
        table.put("kyu", "きゅ");
        table.put("kye1", "きぇ");
        table.put("kyo", "きょ");
        table.put("kkya", "っきゃ");
        table.put("kkyi", "っきぃ");
        table.put("kkyu", "っきゅ");
        table.put("kkye", "っきぇ");
        table.put("kkyo", "っきょ");
        table.put("sya", "しゃ");
        table.put("syi", "しぃ");
        table.put("syu", "しゅ");
        table.put("sye", "しぇ");
        table.put("syo", "しょ");
        table.put("ssya", "っしゃ");
        table.put("ssyi", "っしぃ");
        table.put("ssyu", "っしゅ");
        table.put("ssye", "っしぇ");
        table.put("syyo", "っしょ");
        table.put("tya", "ちゃ");
        table.put("tyi", "ちぃ");
        table.put("tyu", "ちゅ");
        table.put("tye", "ちぇ");
        table.put("tyo", "ちょ");
        table.put("ttya", "っちゃ");
        table.put("ttyi", "っちぃ");
        table.put("ttyu", "っちゅ");
        table.put("ttye", "っちぇ");
        table.put("ttyo", "っちょ");
        table.put("cha", "ちゃ");
        table.put("chu", "ちゅ");
        table.put("che", "ちぇ");
        table.put("cho", "ちょ");
        table.put("ccha", "っちゃ");
        table.put("cchu", "っちゅ");
        table.put("cche", "っちぇ");
        table.put("ccho", "っちょ");
        table.put("nya", "にゃ");
        table.put("nyu", "にゅ");
        table.put("nyo", "にょ");
        table.put("hya", "ひゃ");
        table.put("hyi", "ひぃ");
        table.put("hyu", "ひゅ");
        table.put("hye", "ひぇ");
        table.put("hyo", "ひょ");
        table.put("hhya", "っひゃ");
        table.put("hhyi", "っひぃ");
        table.put("hhyu", "っひゅ");
        table.put("hhye", "っひぇ");
        table.put("hhyo", "っひょ");
        table.put("mya", "みゃ");
        table.put("myi", "みぃ");
        table.put("myu", "みゅ");
        table.put("mye", "みぇ");
        table.put("myo", "みょ");
        table.put("mmya", "っみゃ");
        table.put("mmyi", "っみぃ");
        table.put("mmyu", "っみゅ");
        table.put("mmye", "っみぇ");
        table.put("mmyo", "っみょ");
        table.put("rya", "りゃ");
        table.put("ryi", "りぃ");
        table.put("ryu", "りゅ");
        table.put("rye", "りぇ");
        table.put("ryo", "りょ");
        table.put("rrya", "っりゃ");
        table.put("rryi", "っりぃ");
        table.put("rryu", "っりゅ");
        table.put("rrye", "っりぇ");
        table.put("rryo", "っりょ");
        table.put("ja", "じゃ");
        table.put("jya", "じゃ");
        table.put("jyi", "じぃ");
        table.put("ju", "じゅ");
        table.put("jyu", "じゅ");
        table.put("je", "じぇ");
        table.put("jye", "じぇ");
        table.put("jo", "じょ");
        table.put("jyo", "じょ");
        table.put("jja", "っじゃ");
        table.put("jjya", "っじゃ");
        table.put("jjyi", "っじぃ");
        table.put("jju", "っじゅ");
        table.put("jjyu", "っじゅ");
        table.put("jje", "っじぇ");
        table.put("jjye", "っじぇ");
        table.put("jjo", "っじょ");
        table.put("jjyo", "っじょ");
        table.put("gya", "ぎゃ");
        table.put("gyi", "ぎぃ");
        table.put("gyu", "ぎゅ");
        table.put("gye", "ぎぇ");
        table.put("gyo", "ぎょ");
        table.put("ggya", "っぎゃ");
        table.put("ggyi", "っぎぃ");
        table.put("ggyu", "っぎゅ");
        table.put("ggye", "っぎぇ");
        table.put("ggyo", "っぎょ");
        table.put("dya", "ぢゃ");
        table.put("dyi", "ぢぃ");
        table.put("dyu", "ぢゅ");
        table.put("dye", "ぢぇ");
        table.put("dyo", "ぢょ");
        table.put("ddya", "っぢゃ");
        table.put("ddyi", "っぢぃ");
        table.put("ddyu", "っぢゅ");
        table.put("ddye", "っぢぇ");
        table.put("ddyo", "っぢょ");
        table.put("bya", "びゃ");
        table.put("byi", "びぃ");
        table.put("byu", "びゅ");
        table.put("bye", "びぇ");
        table.put("byo", "びょ");
        table.put("bbya", "っびゃ");
        table.put("bbyi", "っびぃ");
        table.put("bbyu", "っびゅ");
        table.put("bbye", "っびぇ");
        table.put("bbyo", "っびょ");
        table.put("pya", "ぴゃ");
        table.put("pyi", "ぴぃ");
        table.put("pyu", "ぴゅ");
        table.put("pye", "ぴぇ");
        table.put("pyo", "ぴょ");
        table.put("ppya", "っぴゃ");
        table.put("ppyi", "っぴぃ");
        table.put("ppyu", "っぴゅ");
        table.put("ppye", "っぴぇ");
        table.put("ppyo", "っぴょ");
        table.put("va", "ヴぁ");
        table.put("vi", "ヴぃ");
        table.put("vu", "ヴ");
        table.put("ve", "ヴぇ");
        table.put("vo", "ヴぉ");
        table.put("vva", "っヴぁ");
        table.put("vvi", "っヴぃ");
        table.put("vvu", "っヴ");
        table.put("vve", "っヴぇ");
        table.put("vvo", "っヴょ");
        table.put("la", "あ");
        table.put("li", "ぃ");
        table.put("lu", "ぅ");
        table.put("le", "ぇ");
        table.put("lo", "ぉ");
        table.put("-", "ー");
        filterTable.put("殺", "〇");
        filterTable.put("ころす", "〇〇す");
        filterTable.put("死", "〇");
        filterTable.put("しね", "〇ね");

        table = table.entrySet()
                .stream()
                .sorted((e1, e2) -> Integer.compare(e2.getKey().length(), e1.getKey().length()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    // 単純にローマ字 -> かな をするだけ (判定は Main に任せる)
    public static String romajikarakana(String input) {
        String result = input;
        for (Map.Entry<String, String> entry : table.entrySet()) {
            result = result.replace(entry.getKey(), entry.getValue());
        }
        return result;
    }

    // 単純にフィルターをかけるだけ
    public static String applyFilters(String text) {
        String out = text;
        for (Map.Entry<String, String> entry : filterTable.entrySet()) {
            out = out.replace(entry.getKey(), entry.getValue());
        }
        return out;
    }

    public static boolean containsJapanese(String text) {
        return text.codePoints().anyMatch(cp ->
                (cp >= 0x3040 && cp <= 0x309F) || // ひらがな
                        (cp >= 0x30A0 && cp <= 0x30FF) || // カタカナ
                        (cp >= 0x4E00 && cp <= 0x9FFF)    // 漢字
        );
    }

    public static boolean containsUpperCase(String s) {
        return s.chars().anyMatch(Character::isUpperCase);
    }
}