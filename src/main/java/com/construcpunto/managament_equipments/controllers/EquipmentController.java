package com.construcpunto.managament_equipments.controllers;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.EquipmentEntity;
import com.construcpunto.managament_equipments.services.IEquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = {"http://localhost:5173"})
@RestController
@RequestMapping("/equipment")
public class EquipmentController {

    @Autowired
    private IEquipmentService equipmentService;

    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody EquipmentEntity equipment, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(equipmentService.save(equipment));
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(required = false) String name) {
        if (name != null) {
            return ResponseEntity.ok(equipmentService.findByNameContainingIgnoreCase(name));
        }
        return ResponseEntity.ok(equipmentService.findAll());
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<EquipmentEntity> equipmentOptional = equipmentService.findById(id);
        if (equipmentOptional.isPresent()) {
            equipmentService.delete(equipmentOptional.get());
            return ResponseEntity.ok(equipmentOptional.orElseThrow());
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
