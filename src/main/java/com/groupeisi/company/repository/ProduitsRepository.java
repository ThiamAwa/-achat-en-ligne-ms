package com.groupeisi.company.repository;

import com.groupeisi.company.entities.Produits;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitsRepository extends JpaRepository<Produits, String> {
}