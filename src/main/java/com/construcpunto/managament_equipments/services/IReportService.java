package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.InvoiceItemDto;
import net.sf.jasperreports.engine.JRException;

import java.util.List;
import java.util.Map;

public interface IReportService {

    void generatePromisoryNote(Map<String,Object> params, List<InvoiceItemDto> items) throws JRException;
}
