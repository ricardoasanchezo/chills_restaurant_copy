package com.ricardo.chillsrestaurant.services;


import com.ricardo.chillsrestaurant.dtos.EmployeeOrderCreationRequest;
import com.ricardo.chillsrestaurant.dtos.OrderCreationRequest;
import com.ricardo.chillsrestaurant.entities.*;
import com.ricardo.chillsrestaurant.repositories.OrderRepository;
import com.ricardo.chillsrestaurant.user.MyUser;
import com.ricardo.chillsrestaurant.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class OrderService
{
    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final MyUserService myUserService;
    private final PaymentService paymentService;

    public Order findById(Long id)
    {
        return orderRepository.findById(id).orElseThrow();
    }

    public List<Order> findAllOrders()
    {
        Role role = myUserService.getLoggedInUser().getRole();


        return orderRepository.findAll();
    }

    public List<Order> findAllOrdersByState(String state)
    {
        OrderState enumState = OrderState.valueOf(state);

        return orderRepository.findAllOrdersByState(enumState);
    }

    public List<Order> findAllOrdersCreatedAfter(LocalDateTime timeStamp)
    {
        return orderRepository.findAllByCreatedAtAfter(timeStamp);
    }

    public List<Order> findAllOrdersByCreatorEmail(String email)
    {
        return orderRepository.findAllOrdersByCreatorEmail(email);
    }

    public List<Order> findAllOrdersByCreatorId(Long id)
    {
        return orderRepository.findAllOrdersByCreatorId(id);
    }

    public Order createOrder(OrderCreationRequest request)
    {
        if (request.getItemIds().isEmpty())
            throw new RuntimeException("An order needs to have at least 1 item!");

        // Create list from all item ids or throw error
        List<Item> orderItems = new ArrayList<>();
        for (Integer id: request.getItemIds())
            orderItems.add(itemService.findById(id).orElseThrow());

        return orderRepository.save(new Order(
                null,
                myUserService.getLoggedInUser(),
                null,
                LocalDateTime.now(),
                null,
                orderItems,
                request.getCustomerComments(),
                OrderType.valueOf(request.getOrderType()),
                OrderState.IN_PROGRESS,
                paymentService.createPayment(orderItems)
        ));
    }

    public Order createOrderForCustomer(EmployeeOrderCreationRequest request)
    {
        if (request.getItemIds().isEmpty())
            throw new RuntimeException("An order needs to have at least 1 item!");

        // Create list from all item ids or throw error
        List<Item> orderItems = new ArrayList<>();
        for (Integer id: request.getItemIds())
            orderItems.add(itemService.findById(id).orElseThrow());

        MyUser customer = myUserService.findByEmail(request.getCustomerEmail()).orElseThrow();

        return orderRepository.save(new Order(
                null,
                customer,
                null,
                LocalDateTime.now(),
                null,
                orderItems,
                request.getCustomerComments(),
                OrderType.valueOf(request.getOrderType()),
                OrderState.IN_PROGRESS,
                paymentService.createPayment(orderItems)
        ));
    }

    public Order cancelOrderById(Long id)
    {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setClosedAt(LocalDateTime.now());
        order.setState(OrderState.CANCELED);
        return orderRepository.save(order);
    }

    public Order fulfillOrderById(Long id)
    {
        Order order = orderRepository.findById(id).orElseThrow();
        order.setClosedAt(LocalDateTime.now());
        order.setState(OrderState.FULFILLED);
        return orderRepository.save(order);
    }

    public Order applyDiscountCode(Long id, String discountCode)
    {
        Order order = orderRepository.findById(id).orElseThrow();

        Payment newPayment = paymentService.applyDiscountCode(order.getPayment(), discountCode);

        order.setPayment(newPayment);

        return orderRepository.save(order);
    }

    public Order payOrder(Long id, Integer paidAmount)
    {
        Order order = orderRepository.findById(id).orElseThrow();

        order.getPayment().setPaidAmount(order.getPayment().getPaidAmount() + paidAmount);

        if (order.isPaid() && order.getPayment().getDiscountCode() != null)
        {
            order.getPayment().getDiscountCode().setOrderUsed(id);
        }

        return orderRepository.save(order);
    }

    public Order updateOrderState(Long id, String stateString)
    {
        Order order = orderRepository.findById(id).orElseThrow();

        if (order.getClosedAt() != null)
            throw new IllegalStateException("The state of the order can't be changed because the order has already been closed!");

        OrderState state = OrderState.fromString(stateString);

        order.setState(state);

        if (state == OrderState.FULFILLED || state == OrderState.CANCELED)
            order.setClosedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    public void closeOrder(Long id)
    {
        orderRepository.updateOrderClosedAtById(id, LocalDateTime.now());
    }

    public Order claimOwnership(Long id)
    {
        Order order = orderRepository.findById(id).orElseThrow();

        if (order.getClosedAt() != null)
            throw new IllegalArgumentException("You cannot claim ownership of a closed order!");

        MyUser loggedInUser = myUserService.getLoggedInUser();

        if (order.getOrderOwner() == null)
        {
            order.setOrderOwner(loggedInUser);
        }
        else if (!Objects.equals(order.getOrderOwner().getId(), loggedInUser.getId()))
        {
            order.setOrderOwner(loggedInUser);
        }

        return orderRepository.save(order);
    }
}
