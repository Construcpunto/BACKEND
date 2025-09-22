package com.construcpunto.managament_equipments.services;

import com.construcpunto.managament_equipments.dto.InvoiceItemDto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService implements IReportService {

    @Value("${app.path.save.report.dir}")
    String pathSave;

    String timestamp ;

    @Override
    public void generatePromisoryNote(Map<String, Object> params, List<InvoiceItemDto> items, Boolean invoice) throws JRException {
        timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

        String clientCedula = "";

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getKey().equals("clientCedula")) {
                clientCedula = entry.getValue().toString();
            }
        }

        params.put("REPORT_DIR", this.getClass().getResource("/reports/Logo_CONSTRUCPUNTO.jpeg").toString());

        InputStream reportStream = getClass().getResourceAsStream("/reports/construcpunto_invoice_params.jasper");

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, params, dataSource);

        if (invoice)
            JasperExportManager.exportReportToPdfFile
                    (jasperPrint, pathSave + "Facturas/factura_cc_" + clientCedula + "_" + timestamp + ".pdf");
        else
            JasperExportManager.exportReportToPdfFile
                    (jasperPrint, pathSave + "Pagares/pagare_cc_" + clientCedula + "_" + timestamp + ".pdf");

        System.out.println(pathSave + "Pagares/pagare_cc:" + clientCedula + ".pdf");

    }
}
