package com.company;

public class Listing implements Comparable<Listing>{

    private int id;
    private String make;
    private int price;
    private int mileage;
    private String sellerType;

    public Listing(int id, String make, int price, int mileage, String sellerType) {
        this.id = id;
        this.make = make;
        this.price = price;
        this.mileage = mileage;
        this.sellerType = sellerType;
    }

    public int getId() {
        return id;
    }

    public String getMake() {
        return make;
    }

    public int getPrice() {
        return price;
    }

    public int getMileage() {
        return mileage;
    }

    public String getSellerType() {
        return sellerType;
    }

    @Override
    public int compareTo(Listing l) {
        return Integer.compare(this.id, l.id);
    }
}
