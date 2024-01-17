package com.ricardo.chillsrestaurant.repositories;

import com.ricardo.chillsrestaurant.entities.DiscountCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Long>
{
    @Query("SELECT d FROM DiscountCode d WHERE d.code = ?1 AND d.orderUsed = null")
    List<DiscountCode> findUnusedByCode(String code);
}
