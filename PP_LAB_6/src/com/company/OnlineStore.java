package com.company;

import java.io.Serializable;
import java.util.*;

public class OnlineStore implements Serializable {
    public final Date date;
    private List<Client> clients;
    private List<Integer> blackList;
    private HashMap<Integer, Product> catalog;
    public OnlineStore()
    {
        date = new Date();
        clients = new ArrayList<Client>();
        blackList = new ArrayList<Integer>();
        catalog = new HashMap<Integer, Product>();
    }
    public void addClient(Client client) { clients.add(client); }
    public void addProduct(Product product)
    {
        catalog.put(product.getId(), product);
    }
    public Product getProductById(int id)
    {
        return catalog.get(id);
    }
    public Collection<Product> getAllProducts()
    {
        return catalog.values();
    }
    public List<Client> getClients() { return new ArrayList<Client>(clients); }
    public List<Integer> getBlackList() { return new ArrayList<Integer>(blackList); }
    public void moveToBlackList(int id)
    {
        blackList.add(id);
    }

}
