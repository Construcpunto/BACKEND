package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.EquipmentEntity;
import com.construcpunto.managament_equipments.exceptions.RequestException;
import com.construcpunto.managament_equipments.repositories.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements IClientService{

    @Autowired
    IClientRepository clientRepository;

    @Override
    public ClientEntity save(ClientEntity client) {
        return clientRepository.save(client);
    }

    @Override
    public List<ClientEntity> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<ClientEntity> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Optional<ClientEntity> findByCedula(Integer cedula) {
        return clientRepository.findByCedula(cedula);
    }

    @Override
    public Optional<ClientEntity> findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public List<ClientEntity> findByNameContainingIgnoreCase(String name) {
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public void delete(ClientEntity client) {
        clientRepository.delete(client);
    }
}
