package com.algoData;

import java.util.*;

/**
 * Left side stays, right side is in the move.
 * Right side is folded on left.
 */
public class Folding {

    // Works with indexes.
    // Size remains same, elements are not deleted.
    private final List<Fold> foldList;
    private final int[][] memory;

    public Folding(String input) {
        foldList = new ArrayList<>();
        memory = new int[input.length()][];

        for(int i = 0; i < input.length(); i++) {
            foldList.add( input.charAt(i) == 'M' ? Fold.MOUNTAIN : Fold.VALLEY );
            memory[i] = new int[input.length() - i];

            Arrays.fill(memory[i], 0);
        }
    }

    /**
     * Matches opposite FOLD values in foldVector.
     * Indexes of matching are moving towards each other.
     * If the indexes meet, all Folds fit and sides get be folded on each other.
     *
     * Indexes are moving towards each other to have a symmetry of the fold
     *
     *          ->.......<-    .->.....<-.    ..->...<-..    ...->.<-...
     *
     * Movement of indexes
     *
     *          .....v.....    .....v.....    .....v.....    .....v.....    .....v.....
     *          ^         ^     ^       ^       ^     ^         ^   ^           ^ ^
     *
     * @param leftIndex .
     * @param rightIndex .
     * @throws IllegalArgumentException - When sum of arguments is ODD
     * @return True - Fold with given positions can be performed
     */
    private boolean isFoldPossible(int leftIndex, int rightIndex) throws IllegalArgumentException {
        if(leftIndex+rightIndex % 2 == 1) {
            throw new IllegalArgumentException("Range has to have ODD SIZE");
        }

        while(leftIndex + 1 < rightIndex) {
            // For successful fold, fold types on both indexes has to be opposite value
            if( foldList.get(leftIndex) == foldList.get(rightIndex) ){
                return false;
            }
            leftIndex++;
            rightIndex--;
        }
        return true;
    }

    /**
     * Performs 1 fold.
     * Tries to perform biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private int foldMethod(final int firstIndex, final int lastIndex) {

        // BASE CASES
        //      1 Fold
        if(lastIndex - firstIndex == 0) {
            return 1;
        }
        //      2 Folds
        if(lastIndex - firstIndex == 1) {
            return 2;
        }

        // Look into memory, Result might be already computed
        if( memory[firstIndex][lastIndex-firstIndex] != 0 ) {
            return memory[firstIndex][lastIndex-firstIndex];
        }

        int maxRFold = maxRightFold(lastIndex, getMaxFoldIndexOfSide(firstIndex, lastIndex,true));
        int maxLFold = maxLeftFold(firstIndex, getMaxFoldIndexOfSide(firstIndex, lastIndex, false));
        int leftFoldResult = foldMethod(firstIndex + maxLFold, lastIndex);
        int rightFoldResult = foldMethod(firstIndex, lastIndex - maxRFold);

        int result = Math.min(leftFoldResult, rightFoldResult) + 1;

//        IOPut.printListIndex2(foldList, firstIndex, lastIndex, maxLeftFoldIndex, maxRightFoldIndex);

        // Save result
        memory[firstIndex][lastIndex-firstIndex] = result;

        return result;
    }

    /**
     * Finds size of biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private int maxRightFold(int lastIndex, int maxRightFoldIndex) {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = lastIndex - maxRightFoldIndex + 1;
        int leftIndex = maxRightFoldIndex - foldSize + 1;

        while( 0 < foldSize ) {
            if( isFoldPossible(leftIndex, lastIndex) ) {
                return foldSize;
            }
            // Move index to the right
            leftIndex += 2;
            foldSize--;
        }
        return 0;
    }

    private int maxLeftFold(int firstIndex, int maxLeftFoldIndex) {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = maxLeftFoldIndex - firstIndex + 1;
        int rightIndex = maxLeftFoldIndex + foldSize - 1;

        while( 0 < foldSize ) {
            // TODO Look into the memory
            if( isFoldPossible(firstIndex, rightIndex) ) {
                return foldSize;
            }
            // Move index to the left
            rightIndex -= 2;
            foldSize--;
        }
        return 0;
    }

    public void run() {
        int foldCount = foldMethod(0, foldList.size() - 1);
        System.out.println(foldCount);
    }

    /**
     * @param firstIndex
     * @param lastIndex
     * @param isRightSide
     * @return
     *
     *         isRightSide == true
     *         _ _ _ _ _     _ _ _ _
     *             ^             ^
     *
     *         isRightSide == false
     *         _ _ _ _ _     _ _ _ _
     *           ^             ^
     *
     */
    public static int getMaxFoldIndexOfSide(int firstIndex, int lastIndex, boolean isRightSide) {
        int size = lastIndex - firstIndex + 1;
        return firstIndex + ((isRightSide ? size : size - 2) / 2);
    }

}
