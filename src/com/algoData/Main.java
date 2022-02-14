package com.algoData;
import java.lang.*;


public class Main {
    /**
     * Reads the input and runs a new Folding object.
     * @param args
     */
    public static void main(String[] args) {
        String input = IOPut.readInput();
        new Folding(input).run();
    }
}
