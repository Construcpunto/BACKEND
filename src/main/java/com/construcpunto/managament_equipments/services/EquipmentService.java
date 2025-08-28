package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.entities.EquipmentEntity;
import com.construcpunto.managament_equipments.repositories.IEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipmentService implements IEquipmentService {

    @Autowired
    IEquipmentRepository equipmentRepository;

    @Override
    public EquipmentEntity save(EquipmentEntity equipment) {
        return equipmentRepository.save(equipment);
    }

    @Override
    public Optional<EquipmentEntity> findById(long id) {
        return equipmentRepository.findById(id);
    }

    @Override
    public Optional<EquipmentEntity> findByName(String name) {
        return equipmentRepository.findByName(name);
    }

    @Override
    public void delete(EquipmentEntity equipment) {
        equipmentRepository.delete(equipment);
    }

    @Override
    public List<EquipmentEntity> findAll() {
        return equipmentRepository.findAll();
    }
}
