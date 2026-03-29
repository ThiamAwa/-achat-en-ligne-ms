package com.groupeisi.company.repository;

import com.groupeisi.company.entities.Ventes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentesRepository extends JpaRepository<Ventes, Long> {
}