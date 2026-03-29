package com.groupeisi.company.controller;

import com.groupeisi.company.dto.VentesDto;
import com.groupeisi.company.service.VentesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventes")
@RequiredArgsConstructor
@Tag(name = "Ventes", description = "CRUD operations for sales")
@SecurityRequirement(name = "Bearer Authentication")
public class VentesController {

    private final VentesService service;

    @GetMapping
    @Operation(summary = "Get all sales")
    public ResponseEntity<List<VentesDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a sale by ID")
    public ResponseEntity<VentesDto> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new sale")
    public ResponseEntity<VentesDto> create(@Valid @RequestBody VentesDto dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing sale")
    public ResponseEntity<VentesDto> update(@PathVariable Long id, @Valid @RequestBody VentesDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a sale")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}