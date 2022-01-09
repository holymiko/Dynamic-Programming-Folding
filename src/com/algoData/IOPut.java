package com.algoData;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * Handles Input/Output
 */
public class IOPut {

    /**
     * Reads the first line of a .txt file
     * @return contents of the first line of a .txt file as string
     */
    public static String readInput() {
        Scanner s = new Scanner(System.in);
        String d = s.nextLine().trim().toUpperCase(Locale.ROOT);
        s.close();
        return d;
    }

    public static void printCount(int foldCount) {
        System.out.println(foldCount);
    }
}
