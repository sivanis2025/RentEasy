package com.renteasy.renteasy.controller;

import com.renteasy.renteasy.entity.RentPayment;
import com.renteasy.renteasy.entity.Tenant;
import com.renteasy.renteasy.service.RentPaymentService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class RentPaymentController {

    private final RentPaymentService rentPaymentService;

    public RentPaymentController(RentPaymentService rentPaymentService) {
        this.rentPaymentService = rentPaymentService;
    }

    // CREATE PAYMENT
    @PostMapping("/{tenantId}")
    public ResponseEntity<RentPayment> recordPayment(
            @PathVariable Long tenantId,
            @RequestParam LocalDate paymentMonth,
            @RequestParam BigDecimal amount) {

        return ResponseEntity.ok(
                rentPaymentService.recordPayment(
                        tenantId,
                        paymentMonth,
                        amount
                )
        );
    }

    // GET ALL PAYMENTS
    @GetMapping
    public ResponseEntity<List<RentPayment>> getAllPayments() {

        return ResponseEntity.ok(
                rentPaymentService.getAllPayments()
        );
    }

    // GET PENDING DUES
    @GetMapping("/pending/{tenantId}")
    public ResponseEntity<Map<String, Object>> getPendingDues(
            @PathVariable Long tenantId) {

        int pendingMonths =
                rentPaymentService.getPendingMonths(tenantId);

        return ResponseEntity.ok(
                Map.of(
                        "tenantId", tenantId,
                        "pendingMonths", pendingMonths
                )
        );
    }

    // GET CURRENT MONTH UNPAID TENANTS
    @GetMapping("/unpaid/current")
    public ResponseEntity<List<Tenant>> getCurrentMonthUnpaidTenants() {

        return ResponseEntity.ok(
                rentPaymentService.getTenantsWithCurrentMonthDue()
        );
    }
}