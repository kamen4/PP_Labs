package part1;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private static int nextId = 0;
    private int orderId;
    private int productId;
    private boolean isPaid;

    public Order()
    {
        orderId = ++nextId;
        productId = 0;
        isPaid = false;
    }
    public Order(int productId)
    {
        orderId = ++nextId;
        this.productId = productId;
        isPaid = false;
    }
    public void Pay()
    {
        isPaid = true;
    }
    public int getOrderId()
    {
        return orderId;
    }
    public int getProductId()
    {
        return productId;
    }
    public boolean getPaid()
    {
        return isPaid;
    }
}
