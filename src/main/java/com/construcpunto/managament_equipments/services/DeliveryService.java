package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.entities.DeliveryEntity;
import com.construcpunto.managament_equipments.repositories.IDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeliveryService implements IDeliveryService{

    @Autowired
    IDeliveryRepository deliveryRepository;

    @Override
    public DeliveryEntity save(DeliveryEntity delivery) {
        return deliveryRepository.save(delivery);
    }

    @Override
    public Optional<DeliveryEntity> findById(Long id) {
        return deliveryRepository.findById(id);
    }

    @Override
    public Optional<DeliveryEntity> findByCedula(Integer deliveryCedula) {
        return deliveryRepository.findByCedula(deliveryCedula);
    }
}
