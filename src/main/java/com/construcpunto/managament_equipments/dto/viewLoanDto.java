package com.construcpunto.managament_equipments.dto;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class viewLoanDto {

    private Integer cedula;
    private String clientName;
    private List<String> equipmentName;
    private LocalDate deliveryDate;
    private LocalDate returnDate;
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

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Long getPromissoryNoteId() {
        return promissoryNoteId;
    }

    public void setPromissoryNoteId(Long promissoryNoteId) {
        this.promissoryNoteId = promissoryNoteId;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
