package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.dto.LoanEquipmentResponseDto;
import com.construcpunto.managament_equipments.dto.viewLoanDto;
import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.LoanEquipmentEntity;

import java.util.List;
import java.util.Optional;

public interface ILoanEquipmentService {

    ClientEntity save(LoanEquipmentRequestDto loanEquipmentRequestDto);
    List<viewLoanDto> findAll();
    Optional<LoanEquipmentEntity> findById(Long id);
    void delete(LoanEquipmentEntity loanEquipment);
}
