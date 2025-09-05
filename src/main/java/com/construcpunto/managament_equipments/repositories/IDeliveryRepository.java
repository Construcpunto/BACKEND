package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IDeliveryRepository extends JpaRepository<DeliveryEntity, Long> {
    Optional<DeliveryEntity> findByCedula(Integer cedula);
}
