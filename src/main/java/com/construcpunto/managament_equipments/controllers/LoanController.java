package com.construcpunto.managament_equipments.controllers;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.dto.PartialReturnDto;
import com.construcpunto.managament_equipments.dto.viewLoanDto;
import com.construcpunto.managament_equipments.services.ILoanEquipmentService;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@CrossOrigin(origins = {"http://localhost"})
@RestController
@RequestMapping("/loan")
public class LoanController {

    @Autowired
    ILoanEquipmentService loanEquipmentService;


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody LoanEquipmentRequestDto loanEquipmentRequestDto, BindingResult result) throws JRException {
        if (result.hasFieldErrors()) {
            return validation(result);
        }
        loanEquipmentService.save(loanEquipmentRequestDto);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("return-equipment/{promissoryNoteId}")
    public ResponseEntity<?> returnEquipment(@PathVariable Long promissoryNoteId) throws JRException {
        loanEquipmentService.returnEquipment(promissoryNoteId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("partial-return-equipment/{promissoryNoteId}")
    public ResponseEntity<?> partialReturnEquipments(@PathVariable Long promissoryNoteId,
                                                     @RequestBody List<PartialReturnDto> partialReturnDtos) throws JRException {
        loanEquipmentService.partialReturnEquipment(promissoryNoteId, partialReturnDtos);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/find-all")
    public ResponseEntity<?> findAll(@RequestParam(required = false) Boolean active) {
        List<viewLoanDto> viewLoanDtos = new ArrayList<>();
        viewLoanDtos = loanEquipmentService.findAllViewLoanDto(active);

        Collections.reverse(viewLoanDtos);
        return ResponseEntity.ok(viewLoanDtos);
    }

    @GetMapping("/find-loan")
    public ResponseEntity<?> findByPromissory(@RequestParam Long promissoryId) {
        return ResponseEntity.ok(loanEquipmentService.findByPromissoryId(promissoryId));
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filter(@RequestParam(required = false, name = "delivery-date")
                                    @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate deliveryDate,
                                    @RequestParam(required = false, name = "client-cedula") Integer clientCedula,
                                    @RequestParam() Boolean active) {

        List<viewLoanDto> viewLoanDtos = new ArrayList<>();

        if (deliveryDate != null || clientCedula != null) {
            viewLoanDtos = loanEquipmentService.filter(deliveryDate, clientCedula, active);
            Collections.reverse(viewLoanDtos);

            return ResponseEntity.ok(viewLoanDtos);

        }

        viewLoanDtos = loanEquipmentService.findAllViewLoanDto(active);


        return ResponseEntity.ok(viewLoanDtos);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
