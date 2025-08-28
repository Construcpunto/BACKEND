package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEquipmentRepository extends JpaRepository<EquipmentEntity, Long> {

    Optional<EquipmentEntity> findByName(String name);
}
