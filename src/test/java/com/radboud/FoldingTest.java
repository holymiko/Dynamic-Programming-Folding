package com.radboud;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static com.radboud.Main.readInput;

class FoldingTest {

    private static final String TEST_DIR = "test_data/";
    private static final String TEST_INPUT_FILE_SUFFIX = ".in";
    private static final String TEST_OUTPUT_FILE_SUFFIX = ".out";


    @Test
    void test1() {
        Assertions.assertEquals(new Folding("MVMVMVMVMVMVMVMV").computeMinFoldCount(), 16);
    }

    @Test
    void testAll() throws IOException {
        for (int i = 1; i <= 41; i++) {
            String inputFileName = TEST_DIR + i + TEST_INPUT_FILE_SUFFIX;
            String outputFileName = TEST_DIR + i + TEST_OUTPUT_FILE_SUFFIX;
            String input = readInput(inputFileName);
            String output = readInput(outputFileName);

            int minFoldCount = new Folding(input).computeMinFoldCount();

            Assertions.assertEquals(
                    String.valueOf(minFoldCount),
                    output
            );
            System.out.println(minFoldCount);
        }
    }
}