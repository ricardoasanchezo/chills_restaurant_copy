package com.ricardo.chillsrestaurant.repositories;

import com.ricardo.chillsrestaurant.entities.Order;
import com.ricardo.chillsrestaurant.entities.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    @Query("UPDATE Order o SET o.state = ?2 WHERE o.id = ?1")
    Order updateOrderStateById(Long id, OrderState state);

    @Query("UPDATE Order o SET o.closedAt = ?2 WHERE o.id = ?1")
    void updateOrderClosedAtById(Long id, LocalDateTime closedAt);

    List<Order> findAllOrdersByState(OrderState state);

    List<Order> findAllByCreatedAtAfter(LocalDateTime localDateTime);

    @Query("SELECT o FROM Order o WHERE o.orderCreator.email = ?1")
    List<Order> findAllOrdersByCreatorEmail(String email);

    @Query("SELECT o FROM Order o WHERE o.orderCreator.id = ?1")
    List<Order> findAllOrdersByCreatorId(Long id);
}
