package com.construcpunto.managament_equipments.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LoanEquipmentResponseDto {

    private Integer clientCedula;
    private String clientName;
    private String addressClient;
    private String numberPhone;

    private String deliveryName;
    private String deliveryPhone;
    private Double deliveryPrice;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate deliveryDate;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate deliveryReturn;

    private Double deposit;
    private String[][] loanEquipments;
    private Integer totalDays;
    private Double total;
    private String comments;

    public LoanEquipmentResponseDto(int rows) {
        this.loanEquipments = new String[rows][6];
        this.deliveryName = "";
    }

    public Integer getClientCedula() {
        return clientCedula;
    }

    public void setClientCedula(Integer clientCedula) {
        this.clientCedula = clientCedula;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAddressClient() {
        return addressClient;
    }

    public void setAddressClient(String addressClient) {
        this.addressClient = addressClient;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDate getDeliveryReturn() {
        return deliveryReturn;
    }

    public void setDeliveryReturn(LocalDate deliveryReturn) {
        this.deliveryReturn = deliveryReturn;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String[][] getLoanEquipments() {
        return loanEquipments;
    }

    public void setLoanEquipments(String[][] loanEquipments) {
        this.loanEquipments = loanEquipments;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
