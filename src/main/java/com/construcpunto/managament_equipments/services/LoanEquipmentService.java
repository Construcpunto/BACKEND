package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.LoanEquipmentRequestDto;
import com.construcpunto.managament_equipments.dto.LoanEquipmentResponseDto;
import com.construcpunto.managament_equipments.dto.PartialReturnDto;
import com.construcpunto.managament_equipments.dto.viewLoanDto;
import com.construcpunto.managament_equipments.entities.*;
import com.construcpunto.managament_equipments.exceptions.RequestException;
import com.construcpunto.managament_equipments.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        if (loanEquipmentRequestDto.getDeliveryCedula() != 0) {
            delivery = deliveryRepository.findByCedula(loanEquipmentRequestDto.getDeliveryCedula()).
                    orElseThrow(() -> new RequestException("El domiciliario no se encuentra registrado", HttpStatus.NOT_FOUND));
            promissoryNote.setDelivery(delivery);
            promissoryNote.setDeliveryPrice(loanEquipmentRequestDto.getDeliveryPrice());

        } else if(loanEquipmentRequestDto.getDelivery() != null){
            delivery = loanEquipmentRequestDto.getDelivery();
            deliveryRepository.save(delivery);
            promissoryNote.setDelivery(delivery);
            promissoryNote.setDeliveryPrice(loanEquipmentRequestDto.getDeliveryPrice());

        }

        promissoryNote.setDeposit(loanEquipmentRequestDto.getDeposit());
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

        if (active != null) {
            for (LoanEquipmentEntity le : loanEquipments) {
                if (le.getEquipmentReturn())
                    loanEquipmentsNoActives.add(le);
                else
                    loanEquipmentsActives.add(le);
            }

            if (active) {
                return convertToDto(loanEquipmentsActives);
            } else if (!active)
                return convertToDto(loanEquipmentsNoActives);

        }

        return convertToDto(loanEquipmentRepository.findAll());
    }

    @Override
    public List<viewLoanDto> filter(LocalDate deliveryDate, Integer clientCedula) {
        List<PromissoryNoteEntity> promissoryNotes = new ArrayList<>();
        List<LoanEquipmentEntity> loanEquipments = new ArrayList<>();

        if (deliveryDate != null) {
            promissoryNotes.addAll(promissoyNoteRepository.findByDeliveryDate(deliveryDate));

        }

        if (clientCedula != null) {
            ClientEntity client =
                    clientRepository.findByCedula(clientCedula).
                            orElseThrow(() -> new RequestException("El cliente no tiene pagares pendientes", HttpStatus.NOT_FOUND));

            promissoryNotes.addAll(promissoyNoteRepository.findByClientId(client.getId()));

        }

        for (PromissoryNoteEntity promissory : promissoryNotes) {
            loanEquipments.addAll(promissory.getLoanEquipment());
        }

        return convertToDto(loanEquipments);
    }


    @Override
    public LoanEquipmentResponseDto findByPromissoryId(Long promissoryId) {

        PromissoryNoteEntity promissoryNote = promissoyNoteRepository.findById(promissoryId).orElseThrow
                (() -> new RequestException("El cliente no tiene pagares pendientes", HttpStatus.NOT_FOUND));


        ClientEntity client = promissoryNote.getClient();

        List<LoanEquipmentEntity> loanEquipments = promissoryNote.getLoanEquipment();
        LoanEquipmentResponseDto loanEquipmentResponse = new LoanEquipmentResponseDto(loanEquipments.size());
        String[][] equipments = new String[loanEquipments.size()][6];

        DeliveryEntity delivery = new DeliveryEntity();
        if (promissoryNote.getDelivery() != null) {
            delivery = promissoryNote.getDelivery();
            loanEquipmentResponse.setDeliveryName(delivery.getName());
        }

        for (int i = 0; i < loanEquipments.size(); i++) {
            equipments[i][0] = loanEquipments.get(i).getQuantity().toString();
            equipments[i][1] = loanEquipments.get(i).getEquipment().getName();
            equipments[i][2] = String.valueOf(loanEquipments.get(i).getEquipment().getUnitPrice());
            equipments[i][3] = loanEquipments.get(i).getPriceDay().toString();
            if (loanEquipments.get(i).quantityDays(promissoryNote.getDeliveryDate()) <= 5)
                equipments[i][4] = loanEquipments.get(i).getTotal().toString();
            else {
                Double totalUpdate = (loanEquipments.get(i).getTotal() * loanEquipments.get(i).quantityDays(promissoryNote.getDeliveryDate()));
                equipments[i][4] = String.valueOf(totalUpdate);
            }
            equipments[i][5] = loanEquipments.get(i).getEquipment().getId().toString();


        }

        loanEquipmentResponse.setClientCedula(client.getCedula());
        loanEquipmentResponse.setClientName(client.getName());
        loanEquipmentResponse.setAddressClient(client.getAddress());
        loanEquipmentResponse.setNumberPhone(client.getNumberPhone());


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
        invoice.setTotal(totalInvoice);
        invoiceRepository.save(invoice);

    }

    @Override
    public void partialReturnEquipment(Long promissoryNoteId, List<PartialReturnDto> partialReturnDto) {
        PromissoryNoteEntity promissoryNoteDB =
                promissoyNoteRepository.findById(promissoryNoteId).
                        orElseThrow(() -> new RequestException("El pagare no se encuentra", HttpStatus.NOT_FOUND));


        PromissoryNoteEntity promissoryNote = new PromissoryNoteEntity();

        List<LoanEquipmentEntity> loanEquipments = new ArrayList<>();

        LoanEquipmentEntity loan = new LoanEquipmentEntity();

        EquipmentEntity equipment = new EquipmentEntity();

        promissoryNote.setClient(promissoryNoteDB.getClient());
        promissoryNote.setDeliveryDate(promissoryNoteDB.getDeliveryDate());
        promissoryNote.setDeposit(promissoryNoteDB.getDeposit());
        if (promissoryNoteDB.getDelivery() != null) {
            promissoryNote.setDelivery(promissoryNoteDB.getDelivery());
            promissoryNote.setDeliveryPrice(promissoryNote.getDeliveryPrice());
        }
        if (promissoryNoteDB.getComments() != null)
            promissoryNote.setComments(promissoryNoteDB.getComments());

        for (PartialReturnDto partialReturn : partialReturnDto) {
            System.out.println("-------------------------" + "  " + partialReturn.toString() + "  " + "-------------------------");

            equipment = new EquipmentEntity();
            loan = new LoanEquipmentEntity();
            equipment = equipmentRepository.findById(partialReturn.getEquipmentId()).orElseThrow(() -> new RequestException("No se encuentra el equipo", HttpStatus.NOT_FOUND));

            loan.setEquipment(equipment);
            loan.setPromissoryNote(promissoryNote);
            loan.setQuantity(partialReturn.getQuantity());
            loan.setPriceDay(equipment.getUnitPrice() * loan.getQuantity());
            loan.setTotal(loan.getPriceDay() * loan.quantityDays(promissoryNote.getDeliveryDate()));

            loanEquipments.add(loan);
        }

        for (int i = 0; i < promissoryNoteDB.getLoanEquipment().size(); i++) {
            LoanEquipmentEntity loanE = promissoryNoteDB.getLoanEquipment().get(i);

            for (PartialReturnDto partialReturn : partialReturnDto) {
                if (loanE.getEquipment().getId().equals(partialReturn.getEquipmentId())) {

                    promissoryNoteDB.getLoanEquipment().get(i).setQuantity(loanE.getQuantity() - partialReturn.getQuantity());
                    promissoryNoteDB.getLoanEquipment().get(i).reCaluclatePriceDay(loanE.getEquipment().getUnitPrice());

                    long totalDays = promissoryNoteDB.getLoanEquipment().get(i).quantityDays(promissoryNote.getDeliveryDate());
                    System.out.println("-------------------------" + "  " + totalDays + "  " + "-------------------------");

                    promissoryNoteDB.getLoanEquipment().get(i).reCalculateTotal((int) totalDays);
                }
            }
        }

        promissoryNote.setLoanEquipment(loanEquipments);




        promissoyNoteRepository.save(promissoryNoteDB);
        returnEquipment(promissoryNoteId);
        promissoyNoteRepository.save(promissoryNote);


    }

    List<viewLoanDto> convertToDto(List<LoanEquipmentEntity> loanEquipments) {
        Long idPromissoryNoteBack = 0L;
        Long idPromissoryNote = 0L;

        viewLoanDto viewLoanDto = new viewLoanDto();
        List<viewLoanDto> viewLoanDtos = new ArrayList<>();

        for (LoanEquipmentEntity loanEquipment : loanEquipments) {
            viewLoanDto = new viewLoanDto();
            idPromissoryNote = loanEquipment.getPromissoryNote().getId();

            Integer cedula = loanEquipment.getPromissoryNote().getClient().getCedula();
            String clientName = loanEquipment.getPromissoryNote().getClient().getName();
            LocalDate dateTime = loanEquipment.getPromissoryNote().getDeliveryDate();
            Boolean isReturn = loanEquipment.getEquipmentReturn();
            String equipmentName = loanEquipment.getEquipment().getName();
//            System.out.println("-------------------------" + dateTime + "/" + i +"-------------------------");

            if (!idPromissoryNote.equals(idPromissoryNoteBack)) {
                viewLoanDto.setCedula(cedula);
                viewLoanDto.setClientName(clientName);
                viewLoanDto.getEquipmentName().add(equipmentName);
                viewLoanDto.setPromissoryNoteId(idPromissoryNote);


                viewLoanDto.setDate(dateTime);

                viewLoanDtos.add(viewLoanDto);

            } else {
                viewLoanDtos.getLast().getEquipmentName().add(equipmentName);
            }

            idPromissoryNoteBack = loanEquipment.getPromissoryNote().getId();

        }

        return viewLoanDtos;
    }

    @Override
    public void delete(LoanEquipmentEntity loanEquipment) {
        loanEquipmentRepository.delete(loanEquipment);
    }
}
