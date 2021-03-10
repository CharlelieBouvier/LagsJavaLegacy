package com.tof.app;

public class Order implements Comparable<Order> {
    private String id;
    private int debut;
    private int duree;
    private double prix;

    public Order(String id, int debut, int duree, double prix)
    {
        this.id = id;
        this.debut = debut;  // au format AAAAJJJ par exemple 25 février 2015 = 2015056
        this.duree = duree;
        this.prix = prix;
    }
    //id de l'ordre 
    public String getId() {
       return this.id;
    }
    // debut
    public int getStartDate() {
        return this.debut;
    }
    // duree
    public int getDuration() {
        return this.duree;
    }
    // valeur
    public double getPrice() {
        return this.prix;
    }
    public int compareTo(Order other) {
        return this.debut - other.getStartDate();
    }

}
