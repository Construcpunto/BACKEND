package com.construcpunto.managament_equipments.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "promissory_note_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_promissory_note_id")
    )
    private PromissoryNoteEntity promissoryNote;

    @Column(name = "return_date")
    private LocalDateTime returnDate;

    @Column(name = "total_days")
    private Integer totalDays;

    private Double total;

    public InvoiceEntity() {
        this.returnDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PromissoryNoteEntity getPromissoryNote() {
        return promissoryNote;
    }

    public void setPromissoryNote(PromissoryNoteEntity promissoryNote) {
        this.promissoryNote = promissoryNote;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
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

    public void calculateDays(LocalDateTime deliveryDate) {
        long totalDaysLong = ChronoUnit.DAYS.between(deliveryDate, this.returnDate);
        this.totalDays = (int) totalDaysLong;

    }
}
