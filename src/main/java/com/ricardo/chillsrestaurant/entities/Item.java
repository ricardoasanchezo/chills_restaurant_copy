package com.ricardo.chillsrestaurant.entities;

import com.ricardo.chillsrestaurant.dtos.ItemDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_items")
public class Item
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String description;

    private Integer price; // Stored as an integer amount of cents

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Category category;

    @ManyToMany(targetEntity = Tag.class)
    @JoinTable(
            foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT),
            inverseForeignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT)
    )
    private List<Tag> tags;

    private String picture;

    private Boolean isOnMenu;

    public ItemDto toItemDto()
    {
        return new ItemDto(
                this.id,
                this.name,
                this.description,
                this.price,
                this.category.getName(),
                this.tags.stream().map(Tag::getName).toList(),
                this.picture,
                this.isOnMenu
        );
    }
}
