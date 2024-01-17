package com.ricardo.chillsrestaurant.services;

import com.ricardo.chillsrestaurant.entities.DiscountCode;
import com.ricardo.chillsrestaurant.entities.Item;
import com.ricardo.chillsrestaurant.entities.Order;
import com.ricardo.chillsrestaurant.entities.Payment;
import com.ricardo.chillsrestaurant.repositories.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService
{
    public final static float TAX_PERCENTAGE = 7.0F;

    private final PaymentRepository paymentRepository;
    private final DiscountCodeService discountCodeService;

    public Optional<Payment> findPayment(Long id)
    {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(List<Item> items)
    {
        int subtotal = items.stream().mapToInt(Item::getPrice).sum();
        int tax = (int) (subtotal * (TAX_PERCENTAGE / 100.0));
        int total = subtotal + tax;

        Payment payment = new Payment(
                null,
                subtotal,
                tax,
                null,
                total,
                0
        );

        return paymentRepository.save(payment);
    }

    public Payment applyDiscountCode(Payment payment, String code)
    {
        Optional<DiscountCode> discountCode = discountCodeService.findUnusedCode(code);

        if (discountCode.isEmpty())
            throw new RuntimeException("Code is not valid and discount could not be applied!");

        payment.setDiscountCode(discountCode.get());

        int discount = (int) (payment.getTotal() * (discountCode.get().getDiscountPercentage() / 100.0));
        int newTotal = payment.getTotal() - discount;

        payment.setTotal(newTotal);

        return paymentRepository.save(payment);
    }
}
