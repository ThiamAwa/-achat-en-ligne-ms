package com.groupeisi.company.mapper;

import com.groupeisi.company.dto.UserAccountDto;
import com.groupeisi.company.entities.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {
    UserAccountMapper INSTANCE = Mappers.getMapper(UserAccountMapper.class);

    UserAccountDto toDto(UserAccount userAccount);
    UserAccount toEntity(UserAccountDto userAccountDto);
}