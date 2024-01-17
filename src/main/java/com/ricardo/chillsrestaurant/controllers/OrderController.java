package com.ricardo.chillsrestaurant.controllers;

import com.ricardo.chillsrestaurant.dtos.EmployeeOrderCreationRequest;
import com.ricardo.chillsrestaurant.dtos.OrderCreationRequest;
import com.ricardo.chillsrestaurant.dtos.OrderDto;
import com.ricardo.chillsrestaurant.entities.Order;
import com.ricardo.chillsrestaurant.services.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/orders")
public class OrderController
{
    private final OrderService orderService;

    @PostMapping("/create")
    public OrderDto createOrder(@RequestBody OrderCreationRequest request)
    {
        return orderService.createOrder(request).toOrderDto();
    }

    @PostMapping("/createForCustomer")
    @PreAuthorize("hasAnyRole('MANAGER', 'EMPLOYEE')")
    public OrderDto createOrder(@RequestBody EmployeeOrderCreationRequest request)
    {
        return orderService.createOrderForCustomer(request).toOrderDto();
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasAuthority('employee:read')")
    public List<OrderDto> getAllOrders()
    {
        return orderService.findAllOrders().stream().map(Order::toOrderDto).toList();
    }

    @GetMapping("/getAllAfter")
    @PreAuthorize("hasAuthority('employee:read')")
    public List<OrderDto> getAllOrdersCreatedAfter(@RequestParam String isoDateString)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime timeStamp = LocalDateTime.parse(isoDateString, formatter);
        return orderService.findAllOrdersCreatedAfter(timeStamp).stream().map(Order::toOrderDto).toList();
    }

    @GetMapping("/getAllByState")
    @PreAuthorize("hasAuthority('employee:read')")
    public List<OrderDto> getAllByState(@RequestParam String state)
    {
        return orderService.findAllOrdersByState(state).stream().map(Order::toOrderDto).toList();
    }

    @PutMapping("/cancel")
    @PreAuthorize("hasRole('MANAGER')")
    public OrderDto cancelOrderById(@RequestParam Long id)
    {
        return orderService.cancelOrderById(id).toOrderDto();
    }

    @PutMapping("/fulfill")
    @PreAuthorize("hasRole('MANAGER')")
    public OrderDto fulfillOrderById(@RequestParam Long id)
    {
        return orderService.fulfillOrderById(id).toOrderDto();
    }

    @PutMapping("/updateState")
    @PreAuthorize("hasAnyRole('MANAGER','EMPLOYEE')")
    public OrderDto updateOrderStateById(@RequestParam Long id, @RequestParam String state)
    {
        return orderService.updateOrderState(id, state).toOrderDto();
    }

    @PutMapping("/claimOwnership")
    @PreAuthorize("hasAnyRole('MANAGER','EMPLOYEE')")
    public OrderDto claimOwnership(@RequestParam Long id)
    {
        return orderService.claimOwnership(id).toOrderDto();
    }

    @PutMapping("/applyDiscountCode")
    public OrderDto applyDiscountCode(@RequestParam Long id, @RequestParam String discountCode)
    {
        return orderService.applyDiscountCode(id, discountCode).toOrderDto();
    }

    @PutMapping("/pay")
    public OrderDto payOrder(@RequestParam Long id, @RequestParam Integer paidAmount)
    {
        return orderService.payOrder(id, paidAmount).toOrderDto();
    }
}
