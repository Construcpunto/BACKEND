package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
}
