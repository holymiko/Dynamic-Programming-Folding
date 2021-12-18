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

    public Folding(String input) {
        // 0
        foldList = new ArrayList<>();
        // 1
        for(int i = 0; i < input.length(); i++) {
            foldList.add( input.charAt(i) == 'M' ? Fold.MOUNTAIN : Fold.VALLEY );
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
//        System.out.println("L "+leftIndex+"  R "+rightIndex);
        if(leftIndex+rightIndex % 2 == 1) {
            throw new IllegalArgumentException("Range has to have ODD SIZE");
        }

        while(leftIndex + 1 < rightIndex) {
//            System.out.println(">> L "+leftIndex+"  R "+rightIndex);
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
     *  ODD size - edge in the middle
     * @return
     */
    private boolean oddSizeFold(int firstIndex, int lastIndex) {
        int currentSize = lastIndex - firstIndex + 1;

        if(currentSize % 2 == 0) {
            System.out.println("EVEN size = "+currentSize);
            return false;
        }

        System.out.println("ODD size = "+currentSize);
        if( isFoldPossible(firstIndex, lastIndex) ) {
            int foldSize = (currentSize+1) / 2;
//            performRightFold( foldSize );
            System.out.println("ODD FOLD size "+foldSize+"\n");
            return true;
        }
        return false;
    }

    /**
     * Performs 1 fold.
     * Tries to perform biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private int foldMethod(final int firstIndex, final int lastIndex, int foldCount) {

        // TODO Move this below
        // Update values after fold
        int maxLeftFoldIndex = Indexes.getMaxFoldIndexOfSide(firstIndex, lastIndex, false);
        int maxRightFoldIndex = Indexes.getMaxFoldIndexOfSide(firstIndex, lastIndex,true);
        int maxRFold = maxRightFold(lastIndex, maxRightFoldIndex);
        int maxLFold = maxLeftFold(firstIndex, maxLeftFoldIndex);

        IOPut.printListIndex2(foldList, firstIndex, lastIndex, maxLeftFoldIndex, maxRightFoldIndex);

        // BASE CASES
        //      1 Fold
        if(lastIndex - firstIndex == 0) {
            return foldCount + 1;
        }
        //      2 Folds
        if(lastIndex - firstIndex == 1) {
            return foldCount + 2;
        }

        // TODO Look into memory, see if the result is already computed

//        System.out.println( "maxLeftFold(): "  + maxLFold);
//        System.out.println( "maxRightFold(): " + maxRFold);

        int leftFoldResult = foldMethod(firstIndex + maxLFold, lastIndex, foldCount + 1 );
        int rightFoldResult = foldMethod(firstIndex, lastIndex - maxRFold, foldCount + 1 );
        int result = Math.min(leftFoldResult, rightFoldResult);

        // TODO Save result
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
        int foldCount = foldMethod(0, foldList.size() - 1, 0);
        System.out.println(foldCount);
    }

}
