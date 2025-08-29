package com.construcpunto.managament_equipments.dto;

import java.util.ArrayList;
import java.util.List;

public class viewLoanDto {

    private Long cedula;
    private String clientName;
    private List<String> equipmentName;
    private Long promissoryNoteId;

    public viewLoanDto() {
        this.equipmentName = new ArrayList<>();
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public List<String> getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(List<String> equipmentName) {
        this.equipmentName = equipmentName;
    }

    public Long getPromissoryNoteId() {
        return promissoryNoteId;
    }

    public void setPromissoryNoteId(Long promissoryNoteId) {
        this.promissoryNoteId = promissoryNoteId;
    }
}
