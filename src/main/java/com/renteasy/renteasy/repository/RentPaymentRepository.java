package com.renteasy.renteasy.repository;

import com.renteasy.renteasy.entity.RentPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentPaymentRepository
        extends JpaRepository<RentPayment, Long> {

    List<RentPayment> findByTenantTenantId(Long tenantId);
}