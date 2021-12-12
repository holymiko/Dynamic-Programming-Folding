package com.algoData;

import java.util.*;

/**
 * Left side stays, right side is in the move.
 * Right side is folded on left.
 */
public class BuildBridge {


    private int foldCount;

    // Size remains same. Used elements are not deleted. Works with indexes.
    private final List<Fold> foldVector;

    private int firstIndex;
    private int lastIndex;

    public BuildBridge() {
        firstIndex = 0;
        lastIndex = 0;
        foldCount = 0;
        foldVector = new Vector<>();
    }

    private void initFoldVector(String input) {
        for(int i = 0; i < input.length(); i++) {
            if( input.charAt(i) == 'M' ) {
                foldVector.add(Fold.MOUNTAIN);
            } else {
                foldVector.add(Fold.VALLEY);
            }
        }
        lastIndex = foldVector.size() - 1;
    }


    /**
     * Matches opposite FOLD values in foldVector.
     * Indexes of matching are moving towards each other.
     * If the indexes meet, all Folds fit and sides get be folded on each other.
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

        // Indexes are moving towards each other to have a symmetry of the fold
        // ->.......<- // .->.....<-. // ..->...<-.. // ...->.<-... //
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

    private void performRightFold(int foldSize) {
        // DON'T DELETE UNUSED - We want to use this in Documentation

        // 1) Creating subList - copying is TOO EXPENSIVE
//                foldVector = foldVector.subList(0, lastIndex - foldSize*2);

        // 2) Removing elements from Vector - still expensive
//        for (int i = 0; i < foldSize; i++) {
//            foldVector.remove( foldVector.size() - 1 );
//        }

        // 3) Using index
        lastIndex -= foldSize;
    }

    private void performLeftFold(int foldSize) {
        firstIndex += foldSize;
    }

    /**
     * Performs 1 fold.
     * Tries to perform biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private void foldMethod() {

        // Init index // TODO WARNING lastIndex - firstIndex ??
        int middleIndex = getMiddleIndex(lastIndex);

        // Number of edges (folds) which will reduce foldVector
        int foldSize = lastIndex - middleIndex + 1;
        int leftIndex = middleIndex - foldSize + 1;
        final int rightIndex = lastIndex;

        System.out.println("Origin M: "+middleIndex);

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
     * Left side is stable. Right side is in the move.
     * We are folding right side on the left one
     * Left side >= Right side
     * @return For odd length, returns middle character
     *         For even length, returns middle + 1 character
     */
    private int getMiddleIndex(int endIndex) {
        return (endIndex + 1) / 2;
    }

    private int findLeftMaxFold() {
        int max = 0;

        int rightIndex = getMiddleIndex(lastIndex) - 1;

        // Keep number EVEN
        rightIndex = ( rightIndex % 2 == 1 ? rightIndex - 1 : rightIndex );
        System.out.println("R2 "+rightIndex);

        int middleIndex = getMiddleIndex(rightIndex);

        // Number of edges (folds) which will reduce foldVector
        int foldSize = middleIndex + 1;

        while( 0 < foldSize ) {

            if( isFoldPossible(0, rightIndex) ) {
                return foldSize;
            }

            // Move index to the right
            middleIndex--;
            rightIndex -= 2;

            foldSize--;
        }
        return max;
    }

    public void run() {
        // Load data
        String stringStrip = Utils.readInput();
        initFoldVector( stringStrip );
//        System.out.println( stringStrip );
        Utils.printVector(foldVector, firstIndex, lastIndex);



        while (lastIndex >= 0) {
            System.out.println( "findLeftMaxFold: " + findLeftMaxFold() );
            foldMethod();
            foldCount++;
//            break;
            Utils.printVector(foldVector, firstIndex, lastIndex);
        }



        System.out.println("Total count == "+foldCount);
    }


}


