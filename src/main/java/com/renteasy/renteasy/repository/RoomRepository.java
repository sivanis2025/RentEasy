package com.renteasy.renteasy.repository;

import com.renteasy.renteasy.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    long countByOccupiedTrue();

}