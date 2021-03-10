package com.tof.app;

public class Lags {
    static final boolean DEBUG = false;

    public static void main(String[] args) {
        LagsService lagsService = new LagsService();
        lagsService.loadOrdersFromFile("ORDRES.CSV");
        boolean exitProgram = false;

        while (!exitProgram) {

            char input = 'Z';
            while (input != 'A' && input != 'L' && input != 'S' && input != 'Q' && input != 'C') {
                System.out.println("A)JOUTER UN ORDRE  L)ISTER   C)ALCULER CA  S)UPPRIMER  Q)UITTER");
                try {
                    char keyInfo = (char) System.in.read();
                    input = Character.toUpperCase(keyInfo);
                    System.out.print(input);
                } catch (java.io.IOException e) {
                    System.out.print("IO Exception");
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