package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.entities.ClientEntity;

import java.util.List;
import java.util.Optional;

public interface IClientService {
    ClientEntity save(ClientEntity client);
    List<ClientEntity> findAll();
    Optional<ClientEntity> findById(Long id);
    void delete(ClientEntity client);
}
