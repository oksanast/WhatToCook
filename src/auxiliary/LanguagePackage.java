package auxiliary;

import auxiliary.Dictionary;

/**
 * Created by Mateusz on 05.06.2016.
 * Project WhatToCook
 */
public class LanguagePackage {

    public static String getWord(String word) {
        String toReturn = Dictionary.translate(word,language);
        return toReturn;
    }

    public static String language = "Polski";
}
