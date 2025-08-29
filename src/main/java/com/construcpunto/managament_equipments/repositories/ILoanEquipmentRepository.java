package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.LoanEquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoanEquipmentRepository extends JpaRepository<LoanEquipmentEntity, Long> {
}
