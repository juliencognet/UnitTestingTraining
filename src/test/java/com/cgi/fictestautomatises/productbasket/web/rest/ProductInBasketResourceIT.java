package com.cgi.fictestautomatises.productbasket.web.rest;

import com.cgi.fictestautomatises.productbasket.FicTestsAutomatisesApp;
import com.cgi.fictestautomatises.productbasket.domain.ProductInBasket;
import com.cgi.fictestautomatises.productbasket.repository.ProductInBasketRepository;
import com.cgi.fictestautomatises.productbasket.service.ProductInBasketService;
import com.cgi.fictestautomatises.productbasket.service.dto.ProductInBasketDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.ProductInBasketMapper;
import com.cgi.fictestautomatises.productbasket.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.cgi.fictestautomatises.productbasket.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductInBasketResource} REST controller.
 */
@SpringBootTest(classes = FicTestsAutomatisesApp.class)
public class ProductInBasketResourceIT {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    @Autowired
    private ProductInBasketRepository productInBasketRepository;

    @Autowired
    private ProductInBasketMapper productInBasketMapper;

    @Autowired
    private ProductInBasketService productInBasketService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProductInBasketMockMvc;

    private ProductInBasket productInBasket;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductInBasketResource productInBasketResource = new ProductInBasketResource(productInBasketService);
        this.restProductInBasketMockMvc = MockMvcBuilders.standaloneSetup(productInBasketResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInBasket createEntity(EntityManager em) {
        ProductInBasket productInBasket = new ProductInBasket()
            .quantity(DEFAULT_QUANTITY);
        return productInBasket;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInBasket createUpdatedEntity(EntityManager em) {
        ProductInBasket productInBasket = new ProductInBasket()
            .quantity(UPDATED_QUANTITY);
        return productInBasket;
    }

    @BeforeEach
    public void initTest() {
        productInBasket = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductInBasket() throws Exception {
        int databaseSizeBeforeCreate = productInBasketRepository.findAll().size();

        // Create the ProductInBasket
        ProductInBasketDTO productInBasketDTO = productInBasketMapper.toDto(productInBasket);
        restProductInBasketMockMvc.perform(post("/api/product-in-baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInBasketDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductInBasket in the database
        List<ProductInBasket> productInBasketList = productInBasketRepository.findAll();
        assertThat(productInBasketList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInBasket testProductInBasket = productInBasketList.get(productInBasketList.size() - 1);
        assertThat(testProductInBasket.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createProductInBasketWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productInBasketRepository.findAll().size();

        // Create the ProductInBasket with an existing ID
        productInBasket.setId(1L);
        ProductInBasketDTO productInBasketDTO = productInBasketMapper.toDto(productInBasket);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInBasketMockMvc.perform(post("/api/product-in-baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInBasketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInBasket in the database
        List<ProductInBasket> productInBasketList = productInBasketRepository.findAll();
        assertThat(productInBasketList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQuantityIsRequired() throws Exception {
        int databaseSizeBeforeTest = productInBasketRepository.findAll().size();
        // set the field null
        productInBasket.setQuantity(null);

        // Create the ProductInBasket, which fails.
        ProductInBasketDTO productInBasketDTO = productInBasketMapper.toDto(productInBasket);

        restProductInBasketMockMvc.perform(post("/api/product-in-baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInBasketDTO)))
            .andExpect(status().isBadRequest());

        List<ProductInBasket> productInBasketList = productInBasketRepository.findAll();
        assertThat(productInBasketList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductInBaskets() throws Exception {
        // Initialize the database
        productInBasketRepository.saveAndFlush(productInBasket);

        // Get all the productInBasketList
        restProductInBasketMockMvc.perform(get("/api/product-in-baskets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInBasket.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)));
    }
    
    @Test
    @Transactional
    public void getProductInBasket() throws Exception {
        // Initialize the database
        productInBasketRepository.saveAndFlush(productInBasket);

        // Get the productInBasket
        restProductInBasketMockMvc.perform(get("/api/product-in-baskets/{id}", productInBasket.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(productInBasket.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY));
    }

    @Test
    @Transactional
    public void getNonExistingProductInBasket() throws Exception {
        // Get the productInBasket
        restProductInBasketMockMvc.perform(get("/api/product-in-baskets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductInBasket() throws Exception {
        // Initialize the database
        productInBasketRepository.saveAndFlush(productInBasket);

        int databaseSizeBeforeUpdate = productInBasketRepository.findAll().size();

        // Update the productInBasket
        ProductInBasket updatedProductInBasket = productInBasketRepository.findById(productInBasket.getId()).get();
        // Disconnect from session so that the updates on updatedProductInBasket are not directly saved in db
        em.detach(updatedProductInBasket);
        updatedProductInBasket
            .quantity(UPDATED_QUANTITY);
        ProductInBasketDTO productInBasketDTO = productInBasketMapper.toDto(updatedProductInBasket);

        restProductInBasketMockMvc.perform(put("/api/product-in-baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInBasketDTO)))
            .andExpect(status().isOk());

        // Validate the ProductInBasket in the database
        List<ProductInBasket> productInBasketList = productInBasketRepository.findAll();
        assertThat(productInBasketList).hasSize(databaseSizeBeforeUpdate);
        ProductInBasket testProductInBasket = productInBasketList.get(productInBasketList.size() - 1);
        assertThat(testProductInBasket.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingProductInBasket() throws Exception {
        int databaseSizeBeforeUpdate = productInBasketRepository.findAll().size();

        // Create the ProductInBasket
        ProductInBasketDTO productInBasketDTO = productInBasketMapper.toDto(productInBasket);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInBasketMockMvc.perform(put("/api/product-in-baskets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productInBasketDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductInBasket in the database
        List<ProductInBasket> productInBasketList = productInBasketRepository.findAll();
        assertThat(productInBasketList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductInBasket() throws Exception {
        // Initialize the database
        productInBasketRepository.saveAndFlush(productInBasket);

        int databaseSizeBeforeDelete = productInBasketRepository.findAll().size();

        // Delete the productInBasket
        restProductInBasketMockMvc.perform(delete("/api/product-in-baskets/{id}", productInBasket.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductInBasket> productInBasketList = productInBasketRepository.findAll();
        assertThat(productInBasketList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
