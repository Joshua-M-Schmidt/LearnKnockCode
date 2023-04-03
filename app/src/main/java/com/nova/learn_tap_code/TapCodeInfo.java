package com.nova.learn_tap_code;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class TapCodeInfo {
    public static Map<String, String> getMorseAphabet(){
        Map<String, String> alphabet = new HashMap<String, String>();
        alphabet.put("A", "· ·");
        alphabet.put("B", "· ··");
        alphabet.put("C", "· ···");
        alphabet.put("D", "· ····");
        alphabet.put("E", "· ·····");
        alphabet.put("F", "·· ·");
        alphabet.put("G", "·· ··");
        alphabet.put("H", "·· ···");
        alphabet.put("I", "·· ····");
        alphabet.put("J", "·· ·····");
        alphabet.put("K", "· ···");
        alphabet.put("L", "··· ·");
        alphabet.put("M", "··· ··");
        alphabet.put("N", "··· ···");
        alphabet.put("O", "··· ····");
        alphabet.put("P", "··· ·····");
        alphabet.put("Q", "···· ·");
        alphabet.put("R", "···· ··");
        alphabet.put("S", "···· ···");
        alphabet.put("T", "···· ····");
        alphabet.put("U", "···· ·····");
        alphabet.put("V", "····· ·");
        alphabet.put("W", "····· ··");
        alphabet.put("X", "····· ···");
        alphabet.put("Y", "····· ····");
        alphabet.put("Z", "····· ·····");


        return alphabet;
    }

    public static String letterToTapCode(String text){
        Map<String, String> alphabet = getMorseAphabet();
        String morse = "";

        for(int i = 0; i < text.length(); i++){
            if(i == 0){
                morse += alphabet.get(String.valueOf(text.charAt(i)));
            }else{
                morse += "/"+alphabet.get(String.valueOf(text.charAt(i)));
            }
        }
        Log.i("text_to_tap_code", text+" - "+morse);
        return morse;
    }
}
