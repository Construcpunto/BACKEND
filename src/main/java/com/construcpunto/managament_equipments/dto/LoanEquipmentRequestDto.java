package com.construcpunto.managament_equipments.dto;

import com.construcpunto.managament_equipments.entities.ClientEntity;
import com.construcpunto.managament_equipments.entities.DeliveryEntity;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LoanEquipmentRequestDto {

    private Long clientId;

    private ClientEntity client;

    private Integer deliveryCedula;

    private DeliveryEntity delivery;

    @NotNull
    private Double deposit;

    @NotNull
    private Double deliveryPrice;

    private String comments;

    @NotNull
    private Map<Long, Integer> equipmentIds;

    public LoanEquipmentRequestDto() {
        this.equipmentIds = new HashMap<>();
        this.clientId = 0L;
        this.deliveryCedula = 0;
        this.deliveryPrice = 0.0;
        this.deposit = 0.0;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public @NotNull Map<Long, Integer> getEquipmentIds() {
        return equipmentIds;
    }

    public void setEquipmentIds(@NotNull Map<Long, Integer> equipmentIds) {
        this.equipmentIds = equipmentIds;
    }

    public Integer getDeliveryCedula() {
        return deliveryCedula;
    }

    public void setDeliveryCedula(Integer deliveryCedula) {
        this.deliveryCedula = deliveryCedula;
    }

    public DeliveryEntity getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
    }

    public @NotNull Double getDeposit() {
        return deposit;
    }

    public void setDeposit(@NotNull Double deposit) {
        this.deposit = deposit;
    }

    public @NotNull Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(@NotNull Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
