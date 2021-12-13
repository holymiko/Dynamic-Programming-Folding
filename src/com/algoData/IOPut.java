package com.algoData;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Input / Output class handles input from terminal and provides visualizations
 */
public class IOPut {

    /**
     * Reads the input from a .txt file
     */
    public static String readInput() {
        Scanner s = new Scanner(System.in);
        String d = s.nextLine().trim().toUpperCase(Locale.ROOT);

        s.close();
        return d;
    }


    /**
     *
     *          VVMMMVMVVVMVMMMVV
     *          ^      ^ ^      ^
     *
     *          VVMMMVMVVVMVMM___
     *          ^     ^^     ^
     *
     * @param foldVector
     * @param firstIndex
     * @param lastIndex
     */
    public static void printVector(List<Fold> foldVector, int firstIndex, int lastIndex) {
        int left = Indexes.getMaxFoldIndexOfSide(firstIndex, lastIndex, false);
        int right = Indexes.getMaxFoldIndexOfSide(firstIndex, lastIndex, true);

        for (int i = 0; i < firstIndex; i++) {
            System.out.print('_');
        }
        for (int i = firstIndex; i <= lastIndex; i++) {
            System.out.print( foldVector.get(i).equals(Fold.MOUNTAIN) ? 'M' : 'V' );
        }
        for (int i = lastIndex; i < foldVector.size() - 1; i++) {
            System.out.print('_');
        }
        System.out.println();
        for (int i = 0; i < foldVector.size(); i++) {
            System.out.print( i == left || i == right /*|| i == firstIndex || i == lastIndex*/  ? "^" : ' ' );
        }
    }

    public static void printListIndex2(List<Fold> foldVector, int firstIndex, int lastIndex, int leftFoldIndex, int rightFoldIndex) {
        IOPut.printVector(foldVector, firstIndex, lastIndex);
        System.out.println();
        System.out.println(">> leftFoldIndex == "+leftFoldIndex);
        System.out.println(">> rightFoldIndex == "+rightFoldIndex);
    }

}
