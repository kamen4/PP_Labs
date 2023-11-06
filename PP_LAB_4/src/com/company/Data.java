package com.company;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Data {
    private int day;
    private int month;
    private int year;

    public Data(int day, int month, int year) {
        if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0) {
            throw new IllegalArgumentException("Invalid date");
        }

        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getDayOfWeek() {
        Calendar calendar = new GregorianCalendar(year, month - 1, day);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
}
