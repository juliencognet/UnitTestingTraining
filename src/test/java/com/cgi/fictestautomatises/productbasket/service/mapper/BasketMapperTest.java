package com.cgi.fictestautomatises.productbasket.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class BasketMapperTest {

    private BasketMapper basketMapper;

    @BeforeEach
    public void setUp() {
        basketMapper = new BasketMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 2L;
        assertThat(basketMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(basketMapper.fromId(null)).isNull();
    }
}
