package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.EquipmentEntity;
import com.construcpunto.managament_equipments.entities.LoanEquipmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILoanEquipmentRepository extends JpaRepository<LoanEquipmentEntity, Long> {

}
