package com.renteasy.renteasy.controller;

import com.renteasy.renteasy.repository.RentPaymentRepository;
import com.renteasy.renteasy.repository.RoomRepository;
import com.renteasy.renteasy.repository.TenantRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardPageController {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;
    private final RentPaymentRepository rentPaymentRepository;

    public DashboardPageController(
            TenantRepository tenantRepository,
            RoomRepository roomRepository,
            RentPaymentRepository rentPaymentRepository) {

        this.tenantRepository = tenantRepository;
        this.roomRepository = roomRepository;
        this.rentPaymentRepository = rentPaymentRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        long totalTenants = tenantRepository.count();

        long activeTenants =
                tenantRepository.countByActiveTrue();

        long totalRooms = roomRepository.count();

        long occupiedRooms =
                roomRepository.countByOccupiedTrue();

        long availableRooms =
                totalRooms - occupiedRooms;

        long totalPayments =
                rentPaymentRepository.count();

        model.addAttribute("totalTenants", totalTenants);
        model.addAttribute("activeTenants", activeTenants);
        model.addAttribute("totalRooms", totalRooms);
        model.addAttribute("occupiedRooms", occupiedRooms);
        model.addAttribute("availableRooms", availableRooms);
        model.addAttribute("totalPayments", totalPayments);

        return "dashboard";
    }

    @GetMapping("/tenants")
    public String tenants() {
        return "tenants";
    }

    @GetMapping("/rooms")
    public String rooms(Model model) {

        model.addAttribute("rooms", roomRepository.findAll());

        return "rooms";
    }

    @GetMapping("/payments")
    public String payments() {
        return "payments";
    }
}