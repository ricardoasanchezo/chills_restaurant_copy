package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.entities.DiscountCode;
import com.ricardo.chillsrestaurant.services.DiscountCodeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/discount")
public class DiscountCodeController
{
    private final DiscountCodeService discountCodeService;

    @PostMapping("/add")
    public DiscountCode addDiscountCode(@RequestBody DiscountCode discountCode)
    {
        return discountCodeService.saveDiscountCode(discountCode);
    }
}
