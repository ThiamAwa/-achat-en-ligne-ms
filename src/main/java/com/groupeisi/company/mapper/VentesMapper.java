package com.groupeisi.company.mapper;

import com.groupeisi.company.dto.VentesDto;
import com.groupeisi.company.entities.Produits;
import com.groupeisi.company.entities.UserAccount;
import com.groupeisi.company.entities.Ventes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class VentesMapper {

    @Autowired
    protected UserAccountMapper userAccountMapper;

    @Autowired
    protected ProduitsMapper produitsMapper;

    /**
     * Convertit l'entité Ventes en DTO
     */
    @Mapping(target = "productRef", source = "product.ref")
    @Mapping(target = "userId", source = "user.id")
    public abstract VentesDto toDto(Ventes ventes);

    /**
     * Convertit le DTO en entité Ventes
     */
    @Mapping(target = "product", source = "productRef", qualifiedByName = "refToProduits")
    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUserAccount")
    public abstract Ventes toEntity(VentesDto ventesDto);

    /**
     * Convertit une référence produit (String) en objet Produits (avec seulement la référence renseignée)
     */
    @Named("refToProduits")
    protected Produits refToProduits(String ref) {
        if (ref == null) return null;
        Produits produit = new Produits();
        produit.setRef(ref);
        return produit;
    }

    /**
     * Convertit un identifiant utilisateur (Long) en objet UserAccount (avec seulement l'id renseigné)
     */
    @Named("idToUserAccount")
    protected UserAccount idToUserAccount(Long id) {
        if (id == null) return null;
        UserAccount user = new UserAccount();
        user.setId(id);
        return user;
    }
}