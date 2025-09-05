package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.dto.LoanEquipmentResponseDto;
import com.construcpunto.managament_equipments.dto.viewLoanDto;
import com.construcpunto.managament_equipments.entities.*;
import com.construcpunto.managament_equipments.exceptions.RequestException;
import com.construcpunto.managament_equipments.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private IPromissoyNoteRepository promissoyNoteRepository;

    @Autowired
    private IInvoiceRepository invoiceRepository;


    @Override
    public ClientEntity save(LoanEquipmentRequestDto loanEquipmentRequestDto) {
        ClientEntity client;

        PromissoryNoteEntity promissoryNote = new PromissoryNoteEntity();

        if (loanEquipmentRequestDto.getClientId() == 0L)
            client = loanEquipmentRequestDto.getClient();
        else
            client = clientRepository.findById(loanEquipmentRequestDto.getClientId()).orElseThrow(
                    () -> new RequestException("El cliente no se encuentra registrado", HttpStatus.NOT_FOUND));


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
        if (loanEquipmentRequestDto.getDeliveryCedula() != 0)
            delivery = deliveryRepository.findByCedula(loanEquipmentRequestDto.getDeliveryCedula()).
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
    public List<LoanEquipmentEntity> findAll() {
        return loanEquipmentRepository.findAll();
    }

    @Override
    public List<viewLoanDto> findAllViewLoanDto(Boolean active) {
        List<LoanEquipmentEntity> loanEquipments = findAll();
        List<LoanEquipmentEntity> loanEquipmentsActives = new ArrayList<>();
        List<LoanEquipmentEntity> loanEquipmentsNoActives = new ArrayList<>();

        if (active != null){
            for (LoanEquipmentEntity le : loanEquipments) {
                if (le.getEquipmentReturn())
                    loanEquipmentsActives.add(le);
                else
                    loanEquipmentsNoActives.add(le);
            }

            if (active) {
                return convertToDto(loanEquipmentsActives);
            }
            else if(!active)
                return convertToDto(loanEquipmentsNoActives);

        }

        return convertToDto(loanEquipmentRepository.findAll());    }


    @Override
    public LoanEquipmentResponseDto findByPromissoryId(Long promissoryId) {

        PromissoryNoteEntity promissoryNote = promissoyNoteRepository.findById(promissoryId).orElseThrow
                (() -> new RequestException("El cliente no tiene pagares pendientes", HttpStatus.NOT_FOUND));


        ClientEntity client = promissoryNote.getClient();

        DeliveryEntity delivery = promissoryNote.getDelivery();

        List<LoanEquipmentEntity> loanEquipments = promissoryNote.getLoanEquipment();

        LoanEquipmentResponseDto loanEquipmentResponse = new LoanEquipmentResponseDto(loanEquipments.size());

        String[][] equipments = new String[loanEquipments.size()][5];
        ;

        for (int i = 0; i < loanEquipments.size(); i++) {
            equipments[i][0] = loanEquipments.get(i).getQuantity().toString();
            equipments[i][1] = loanEquipments.get(i).getEquipment().getName();
            equipments[i][2] = String.valueOf(loanEquipments.get(i).getEquipment().getUnitPrice());
            equipments[i][3] = loanEquipments.get(i).getPriceDay().toString();
            equipments[i][4] = loanEquipments.get(i).getTotal().toString();
        }

        loanEquipmentResponse.setClientId(client.getId());
        loanEquipmentResponse.setClientName(client.getName());
        loanEquipmentResponse.setAddressClient(client.getAddress());
        loanEquipmentResponse.setNumberPhone(client.getNumberPhone());

        loanEquipmentResponse.setDeliveryName(delivery.getName());

        loanEquipmentResponse.setDeliveryDate(promissoryNote.getDeliveryDate());
        loanEquipmentResponse.setDeposit(promissoryNote.getDeposit());
        loanEquipmentResponse.setDeliveryPrice(promissoryNote.getDeliveryPrice());
        loanEquipmentResponse.setLoanEquipments(equipments);
        loanEquipmentResponse.setComments(promissoryNote.getComments());

        if (loanEquipments.getFirst().getEquipmentReturn()) {
            InvoiceEntity invoice = invoiceRepository.findById(promissoryNote.getId()).orElseThrow
                    (() -> new RequestException("No hay factura generada aun", HttpStatus.NOT_FOUND));

            loanEquipmentResponse.setDeliveryReturn(invoice.getReturnDate());
            loanEquipmentResponse.setTotalDays(invoice.getTotalDays());
            loanEquipmentResponse.setTotal(invoice.getTotal());
        }

        return loanEquipmentResponse;
    }

    @Override
    public void returnEquipment(Long promissoryNoteId) {
        InvoiceEntity invoice = new InvoiceEntity();
        Double totalInvoice = 0.0;

        LoanEquipmentEntity loanEquipment;
        EquipmentEntity equipment;

        LoanEquipmentResponseDto loanEquipmentResponseDto = this.findByPromissoryId(promissoryNoteId);

        PromissoryNoteEntity promissoryNote =
                promissoyNoteRepository.findById(promissoryNoteId).
                        orElseThrow(() -> new RequestException("El pagare no se encuentra", HttpStatus.NOT_FOUND));

        invoice.setId(promissoryNoteId);
        invoice.calculateDays(promissoryNote.getDeliveryDate());
        invoice.setPromissoryNote(promissoryNote);

        invoiceRepository.save(invoice);

        for (LoanEquipmentEntity le : promissoryNote.getLoanEquipment()) {
            loanEquipment = new LoanEquipmentEntity();
            equipment = new EquipmentEntity();

            equipment = le.getEquipment();

            loanEquipment = le;
            loanEquipment.reCalculateTotal(invoice.getTotalDays());
            if (!loanEquipment.getEquipmentReturn()) {
                equipment.setQuantity(equipment.getQuantity() + loanEquipment.getQuantity());
            }
            loanEquipment.setEquipmentReturn(true);


            equipmentRepository.save(equipment);
            loanEquipmentRepository.save(loanEquipment);

            totalInvoice += loanEquipment.getTotal();
        }
//        System.out.println("-------------------------" + "  " + loanEquipment.getEquipmentReturn()  + "/" + totalInvoice + "  " + "-------------------------");
        invoice.setTotal(totalInvoice);
        invoiceRepository.save(invoice);

    }

    List<viewLoanDto> convertToDto(List<LoanEquipmentEntity> loanEquipments) {
        Long idPrommissoryNoteBack = 0L;
        Long idPromissoryNote = 0L;

        viewLoanDto viewLoanDto = new viewLoanDto();
        List<viewLoanDto> viewLoanDtos = new ArrayList<>();

        System.out.println("-------------------------" + loanEquipments.size() + "-------------------------");


        for (int i = 0; i < loanEquipments.size(); i++) {
            viewLoanDto = new viewLoanDto();
            idPromissoryNote = loanEquipments.get(i).getPromissoryNote().getId();

            Integer cedula = loanEquipments.get(i).getPromissoryNote().getClient().getCedula();
            String clientName = loanEquipments.get(i).getPromissoryNote().getClient().getName();
            Boolean isReturn = loanEquipments.get(i).getEquipmentReturn();
            String equipmentName = loanEquipments.get(i).getEquipment().getName();

            if (!idPromissoryNote.equals(idPrommissoryNoteBack)) {
                viewLoanDto.setCedula(cedula);
                viewLoanDto.setClientName(clientName);
                viewLoanDto.getEquipmentName().add(equipmentName);
                viewLoanDto.setPromissoryNoteId(idPromissoryNote);

                viewLoanDtos.add(viewLoanDto);

            } else {
                viewLoanDtos.getLast().getEquipmentName().add(equipmentName);
            }

            idPrommissoryNoteBack = loanEquipments.get(i).getPromissoryNote().getId();

        }

        return viewLoanDtos;
    }


    @Override
    public void delete(LoanEquipmentEntity loanEquipment) {
        loanEquipmentRepository.delete(loanEquipment);
    }
}
