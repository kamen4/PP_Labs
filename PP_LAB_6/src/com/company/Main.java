package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //Создаём магазин
        OnlineStore store = new OnlineStore();
        //добавляем продукты
        var Apple = new String[]{"Apple", "Яблоко", "Яблык"};
        store.addProduct(new Product(new Name(Apple), 1.2f));
        var Shirt = new String[]{"Shirt", "Майка", "Майка"};
        store.addProduct(new Product(new Name(Shirt), 2.3f));
        var Sim = new String[]{"Sim", "Сим", "Сiм"};
        store.addProduct(new Product(new Name(Sim), 0.3f));
        var Rain = new String[]{"Rain", "Дождь", "Дождж"};
        store.addProduct(new Product(new Name(Rain), 10.1f));
        var Emulator = new String[]{"Emulator", "Эмулятор", "Эмулятор"};
        store.addProduct(new Product(new Name(Emulator), 11.2f));
        var Powerbank = new String[]{"Powerbank", "Павербанк", "Павербанк"};
        store.addProduct(new Product(new Name(Powerbank), 12.25f));
        var Public = new String[]{"Public", "Публика", "Публiка"};
        store.addProduct(new Product(new Name(Public), 3.2f));
        var Pen = new String[]{"Pen", "Ручка", "Аловак"};
        store.addProduct(new Product(new Name(Pen), 14.2f));
        var Picture = new String[]{"Picture", "Картина", "Карцiна"};
        store.addProduct(new Product(new Name(Picture), 4.2f));
        var Wand = new String[]{"Wand", "Палочка", "Галiнка"};
        store.addProduct(new Product(new Name(Wand), 7.7f));

        //создаём клиента Jhon и делаем ему заказы
        Client jhon = new Client("Jhon");
        store.addClient(jhon);
        jhon.makeOrder(2);
        jhon.makeOrder(1);
        jhon.makeOrder(2);
        jhon.makeOrder(4);
        jhon.payForOrder(jhon.getOrders().get(1).getOrderId());

        //создаём клиента Nancy и делаем ему заказы
        Client nancy = new Client("Nancy");
        store.addClient(nancy);
        nancy.makeOrder(6);
        nancy.makeOrder(6);
        nancy.makeOrder(6);
        nancy.makeOrder(6);
        nancy.makeOrder(6);
        nancy.makeOrder(6);

        //помещаем в blacklist
        store.moveToBlackList(jhon.getId());
        store.moveToBlackList(nancy.getId());

        //создаём клиента Lacy и делаем ему заказы
        Client lacy = new Client("Lacy");
        store.addClient(lacy);
        lacy.makeOrder(10);
        lacy.makeOrder(9);
        lacy.makeOrder(7);
        lacy.makeOrder(8);
        for (var a : lacy.getOrders())
            lacy.payForOrder(a.getOrderId());


        //сохраняем и читаем всё
        Connector c1 = new Connector("C:\\Users\\volde\\Desktop\\laba.txt");
        try {
            c1.write(store);
            store = c1.read();
        }
        catch(Exception e)
        {
            System.err.print(e);
        }

        //выводим информацию
        int n = 0;
        for(; n < args.length; n++)
            if (args[n].equals("-lang"))
            {
                n++;
                break;
            }
        if (n >= args.length)
            Info(store, "en");
        else
            Info(store, args[n]);
    }

    private static void Info(OnlineStore store, String arg) {
        if (arg.equals("en")) {
            System.out.println("Clients:");
            for (var c : store.getClients()) {
                System.out.println("\t" + c.getId() + ": " + c.getName() + "\t\t[Date: " + c.date + "]");
                System.out.println("\t\t" + c.getName() + "'s orders:");
                for (var s : c.getOrders())
                    System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName("en") + " (" + (s.getPaid() ? "" : "not ") + "paid)\t\t[Date: " + s.date + "]");
            }

            System.out.println("\nBlacklist:");
            for (var c : store.getBlackList())
                System.out.println("\t" + store.getClients().stream().filter(x -> x.getId() == c).findFirst().get().getName());

            System.out.println("\nProducts:");
            for (var p : store.getAllProducts()) {
                System.out.println("\t" + p.getId() + ": " + p.getPrice() + "$     " + p.getName("en") + " \t\t[Date: " + p.date.toString() + "]");
            }
        }
        else if (arg.equals("ru"))
        {
            System.out.println("Клиенты:");
            for (var c : store.getClients()) {
                System.out.println("\t" + c.getId() + ": " + c.getName() + "\t\t[Дата: " + c.date + "]");
                System.out.println("\t\tЗаказы клиента " + c.getName() +":");
                for (var s : c.getOrders())
                    System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName("ru") + " (" + (s.getPaid() ? "" : "не ") + "оплачено)\t\t[Дата: " + s.date + "]");
            }

            System.out.println("\nЧёрный список:");
            for (var c : store.getBlackList())
                System.out.println("\t" + store.getClients().stream().filter(x -> x.getId() == c).findFirst().get().getName());

            System.out.println("\nПродукты:");
            for (var p : store.getAllProducts()) {
                System.out.println("\t" + p.getId() + ": " + p.getPrice() + "$     " + p.getName("ru") + " \t\t[Дата: " + p.date.toString() + "]");
            }
        }
        else if (arg.equals("by"))
        {
            System.out.println("Клiенты:");
            for (var c : store.getClients()) {
                System.out.println("\t" + c.getId() + ": " + c.getName() + "\t\t[Дата: " + c.date + "]");
                System.out.println("\t\tЗаказы клiента " + c.getName() +":");
                for (var s : c.getOrders())
                    System.out.println("\t\t" + s.getOrderId() + ": " + store.getProductById(s.getProductId()).getName("by") + " (" + (s.getPaid() ? "" : "не ") + "аплачана)\t\t[Дата: " + s.date + "]");
            }

            System.out.println("\nЧорны список:");
            for (var c : store.getBlackList())
                System.out.println("\t" + store.getClients().stream().filter(x -> x.getId() == c).findFirst().get().getName());

            System.out.println("\nПрадукты:");
            for (var p : store.getAllProducts()) {
                System.out.println("\t" + p.getId() + ": " + p.getPrice() + "$     " + p.getName("by") + " \t\t[Дата: " + p.date.toString() + "]");
            }
        }
    }
}
