package com.renteasy.renteasy.service;

import com.renteasy.renteasy.entity.Room;
import com.renteasy.renteasy.entity.Tenant;
import com.renteasy.renteasy.repository.RoomRepository;
import com.renteasy.renteasy.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;
    private final RoomRepository roomRepository;

    public TenantService(
            TenantRepository tenantRepository,
            RoomRepository roomRepository) {

        this.tenantRepository = tenantRepository;
        this.roomRepository = roomRepository;
    }

    public Tenant registerTenant(Tenant tenant, Long roomId) {

        Room room = roomRepository.findById(roomId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Room not found with ID: " + roomId));

        if (room.isOccupied()) {
            throw new RuntimeException(
                    "Room " + room.getRoomNumber() + " is already occupied.");
        }

        tenant.setRoom(room);
        room.setOccupied(true);

        Tenant savedTenant = tenantRepository.save(tenant);

        roomRepository.save(room);

        return savedTenant;
    }

    public Tenant vacateTenant(Long tenantId) {

        Tenant tenant = tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tenant not found with ID: " + tenantId));

        Room room = tenant.getRoom();

        if (room != null) {
            room.setOccupied(false);
            roomRepository.save(room);
        }

        tenant.setActive(false);

        return tenantRepository.save(tenant);
    }

    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    public Tenant getTenantById(Long tenantId) {

        return tenantRepository.findById(tenantId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Tenant not found with ID: " + tenantId));
    }
}