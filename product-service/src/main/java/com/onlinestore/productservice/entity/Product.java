package com.onlinestore.productservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Entity
@NoArgsConstructor(force = true)
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name couldn't be empty.")
    private String name;
    private String description;

    @NotNull
    @Positive(message = "Stock non negative.")
    private Double stock;

    @NotNull
    @Min(0)
    private Double price;
    private String status;

    @Column(name = "created_at")
    private Date createdAt;

    @NotNull(message = "Category can be empty.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

}
