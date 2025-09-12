package lk.ijse.gdse71.backend.util;

import lk.ijse.gdse71.backend.entity.Inquiry;
import lk.ijse.gdse71.backend.entity.InquiryItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ExcelGenerator {
    public static byte[] generateInquiryExcel(Inquiry inquiry) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Inquiry_" + inquiry.getId());

        int rowCount = 0;

        // Header
        Row header = sheet.createRow(rowCount++);
        String[] headers = {"Product Name", "Quantity", "Unit Price", "Status","Remark"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(headers[i]);
            CellStyle style = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            style.setFont(font);
            cell.setCellStyle(style);
        }

        // Data rows
        for (InquiryItem item : inquiry.getItems()) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(item.getProduct().getName());
            row.createCell(1).setCellValue(item.getQuantity());
            row.createCell(2).setCellValue(item.getUnitPrice() != null ? item.getUnitPrice() : 0.0);
            row.createCell(3).setCellValue(item.getStatus() != null ? item.getStatus().name() : "N/A");
            row.createCell(4).setCellValue(item.getStatus() != null ? item.getRemarks() : "");
        }

        // Autosize columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }
}
