package com.berber_co.barber.data.mapstruct;

import com.berber_co.barber.data.response.SellerProfileResponse;
import com.berber_co.barber.entity.barber.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerProfileMapper {
    @Mapping(target = "fullName", expression = "java(seller.getFirstName() + \" \" + seller.getLastName())")
    @Mapping(target = "membershipDate", expression = "java(seller.getCreatedDate())")
    SellerProfileResponse toResponse(Seller seller);
}
