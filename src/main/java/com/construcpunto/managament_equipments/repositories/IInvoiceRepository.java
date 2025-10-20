package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.InvoiceEntity;
import com.construcpunto.managament_equipments.entities.PromissoryNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
    Optional<InvoiceEntity> findByPromissoryNote(PromissoryNoteEntity promissoryNote);
}
