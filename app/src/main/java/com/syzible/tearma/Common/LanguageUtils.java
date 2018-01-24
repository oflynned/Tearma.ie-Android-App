package com.syzible.tearma.Common;

import java.util.HashMap;

public class LanguageUtils {
    private static HashMap<String, String> lenitionRules;

    static {
        // b c d f g m p s t
        lenitionRules = new HashMap<>();
        lenitionRules.put("b", "bh");
        lenitionRules.put("c", "ch");
        lenitionRules.put("d", "dh");
        lenitionRules.put("f", "fh");
        lenitionRules.put("g", "gh");
        lenitionRules.put("m", "mh");
        lenitionRules.put("p", "ph");
        lenitionRules.put("s", "sh");
        lenitionRules.put("t", "th");

        lenitionRules.put("B", "Bh");
        lenitionRules.put("C", "Ch");
        lenitionRules.put("D", "Dh");
        lenitionRules.put("F", "Fh");
        lenitionRules.put("G", "Gh");
        lenitionRules.put("M", "Mh");
        lenitionRules.put("P", "Ph");
        lenitionRules.put("S", "Sh");
        lenitionRules.put("T", "Th");
    }

    private static HashMap<String, String> eclipsisRules;

    static {
        eclipsisRules = new HashMap<>();
        eclipsisRules.put("a", "n-a");
        eclipsisRules.put("A", "nA");
        eclipsisRules.put("e", "n-e");
        eclipsisRules.put("E", "nE");
        eclipsisRules.put("i", "n-i");
        eclipsisRules.put("I", "nI");
        eclipsisRules.put("o", "n-o");
        eclipsisRules.put("O", "nO");
        eclipsisRules.put("u", "n-u");
        eclipsisRules.put("U", "nU");

        eclipsisRules.put("á", "n-á");
        eclipsisRules.put("Á", "nÁ");
        eclipsisRules.put("é", "n-é");
        eclipsisRules.put("É", "nÉ");
        eclipsisRules.put("í", "n-í");
        eclipsisRules.put("Í", "nÍ");
        eclipsisRules.put("ó", "n-ó");
        eclipsisRules.put("Ó", "nÓ");
        eclipsisRules.put("ú", "n-ú");
        eclipsisRules.put("Ú", "nÚ");

        eclipsisRules.put("b", "mb");
        eclipsisRules.put("c", "gc");
        eclipsisRules.put("d", "nd");
        eclipsisRules.put("f", "bhf");
        eclipsisRules.put("g", "ng");
        eclipsisRules.put("p", "bp");
        eclipsisRules.put("t", "dt");

        eclipsisRules.put("B", "mB");
        eclipsisRules.put("C", "gC");
        eclipsisRules.put("D", "nD");
        eclipsisRules.put("F", "bhF");
        eclipsisRules.put("G", "nG");
        eclipsisRules.put("P", "bP");
        eclipsisRules.put("T", "dT");
    }

    private static String getMutation(HashMap<String, String> map, String input) {
        String initial = String.valueOf(input.charAt(0));
        if (!map.containsKey(initial))
            return input;

        String mutation = map.get(initial);
        String strippedInput = input.substring(1);
        return mutation + strippedInput;
    }

    public static String lenite(String input, boolean shouldCheckDentalDotsRule) {
        if (input.length() < 3)
            return input;

        if (input.charAt(1) == 'h')
            return input;

        // extra rules
        if (shouldCheckDentalDotsRule)
            if (input.charAt(0) == 'd' || input.charAt(0) == 'n' ||
                    input.charAt(0) == 't' || input.charAt(0) == 'l' || input.charAt(0) == 's')
                return input;

        return getMutation(lenitionRules, input);
    }

    public static String eclipse(String input) {
        return getMutation(eclipsisRules, input);
    }

    private static boolean isVowel(String letter) {
        return letter.toLowerCase().matches("[aeiouáéíóú]");
    }

    private static String palatalise(String input) {
        // Seán -> Seáin
        int wordLength = input.length();
        if (wordLength > 2)
            return input.substring(0, wordLength - 1) + "i" + input.substring(wordLength - 1, wordLength);
        return input;
    }

    public static String getPrepositionalForm(String preposition, String noun) {
        String firstLetter = String.valueOf(noun.charAt(0));

        switch (preposition.toLowerCase()) {
            case "le":
                return preposition + (isVowel(firstLetter) ? " h-" : " ") + noun;

            case "de":
            case "do":
                return isVowel(firstLetter) ? "d'" + noun : preposition + " " + lenite(noun, false);

            case "ar":
            case "faoi":
            case "idir":
            case "ionsar":
            case "ó":
            case "roimh":
            case "trí":
                return preposition + " " + lenite(noun, false);

            case "thar":
                switch (firstLetter.toLowerCase()) {
                    case "c":
                    case "d":
                    case "f":
                    case "g":
                    case "s":
                    case "t":
                        return preposition + " " + lenite(noun, false);
                }
                return preposition + " " + noun;

            case "i":
                return preposition + " " + eclipse(noun);
            default:
                break;
        }

        return preposition + " " + noun;
    }

    public static String getCountForm(int number, String noun) {
        // -1 bhád
        int count = Math.abs(number);

        // exception
        if (noun.toLowerCase().equals("bliain")) {
            if (count == 10) {
                String depalatalised = noun.substring(0, noun.length() - 2) +
                        noun.substring(noun.length() - 1) + "a";
                return eclipse(depalatalised);
            }

            if (count % 10 == 0)
                return noun;

            count %= 10;

            // 1 bhliain
            if (count > 0 && count < 3)
                return lenite(noun, false);
            // 3 bliana
            if (count > 2 && count < 7) {
                // bliain -> bliana
                return noun.substring(0, noun.length() - 2) + noun.substring(noun.length() - 1) + "a";
            }
            // 7 mbliana
            if (count > 6 && count < 10) {
                String depalatalised = noun.substring(0, noun.length() - 2) +
                        noun.substring(noun.length() - 1) + "a";
                return eclipse(depalatalised);
            }
        } else {
            if (count == 10)
                return eclipse(noun);

            // 20, 30, 40...
            if (count % 10 == 0)
                return noun;

            count %= 10;

            // 1 bhád, 2 bhád...
            if (count > 0 && count < 7)
                return lenite(noun, false);

            // 7 mbád, 8 mbád...
            if (count > 6 && count < 10)
                return eclipse(noun);
        }

        return noun;
    }

    public static String getVirileCountForm(int number, String noun) {
        int count = Math.abs(number) % 10;

        if (count > 0 && count < 3)
            return lenite(noun, false);

        return noun;
    }
}