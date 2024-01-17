package com.ricardo.chillsrestaurant.entities;

import com.ricardo.chillsrestaurant.dtos.PaymentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_payments")
public class Payment
{
    // All money amounts are represented as an integer amount of cents
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Integer subtotal;

    private Integer tax;

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private DiscountCode discountCode;

    private Integer total;

    private Integer paidAmount;

    public Boolean isPaid()
    {
        return paidAmount >= total;
    }

    public PaymentDto toPaymentDto()
    {
        return new PaymentDto(
                this.id,
                this.subtotal,
                this.tax,
                this.discountCode != null ? this.discountCode.getCode() : null,
                this.discountCode != null ? this.discountCode.getDiscountPercentage() : null,
                this.total,
                this.paidAmount
        );
    }
}
