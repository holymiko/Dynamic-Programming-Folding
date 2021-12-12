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

    public static void printVector(List<Fold> foldVector) {
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
