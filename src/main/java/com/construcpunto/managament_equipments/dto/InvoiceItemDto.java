package com.construcpunto.managament_equipments.dto;

public class InvoiceItemDto {

    private String equipmentQuantity;
    private String equipmentName;
    private String equipmentUnitPrice;
    private String loanValueDay;
    private String loanTotal;

    public InvoiceItemDto() {
    }

    public InvoiceItemDto(String equipmentQuantity, String equipmentName, String equipmentUnitPrice, String loanValueDay, String loanTotal) {
        this.equipmentQuantity = equipmentQuantity;
        this.equipmentName = equipmentName;
        this.equipmentUnitPrice = equipmentUnitPrice;
        this.loanValueDay = loanValueDay;
        this.loanTotal = loanTotal;
    }

    public String getEquipmentQuantity() {
        return equipmentQuantity;
    }

    public void setEquipmentQuantity(String equipmentQuantity) {
        this.equipmentQuantity = equipmentQuantity;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentUnitPrice() {
        return equipmentUnitPrice;
    }

    public void setEquipmentUnitPrice(String equipmentUnitPrice) {
        this.equipmentUnitPrice = equipmentUnitPrice;
    }

    public String getLoanValueDay() {
        return loanValueDay;
    }

    public void setLoanValueDay(String loanValueDay) {
        this.loanValueDay = loanValueDay;
    }

    public String getLoanTotal() {
        return loanTotal;
    }

    public void setLoanTotal(String loanTotal) {
        this.loanTotal = loanTotal;
    }

}
