package com.ricardo.chillsrestaurant.services;

import com.ricardo.chillsrestaurant.entities.DiscountCode;
import com.ricardo.chillsrestaurant.repositories.DiscountCodeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DiscountCodeService
{
    private final DiscountCodeRepository discountCodeRepository;

    public DiscountCode saveDiscountCode(DiscountCode discountCode)
    {
        return discountCodeRepository.save(discountCode);
    }

    public Optional<DiscountCode> findUnusedCode(String code)
    {
        List<DiscountCode> discountCodeList = discountCodeRepository.findUnusedByCode(code);

        if (discountCodeList.isEmpty())
            return Optional.empty();

        return Optional.of(discountCodeList.get(0));
    }
}
