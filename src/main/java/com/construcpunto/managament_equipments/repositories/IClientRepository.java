package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends JpaRepository<ClientEntity, Long> {
}
