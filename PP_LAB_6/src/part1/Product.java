package part1;

import java.io.Serializable;
import java.util.Date;

public class Product implements Serializable {
    private static int nextId = 0;
    private int id;
    private String name;
    private float price;
    public Product(String name, float price)
    {
        id = ++nextId;
        this.name = name;
        this.price = price;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public float getPrice() {
        return price;
    }
}
