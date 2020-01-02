package com.cgi.fictestautomatises.productbasket.web.rest;

import com.cgi.fictestautomatises.productbasket.FicTestsAutomatisesApp;
import com.cgi.fictestautomatises.productbasket.domain.DiscountCode;
import com.cgi.fictestautomatises.productbasket.repository.DiscountCodeRepository;
import com.cgi.fictestautomatises.productbasket.service.DiscountCodeService;
import com.cgi.fictestautomatises.productbasket.service.dto.DiscountCodeDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.DiscountCodeMapper;
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
 * Integration tests for the {@link DiscountCodeResource} REST controller.
 */
@SpringBootTest(classes = FicTestsAutomatisesApp.class)
public class DiscountCodeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Float DEFAULT_DISCOUNT = 1F;
    private static final Float UPDATED_DISCOUNT = 2F;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Autowired
    private DiscountCodeMapper discountCodeMapper;

    @Autowired
    private DiscountCodeService discountCodeService;

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

    private MockMvc restDiscountCodeMockMvc;

    private DiscountCode discountCode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DiscountCodeResource discountCodeResource = new DiscountCodeResource(discountCodeService);
        this.restDiscountCodeMockMvc = MockMvcBuilders.standaloneSetup(discountCodeResource)
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
    public static DiscountCode createEntity(EntityManager em) {
        DiscountCode discountCode = new DiscountCode()
            .code(DEFAULT_CODE)
            .discount(DEFAULT_DISCOUNT);
        return discountCode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DiscountCode createUpdatedEntity(EntityManager em) {
        DiscountCode discountCode = new DiscountCode()
            .code(UPDATED_CODE)
            .discount(UPDATED_DISCOUNT);
        return discountCode;
    }

    @BeforeEach
    public void initTest() {
        discountCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscountCode() throws Exception {
        int databaseSizeBeforeCreate = discountCodeRepository.findAll().size();

        // Create the DiscountCode
        DiscountCodeDTO discountCodeDTO = discountCodeMapper.toDto(discountCode);
        restDiscountCodeMockMvc.perform(post("/api/discount-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountCodeDTO)))
            .andExpect(status().isCreated());

        // Validate the DiscountCode in the database
        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeCreate + 1);
        DiscountCode testDiscountCode = discountCodeList.get(discountCodeList.size() - 1);
        assertThat(testDiscountCode.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testDiscountCode.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    public void createDiscountCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discountCodeRepository.findAll().size();

        // Create the DiscountCode with an existing ID
        discountCode.setId(1L);
        DiscountCodeDTO discountCodeDTO = discountCodeMapper.toDto(discountCode);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscountCodeMockMvc.perform(post("/api/discount-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountCodeRepository.findAll().size();
        // set the field null
        discountCode.setCode(null);

        // Create the DiscountCode, which fails.
        DiscountCodeDTO discountCodeDTO = discountCodeMapper.toDto(discountCode);

        restDiscountCodeMockMvc.perform(post("/api/discount-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountCodeDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = discountCodeRepository.findAll().size();
        // set the field null
        discountCode.setDiscount(null);

        // Create the DiscountCode, which fails.
        DiscountCodeDTO discountCodeDTO = discountCodeMapper.toDto(discountCode);

        restDiscountCodeMockMvc.perform(post("/api/discount-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountCodeDTO)))
            .andExpect(status().isBadRequest());

        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDiscountCodes() throws Exception {
        // Initialize the database
        discountCodeRepository.saveAndFlush(discountCode);

        // Get all the discountCodeList
        restDiscountCodeMockMvc.perform(get("/api/discount-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discountCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getDiscountCode() throws Exception {
        // Initialize the database
        discountCodeRepository.saveAndFlush(discountCode);

        // Get the discountCode
        restDiscountCodeMockMvc.perform(get("/api/discount-codes/{id}", discountCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discountCode.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscountCode() throws Exception {
        // Get the discountCode
        restDiscountCodeMockMvc.perform(get("/api/discount-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscountCode() throws Exception {
        // Initialize the database
        discountCodeRepository.saveAndFlush(discountCode);

        int databaseSizeBeforeUpdate = discountCodeRepository.findAll().size();

        // Update the discountCode
        DiscountCode updatedDiscountCode = discountCodeRepository.findById(discountCode.getId()).get();
        // Disconnect from session so that the updates on updatedDiscountCode are not directly saved in db
        em.detach(updatedDiscountCode);
        updatedDiscountCode
            .code(UPDATED_CODE)
            .discount(UPDATED_DISCOUNT);
        DiscountCodeDTO discountCodeDTO = discountCodeMapper.toDto(updatedDiscountCode);

        restDiscountCodeMockMvc.perform(put("/api/discount-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountCodeDTO)))
            .andExpect(status().isOk());

        // Validate the DiscountCode in the database
        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeUpdate);
        DiscountCode testDiscountCode = discountCodeList.get(discountCodeList.size() - 1);
        assertThat(testDiscountCode.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testDiscountCode.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscountCode() throws Exception {
        int databaseSizeBeforeUpdate = discountCodeRepository.findAll().size();

        // Create the DiscountCode
        DiscountCodeDTO discountCodeDTO = discountCodeMapper.toDto(discountCode);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDiscountCodeMockMvc.perform(put("/api/discount-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discountCodeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DiscountCode in the database
        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDiscountCode() throws Exception {
        // Initialize the database
        discountCodeRepository.saveAndFlush(discountCode);

        int databaseSizeBeforeDelete = discountCodeRepository.findAll().size();

        // Delete the discountCode
        restDiscountCodeMockMvc.perform(delete("/api/discount-codes/{id}", discountCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DiscountCode> discountCodeList = discountCodeRepository.findAll();
        assertThat(discountCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
