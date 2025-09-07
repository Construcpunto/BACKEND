package com.construcpunto.managament_equipments.dto;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class viewLoanDto {

    private Integer cedula;
    private String clientName;
    private List<String> equipmentName;
    private LocalDateTime date;
    private Long promissoryNoteId;

    public viewLoanDto() {
        this.equipmentName = new ArrayList<>();
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getPromissoryNoteId() {
        return promissoryNoteId;
    }

    public void setPromissoryNoteId(Long promissoryNoteId) {
        this.promissoryNoteId = promissoryNoteId;
    }
}
