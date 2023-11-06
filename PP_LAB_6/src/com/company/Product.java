package com.company;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    public final Date date;
    private static int nextId = 0;
    private int id;
    private Name name;
    private float price;
    public Product(Name name, float price)
    {
        date = new Date();
        id = ++nextId;
        this.name = name;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public String getName(String arg) {
        return name.getName(arg);
    }
    public float getPrice() {
        return price;
    }
}
