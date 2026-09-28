package com.renteasy.renteasy.controller;

import com.renteasy.renteasy.entity.Tenant;
import com.renteasy.renteasy.repository.TenantRepository;
import com.renteasy.renteasy.service.TenantService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;
    private final TenantRepository tenantRepository;

    public TenantController(
            TenantService tenantService,
            TenantRepository tenantRepository) {

        this.tenantService = tenantService;
        this.tenantRepository = tenantRepository;
    }

    @PostMapping("/register/{roomId}")
    public ResponseEntity<Tenant> registerTenant(
            @PathVariable Long roomId,
            @Valid @RequestBody Tenant tenant) {

        return ResponseEntity.ok(
                tenantService.registerTenant(tenant, roomId)
        );
    }

    @GetMapping
    public ResponseEntity<List<Tenant>> getAllTenants() {

        return ResponseEntity.ok(
                tenantService.getAllTenants()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenantById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                tenantService.getTenantById(id)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<Tenant>> searchTenants(
            @RequestParam String name) {

        return ResponseEntity.ok(
                tenantRepository.findByNameContainingIgnoreCase(name)
        );
    }

    @PutMapping("/vacate/{id}")
    public ResponseEntity<Tenant> vacateTenant(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                tenantService.vacateTenant(id)
        );
    }
}