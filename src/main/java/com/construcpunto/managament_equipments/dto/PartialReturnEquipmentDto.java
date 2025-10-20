package com.construcpunto.managament_equipments.dto;

import java.time.LocalDate;
import java.util.List;

public class PartialReturnEquipmentDto {
    private List<PartialReturnDto> partialReturnDtos;
    private LocalDate date;

    public List<PartialReturnDto> getPartialReturnDtos() {
        return partialReturnDtos;
    }

    public void setPartialReturnDtos(List<PartialReturnDto> partialReturnDtos) {
        this.partialReturnDtos = partialReturnDtos;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
