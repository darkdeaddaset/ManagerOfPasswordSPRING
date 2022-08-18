package com.savin.mop.service;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GeneratePassword {
    private final String SMALL_WORD = "abcdefghijklmnopqrstuvwxyz";
    private final String BIG_WORD = SMALL_WORD.toUpperCase();
    private final String NUMBERS = "0123456789";
    private final String SYMBOLS = "!@#$%^&*()_+№;:?-=";

    public String generatePassword(int range, boolean statusSymbols, boolean statusNumber){
        Random random = new Random();
        StringBuilder result = new StringBuilder(range);
        String temp = SMALL_WORD + BIG_WORD;

        if (statusNumber){
            temp += NUMBERS;
        }
        if (statusSymbols){
            temp += SYMBOLS;
        }

        for (int i = 0; i<range; i++){
            result.append(temp.charAt(random.nextInt(temp.length())));
        }
        return result.toString();
    }
}
