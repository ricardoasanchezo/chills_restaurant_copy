package com.ricardo.chillsrestaurant.repositories;

import com.ricardo.chillsrestaurant.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>
{
}
