package com.construcpunto.managament_equipments.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "promissory-note")
public class PromissoryNoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private ClientEntity client;

    @OneToMany(mappedBy = "promissoryNote", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanEquipmentEntity> loanEquipment;

    private LocalDateTime deliveryDate;

    @NotNull
    private Double deposit;

    @NotNull
    private Double deliveryPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private DeliveryEntity delivery;

    private String comments;

    public PromissoryNoteEntity() {
        this.loanEquipment = new ArrayList<>();
        deliveryDate = LocalDateTime.now();
        this.deliveryPrice = 0.0;
        this.deposit = 0.0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    public List<LoanEquipmentEntity> getLoanEquipment() {
        return loanEquipment;
    }

    public void setLoanEquipment(List<LoanEquipmentEntity> loanEquipment) {
        this.loanEquipment = loanEquipment;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
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

    public DeliveryEntity getDelivery() {
        return delivery;
    }

    public void setDelivery(DeliveryEntity delivery) {
        this.delivery = delivery;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
