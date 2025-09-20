package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.dto.LoanEquipmentResponseDto;
import com.construcpunto.managament_equipments.dto.PartialReturnDto;
import com.construcpunto.managament_equipments.dto.viewLoanDto;
import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.LoanEquipmentEntity;
import net.sf.jasperreports.engine.JRException;

import java.time.LocalDate;
import java.util.List;

public interface ILoanEquipmentService {

    ClientEntity save(LoanEquipmentRequestDto loanEquipmentRequestDto) throws JRException;

    List<viewLoanDto> findAllViewLoanDto(Boolean active);
    List<LoanEquipmentEntity> findAll();
    LoanEquipmentResponseDto findByPromissoryId(Long promissoryId);
    List<viewLoanDto> filter(LocalDate deliveryDate, Integer clientCedula);

    void delete(LoanEquipmentEntity loanEquipment);

    void returnEquipment(Long promissoryNoteId);
    void partialReturnEquipment(Long promissoryNoteId, List<PartialReturnDto> partialReturnDto);

}
