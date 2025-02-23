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
        Assertions.assertEquals(new Folding("MVMVMVMVMVMVMVMV").run(), 16);
    }

    @Test
    void testAll() throws IOException {
        for (int i = 1; i <= 41; i++) {
            Assertions.assertEquals(
                    String.valueOf(new Folding(readInput(TEST_DIR+i+TEST_INPUT_FILE_SUFFIX)).run()),
                    readInput(TEST_DIR+i+TEST_OUTPUT_FILE_SUFFIX)
            );
        }
    }
}