package part1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Client implements Serializable {
    private static int nextId = 0;
    private int id;
    private String name;
    private List<Order> orders;

    public Client(String name) {
        id = ++nextId;
        this.name = name;
        this.orders = new ArrayList<Order>();
    }
    public void makeOrder(int productId)
    {
        orders.add(new Order(productId));
    }
    public void payForOrder(int orderId)
    {
        var a = orders.stream().filter(x -> x.getOrderId() == orderId).findFirst();
        if (a.isPresent()) a.get().Pay();
    }
    int getId()
    {
        return id;
    }
    String getName()
    {
        return name;
    }
    public List<Order> getOrders() {
        return new ArrayList(orders);
    }
    public List<Order> getPaidOrders()
    {
        return orders.stream().filter(x -> x.getPaid()).collect(Collectors.toList());
    }
}
