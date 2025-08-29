package com.construcpunto.managament_equipments.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class ClientEntity {

    @Id
    @Column(name = "client_id")
    private Long id;

    @Column(name = "cient_name")
    @NotBlank
    @Size(max = 50)
    private String name;

    @Column(name = "address")
    @NotBlank
    @Size(max = 100)
    private String address;

    @Column(name = "number_phone")
    @NotBlank
    @Size(max = 50)
    private String numberPhone;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PromissoryNoteEntity> promissoryNotes;

    public ClientEntity() {
         this.promissoryNotes = new ArrayList<>();
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

    public @NotBlank @Size(max = 100) String getAddress() {
        return address;
    }

    public void setAddress(@NotBlank @Size(max = 100) String address) {
        this.address = address;
    }

    public @NotBlank @Size(max = 50) String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(@NotBlank @Size(max = 50) String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public List<PromissoryNoteEntity> getPromissoryNotes() {
        return promissoryNotes;
    }

    public void setPromissoryNotes(List<PromissoryNoteEntity> promissoryNotes) {
        this.promissoryNotes = promissoryNotes;
    }
}
