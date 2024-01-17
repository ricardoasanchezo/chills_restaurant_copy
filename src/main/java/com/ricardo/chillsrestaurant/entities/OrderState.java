package com.ricardo.chillsrestaurant.entities;

public enum OrderState
{
    IN_PROGRESS("In Progress"), // Being cooked
    READY("Ready"), // Finished cooking but hasn't been given to customer
    FULFILLED("Fulfilled"), // Has been given to customer
    CANCELED("Canceled");

    private final String name;

    OrderState(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }

    public static OrderState fromString(String s)
    {
        for (OrderState state: OrderState.values())
            if (state.name.equalsIgnoreCase(s))
                return state;

        throw new IllegalArgumentException("Order state " + s + " does not exist!");
    }
}
