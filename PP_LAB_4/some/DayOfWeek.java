package com.company;

public enum DayOfWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday;

    static DayOfWeek getDay(int dayOfWeek)
    {
        switch (dayOfWeek) {
            case 1:
                return Sunday;
            case 2:
                return Monday;
            case 3:
                return Tuesday;
            case 4:
                return Wednesday;
            case 5:
                return Thursday;
            case 6:
                return Friday;
            case 7:
                return Saturday;
            default:
                throw new IllegalArgumentException("Invalid day of week");
        }
    }
}
