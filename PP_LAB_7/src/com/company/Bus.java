package com.company;

import java.io.*;
import java.util.*;

public class Bus implements Serializable {// class release version:
    private static final long serialVersionUID = 1L;
    // areas with prompts:
    String name;
    static final String P_name = "Name";
    int busNum;
    static final String P_busNum = "BusNumber";
    int routeNum;
    static final String P_routeNum = "RouteNumber";
    String brand;
    static final String P_brand = "Brand";
    int year;
    static final String P_year = "Year";
    int mileage;
    static final String P_mileage = "Mileage";

    // validation methods:
    static Boolean validBusNum(int num) {
        return Integer.toString(num).length() == 4;
    }

    private static GregorianCalendar curCalendar = new GregorianCalendar();

    static Boolean validYear(int year) {
        return year > 0 && year <= curCalendar.get(Calendar.YEAR);
    }

    public static boolean nextRead(Scanner fin, PrintStream out) {
        return nextRead(P_name, fin, out);
    }

    static boolean nextRead(final String prompt, Scanner fin, PrintStream out) {
        out.print(prompt);
        out.print(": ");
        return fin.hasNextLine();
    }

    public static final String authorDel = ",";

    public static Bus read(Scanner fin, PrintStream out) throws IOException,
            NumberFormatException {
        String str;
        Bus bus = new Bus();
        bus.name = fin.nextLine().trim();

        if (!nextRead(P_busNum, fin, out)) {
            return null;
        }
        bus.busNum = Integer.parseInt(fin.nextLine().trim());
        if (!Bus.validBusNum(bus.busNum)) {
            throw new IOException("Invalid Bus.busNum value");
        }

        if (!nextRead(P_routeNum, fin, out)) {
            return null;
        }
        bus.routeNum = Integer.parseInt(fin.nextLine().trim());

        if (!nextRead(P_brand, fin, out)) {
            return null;
        }
        bus.brand = fin.nextLine().trim();

        if (!nextRead(P_year, fin, out)) {
            return null;
        }
        bus.year = Integer.parseInt(fin.nextLine());
        if (!Bus.validYear(bus.year)) {
            throw new IOException("Invalid Book.year value");
        }

        if (!nextRead(P_mileage, fin, out)) {
            return null;
        }
        bus.mileage = Integer.parseInt(fin.nextLine());
        return bus;
    }

    public Bus() {
    }

    public static final String areaDel = "\n";

    public String toString() {
        return new String(
                name + areaDel +
                        busNum + areaDel +
                        routeNum + areaDel +
                        brand + areaDel +
                        year + areaDel +
                        mileage
        );
    }
}
