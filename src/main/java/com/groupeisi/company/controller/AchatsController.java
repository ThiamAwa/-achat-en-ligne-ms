package com.groupeisi.company.controller;

import com.groupeisi.company.dto.AchatsDto;
import com.groupeisi.company.service.AchatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/achats")
@RequiredArgsConstructor
@Tag(name = "Achats", description = "CRUD operations for purchases")
@SecurityRequirement(name = "Bearer Authentication")
public class AchatsController {

    private final AchatsService service;

    @GetMapping
    @Operation(summary = "Get all purchases")
    public ResponseEntity<List<AchatsDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a purchase by ID")
    public ResponseEntity<AchatsDto> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new purchase")
    public ResponseEntity<AchatsDto> create(@Valid @RequestBody AchatsDto dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing purchase")
    public ResponseEntity<AchatsDto> update(@PathVariable Long id, @Valid @RequestBody AchatsDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a purchase")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}