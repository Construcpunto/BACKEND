package com.construcpunto.managament_equipments.controllers;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.services.IClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/find-by")
    public ResponseEntity<?> findById(@RequestParam(required = false, name = "client-cedula") Integer clientCedula,
                                      @RequestParam(required = false, name = "client-name") String clientName) {

        Optional<ClientEntity> clientOptional;
        List<ClientEntity> clients = new ArrayList<>();

        if(clientCedula != null){
            clientOptional = clientService.findByCedula(clientCedula);
            return ResponseEntity.ok(clientOptional);
        } else {
            clients = clientService.findByNameContainingIgnoreCase(clientName);
            return ResponseEntity.ok(clients);

        }
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody ClientEntity client,
                                  BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.save(client));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ClientEntity> clientOptional = clientService.findById(id);
        if (clientOptional.isPresent()) {
            clientService.delete(clientOptional.get());
            return ResponseEntity.ok(clientOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


}
