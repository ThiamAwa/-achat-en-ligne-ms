package com.groupeisi.company.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ventes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ventes implements Serializable {

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