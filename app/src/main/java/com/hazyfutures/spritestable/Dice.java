package com.hazyfutures.spritestable;

import java.util.Random;

/**
 * Created by cdavis on 1/17/2015.
 * Die roller using Shadowrun 5E rules.
 */
public class Dice {
    public int rollDice(int Number, boolean Exploding) {
        return rollDice(Number, Exploding, -1);
    }

    public int rollDice(int Number, boolean Exploding, int Limit) {
        int hits = 0;
        Random randomGenerator = new Random();
        for (int idx = 1; idx <= Number; ++idx) {
            int randomInt = randomGenerator.nextInt(6) + 1;
            if (randomInt >= 5) {
                hits++;
                if (Exploding && randomInt == 6) {
                    --idx;
                }
            }
        }
        if (Limit > 0 && hits > Limit) {
            hits = Limit;
        }
//TODO: Glitches
        return hits;
    }
}
