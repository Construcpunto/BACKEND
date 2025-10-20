package com.construcpunto.managament_equipments.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate returnDate;

    @Column(name = "total_days")
    private Integer totalDays;

    private Double total;

    public InvoiceEntity() {
        this.returnDate =  LocalDate.now();
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

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
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

    public void calculateDays(LocalDate deliveryDate) {
        long totalDaysLong = ChronoUnit.DAYS.between(deliveryDate, this.returnDate);
        this.totalDays = (int) totalDaysLong;

        if (this.totalDays.equals(0))
            totalDays = 1;

    }
}
