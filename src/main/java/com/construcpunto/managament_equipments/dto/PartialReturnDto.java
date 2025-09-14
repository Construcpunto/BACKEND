package com.construcpunto.managament_equipments.dto;

public class PartialReturnDto {
    private Long equipmentId;
    private Integer quantity;


    public PartialReturnDto() {
    }


    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PartialReturnDto{" +
                "equipmentId=" + equipmentId +
                ", quantity=" + quantity +
                '}';
    }
}
