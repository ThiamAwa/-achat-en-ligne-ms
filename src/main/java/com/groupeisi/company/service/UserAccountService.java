package com.groupeisi.company.service;

import com.groupeisi.company.dto.UserAccountDto;
import com.groupeisi.company.entities.UserAccount;
import com.groupeisi.company.mapper.UserAccountMapper;
import com.groupeisi.company.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAccountService {

    private final UserAccountRepository repository;
    private final UserAccountMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Cacheable(value = "users", key = "#id")
    public Optional<UserAccountDto> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        return repository.findById(id).map(mapper::toDto);
    }

    @Cacheable(value = "users")
    public List<UserAccountDto> findAll() {
        log.debug("Finding all users");
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public UserAccountDto save(UserAccountDto dto) {
        log.info("Saving user with email: {}", dto.getEmail());
        // Encode password before saving
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        UserAccount entity = mapper.toEntity(dto);
        UserAccount saved = repository.save(entity);
        return mapper.toDto(saved);
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public void deleteById(Long id) {
        log.warn("Deleting user with id: {}", id);
        repository.deleteById(id);
    }

    public Optional<UserAccountDto> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return repository.findByEmail(email).map(mapper::toDto);
    }
}