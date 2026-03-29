package com.groupeisi.company.service;

import com.groupeisi.company.dto.VentesDto;
import com.groupeisi.company.entities.Ventes;
import com.groupeisi.company.mapper.VentesMapper;
import com.groupeisi.company.repository.VentesRepository;
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
public class VentesService {

    private final VentesRepository repository;
    private final VentesMapper mapper;

    @Cacheable(value = "ventes", key = "#id")
    public Optional<VentesDto> findById(Long id) {
        log.debug("Finding vente by id: {}", id);
        return repository.findById(id).map(mapper::toDto);
    }

    @Cacheable(value = "ventes")
    public List<VentesDto> findAll() {
        log.debug("Finding all ventes");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "ventes", allEntries = true)
    public VentesDto save(VentesDto dto) {
        log.info("Saving vente with id: {}", dto.getId());
        Ventes entity = mapper.toEntity(dto);
        Ventes saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @CacheEvict(value = "ventes", allEntries = true)
    public VentesDto update(Long id, VentesDto dto) {
        log.info("Updating vente with id: {}", id);
        return repository.findById(id).map(existing -> {
            dto.setId(id);
            Ventes entity = mapper.toEntity(dto);
            // Conserver les associations existantes (product, user)
            entity.setProduct(existing.getProduct());
            entity.setUser(existing.getUser());
            Ventes updated = repository.save(entity);
            return mapper.toDto(updated);
        }).orElseThrow(() -> {
            log.error("Vente not found with id: {}", id);
            return new RuntimeException("Vente not found with id " + id);
        });
    }

    @Transactional
    @CacheEvict(value = "ventes", allEntries = true)
    public void deleteById(Long id) {
        log.warn("Deleting vente with id: {}", id);
        repository.deleteById(id);
    }
}