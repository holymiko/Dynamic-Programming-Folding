package com.algoData;

import java.util.*;

/**
 * Left side stays, right side is in the move.
 * Right side is folded on left.
 */
public class BuildBridge {


    private int foldCount;
    private final List<Fold> foldVector;

    public BuildBridge() {
        foldCount = 0;
        foldVector = new Vector<>();
    }

    private void initFoldVector(String input) {
        for(int i = 0; i<input.length(); i++) {
            if( input.charAt(i) == 'M' ) {
                foldVector.add(Fold.MOUNTAIN);
            } else {
                foldVector.add(Fold.VALLEY);
            }
        }
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

    private void performFold(int foldSize) {
        // TODO Perform fold
//                foldVector = foldVector.subList(0, foldVector.size() - 1 - foldSize*2);

        // TODO Is subList more efficient?
        // Deleting foldSize + 1 // +1 because of the place where fold will be performed
        for (int i = 0; i < foldSize; i++) {
            foldVector.remove( foldVector.size() - 1 );
        }
    }

    /**
     * Performs 1 fold.
     * Tries to perform biggest currently possible fold.
     * Goes from MAX size of fold to size of 1
     */
    private void foldMethod() {
        final int afterLastIndex = foldVector.size();

        // Init index
        int middleIndex = getMiddleIndex();
        // Number of edges (folds) which will reduce foldVector
        int foldSize = afterLastIndex - middleIndex;

        while( 0 < foldSize ) {

            final int leftIndex = middleIndex - foldSize + 1;
            final int rightIndex = foldVector.size() - 1;

            if( isFoldPossible(leftIndex, rightIndex) ) {
                System.out.println("L "+leftIndex+"  R "+rightIndex);
                System.out.print("Fold size "+foldSize);
                System.out.println( "  Index: "+middleIndex );
                System.out.println();

                performFold(foldSize);
                break;
            }

            // Move index to the right
            middleIndex++;
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
    private int getMiddleIndex() {
        return foldVector.size() / 2;
    }

    private int findMaxFold(int leftIndex, int rightIndex) {
        int max = 0;
        return max;
    }

    public void run() {
        // Load data
        String stringStrip = readInput();
        initFoldVector( stringStrip );
//        System.out.println( stringStrip );
        printVector();



        while (foldVector.size() > 0) {
            findMaxFold(0, foldVector.size()-1);
            foldMethod();
            foldCount++;
//            break;
            printVector();
        }



        System.out.println("Total count == "+foldCount);
    }


    /**
     * Reads the input from a .txt file
     */
    private String readInput() {
        Scanner s = new Scanner(System.in);
        String d = s.nextLine().trim().toUpperCase(Locale.ROOT);

        s.close();
        return d;
    }

    private void printVector() {
        for (Fold fold:foldVector) {
            if(fold.equals(Fold.MOUNTAIN)) {
                System.out.print('M');
            } else {
                System.out.print('V');
            }
        }
        System.out.println();
    }

}


