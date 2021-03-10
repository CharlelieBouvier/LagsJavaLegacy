package com.tof.app;

import java.io.InputStream;
import java.io.PrintStream;

public class Lags {
    public static String PATH_TO_ORDER = "ORDRES.CSV";
    static final boolean DEBUG = false;
    public static PrintStream printStream = System.out;
    public static InputStream inputStream = System.in;

    public static void main(String[] args) {
        OutputPrinter printer = new OutputPrinter(printStream);
        InputReader reader = new InputReader(inputStream);
        LagsService lagsService = new LagsService(printer, reader);
        if (null != args && args.length > 0) {
            PATH_TO_ORDER = args[0];
        }
        lagsService.loadOrdersFromFile(PATH_TO_ORDER);
        boolean exitProgram = false;

        while (!exitProgram) {

            char input = 'Z';
            while (input != 'A' && input != 'L' && input != 'S' && input != 'Q' && input != 'C') {

                printer.println("A)JOUTER UN ORDRE  L)ISTER   C)ALCULER CA  S)UPPRIMER  Q)UITTER");
                try {
                    char keyInfo = (char) reader.read();
                    input = Character.toUpperCase(keyInfo);
                    printer.print(input);
                } catch (java.io.IOException e) {
                    printer.print("IO Exception");
                }
                switch (input) {
                    case 'Q': {
                        exitProgram = true;
                        break;
                    }
                    case 'L': {
                        lagsService.listOrders();
                        break;
                    }
                    case 'A': {
                        lagsService.addOrder();
                        break;
                    }
                    case 'S': {
                        lagsService.deleteOrder();
                        break;
                    }
                    case 'C': {
                        lagsService.computeRevenue(DEBUG);
                        break;
                    }
                }

            }
        }
    }
}