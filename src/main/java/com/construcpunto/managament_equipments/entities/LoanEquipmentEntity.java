package com.construcpunto.managament_equipments.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "loan-equipment")
public class LoanEquipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private EquipmentEntity equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promissory_note_id")
    private PromissoryNoteEntity promissoryNote;

    private Integer quantity;

    @Column(name = "price_day")
    private Double priceDay;

    private Double total;

    @Column(name = "equipment_return")
    private Boolean equipmentReturn;

    public LoanEquipmentEntity() {
        this.equipmentReturn = false;
    }


    public EquipmentEntity getEquipment() {
        return equipment;
    }

    public void setEquipment(EquipmentEntity equipment) {
        this.equipment = equipment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPriceDay() {
        return priceDay;
    }

    public void setPriceDay(Double priceDay) {
        this.priceDay = priceDay;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEquipmentReturn() {
        return equipmentReturn;
    }

    public void setEquipmentReturn(Boolean equipmentReturn) {
        this.equipmentReturn = equipmentReturn;
    }

    public PromissoryNoteEntity getPromissoryNote() {
        return promissoryNote;
    }

    public void setPromissoryNote(PromissoryNoteEntity promissoryNote) {
        this.promissoryNote = promissoryNote;
    }
}
