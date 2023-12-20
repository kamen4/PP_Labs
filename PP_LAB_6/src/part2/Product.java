package part2;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

public class Product implements Serializable {
    public final Date date = new Date();

    public String getCreationDate() {
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
        String dateOut = dateFormatter.format(date);
        return dateOut;
    }

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
