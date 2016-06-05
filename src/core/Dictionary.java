package core;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Mateusz on 05.06.2016.
 * Project WhatToCook
 */
public class Dictionary {

    public static void initialize() {
        Scanner listScanner = new Scanner(new InputStreamReader(Dictionary.class.getResourceAsStream("resources/languages/list")));
            while(listScanner.hasNextLine()) {
                languages.add(listScanner.nextLine());
                words.add(new ArrayList<>());
            }
        for(int i = 0; i < languages.size();i++) {
            Scanner wordsScanner = new Scanner(new InputStreamReader(Dictionary.class.getResourceAsStream("resources/languages/words/" + languages.get(i))));
            while (wordsScanner.hasNextLine()) {
                    while(wordsScanner.hasNextLine()) {
                        String line = wordsScanner.nextLine();
                        words.get(i).add(line);
                    }
            }
        }
    }

    public static String translate(String toTranslate,String language) {
        int languageIndex = -1;
        for( int i = 0; i < languages.size();i++) {
            if(language.equals(languages.get(i)))
                languageIndex = i;
        }
        if(languageIndex==-1)
            return "";
        int wordIndex = -1;
        for(int i = 0; i < words.get(0).size();i++) {
            if(words.get(0).get(i).equals(toTranslate)) {
                wordIndex = i;
            }
        }
        if(wordIndex==-1) {
            System.out.println("Nie znaleziono tłumaczenia dla: "+toTranslate);
            return "";
        }
        return words.get(languageIndex).get(wordIndex);
    }
    public static ArrayList<ArrayList<String>> words = new ArrayList<>();
    public static ArrayList<String> languages = new ArrayList<>();

}
