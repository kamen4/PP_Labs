package com.company;

import java.util.Scanner;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Enter year:");
        int year = in.nextInt();
        if (year < 0)
        {
            System.err.println("Invalid year!");
            in.close();
            exit(1);
        }

        CalendarMonth[] yearMonthes = new CalendarMonth[12];
        for (int i = 0; i < 12; i++)
            yearMonthes[i] = new CalendarMonth(i + 1, year);
        System.out.println("\t[Calendar created (year: " + year + ")]");

        System.out.println("\nEnter day:");
        int day = in.nextInt();
        try {
            yearMonthes[0].printDayOfWeekByDate(day);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            in.close();
            exit(1);
        }

        System.out.println("\nEnter day of week (1 - Sunday, 2 - Monday, etc.):");
        int dayOfWeek = in.nextInt();
        try {
            yearMonthes[0].printDatesByDayOfWeek(dayOfWeek);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            exit(1);
        } finally {
            in.close();
        }
    }
}
