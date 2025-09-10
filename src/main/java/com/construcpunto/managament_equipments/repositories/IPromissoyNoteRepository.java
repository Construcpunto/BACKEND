package com.construcpunto.managament_equipments.repositories;

import com.construcpunto.managament_equipments.entities.PromissoryNoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IPromissoyNoteRepository extends JpaRepository<PromissoryNoteEntity,Long> {

    @Query("SELECT p FROM PromissoryNoteEntity p WHERE p.client.id = ?1")
    List<PromissoryNoteEntity> findByClientId(Long clientId);

    List<PromissoryNoteEntity> findByDeliveryDate(LocalDate deliveryDate);
}
