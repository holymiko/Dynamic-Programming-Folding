package com.algoData;

import org.junit.jupiter.api.Assertions;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class FoldingTest {

    String read(String filename) throws IOException {
        return new BufferedReader(new FileReader(filename)).readLine();
    }


    @org.junit.jupiter.api.Test
    void test1() {
        Assertions.assertEquals(new Folding("MVMVMVMVMVMVMVMV").run(), 16);
    }

    @org.junit.jupiter.api.Test
    void testAll() throws IOException {
        for (int i = 1; i <= 41; i++) {
            Assertions.assertEquals(
                    String.valueOf(new Folding(read("test/"+i+".in")).run()),
                    read("test/"+i+".out")
            );
        }
    }
}