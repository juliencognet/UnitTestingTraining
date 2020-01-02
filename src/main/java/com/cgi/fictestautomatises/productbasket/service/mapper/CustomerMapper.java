package com.cgi.fictestautomatises.productbasket.service.mapper;

import com.cgi.fictestautomatises.productbasket.domain.*;
import com.cgi.fictestautomatises.productbasket.service.dto.CustomerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Customer} and its DTO {@link CustomerDTO}.
 */
@Mapper(componentModel = "spring", uses = {BasketMapper.class})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {

    @Mapping(source = "basket.id", target = "basketId")
    CustomerDTO toDto(Customer customer);

    @Mapping(source = "basketId", target = "basket")
    Customer toEntity(CustomerDTO customerDTO);

    default Customer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }
}
