package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.entities.EquipmentEntity;

import java.util.List;
import java.util.Optional;

public interface IEquipmentService {
    EquipmentEntity save(EquipmentEntity equipment);

    List<EquipmentEntity> findAll();
    Optional<EquipmentEntity> findById(long id);
    Optional<EquipmentEntity> findByName(String name);
    List<EquipmentEntity> findByNameContainingIgnoreCase(String name);

    void delete(EquipmentEntity equipment);

}
