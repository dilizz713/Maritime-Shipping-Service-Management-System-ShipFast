package lk.ijse.gdse71.backend.util;

import lk.ijse.gdse71.backend.entity.ConfirmInquiry;
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


        for (InquiryItem item : inquiry.getItems()) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(item.getProduct().getName());
            row.createCell(1).setCellValue(item.getQuantity());
            row.createCell(2).setCellValue(item.getUnitPrice() != null ? item.getUnitPrice() : 0.0);
            row.createCell(3).setCellValue(item.getStatus() != null ? item.getStatus().name() : "N/A");
            row.createCell(4).setCellValue(item.getStatus() != null ? item.getRemarks() : "");
        }


        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }


    public static byte[] generateConfirmBillExcel(Inquiry inquiry, ConfirmInquiry confirm) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Workbook workbook = null;

        try {
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Bill_" + confirm.getBillNumber());

            int rowCount = 0;


            Row billRow = sheet.createRow(rowCount++);
            billRow.createCell(0).setCellValue("Bill Number:");
            billRow.createCell(1).setCellValue(confirm.getBillNumber());

            Row refRow = sheet.createRow(rowCount++);
            refRow.createCell(0).setCellValue("Reference Number:");
            refRow.createCell(1).setCellValue(inquiry.getReferenceNumber());

            Row descRow = sheet.createRow(rowCount++);
            descRow.createCell(0).setCellValue("Description:");
            descRow.createCell(1).setCellValue(confirm.getDescription());

            rowCount++;


            Row header = sheet.createRow(rowCount++);
            String[] headers = {"Product Code", "Product Name", "Quantity", "Remark"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                CellStyle style = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                style.setFont(font);
                cell.setCellStyle(style);
            }


            for (InquiryItem item : inquiry.getItems()) {
                Row row = sheet.createRow(rowCount++);
                row.createCell(0).setCellValue(item.getProduct().getCode() != null ? item.getProduct().getCode() : "");
                row.createCell(1).setCellValue(item.getProduct().getName() != null ? item.getProduct().getName() : "");
                row.createCell(2).setCellValue(item.getQuantity() != null ? item.getQuantity() : 0);
                row.createCell(3).setCellValue(item.getRemarks() != null ? item.getRemarks() : "");
            }


            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);
            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];

        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                outputStream.close();
            } catch (Exception ignore) {
            }
        }
    }


}
