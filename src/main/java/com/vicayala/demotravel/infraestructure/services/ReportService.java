package com.vicayala.demotravel.infraestructure.services;

import com.vicayala.demotravel.domain.entities.CustomerEntity;
import com.vicayala.demotravel.domain.repositories.CustomerRepository;
import com.vicayala.demotravel.infraestructure.abstract_services.IReportService;
import com.vicayala.demotravel.util.ServiceConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;

@Service
@AllArgsConstructor
@Slf4j
public class ReportService implements IReportService {

    private final CustomerRepository customerRepository;

    @Override
    public byte[] readFile() {
        try {
            this.createReport();
            var path = Paths.get(ServiceConstants.REPORTS_PATH,
                    String.format(ServiceConstants.FILE_NAME, LocalDate.now().getMonth()))
                    .toAbsolutePath();
            return Files.readAllBytes(path);
        } catch (IOException ioe){
            throw new RuntimeException();
        }
    }

    private void createReport(){
        var workbook = new XSSFWorkbook();
        var sheet = workbook.createSheet(ServiceConstants.SHEET_NAME);

        sheet.setColumnWidth(0, 5500);
        sheet.setColumnWidth(1, 7000);
        sheet.setColumnWidth(2, 4000);
        var header = sheet.createRow(0);
        var headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE1.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        var font = workbook.createFont();
        font.setFontName(ServiceConstants.FONT_TYPE);
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);

        headerStyle.setFont(font);

        var headerCell = header.createCell(0);
        headerCell.setCellValue(ServiceConstants.COLUMN_CUSTOMER_ID);
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(1);
        headerCell.setCellValue(ServiceConstants.COLUMN_CUSTOMER_NAME);
        headerCell.setCellStyle(headerStyle);
        headerCell = header.createCell(2);
        headerCell.setCellValue(ServiceConstants.COLUMN_CUSTOMER_PURCHASES);
        headerCell.setCellStyle(headerStyle);

        var style = workbook.createCellStyle();
        style.setWrapText(true);

        var customers = this.customerRepository.findAll();
        var rowPos = 1;
        for (CustomerEntity customer:customers){
            var row  = sheet.createRow(rowPos);
            var cell = row.createCell(0);
            cell.setCellValue(customer.getDni());
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue(customer.getFullName());
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue(getTotalPurchase(customer));
            cell.setCellStyle(style);

            rowPos++;
        }

        var report = new File(String.format(ServiceConstants.REPORTS_PATH_WITH_NAME,
            LocalDate.now().getMonth()));
        var path = report.getAbsolutePath();
        var fileLocation = path + ServiceConstants.FILE_TYPE;

        try (var outputStream = new FileOutputStream(fileLocation)){
            workbook.write(outputStream);
            workbook.close();
        }catch (IOException ioe){
            log.error("Cant create Excel", ExceptionUtils.getStackTrace(ioe));
            throw  new RuntimeException();
        }

    }

    private static int getTotalPurchase(CustomerEntity customer){
        return customer.getTotalLodgings() +
                customer.getTotalFlights() +
                customer.getTotalTours();
    }
}
