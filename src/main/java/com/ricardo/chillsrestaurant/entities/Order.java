package com.ricardo.chillsrestaurant.entities;

import com.ricardo.chillsrestaurant.dtos.OrderDto;
import com.ricardo.chillsrestaurant.user.MyUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private MyUser orderCreator;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private MyUser orderOwner;

    private LocalDateTime createdAt;

    private LocalDateTime closedAt;

    @ManyToMany(targetEntity = Item.class)
    @JoinTable(
            foreignKey = @ForeignKey(name = "", value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    private List<Item> items;

    private String customerComments;

    @Enumerated(EnumType.STRING)
    private OrderType type;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Payment payment;


    public OrderDto toOrderDto()
    {
        return new OrderDto(
                this.id,
                this.orderCreator != null ? this.orderCreator.getEmail() : null,
                this.orderOwner != null ? this.orderOwner.getEmail() : null,
                this.getCreatedAt(),
                this.getClosedAt(),
                this.items.stream().map(Item::toItemDto).toList(),
                this.customerComments,
                this.type.getName(),
                this.state.getName(),
                this.payment.toPaymentDto()
        );
    }

    public boolean isPaid()
    {
        return payment.isPaid();
    }
}
