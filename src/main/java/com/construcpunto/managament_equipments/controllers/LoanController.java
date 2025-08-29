package com.construcpunto.managament_equipments.controllers;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.services.ILoanEquipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    ILoanEquipmentService loanEquipmentService;


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody LoanEquipmentRequestDto loanEquipmentRequestDto, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        loanEquipmentService.save(loanEquipmentRequestDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }


    @GetMapping("/find-all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(loanEquipmentService.findAll());
    }

    @GetMapping("/find-loan")
    public ResponseEntity<?> findByPromissory(@RequestParam Long promissoryId){
        return ResponseEntity.ok(loanEquipmentService.findByPromissoryId(promissoryId));
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
