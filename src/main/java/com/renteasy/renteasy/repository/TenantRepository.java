package com.renteasy.renteasy.repository;

import com.renteasy.renteasy.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TenantRepository extends JpaRepository<Tenant, Long> {

    long countByActiveTrue();

    List<Tenant> findByNameContainingIgnoreCase(String name);
}