package com.groupeisi.company.service;

import com.groupeisi.company.dto.AchatsDto;
import com.groupeisi.company.entities.Achats;
import com.groupeisi.company.mapper.AchatsMapper;
import com.groupeisi.company.repository.AchatsRepository;
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
public class AchatsService {

    private final AchatsRepository repository;
    private final AchatsMapper mapper;

    @Cacheable(value = "achats", key = "#id")
    public Optional<AchatsDto> findById(Long id) {
        log.debug("Finding achat by id: {}", id);
        return repository.findById(id).map(mapper::toDto);
    }

    @Cacheable(value = "achats")
    public List<AchatsDto> findAll() {
        log.debug("Finding all achats");
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "achats", allEntries = true)
    public AchatsDto save(AchatsDto dto) {
        log.info("Saving achat with id: {}", dto.getId());
        Achats entity = mapper.toEntity(dto);
        Achats saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @CacheEvict(value = "achats", allEntries = true)
    public AchatsDto update(Long id, AchatsDto dto) {
        log.info("Updating achat with id: {}", id);
        return repository.findById(id).map(existing -> {
            dto.setId(id);
            Achats entity = mapper.toEntity(dto);
            // Conserver les associations si nécessaire (product, user)
            entity.setProduct(existing.getProduct());
            entity.setUser(existing.getUser());
            Achats updated = repository.save(entity);
            return mapper.toDto(updated);
        }).orElseThrow(() -> {
            log.error("Achat not found with id: {}", id);
            return new RuntimeException("Achat not found with id " + id);
        });
    }

    @Transactional
    @CacheEvict(value = "achats", allEntries = true)
    public void deleteById(Long id) {
        log.warn("Deleting achat with id: {}", id);
        repository.deleteById(id);
    }
}