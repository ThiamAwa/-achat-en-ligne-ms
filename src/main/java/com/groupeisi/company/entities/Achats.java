package com.groupeisi.company.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "achats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Achats implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date dateP;

    private double quantity;

    @ManyToOne
    @JoinColumn(name = "product_ref", referencedColumnName = "ref")
    private Produits product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;
}