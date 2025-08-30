package com.construcpunto.managament_equipments.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "equipments")
public class EquipmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "equipment_name")
    @NotBlank
    @Size(max = 50)
    private String name;

    @Column(name = "unit_price")
    @NotNull
    private double unitPrice;

    @Size(max = 500)
    private String description;

    @NotNull
    private Integer quantity;

    @NotNull
    private Integer total;

    @JsonIgnore
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanEquipmentEntity> clients;


    public EquipmentEntity() {
        this.clients = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank @Size(max = 50) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(max = 50) String name) {
        this.name = name;
    }

    @NotNull
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(@NotNull double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public @NotBlank @Size(max = 500) String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank @Size(max = 500) String description) {
        this.description = description;
    }

    public List<LoanEquipmentEntity> getClients() {
        return clients;
    }

    public void setClients(List<LoanEquipmentEntity> clients) {
        this.clients = clients;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public @NotNull Integer getTotal() {
        return total;
    }

    public void setTotal(@NotNull Integer total) {
        this.total = total;
    }
}
