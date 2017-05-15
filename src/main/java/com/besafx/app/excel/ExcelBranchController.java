package com.besafx.app.excel;
import com.besafx.app.entity.Branch;
import com.besafx.app.entity.Company;
import com.besafx.app.entity.Person;
import com.besafx.app.service.BranchService;
import com.besafx.app.service.CompanyService;
import com.besafx.app.service.PersonService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ExcelBranchController {

    private final static Logger log = LoggerFactory.getLogger(ExcelBranchController.class);

    private SecureRandom random;

    @Autowired
    private BranchService branchService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private PersonService personService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ExcelCellHelper excelCellHelper;

    @PostConstruct
    public void init() {
        random = new SecureRandom();
    }

    @RequestMapping(value = "/api/heavy-work/branch/write", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void writeBranchFile(HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("اضافة فروع جديدة دفعة واحدة");
        Row row = sheet.createRow(0);
        row.setHeightInPoints((short) 25);
        //
        XSSFFont fontColumnHeader = workbook.createFont();
        fontColumnHeader.setBold(true);
        fontColumnHeader.setColor(IndexedColors.WHITE.getIndex());
        //
        XSSFFont fontCellDate = workbook.createFont();
        fontCellDate.setBold(true);
        fontCellDate.setColor(IndexedColors.BLACK.getIndex());
        //
        XSSFCellStyle styleColumnHeader = workbook.createCellStyle();
        styleColumnHeader.setFont(fontColumnHeader);
        styleColumnHeader.setAlignment(HorizontalAlignment.CENTER);
        styleColumnHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        styleColumnHeader.setBorderTop(BorderStyle.THIN);
        styleColumnHeader.setBorderLeft(BorderStyle.THIN);
        styleColumnHeader.setBorderBottom(BorderStyle.THIN);
        styleColumnHeader.setBorderRight(BorderStyle.THIN);
        styleColumnHeader.setFillForegroundColor(new XSSFColor(Color.decode("#795548")));
        styleColumnHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //
        XSSFCellStyle styleCellDate = workbook.createCellStyle();
        styleCellDate.setFont(fontCellDate);
        styleCellDate.setAlignment(HorizontalAlignment.CENTER);
        styleCellDate.setVerticalAlignment(VerticalAlignment.CENTER);
        styleCellDate.setBorderTop(BorderStyle.THIN);
        styleCellDate.setBorderLeft(BorderStyle.THIN);
        styleCellDate.setBorderBottom(BorderStyle.THIN);
        styleCellDate.setBorderRight(BorderStyle.THIN);
        styleCellDate.setFillForegroundColor(new XSSFColor(Color.WHITE));
        styleCellDate.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //
        Cell cell = row.createCell(0);
        cell.setCellValue("رمز المعاملات");
        cell.setCellType(CellType.STRING);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(0, 20 * 256);
        //
        cell = row.createCell(1);
        cell.setCellValue("اسم الفرع");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(1, 20 * 256);
        //
        cell = row.createCell(2);
        cell.setCellValue("العنوان");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(2, 20 * 256);
        //
        cell = row.createCell(3);
        cell.setCellValue("الهاتف");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(3, 20 * 256);
        //
        cell = row.createCell(4);
        cell.setCellValue("الجوال");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(4, 20 * 256);
        //
        cell = row.createCell(5);
        cell.setCellValue("الفاكس");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(5, 20 * 256);
        //
        cell = row.createCell(6);
        cell.setCellValue("البريد الإلكتروني");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(6, 20 * 256);
        //
        cell = row.createCell(7);
        cell.setCellValue("الموقع الرسمي");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(7, 20 * 256);
        //
        cell = row.createCell(8);
        cell.setCellValue("السجل التجاري");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(8, 20 * 256);
        //
        cell = row.createCell(9);
        cell.setCellValue("رقم الشركة");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(9, 20 * 256);
        //
        cell = row.createCell(10);
        cell.setCellValue("رقم حساب المدير");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(10, 20 * 256);
        //
        for (int i = 1; i <= 10; i++) {
            row = sheet.createRow(i);
            row.setHeightInPoints((short) 25);
            //
            for (int j = 0; j <= 10; j++) {
                cell = row.createCell(j);
                cell.setCellType(CellType.STRING);
                cell.setCellValue("---");
                cell.setCellStyle(styleCellDate);
            }
        }
        //
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(Lists.newArrayList(companyService.findAll()).stream().map(company -> company.getId().toString()).collect(Collectors.toList()).stream().toArray(String[]::new));
        CellRangeAddressList addressList = new CellRangeAddressList(1, 10, 9, 9);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("المدير الذكي", "فضلاً اختر رقم الشركة دون تعديل من القائمة.");
        sheet.addValidationData(validation);
        //
        dvHelper = new XSSFDataValidationHelper(sheet);
        dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(Lists.newArrayList(personService.findAll()).stream().map(person -> person.getId().toString()).collect(Collectors.toList()).stream().toArray(String[]::new));
        addressList = new CellRangeAddressList(1, 10, 10, 10);
        validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("المدير الذكي", "فضلاً اختر رقم حساب المدير دون تعديل من القائمة.");
        sheet.addValidationData(validation);
        //
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment; filename=\"SmartManager-HeavyWork-Branch-Insert.xlsx\"");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    @RequestMapping(value = "/api/heavy-work/branch/read", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void readBranchFile(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        try {
            List<Branch> branchList = new ArrayList<>();
            //
            File tempFile = File.createTempFile(new BigInteger(130, random).toString(32), "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            FileUtils.writeByteArrayToFile(tempFile, multipartFile.getBytes());
            FileInputStream fileInputStream = new FileInputStream(tempFile);
            //
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            //
            Iterator<Row> iterator = dataTypeSheet.iterator();
            log.info("تجاهل الصف الأول من الجدول والذي يحتوي على رؤوس الأعمدة");
            if (iterator.hasNext()) iterator.next();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Branch branch = new Branch();
                boolean accept = true;
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    nextCell.setCellType(CellType.STRING);
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            if (branchService.findByCode((String) excelCellHelper.getCellValue(nextCell)) != null) {
                                accept = false;
                            }
                            branch.setCode((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 1:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setName((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 2:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setAddress((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 3:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setPhone((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 4:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setMobile((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 5:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setFax((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 6:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setEmail((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 7:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setWebsite((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 8:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            branch.setCommericalRegisteration((String) excelCellHelper.getCellValue(nextCell));
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 9:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                                break;
                            }
                            Long companyId = null;
                            try {
                                companyId = Long.parseLong((String) excelCellHelper.getCellValue(nextCell));
                            } catch (Exception ex) {
                                accept = false;
                                break;
                            }
                            Company company = companyService.findOne(companyId);
                            if (company == null) {
                                accept = false;
                                break;
                            }
                            branch.setCompany(company);
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                        case 10:
                            if (excelCellHelper.getCellValue(nextCell) == null) {
                                accept = false;
                                break;
                            }
                            Long personId = null;
                            try {
                                personId = Long.parseLong((String) excelCellHelper.getCellValue(nextCell));
                            } catch (Exception ex) {
                                accept = false;
                                break;
                            }
                            Person person = personService.findOne(personId);
                            if (person == null) {
                                accept = false;
                                break;
                            }
                            branch.setManager(person);
                            log.info((String) excelCellHelper.getCellValue(nextCell));
                            break;
                    }

                }
                if (accept) {
                    branchList.add(branch);
                }
            }
            branchService.save(branchList);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على الفروع")
                    .message("تم اضافة عدد " + branchList.size() + " من الفروع بنجاح.")
                    .type("success")
                    .icon("fa-plus-circle")
                    .build(), principal.getName());
            workbook.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
