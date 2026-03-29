package com.groupeisi.company.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "produits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Produits implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    private String ref;

    private String name;

    private double stock;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
}