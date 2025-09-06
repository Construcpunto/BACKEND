package com.construcpunto.managament_equipments.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    private Integer cedula;

    @JsonIgnore
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromissoryNoteEntity> promissoryNotes;

    public DeliveryEntity() {
        this.promissoryNotes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public List<PromissoryNoteEntity> getPromissoryNotes() {
        return promissoryNotes;
    }

    public void setPromissoryNotes(List<PromissoryNoteEntity> promissoryNotes) {
        this.promissoryNotes = promissoryNotes;
    }
}
