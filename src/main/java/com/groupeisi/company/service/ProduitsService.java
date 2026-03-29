package com.groupeisi.company.service;

import com.groupeisi.company.dto.ProduitsDto;
import com.groupeisi.company.entities.Produits;
import com.groupeisi.company.mapper.ProduitsMapper;
import com.groupeisi.company.repository.ProduitsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProduitsService {

    private final ProduitsRepository repository;
    private final ProduitsMapper mapper;

    @Cacheable(value = "produits", key = "#ref")
    public Optional<ProduitsDto> findByRef(String ref) {
        log.debug("Finding product by ref: {}", ref);
        return repository.findById(ref).map(mapper::toDto);
    }

    @Cacheable(value = "produits")
    public List<ProduitsDto> findAll() {
        log.debug("Finding all products");
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "produits", allEntries = true)
    public ProduitsDto save(ProduitsDto dto) {
        log.info("Saving product with ref: {}", dto.getRef());
        Produits entity = mapper.toEntity(dto);
        Produits saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @CacheEvict(value = "produits", allEntries = true)
    public void deleteByRef(String ref) {
        log.warn("Deleting product with ref: {}", ref);
        repository.deleteById(ref);
    }
}