package de.holisticon.vorsprechen.niilz.nimgame.common;

public abstract class MoveUtils {

    /**
     * @param remainingMatches The amount of remaining matches
     * @param maxMatchCountToDraw Hom many matches can be drawn most
     * @return the best possible choice for the given conditions
     *
     * This method has only been tested for remaining-matches bewteen 13 and 1,
     * for a max-count-to-draw of 3
     */
    public static int makeSmartChoice(int remainingMatches, int maxMatchCountToDraw) {
        // Example for Max count to draw of 3:
        // if 5 are remaining there is no good choice
        // (because next can always enforce that only 1 match remains)
        // so just take 1
        // if 9 are remaining there is no good choice
        // (because next can always enforce 5)
        // if 13 are remaining there is no good coice
        // (because next can always enforce 9)
        //return remainingMatches % (maxMatchCountToDraw + 1);
        switch (remainingMatches % (maxMatchCountToDraw + 1)) {
            // 13, 9, 5, 1 all match 1 don't offer good choices
            case 1:
                return 1;
            // 12, 8, 4 make it possible to enforce 9, 5, 1 by drawing 3
            case 0:
                return 3;
            // 11, 7, 3 make it possible to enforce 9, 5, 1 by drawing 2
            case 3:
                return 2;
            // 10, 6, 2 make it possible to enforce 9, 5, 1 by drawing 1
            case 2:
                return 1;
            default:
                throw new IllegalArgumentException("No smart choice could be done by the given arguments");
        }
    }
}
