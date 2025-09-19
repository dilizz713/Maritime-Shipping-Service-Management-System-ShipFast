package lk.ijse.gdse71.backend.util;

import lk.ijse.gdse71.backend.entity.Provision;
import lk.ijse.gdse71.backend.entity.ProvisionItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProvisionExcelGenerator {

    public static byte[] generateProvisionExcel(Provision provision) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Provision_" + provision.getProvisionReference());

        int rowCount = 0;

        // Provision Header
        Row infoRow1 = sheet.createRow(rowCount++);
        infoRow1.createCell(0).setCellValue("Provision Reference:");
        infoRow1.createCell(1).setCellValue(provision.getProvisionReference());

        Row infoRow2 = sheet.createRow(rowCount++);
        infoRow2.createCell(0).setCellValue("Job Reference:");
        infoRow2.createCell(1).setCellValue(provision.getJob() != null ? provision.getJob().getJobReference() : "");

        Row infoRow3 = sheet.createRow(rowCount++);
        infoRow3.createCell(0).setCellValue("Provision Date:");
        infoRow3.createCell(1).setCellValue(provision.getProvisionDate() != null ? provision.getProvisionDate().toString() : "");

        Row infoRow4 = sheet.createRow(rowCount++);
        infoRow4.createCell(0).setCellValue("Status:");
        infoRow4.createCell(1).setCellValue(provision.getStatus() != null ? provision.getStatus() : "");

        Row infoRow5 = sheet.createRow(rowCount++);
        infoRow5.createCell(0).setCellValue("Description:");
        infoRow5.createCell(1).setCellValue(provision.getDescription() != null ? provision.getDescription() : "");

        rowCount++;

        // Provision Items Header
        Row header = sheet.createRow(rowCount++);
        String[] headers = {"Product Code", "Product Name", "UOM", "Unit Price", "Quantity", "Remark"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Provision Items
        for (ProvisionItem item : provision.getItems()) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(item.getProductCode() != null ? item.getProductCode() : "");
            row.createCell(1).setCellValue(item.getProductName() != null ? item.getProductName() : "");
            row.createCell(2).setCellValue(item.getUomCode() != null ? item.getUomCode() : "");
            row.createCell(3).setCellValue(item.getUnitPrice() != null ? item.getUnitPrice() : 0.0);
            row.createCell(4).setCellValue(item.getQuantity() != null ? item.getQuantity() : 0);
            row.createCell(5).setCellValue(item.getRemark() != null ? item.getRemark() : "");
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}