package part2;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    static Locale createLocale(String[] args) {
        if (args.length == 2) {
            return new Locale(args[0], args[1]);
        } else if (args.length == 4) {
            return new Locale(args[2], args[3]);
        }
        return null;
    }

    static void setupConsole(String[] args) {
        if (args.length >= 2) {
            if (args[0].equals("-encoding")) {
                try {
                    System.setOut(new PrintStream(System.out, true, args[1]));
                } catch (UnsupportedEncodingException ex) {
                    System.err.println("Unsupported encoding: " + args[1]);
                    System.exit(1);
                }
            }
        }
    }

    public static void main(String[] args) {

        try {
            setupConsole(args);
            Locale loc = createLocale(args);
            if (loc == null) {
                System.err.println("Invalid argument(s)\n"
                        + 	"Syntax: [-encoding ENCODING_ID] language country\n"
                        + "Example: -encoding Cp855 be BY");
                System.exit(1);
            }
            AppLocale.set(loc);
        } catch (Exception e) {
            System.err.println(e);
        }

        OnlineStore store = new OnlineStore();;

        final String filePath = "C:\\Users\\volde\\Desktop\\laba2.txt";
        Connector c1 = new Connector(filePath);

        try {
            System.out.println(AppLocale.getString(AppLocale.loading));
            store = c1.read();
        }
        catch (Exception e) {
            System.out.println(AppLocale.getString(AppLocale.loadFailure) + " ( " + e.getMessage() + " )");
        }

        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println(AppLocale.getString(AppLocale.header1) +
                    " q/quit " + AppLocale.getString(AppLocale.header2));
            var name = in.nextLine();
            if (name.equals("admin")) {
                while (true) {
                    System.out.println(AppLocale.getString(AppLocale.entercommand) + " (\"clients\", \"client <id>\", \"bl\", \"tobl <id>\", \"newprod <name> <price>\", \"q/quit\"):");
                    var cmnd = in.nextLine();
                    if (cmnd.equals("clients")) {
                        for (var c : store.getClients()) {
                            System.out.println("\t" + c.getId() + ": " + c.getName() + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + c.getCreationDate() + "]");
                            System.out.println("\t\t" + c.getName() + " " + AppLocale.getString(AppLocale.orders_post));
                            for (var s : c.getOrders())
                                System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName() +
                                        " (" + (s.getPaid() ? "" : AppLocale.getString(AppLocale.not)) + AppLocale.getString(AppLocale.paid) + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + s.getCreationDate() + "]");
                        }
                    }
                    else if (cmnd.equals("bl")) {
                        for (var i : store.getBlackList()) {
                            var c = store.getClients().stream().filter(x -> x.getId() == i).findFirst().get();
                            System.out.println("\t Id: " + c.getId() + " "+AppLocale.getString(AppLocale.name)+": " + c.getName() + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + c.getCreationDate() + "]");
                        }
                    }
                    else if (cmnd.split(" ")[0].equals("newprod")) {
                        store.addProduct(new Product(cmnd.split(" ")[1], Float.parseFloat(cmnd.split(" ")[2])));
                    }
                    else if (cmnd.split(" ")[0].equals("client")) {
                        Client c = store.getClientById(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                        if (c == null) {
                            System.out.println(AppLocale.getString(AppLocale.clients404));
                            continue;
                        }
                        System.out.println("\t" + c.getId() + ": " + c.getName() + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + c.getCreationDate() + "]");
                        System.out.println("\t\t" + c.getName() + " " +AppLocale.getString(AppLocale.orders_post));
                        for (var s : c.getOrders())
                            System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName() +
                                    " (" + (s.getPaid() ? "" : AppLocale.getString(AppLocale.not)) + AppLocale.getString(AppLocale.paid) + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + s.getCreationDate() + "]");
                    } else if (cmnd.split(" ")[0].equals("tobl")) {
                        Client c = store.getClientById(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                        if (c == null) {
                            System.out.println(AppLocale.getString(AppLocale.clients404));
                            continue;
                        }
                        store.moveToBlackList(c.getId());
                    } else if (cmnd.equals("q") || cmnd.equals("quit")) {
                        break;
                    } else {
                        System.out.println(AppLocale.getString(AppLocale.command404));
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
                    System.out.println(AppLocale.getString(AppLocale.Hello)+", " + name + ", "+AppLocale.getString(AppLocale.newuser));
                }
                while (true) {
                    System.out.println(AppLocale.getString(AppLocale.entercommand)+" (\"order <id>\", \"pay <id>\", \"products\", \"info\", \"q/quit\":)");
                    var cmnd = in.nextLine();
                    if (cmnd.split(" ")[0].equals("order")) {
                        if (store.getProductById(Integer.parseUnsignedInt(cmnd.split(" ")[1])) == null) {
                            System.out.println(AppLocale.getString(AppLocale.product404));
                            continue;
                        }
                        c.makeOrder(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                    } else if (cmnd.split(" ")[0].equals("pay")) {
                        c.payForOrder(Integer.parseUnsignedInt(cmnd.split(" ")[1]));
                    } else if (cmnd.equals("products")) {
                        for (var p : store.getAllProducts())
                            System.out.println("\t" + p.getId() + ": " + p.getPrice() + "$   " + p.getName() + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + p.getCreationDate() + "]");
                    } else if (cmnd.equals("info")) {
                        System.out.println("\t" + c.getId() + ": " + c.getName()  + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + c.getCreationDate() + "]");
                        System.out.println("\t\t" + c.getName() +" "+ AppLocale.getString(AppLocale.orders_post));
                        for (var s : c.getOrders())
                            System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName() +
                                    " (" + (s.getPaid() ? "" : "not ") + "paid)" + "\n\t\t["+AppLocale.getString(AppLocale.date)+": " + s.getCreationDate() + "]");
                    } else if (cmnd.equals("q") || cmnd.equals("quit")) {
                        break;
                    } else {
                        System.out.println(AppLocale.getString(AppLocale.command404));
                    }
                }
            }
        }

        try {
            c1.write(store);
        } catch (IOException e) {
            System.err.println(AppLocale.getString(AppLocale.saveFailure)+" (" + e.getMessage() + ")");
        }
    }
}
