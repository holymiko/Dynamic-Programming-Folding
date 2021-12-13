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

        if( oddSizeFold() ) {
            return;
        }

        int leftFoldIndex = Indexes.getMaxFoldIndexOfSide(indexes.getFirstIndex(), indexes.getLastIndex(), false);
        int rightFoldIndex = Indexes.getMaxFoldIndexOfSide(indexes.getFirstIndex(), indexes.getLastIndex(), true);

        IOPut.printVector(foldVector, indexes.getFirstIndex(), indexes.getLastIndex());
        System.out.println();
        System.out.println(">> leftFoldIndex == "+leftFoldIndex);
        System.out.println(">> rightFoldIndex == "+rightFoldIndex);

        performRightFold( maxRightFold(rightFoldIndex) );
        maxLeftFold(leftFoldIndex);
        System.out.println();
    }

    /**
     * Finds size of biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private int maxRightFold(int middleIndex) {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = indexes.getLastIndex() - middleIndex + 1;
        int leftIndex = middleIndex - foldSize + 1;

        while( 0 < foldSize ) {
            if( isFoldPossible(leftIndex, indexes.getLastIndex()) ) {
//                System.out.println("L "+leftIndex+"  R "+indexes.getLastIndex()+"  Index: "+middleIndex);
                System.out.println( "maxRightFold(): "+foldSize);
                return foldSize;
            }
            // Move index to the right
            middleIndex++;
            leftIndex += 2;

            foldSize--;
        }
        return 0;
    }

    // TODO Test
    private int maxLeftFold(int middleIndex) {
        // Number of edges (folds) which will reduce foldVector
        int foldSize = middleIndex + 1;
        int rightIndex = middleIndex * 2;

        while( 0 < foldSize ) {
            if( isFoldPossible(indexes.getFirstIndex(), rightIndex) ) {
//                System.out.println("L "+indexes.getFirstIndex()+"  R "+rightIndex+"  Index: "+middleIndex);
                System.out.println( "maxLeftFold(): "+foldSize);
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


