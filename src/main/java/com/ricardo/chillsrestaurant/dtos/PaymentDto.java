package com.ricardo.chillsrestaurant.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class PaymentDto
{
    private Long id;
    private Integer subtotal;
    private Integer tax;
    private String discountCode;
    private Integer discountPercentage;
    private Integer total;
    private Integer paidAmount;
}
