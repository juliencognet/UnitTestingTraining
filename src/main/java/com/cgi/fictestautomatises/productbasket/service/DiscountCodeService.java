package com.cgi.fictestautomatises.productbasket.service;

import com.cgi.fictestautomatises.productbasket.domain.DiscountCode;
import com.cgi.fictestautomatises.productbasket.repository.DiscountCodeRepository;
import com.cgi.fictestautomatises.productbasket.service.dto.DiscountCodeDTO;
import com.cgi.fictestautomatises.productbasket.service.mapper.DiscountCodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DiscountCode}.
 */
@Service
@Transactional
public class DiscountCodeService {

    private final Logger log = LoggerFactory.getLogger(DiscountCodeService.class);

    private final DiscountCodeRepository discountCodeRepository;

    private final DiscountCodeMapper discountCodeMapper;

    public DiscountCodeService(DiscountCodeRepository discountCodeRepository, DiscountCodeMapper discountCodeMapper) {
        this.discountCodeRepository = discountCodeRepository;
        this.discountCodeMapper = discountCodeMapper;
    }

    /**
     * Save a discountCode.
     *
     * @param discountCodeDTO the entity to save.
     * @return the persisted entity.
     */
    public DiscountCodeDTO save(DiscountCodeDTO discountCodeDTO) {
        log.debug("Request to save DiscountCode : {}", discountCodeDTO);
        DiscountCode discountCode = discountCodeMapper.toEntity(discountCodeDTO);
        discountCode = discountCodeRepository.save(discountCode);
        return discountCodeMapper.toDto(discountCode);
    }

    /**
     * Get all the discountCodes.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DiscountCodeDTO> findAll() {
        log.debug("Request to get all DiscountCodes");
        return discountCodeRepository.findAll().stream()
            .map(discountCodeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one discountCode by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiscountCodeDTO> findOne(Long id) {
        log.debug("Request to get DiscountCode : {}", id);
        return discountCodeRepository.findById(id)
            .map(discountCodeMapper::toDto);
    }

    /**
     * Get one discountCode by code.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiscountCode> findOneByCode(String code) {
        log.debug("Request to get DiscountCode : {} by code", code);
        return discountCodeRepository.findOneByCode(code);
    }

    /**
     * Delete the discountCode by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DiscountCode : {}", id);
        discountCodeRepository.deleteById(id);
    }
}
