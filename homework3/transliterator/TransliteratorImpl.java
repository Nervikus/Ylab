package transliterator;

import java.util.HashMap;
import java.util.Map;

public class TransliteratorImpl implements Transliterator {
    @Override
    public String transliterate(String source) {
        StringBuilder builder = new StringBuilder();
        for (char c : source.toCharArray()) {
            if (letters.containsKey(c)) {
                builder.append(letters.get(c));
            } else {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    private static Map<Character, String> letters = new HashMap<>() {{
        put('А', "A");
        put('Б', "B");
        put('В', "V");
        put('Г', "G");
        put('Д', "D");
        put('Е', "E");
        put('Ё', "E");
        put('Ж', "ZH");
        put('З', "Z");
        put('И', "I");
        put('Й', "I");
        put('К', "K");
        put('Л', "L");
        put('М', "M");
        put('Н', "N");
        put('О', "O");
        put('П', "P");
        put('Р', "R");
        put('С', "S");
        put('Т', "T");
        put('У', "U");
        put('Ф', "F");
        put('Х', "KH");
        put('Ц', "TS");
        put('Ч', "CH");
        put('Ш', "SH");
        put('Щ', "SHCH");
        put('Ы', "Y");
        put('Ь', "");
        put('Ъ', "IE");
        put('Э', "E");
        put('Ю', "IU");
        put('Я', "IA");
    }};
}
