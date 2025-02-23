package com.radboud;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    private static final String SRC_DIR = "input/";
    private static final String INPUT_FILE_PREFIX = "input";
    private static final String INPUT_FILE_SUFFIX = ".txt";


    public static String readInput(String filename) throws IOException {
        return new BufferedReader(new FileReader(filename)).readLine();
    }

    /**
     * Reads the input and runs a new Folding object.
     */
    public static void main(String[] args) throws IOException {
        for (int i = 1; i <= 5; i++) {
            String filename = SRC_DIR + INPUT_FILE_PREFIX + i + INPUT_FILE_SUFFIX;
            String input = readInput(filename);
            new Folding(input).run();
        }
    }
}
