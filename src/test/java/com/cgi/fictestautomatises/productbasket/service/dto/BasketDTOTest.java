package com.cgi.fictestautomatises.productbasket.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.cgi.fictestautomatises.productbasket.web.rest.TestUtil;

public class BasketDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BasketDTO.class);
        BasketDTO basketDTO1 = new BasketDTO();
        basketDTO1.setId(1L);
        BasketDTO basketDTO2 = new BasketDTO();
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
        basketDTO2.setId(basketDTO1.getId());
        assertThat(basketDTO1).isEqualTo(basketDTO2);
        basketDTO2.setId(2L);
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
        basketDTO1.setId(null);
        assertThat(basketDTO1).isNotEqualTo(basketDTO2);
    }
}
