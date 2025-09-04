package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClientRepository extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> findByCedula(Integer cedula);
    Optional<ClientEntity> findByName(String name);
}
