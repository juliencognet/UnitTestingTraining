package com.cgi.fictestautomatises.productbasket.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class DiscountCodeMapperTest {

    private DiscountCodeMapper discountCodeMapper;

    @BeforeEach
    public void setUp() {
        discountCodeMapper = new DiscountCodeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(discountCodeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(discountCodeMapper.fromId(null)).isNull();
    }
}
