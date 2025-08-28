package com.construcpunto.managament_equipments.controllers;

import com.construcpunto.managament_equipments.entities.DeliveryEntity;
import com.construcpunto.managament_equipments.services.IDeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    IDeliveryService deliveryService;

    @PostMapping("save")
    public ResponseEntity<?> save(@Valid  @RequestBody DeliveryEntity delivery, BindingResult result){
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        deliveryService.save(delivery);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
