package com.construcpunto.managament_equipments.dto;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.DeliveryEntity;

import java.time.LocalDateTime;

public class LoanEquipmentResponseDto {

    private Long clientId;
    private String clientName;
    private String addressClient;
    private String numberPhone;
    private String deliveryName;

    private LocalDateTime deliveryDate;
    private LocalDateTime deliveryReturn;

    private Double deposit;
    private Double deliveryPrice;
    private String[][] loanEquipments;
    private Integer totalDays;
    private Double total;
    private String comments;

    public LoanEquipmentResponseDto(int rows) {
        this.loanEquipments = new String[rows][5];
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public LocalDateTime getDeliveryReturn() {
        return deliveryReturn;
    }

    public void setDeliveryReturn(LocalDateTime deliveryReturn) {
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
