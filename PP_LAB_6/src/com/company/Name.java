package com.company;

public class Name {
    String[] name;
    public Name(String[] name)
    {
        this.name = name;
    }
    public String getName(String arg)
    {
        if (arg.equals("ru"))
            return name[1];
        if (arg.equals("by"))
            return name[2];
        return name[0];
    }
}
