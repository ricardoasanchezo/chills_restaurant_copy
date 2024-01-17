package com.ricardo.chillsrestaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderDto
{
    private Long id;
    private String orderCreatorEmail;
    private String orderOwnerEmail;
    private LocalDateTime createdAt;
    private LocalDateTime closedAt;
    private List<ItemDto> items;
    private String customerComments;
    private String orderType;
    private String orderState;
    private PaymentDto payment;
}
