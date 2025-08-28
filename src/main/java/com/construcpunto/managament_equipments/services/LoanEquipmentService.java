package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.dto.viewLoanDto;
import com.construcpunto.managament_equipments.entities.*;
import com.construcpunto.managament_equipments.exceptions.RequestException;
import com.construcpunto.managament_equipments.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanEquipmentService implements ILoanEquipmentService {

    @Autowired
    private IClientRepository clientRepository;

    @Autowired
    private IEquipmentRepository equipmentRepository;

    @Autowired
    private ILoanEquipmentRepository loanEquipmentRepository;

    @Autowired
    private IDeliveryRepository deliveryRepository;

    @Autowired
    IPromissoyNoteRepository promissoyNoteRepository;


    @Override
    public ClientEntity save(LoanEquipmentRequestDto loanEquipmentRequestDto) {
        ClientEntity client;

        PromissoryNoteEntity promissoryNote = new PromissoryNoteEntity();

        if (loanEquipmentRequestDto.getClientId() == 0L)
            client = loanEquipmentRequestDto.getClient();
        else
            client = clientRepository.findById(loanEquipmentRequestDto.getClientId()).orElseThrow(
                    () -> new RequestException("El cliente no se encuentra registrado", HttpStatus.NOT_FOUND));


        System.out.println("-------------------------" + "  " + verifyStockBeforePromissoryNote(loanEquipmentRequestDto) + "  " + "-------------------------");
        if (verifyStockBeforePromissoryNote(loanEquipmentRequestDto) > 0) {
            promissoryNote = makePromissoryNote(loanEquipmentRequestDto);
            promissoryNote.setClient(client);
            promissoryNote = promissoyNoteRepository.save(promissoryNote);


            for (Map.Entry<Long, Integer> entry : loanEquipmentRequestDto.getEquipmentIds().entrySet()) {

                Optional<EquipmentEntity> equipment = Optional.of(new EquipmentEntity());
                LoanEquipmentEntity loanEquipment = new LoanEquipmentEntity();

                loanEquipment = new LoanEquipmentEntity();
                equipment = Optional.of(new EquipmentEntity());
                loanEquipment.setPromissoryNote(promissoryNote);

                equipment = equipmentRepository.findById(entry.getKey());

                if (equipment.isEmpty())
                    throw new RequestException("El equipo no existe", HttpStatus.NOT_FOUND);
                else {
                    if (equipment.get().getQuantity() < entry.getValue())
                        throw new RequestException("No hay la cantidad disponible para el equipo: " + equipment.get().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
                    else {
                        loanEquipment = loadLoanEquipment(loanEquipment, equipment.get(), entry.getValue());
                    }
                }
                promissoryNote.getLoanEquipment().add(loanEquipment);

            }
        }

        return clientRepository.save(client);
    }

    Integer verifyStockBeforePromissoryNote(LoanEquipmentRequestDto loanEquipmentRequestDto) {
        Integer availableEquipments = 0;
        Optional<EquipmentEntity> equipment = Optional.of(new EquipmentEntity());

        for (Map.Entry<Long, Integer> entry : loanEquipmentRequestDto.getEquipmentIds().entrySet()) {
            equipment = equipmentRepository.findById(entry.getKey());

            if (equipment.isEmpty())
                throw new RequestException("El equipo no existe.", HttpStatus.NOT_FOUND);
            else {
                if (equipment.get().getQuantity() < entry.getValue()) {
                    throw new RequestException("No hay la cantidad disponible para el equipo: " + equipment.get().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
                } else
                    availableEquipments++;
            }
        }

        return availableEquipments;
    }

    LoanEquipmentEntity loadLoanEquipment(LoanEquipmentEntity loanEquipment, EquipmentEntity equipment, Integer quantity) {
        loanEquipment.setEquipment(equipment);
        loanEquipment.setQuantity(quantity);
        loanEquipment.setPriceDay(equipment.getUnitPrice() * quantity);
        loanEquipment.setTotal(loanEquipment.getPriceDay() * 5);

        equipment.setQuantity(equipment.getQuantity() - quantity);

        equipmentRepository.save(equipment);

        return loanEquipment;
    }

    PromissoryNoteEntity makePromissoryNote(LoanEquipmentRequestDto loanEquipmentRequestDto) {
        PromissoryNoteEntity promissoryNote = new PromissoryNoteEntity();
        DeliveryEntity delivery = new DeliveryEntity();
        if (loanEquipmentRequestDto.getDeliveryId() != 0L)
            delivery = deliveryRepository.findById(loanEquipmentRequestDto.getDeliveryId()).
                    orElseThrow(() -> new RequestException("El domiciliario no se encuentra registrado", HttpStatus.NOT_FOUND));
        else
            delivery = loanEquipmentRequestDto.getDelivery();


        promissoryNote.setDeposit(loanEquipmentRequestDto.getDeposit());
        promissoryNote.setDelivery(delivery);
        promissoryNote.setDeliveryPrice(loanEquipmentRequestDto.getDeliveryPrice());
        promissoryNote.setComments(loanEquipmentRequestDto.getComments());

        return promissoryNote;
    }

    @Override
    public List<viewLoanDto> findAll() {
        List<LoanEquipmentEntity> loanEquipments = loanEquipmentRepository.findAll();

        return List.of();
    }

    @Override
    public Optional<LoanEquipmentEntity> findById(Long id) {
        return loanEquipmentRepository.findById(id);
    }

    @Override
    public void delete(LoanEquipmentEntity loanEquipment) {
        loanEquipmentRepository.delete(loanEquipment);
    }
}
