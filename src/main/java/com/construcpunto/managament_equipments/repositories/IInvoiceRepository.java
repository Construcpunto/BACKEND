package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IInvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
