package com.groupeisi.company.controller;

import com.groupeisi.company.dto.ProduitsDto;
import com.groupeisi.company.service.ProduitsService;
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
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Tag(name = "Produits", description = "CRUD operations for products")
@SecurityRequirement(name = "Bearer Authentication")
public class ProduitsController {

    private final ProduitsService service;

    @GetMapping
    @Operation(summary = "Get all products")
    public ResponseEntity<List<ProduitsDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{ref}")
    @Operation(summary = "Get a product by reference")
    public ResponseEntity<ProduitsDto> findByRef(@PathVariable String ref) {
        return service.findByRef(ref)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<ProduitsDto> create(@Valid @RequestBody ProduitsDto dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{ref}")
    @Operation(summary = "Update an existing product")
    public ResponseEntity<ProduitsDto> update(@PathVariable String ref, @Valid @RequestBody ProduitsDto dto) {
        dto.setRef(ref);
        return ResponseEntity.ok(service.save(dto));
    }

    @DeleteMapping("/{ref}")
    @Operation(summary = "Delete a product")
    public ResponseEntity<Void> delete(@PathVariable String ref) {
        service.deleteByRef(ref);
        return ResponseEntity.noContent().build();
    }
}