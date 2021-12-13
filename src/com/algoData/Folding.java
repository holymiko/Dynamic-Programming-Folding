package com.algoData;

import java.util.*;

/**
 * Left side stays, right side is in the move.
 * Right side is folded on left.
 */
public class Folding {

    private Indexes indexes;

    // Works with indexes.
    // Size remains same, elements are not deleted.
    private final List<Fold> foldVector;
    private int foldCount;

    public Folding() {
        foldCount = 0;
        foldVector = new Vector<>();
    }

    private void initFoldVector(String input) {
        for(int i = 0; i < input.length(); i++) {
            foldVector.add( input.charAt(i) == 'M' ? Fold.MOUNTAIN : Fold.VALLEY );
        }
        indexes = new Indexes(foldVector.size() - 1);
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
            if( foldVector.get(leftIndex) == foldVector.get(rightIndex) ){
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
    private void foldMethod() {

        int currentSize = indexes.getLastIndex() - indexes.getFirstIndex() + 1;

        // ODD size - edge in the middle
        if(currentSize % 2 == 1) {
            System.out.println("ODD size = "+currentSize);
            if( isFoldPossible(indexes.getFirstIndex(), indexes.getLastIndex()) ) {
                int foldSize = (currentSize+1) / 2;
                System.out.println("ODD FOLD");
                System.out.println("Fold size "+foldSize);
                System.out.println();

                performRightFold( foldSize );
                return;
            }
        } else {
            System.out.println("EVEN size = "+currentSize);
        }

        IOPut.printVector(foldVector, indexes.getFirstIndex(), indexes.getLastIndex());


        // Init index // TODO WARNING indexes.getLastIndex() - indexes.getFirstIndex() ??
        int middleIndex = Indexes.getMiddleIndex(indexes.getLastIndex());
        int middleIndex2 = Indexes.getMiddleIndex(indexes.getFirstIndex(), indexes.getLastIndex(), true);

        System.out.println();
        System.out.println(">> middleIndex == "+middleIndex);
        System.out.println(">> middleIndex2 == "+middleIndex2);

        // Number of edges (folds) which will reduce foldVector
        int foldSize = indexes.getLastIndex() - middleIndex + 1;
        int leftIndex = middleIndex - foldSize + 1;
        final int rightIndex = indexes.getLastIndex();

//        System.out.println("Origin M: "+middleIndex);

        while( 0 < foldSize ) {

            if( isFoldPossible(leftIndex, rightIndex) ) {
                System.out.println("L "+leftIndex+"  R "+rightIndex);
                System.out.print("Fold size "+foldSize);
                System.out.println( "  Index: "+middleIndex );
                System.out.println();

                performRightFold(foldSize);
                break;
            }

            // Move index to the right
            middleIndex++;
            leftIndex += 2;

            foldSize--;
        }
    }

    /**
     * Performs 1 fold.
     * Tries to perform biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private int foldRightMethod() {

        // Init index // TODO WARNING indexes.getLastIndex() - indexes.getFirstIndex() ??
        int middleIndex = Indexes.getMiddleIndex(indexes.getLastIndex());

        // Number of edges (folds) which will reduce foldVector
        int foldSize = indexes.getLastIndex() - middleIndex + 1;
        int leftIndex = middleIndex - foldSize + 1;

        while( 0 < foldSize ) {

            if( isFoldPossible(leftIndex, indexes.getLastIndex()) ) {
                return foldSize;
            }

            // Move index to the right
            middleIndex++;
            leftIndex += 2;

            foldSize--;
        }
        return 0;
    }

    private int findLeftMaxFold() {
        int rightIndex = Indexes.getMiddleIndex(indexes.getLastIndex()) - 1;

        // Keep number EVEN
        rightIndex = ( rightIndex % 2 == 1 ? rightIndex - 1 : rightIndex );
        System.out.println("R2 "+rightIndex);

        int middleIndex = Indexes.getMiddleIndex(rightIndex);

        // Number of edges (folds) which will reduce foldVector
        int foldSize = middleIndex + 1;

        while( 0 < foldSize ) {

            if( isFoldPossible(indexes.getFirstIndex(), rightIndex) ) {
                return foldSize;
            }

            // Move index to the right
            middleIndex--;
            rightIndex -= 2;

            foldSize--;
        }
        return 0;
    }

    public void run() {
        String stringStrip = IOPut.readInput();
        initFoldVector( stringStrip );

        while (indexes.getLastIndex() >= 0) {
//            System.out.println( "findLeftMaxFold: " + findLeftMaxFold() );
            foldMethod();
            foldCount++;
//            break;
        }
        IOPut.printVector(foldVector, indexes.getFirstIndex(), indexes.getLastIndex());


        System.out.println("\nTotal count == "+foldCount);
    }




    public void performLeftFold(int foldSize) {
        indexes.increaseFirstIndex(foldSize);
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
    }
}


