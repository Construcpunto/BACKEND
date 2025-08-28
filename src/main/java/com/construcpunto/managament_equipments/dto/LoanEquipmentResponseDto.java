package com.construcpunto.managament_equipments.dto;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.DeliveryEntity;

import java.time.LocalDateTime;

public class LoanEquipmentResponseDto {

    private ClientEntity client;
    private DeliveryEntity delivery;
    private LocalDateTime deliveryDate;
    private LocalDateTime deliveryReturn;
    private Double deposit;
    private Double deliveryPrice;
    private String[][] loanEquipments;
    private Integer totalDays;
    private Double total;


    public LoanEquipmentResponseDto(int rows) {
        this.loanEquipments = new String[rows][5];
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public DeliveryEntity getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
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
}
