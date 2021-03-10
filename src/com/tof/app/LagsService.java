package com.tof.app;

import java.time.Instant;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

import static java.util.Comparator.*;

class LagsService {
    private final DateHelper dateHelper = new DateHelper();
    private List<Order> orders = new ArrayList<>();
    private OutputPrinter printer;
    private InputReader reader;

    public LagsService(OutputPrinter printer, InputReader reader) {
        this.printer = printer;
        this.reader = reader;
    }

    public void loadOrdersFromFile(String fileName) {
        try {

            for (String line : Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8)) {
                String[] fields = line.split(";");
                String id = fields[0];
                int startDate = Integer.parseInt(fields[1]);
                int duration = Integer.parseInt(fields[2]);
                double price = Double.parseDouble(fields[3]);
                Order order = new Order(id, startDate, duration, price);
                orders.add(order);

            }
        } catch (IOException e) {
            printer.println("FICHIER ORDRES.CSV NON TROUVE. CREATION FICHIER.");
            saveOrders(fileName);
        }
    }

    void saveOrders(String fileName) {
        List<String> lines = new ArrayList<>();
        for (Order order : orders) {
            lines.add(order.getId() + ";" + order.getStartDate() + ";" + order.getDuration() + ";" + order.getPrice());
        }
        try {
            Files.write(Paths.get(fileName), lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.WRITE);
        } catch (IOException e) {
            printer.println("PROBLEME AVEC FICHIER");
        }
    }

    public void listOrders() {
        orders.sort(comparingInt(Order::getStartDate));
        printer.println("LISTE DES ORDRES\n");
        printer.format("%8s %8s %5s %13s", "ID", "DEBUT", "DUREE", "PRIX\n");
        printer.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
        for (Order order : orders) {
            printOrder(order);
        }
        printer.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
    }

    public void printOrder(Order order) {
        printer.format("%8s %8d %5d %10.2f\n", order.getId(), order.getStartDate(), order.getDuration(), order.getPrice());

    }

    public void addOrder() {
        printer.println("AJOUTER UN ORDRE");
        printer.println("FORMAT = ID;DEBUT;FIN;PRIX");
        Scanner in = new Scanner(reader.getInputStream());
        in.nextLine();
        String line = in.nextLine().toUpperCase();
        String[] fields = line.split(";");
        String id = fields[0];
        int startDate = Integer.parseInt(fields[1]);
        int duration = Integer.parseInt(fields[2]);
        double price = Double.parseDouble(fields[3]);
        Order order = new Order(id, startDate, duration, price);
        orders.add(order);
        saveOrders("orders.csv");
    }

    public void deleteOrder() {
        printer.println("SUPPRIMER UN ORDRE");
        printer.println("ID:");
        String id = System.console().readLine().toUpperCase();
        for (Iterator<Order> iter = orders.listIterator(); iter.hasNext(); ) {
            Order o = iter.next();
            if (o.getId().equals(id)) {
                iter.remove();
            }
        }
        saveOrders("ORDRES.CSV");
    }

    protected Instant getDateFromInt(int d) {
        return dateHelper.getDateFromInt(d);
    }

    public void computeRevenue() {
        printer.println("CALCUL CA..");
        orders.sort(comparingInt(Order::getStartDate));
        RevenueCalculator calculator = new RevenueCalculator();
        printer.format("CA: %10.2f\n", calculator.revenue(orders));
    }
}

