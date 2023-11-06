package com.company;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarMonth {
    private Data[] dates;

    public CalendarMonth(int month, int year) {
        if (month > 12 || month < 1 || year < 0)
            throw new IllegalArgumentException("Invalid calendar size");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.YEAR, year);
        dates = new Data[calendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        for (int i = 0; i < dates.length; i++)
            dates[i] = new Data(i + 1, month, year);
    }

    public int DaysCount()
    {
        return dates.length;
    }

    public void printDayOfWeekByDate(int day) {
        if (day > dates.length || day < 1)
            throw new IllegalArgumentException("Invalid day");
        Data date = dates[day - 1];
        if (date != null)
            System.out.println(
                    "Day of week for date " +
                    (date.getDay() < 10 ? "0" : "") + date.getDay() + "." +
                    (date.getMonth() < 10 ? "0" : "") + date.getMonth()+ ": " +
                    DayOfWeek.getDay(date.getDayOfWeek()).name());
        else
            System.out.println("No data available for date " + date.getDay()  + "." + date.getMonth());
    }

    public void printDatesByDayOfWeek(int dayOfWeek) {
        System.out.println("Dates for "  + DayOfWeek.getDay(dayOfWeek).name() + ":");
        for (int i = 0; i < dates.length; i++)
                if (dates[i] != null && dates[i].getDayOfWeek() == dayOfWeek)
                    System.out.println(
                            (dates[i].getDay() < 10 ? "0" : "") + dates[i].getDay() + "." +
                            (dates[i].getMonth() < 10 ? "0" : "") + dates[i].getMonth() + "." +
                            dates[i].getYear());
    }
}
