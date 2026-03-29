package com.groupeisi.company.mapper;

import com.groupeisi.company.dto.ProduitsDto;
import com.groupeisi.company.entities.Produits;
import com.groupeisi.company.entities.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = {UserAccountMapper.class})
public abstract class ProduitsMapper {

    @Autowired
    protected UserAccountMapper userAccountMapper;

    @Mapping(target = "userId", source = "user", qualifiedByName = "userToId")
    public abstract ProduitsDto toDto(Produits produits);

    @Mapping(target = "user", source = "userId", qualifiedByName = "idToUser")
    public abstract Produits toEntity(ProduitsDto produitsDto);

    @Named("userToId")
    protected Long userToId(UserAccount user) {
        return user != null ? user.getId() : null;
    }

    @Named("idToUser")
    protected UserAccount idToUser(Long id) {
        if (id == null) return null;
        UserAccount user = new UserAccount();
        user.setId(id);
        return user;
    }
}