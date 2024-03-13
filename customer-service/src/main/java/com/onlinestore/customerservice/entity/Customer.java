package com.onlinestore.customerservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name="customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "ID can be empty.")
    @Size(min = 6, max = 10, message = "ID should length between 8 or 10")
    private String numberId;

    @NotEmpty(message = "Name can be null.")
    private String name;

    @Email(message = "Bad format for email")
    private String email;

    @NotNull(message = "Define a RegionDto.")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "region_id")
    private Region region;

    private String state;

    @Column(name="created_at")
    private Date createdAt;

}
