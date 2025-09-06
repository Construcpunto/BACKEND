package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.EquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEquipmentRepository extends JpaRepository<EquipmentEntity, Long> {

    Optional<EquipmentEntity> findByName(String name);
    List<EquipmentEntity> findByNameContainingIgnoreCase(String name);

}
