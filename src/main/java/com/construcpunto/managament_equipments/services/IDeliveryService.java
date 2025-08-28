package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.entities.DeliveryEntity;

import java.util.Optional;

public interface IDeliveryService {

    DeliveryEntity save(DeliveryEntity delivery);

    Optional<DeliveryEntity> findById(Long id);


}
