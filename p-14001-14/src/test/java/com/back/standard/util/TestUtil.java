package com.back.standard.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.util.Scanner;

public class TestUtil {
    private static final PrintStream ORIGINAL_OUT = System.out;
    private static PrintStream CURRENT_OUT;
    public static Scanner getScanner(String input) {
        return new Scanner(input);
    }

    public static ByteArrayOutputStream setOutToByteArray() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CURRENT_OUT = new PrintStream(byteArrayOutputStream, true);
        System.setOut(CURRENT_OUT);

        return byteArrayOutputStream;
    }

    public static void clearSetOutToByteArray(ByteArrayOutputStream output) {
        System.setOut(ORIGINAL_OUT);
        CURRENT_OUT.close();
        CURRENT_OUT = null;
    }
}
