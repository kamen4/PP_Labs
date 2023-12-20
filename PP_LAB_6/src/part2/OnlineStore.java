package part2;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.*;

public class OnlineStore implements Serializable {
    public final Date date = new Date();

    public String getCreationDate() {
        DateFormat dateFormatter = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, AppLocale.get());
        String dateOut = dateFormatter.format(date);
        return dateOut;
    }

    private List<Client> clients;
    private List<Integer> blackList;
    private HashMap<Integer, Product> catalog;
    public OnlineStore()
    {
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
    public Client getClientById(int id) {
        var c = clients.stream().filter(x -> x.getId() == id).findFirst();
        if (c.isPresent())
            return c.get();
        return null;
    }
    public Client getClientByName(String name) {
        var c = clients.stream().filter(x -> x.getName().equals(name)).findFirst();
        if (c.isPresent())
            return c.get();
        return null;
    }
    public List<Integer> getBlackList() { return new ArrayList<Integer>(blackList); }
    public void moveToBlackList(int id)
    {
        blackList.add(id);
    }

}
