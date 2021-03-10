package com.tof.app;

import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardOpenOption;

import static java.util.Comparator.*;

class LagsService {
    private List<Order> orders = new ArrayList<>();

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
            System.out.println("FICHIER ORDRES.CSV NON TROUVE. CREATION FICHIER.");
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
            System.out.println("PROBLEME AVEC FICHIER");
        }
    }

    public void listOrders() {
        orders.sort(comparingInt(Order::getStartDate));
        System.out.println("LISTE DES ORDRES\n");
        System.out.format("%8s %8s %5s %13s", "ID", "DEBUT", "DUREE", "PRIX\n");
        System.out.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
        for (Order order : orders) {
            printOrder(order);
        }
        System.out.format("%8s %8s %5s %13s", "--------", "-------", "-----", "----------\n");
    }

    public void printOrder(Order order) {
        System.out.format("%8s %8d %5d %10.2f\n", order.getId(), order.getStartDate(), order.getDuration(), order.getPrice());

    }

    public void addOrder() {
        System.out.println("AJOUTER UN ORDRE");
        System.out.println("FORMAT = ID;DEBUT;FIN;PRIX");
        Scanner in = new Scanner(System.in);
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
        System.out.println("SUPPRIMER UN ORDRE");
        System.out.println("ID:");
        String id = System.console().readLine().toUpperCase();
        for (Iterator<Order> iter = orders.listIterator(); iter.hasNext(); ) {
            Order o = iter.next();
            if (o.getId().equals(id)) {
                iter.remove();
            }
        }
        saveOrders("ORDRES.CSV");
    }

    private double revenue(List<Order> orders, boolean debug) {
        if (orders.size() == 0)
            return 0.0;
        Order firstOrder = orders.get(0);
        List<Order> compatibleOrders = new ArrayList<>();
        for (Order o : this.orders) {
            if (o.getStartDate() >= firstOrder.getStartDate() + firstOrder.getDuration()) {
                compatibleOrders.add(o);
            }
        }
        List<Order> followingOrders = new ArrayList<>();
        for (int i = 1; i < orders.size(); i++) {
            followingOrders.add(orders.get(i));
        }
        double revenueWithFirstOrder = firstOrder.getPrice() + revenue(compatibleOrders, debug);
        double revenueWithFollowingOrders = revenue(followingOrders, debug);
        if (debug) {
            System.out.format("%10.2f\n", Math.max(revenueWithFirstOrder, revenueWithFollowingOrders));
        } else
            System.out.print(".");
        return Math.max(revenueWithFirstOrder, revenueWithFollowingOrders);
    }

    public void computeRevenue(boolean debug) {
        System.out.println("CALCUL CA..");
        orders.sort(comparingInt(Order::getStartDate));
        System.out.format("CA: %10.2f\n", revenue(orders, debug));
    }
}

