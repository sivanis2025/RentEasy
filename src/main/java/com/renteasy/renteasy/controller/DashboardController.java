package com.renteasy.renteasy.controller;

import com.renteasy.renteasy.repository.RentPaymentRepository;
import com.renteasy.renteasy.repository.RoomRepository;
import com.renteasy.renteasy.repository.TenantRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;
    private final RentPaymentRepository rentPaymentRepository;

    public DashboardController(
            TenantRepository tenantRepository,
            RoomRepository roomRepository,
            RentPaymentRepository rentPaymentRepository) {

        this.tenantRepository = tenantRepository;
        this.roomRepository = roomRepository;
        this.rentPaymentRepository = rentPaymentRepository;
    }

    @GetMapping
    public ResponseEntity<Map<String, Long>> getDashboard() {

        long totalTenants = tenantRepository.count();

        long activeTenants =
                tenantRepository.countByActiveTrue();

        long totalRooms =
                roomRepository.count();

        long occupiedRooms =
                roomRepository.countByOccupiedTrue();

        long availableRooms =
                totalRooms - occupiedRooms;

        long totalPayments =
                rentPaymentRepository.count();

        Map<String, Long> dashboard = Map.of(
                "totalTenants", totalTenants,
                "activeTenants", activeTenants,
                "totalRooms", totalRooms,
                "occupiedRooms", occupiedRooms,
                "availableRooms", availableRooms,
                "totalPayments", totalPayments
        );

        return ResponseEntity.ok(dashboard);
    }
}