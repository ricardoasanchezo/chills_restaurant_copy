package com.ricardo.chillsrestaurant.entities;

public enum OrderType
{
    DINE_IN("Dine In"),
    CARRY_OUT("Carry Out"),
    DELIVERY("Delivery");

    private final String name;

    OrderType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public static OrderType fromString(String s)
    {
        for (OrderType type : OrderType.values())
            if (type.name.equalsIgnoreCase(s))
                return type;

        throw new IllegalArgumentException("Order type " + s + " does not exist!");
    }
}
