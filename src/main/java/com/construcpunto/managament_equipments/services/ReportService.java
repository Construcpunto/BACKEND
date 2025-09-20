package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.InvoiceItemDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService implements IReportService{

    @Override
    public void generatePromisoryNote(Map<String, Object> params, List<InvoiceItemDto> items) throws JRException {
//        params.put("REPORT_DIR", "/reports/Logo_CONSTRUCPUNTO.jpeg");
        params.put("REPORT_DIR", this.getClass().getResource("/reports/Logo_CONSTRUCPUNTO.jpeg").toString());

        InputStream reportStream = getClass().getResourceAsStream("/reports/construcpunto_invoice_params.jasper");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, dataSource);

        JasperExportManager.exportReportToPdfFile(jasperPrint, "C:/Users/juanm/Downloads/pagare.pdf");
    }
}
