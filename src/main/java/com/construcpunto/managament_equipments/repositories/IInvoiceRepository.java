package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
