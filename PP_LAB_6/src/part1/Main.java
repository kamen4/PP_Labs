package part1;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Создаём магазин
        OnlineStore store = new OnlineStore();;

        final String filePath = "C:\\Users\\volde\\Desktop\\laba.txt";
        Connector c1 = new Connector(filePath);

        try {
            System.out.println("Loading data...");
            store = c1.read();
        }
        catch (Exception e) {
            System.out.println("Cannot load data ( " + e.getMessage() + " )");
        }

        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Enter name (or q/quit to exit):");
            var name = in.nextLine();
            if (name.equals("admin")) {
                while (true) {
                    System.out.println("Enter command (\"clients\", \"client <id>\", \"bl\", \"tobl <id>\", \"newprod <name> <price>\", \"q/quit\"):");
                    var cmnd = in.nextLine();
                    if (cmnd.equals("clients")) {
                        for (var c : store.getClients()) {
                            System.out.println("\t" + c.getId() + ": " + c.getName());
                            System.out.println("\t\t" + c.getName() + "'s orders:");
                            for (var s : c.getOrders())
                                System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName() + " (" + (s.getPaid() ? "" : "not ") + "paid)");
                        }
                    }
                    else if (cmnd.equals("bl")) {
                        for (var i : store.getBlackList()) {
                            var c = store.getClients().stream().filter(x -> x.getId() == i).findFirst().get();
                            System.out.println("\t Id: " + c.getId() + " Name: " + c.getName());
                        }
                    }
                    else if (cmnd.split(" ")[0].equals("newprod")) {
                        store.addProduct(new Product(cmnd.split(" ")[1], Float.parseFloat(cmnd.split(" ")[2])));
                    }
                    else if (cmnd.split(" ")[0].equals("client")) {
                        Client c = store.getClientById(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                        if (c == null) {
                            System.out.println("Clients not found!");
                            continue;
                        }
                        System.out.println("\t" + c.getId() + ": " + c.getName());
                        System.out.println("\t\t" + c.getName() + "'s orders:");
                        for (var s : c.getOrders())
                            System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName() + " (" + (s.getPaid() ? "" : "not ") + "paid)");
                    } else if (cmnd.split(" ")[0].equals("tobl")) {
                        Client c = store.getClientById(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                        if (c == null) {
                            System.out.println("Clients not found!");
                            continue;
                        }
                        store.moveToBlackList(c.getId());
                    } else if (cmnd.equals("q") || cmnd.equals("quit")) {
                        break;
                    } else {
                        System.out.println("Unknown command");
                    }
                }
            }
            else if (name.equals("q") || name.equals("quit")) {
                break;
            }
            else {
                Client c = store.getClientByName(name);
                if (c == null) {
                    c = new Client(name);
                    store.addClient(c);
                    System.out.println("Hello, " + name + ", you are a new user!");
                }
                while (true) {
                    System.out.println("Enter command (\"order <id>\", \"pay <id>\", \"products\", \"info\", \"q/quit\":)");
                    var cmnd = in.nextLine();
                    if (cmnd.split(" ")[0].equals("order")) {
                        if (store.getProductById(Integer.parseUnsignedInt(cmnd.split(" ")[1])) == null) {
                            System.out.println("This product does not exist");
                            continue;
                        }
                        c.makeOrder(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                    } else if (cmnd.split(" ")[0].equals("pay")) {
                        c.payForOrder(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                    } else if (cmnd.equals("products")) {
                        for (var p : store.getAllProducts())
                            System.out.println("\t" + p.getId() + ": " + p.getPrice() + "$   " + p.getName());
                    } else if (cmnd.equals("info")) {
                        System.out.println("\t" + c.getId() + ": " + c.getName());
                        System.out.println("\t\t" + c.getName() + "'s orders:");
                        for (var s : c.getOrders())
                            System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName() + " (" + (s.getPaid() ? "" : "not ") + "paid)");
                    } else if (cmnd.equals("q") || cmnd.equals("quit")) {
                        break;
                    } else {
                        System.out.println("Unknown command");
                    }
                }
            }
        }

        try {
            c1.write(store);
        } catch (IOException e) {
            System.err.println("Writing error (" + e.getMessage() + ")");
        }
    }
}
