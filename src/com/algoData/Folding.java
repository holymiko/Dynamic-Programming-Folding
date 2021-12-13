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
    private final Indexes indexes;

    private int foldCount;
    private int maxRFold;
    private int maxLFold;
    private int leftFoldIndex;
    private int rightFoldIndex;

    public Folding(String input) {
        // 0
        foldCount = 0;
        foldList = new ArrayList<>();
        // 1
        for(int i = 0; i < input.length(); i++) {
            foldList.add( input.charAt(i) == 'M' ? Fold.MOUNTAIN : Fold.VALLEY );
        }
        // 2
        indexes = new Indexes(foldList.size() - 1);
        // 3
        rightFoldIndex = indexes.getMaxFoldIndexOfSide(true);
        leftFoldIndex = indexes.getMaxFoldIndexOfSide(false);
        // 4
        maxRFold = maxRightFold();
        maxLFold = maxLeftFold();
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
    private boolean oddSizeFold() {
        int currentSize = indexes.getLastIndex() - indexes.getFirstIndex() + 1;

        if(currentSize % 2 == 0) {
            System.out.println("EVEN size = "+currentSize);
            return false;
        }

        System.out.println("ODD size = "+currentSize);
        if( isFoldPossible(indexes.getFirstIndex(), indexes.getLastIndex()) ) {
            int foldSize = (currentSize+1) / 2;
            performRightFold( foldSize );
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
    private void foldMethod() {

        IOPut.printListIndex2(foldList, indexes.getFirstIndex(), indexes.getLastIndex(), leftFoldIndex, rightFoldIndex);

        if( oddSizeFold() ) {
            return;
        }

        System.out.println( "maxLeftFold(): "  + maxLFold);
        System.out.println( "maxRightFold(): " + maxRFold);
        System.out.println();

        if( maxLFold <= maxRFold ) {
            performRightFold( maxRFold );
        } else {
            performLeftFold( maxLFold );
        }
    }

    /**
     * Finds size of biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private int maxRightFold() {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = indexes.getLastIndex() - rightFoldIndex + 1;
        int leftIndex = rightFoldIndex - foldSize + 1;

        while( 0 < foldSize ) {
            if( isFoldPossible(leftIndex, indexes.getLastIndex()) ) {
                return foldSize;
            }
            // Move index to the right
            leftIndex += 2;
            foldSize--;
        }
        return 0;
    }

    private int maxLeftFold() {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = leftFoldIndex - indexes.getFirstIndex() + 1;
        int rightIndex = leftFoldIndex + foldSize - 1;

        while( 0 < foldSize ) {
            if( isFoldPossible(indexes.getFirstIndex(), rightIndex) ) {
                return foldSize;
            }
            // Move index to the left
            rightIndex -= 2;
            foldSize--;
        }
        return 0;
    }

    public void run() {
        while (indexes.getLastIndex() - indexes.getFirstIndex() >= 0) {
            foldMethod();
            foldCount++;
        }
        IOPut.printVector(foldList, indexes.getFirstIndex(), indexes.getLastIndex());
        System.out.println("\nTotal count == "+foldCount);
    }


    public void performRightFold(int foldSize) {
        // DON'T DELETE UNUSED - We want to use this in Documentation

        // 1) Creating subList - copying is TOO EXPENSIVE
//                foldVector = foldVector.subList(0, lastIndex - foldSize*2);

        // 2) Removing elements from Vector - still expensive
//        for (int i = 0; i < foldSize; i++) {
//            foldVector.remove( foldVector.size() - 1 );
//        }

        // 3) Using index
        indexes.decreaseLastIndex(foldSize);

        // Update values after fold
        leftFoldIndex = indexes.getMaxFoldIndexOfSide(false);
        rightFoldIndex = indexes.getMaxFoldIndexOfSide(true);
        maxRFold = maxRightFold();

        // Dynamic programming
        // Middle index of maxLFold > max leftFoldIndex => compute new maxLFold
        if( maxLFold > leftFoldIndex + 1 ) {
            System.out.println(">> Compute new L");
            maxLFold = maxLeftFold();
        }
    }

    public void performLeftFold(int foldSize) {
        indexes.increaseFirstIndex(foldSize);

        // Update values after fold
        leftFoldIndex = indexes.getMaxFoldIndexOfSide(false);
        rightFoldIndex = indexes.getMaxFoldIndexOfSide(true);
        maxLFold = maxLeftFold();

        // Dynamic programming
        if( (indexes.getLastIndex() - maxRFold + 1) <  rightFoldIndex ) {
            System.out.println(">> Compute new R");
            maxRFold = maxRightFold();
        }
    }
}
