package com.groupeisi.company.mapper;

import com.groupeisi.company.dto.AchatsDto;
import com.groupeisi.company.entities.Achats;
import com.groupeisi.company.entities.Produits;
import com.groupeisi.company.entities.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AchatsMapper {

    @Autowired
    protected UserAccountMapper userAccountMapper; // optional, but for consistency
    @Autowired
    protected ProduitsMapper produitsMapper;

    @Mapping(target = "productRef", source = "product.ref")
    @Mapping(target = "userId", source = "user.id")
    public abstract AchatsDto toDto(Achats achats);

    @Mapping(target = "product", source = "productRef", qualifiedByName = "refToProduits")
    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUserAccount")
    public abstract Achats toEntity(AchatsDto achatsDto);

    @Named("refToProduits")
    protected Produits refToProduits(String ref) {
        if (ref == null) return null;
        Produits produit = new Produits();
        produit.setRef(ref);
        return produit;
    }

    @Named("idToUserAccount")
    protected UserAccount idToUserAccount(Long id) {
        if (id == null) return null;
        UserAccount user = new UserAccount();
        user.setId(id);
        return user;
    }
}