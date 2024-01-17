package com.ricardo.chillsrestaurant.dtos;

import lombok.Data;

import java.util.List;

@Data
public class OrderCreationRequest
{
    private List<Integer> itemIds;

    private String customerComments;

    private String orderType;
}
