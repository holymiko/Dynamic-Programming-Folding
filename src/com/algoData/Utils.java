package com.algoData;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Utils {

    /**
     * Reads the input from a .txt file
     */
    public static String readInput() {
        Scanner s = new Scanner(System.in);
        String d = s.nextLine().trim().toUpperCase(Locale.ROOT);

        s.close();
        return d;
    }

    public static void printVector(List<Fold> foldVector, int firstIndex, int lastIndex) {
        for (int i = 0; i < firstIndex; i++) {
            System.out.print('_');
        }
        for (int i = firstIndex; i <= lastIndex; i++) {
            if(foldVector.get(i).equals(Fold.MOUNTAIN)) {
                System.out.print('M');
            } else {
                System.out.print('V');
            }
        }
        for (int i = lastIndex; i < foldVector.size() - 1; i++) {
            System.out.print('_');
        }
        System.out.println();
    }
}
