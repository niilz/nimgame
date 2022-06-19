package de.holisticon.vorsprechen.niilz.nimgame.common;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;

public abstract class SmartChoiceMap {

    private static final HashMap<Integer, Integer> smartChoiceForRemaining = new HashMap<>();
    static {
        smartChoiceForRemaining.put(2, 1);
        smartChoiceForRemaining.put(3, 2);
        smartChoiceForRemaining.put(4, 3);
        smartChoiceForRemaining.put(6, 1);
        smartChoiceForRemaining.put(7, 2);
        smartChoiceForRemaining.put(8, 1);
        smartChoiceForRemaining.put(9, 1);
        smartChoiceForRemaining.put(10, 1);
        smartChoiceForRemaining.put(11, 1);
        smartChoiceForRemaining.put(13, 1);
    }

    public static int getSmarchChoiceForRemaining(@Max(13) @Min(1) int remainingMatches) {
        return smartChoiceForRemaining.get(remainingMatches);
    }
}
