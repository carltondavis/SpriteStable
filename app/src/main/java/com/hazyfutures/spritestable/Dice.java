package com.hazyfutures.spritestable;

import java.util.Random;

/**
 * Created by cdavis on 1/17/2015.
 * Die roller using Shadowrun 5E rules.
 */
public class Dice {
    public Boolean isGlitch = false;
    public Boolean isCriticalGlitch = false;
    public String rawResult = "";

    public int rollDice(int Number, boolean Exploding) {
        return rollDice(Number, Exploding, -1);
    }
    public int rollDie(int Sides){
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(Sides) + 1;
    }
    public int rollDice(int Number, boolean Exploding, int Limit) {
        int hits = 0;
        int ones = 0;

        rawResult = "";
        isGlitch = false;
        isCriticalGlitch = false;

        Random randomGenerator = new Random();
        for (int idx = 1; idx <= Number; ++idx) {
            int randomInt = randomGenerator.nextInt(6) + 1;
            if (!rawResult.isEmpty()) {
                rawResult += ", ";
            }
                rawResult += String.valueOf(randomInt);
                if (randomInt == 1) {
                    ones++;
                }
                if (randomInt >= 5) {
                    hits++;
                    if (Exploding && randomInt == 6) {
                        --idx;
                    }
                }
        }

        isGlitch = (ones > (Number / 2));
        isCriticalGlitch = isGlitch && (hits == 0);
        if (Limit > 0 && hits > Limit && !Exploding) {
            hits = Limit;
        }
        return hits;

    }
}