package com.bingbaihanji.utils.code;

import java.util.HashMap;
import java.util.Map;

public class MorseCode {

    private static final Map<Character, String> CHAR_TO_CODE = new HashMap<>();

    private static final Map<String, Character> CODE_TO_CHAR = new HashMap<>();


    static {
        CHAR_TO_CODE.put('A', ".-");
        CHAR_TO_CODE.put('B', "-...");
        CHAR_TO_CODE.put('C', "-.-.");
        CHAR_TO_CODE.put('D', "-..");
        CHAR_TO_CODE.put('E', ".");
        CHAR_TO_CODE.put('F', "..-.");
        CHAR_TO_CODE.put('G', "--.");
        CHAR_TO_CODE.put('H', "....");
        CHAR_TO_CODE.put('I', "..");
        CHAR_TO_CODE.put('J', ".---");
        CHAR_TO_CODE.put('K', "-.-");
        CHAR_TO_CODE.put('L', ".-..");
        CHAR_TO_CODE.put('M', "--");
        CHAR_TO_CODE.put('N', "-.");
        CHAR_TO_CODE.put('O', "---");
        CHAR_TO_CODE.put('P', ".--.");
        CHAR_TO_CODE.put('Q', "--.-");
        CHAR_TO_CODE.put('R', ".-.");
        CHAR_TO_CODE.put('S', "...");
        CHAR_TO_CODE.put('T', "-");
        CHAR_TO_CODE.put('U', "..-");
        CHAR_TO_CODE.put('V', "...-");
        CHAR_TO_CODE.put('W', ".--");
        CHAR_TO_CODE.put('X', "-..-");
        CHAR_TO_CODE.put('Y', "-.--");
        CHAR_TO_CODE.put('Z', "--..");

        for (Map.Entry<Character, String> entry : CHAR_TO_CODE.entrySet()) {
            CODE_TO_CHAR.put(entry.getValue(), entry.getKey());
        }
    }

    /**
     * @param message
     * @return String
     * @Description 加密
     */
    public static String encode(String message) {
        StringBuilder stringBuilder = new StringBuilder();
        for (char chars : message.toUpperCase().toCharArray()) {
            if (CHAR_TO_CODE.containsKey(chars)) {
                stringBuilder.append(CHAR_TO_CODE.get(chars)).append(" ");
            } else {
                stringBuilder.append(chars);
            }
        }
        return stringBuilder.toString().trim();
    }

    /**
     * @param morseCode
     * @return
     * @Description 解密
     */
    public static String decode(String morseCode) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String code : morseCode.split(" ")) {
            if (CODE_TO_CHAR.containsKey(code)) {
                stringBuilder.append(CODE_TO_CHAR.get(code));
            } else {
                stringBuilder.append(code);
            }
        }
        return stringBuilder.toString();
    }
}