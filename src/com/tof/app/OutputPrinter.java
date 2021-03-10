package com.tof.app;

import java.io.PrintStream;

public class OutputPrinter {

    private final PrintStream printStream;

    public OutputPrinter(PrintStream printStream) {
        this.printStream = printStream;
    }

    public void format(String format, Object ... args) {
        printStream.format(format, args);
    }

    public void println(String x) {
        printStream.println(x);
    }

    public void print(char x) {
        printStream.print(x);
    }

    public void print(String x) {
        printStream.print(x);
    }
}
