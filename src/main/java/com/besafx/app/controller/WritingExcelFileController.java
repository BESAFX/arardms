package com.besafx.app.controller;
import com.besafx.app.entity.Branch;
import com.besafx.app.entity.Person;
import com.besafx.app.entity.Team;
import com.besafx.app.service.BranchService;
import com.besafx.app.service.PersonService;
import com.besafx.app.service.TeamService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class WritingExcelFileController {

    private final static Logger log = LoggerFactory.getLogger(WritingExcelFileController.class);

    private SecureRandom random;

    @Autowired
    private TeamService teamService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private NotificationService notificationService;

    @PostConstruct
    public void init() {
        random = new SecureRandom();
    }

    @RequestMapping(value = "/api/heavy-work/person/write", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void writePersonFile(HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("اضافة مستخدمين جدد دفعة واحدة");
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
        cell.setCellValue("اللقب");
        cell.setCellType(CellType.STRING);
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(0, 20 * 256);
        //
        cell = row.createCell(1);
        cell.setCellValue("الاسم كاملاً");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(1, 20 * 256);
        //
        cell = row.createCell(2);
        cell.setCellValue("الجنسية");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(2, 20 * 256);
        //
        cell = row.createCell(3);
        cell.setCellValue("رقم البطاقة");
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
        cell.setCellValue("المؤهل");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(5, 20 * 256);
        //
        cell = row.createCell(6);
        cell.setCellValue("العنوان الحالي");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(6, 20 * 256);
        //
        cell = row.createCell(7);
        cell.setCellValue("رقم الفرع");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(7, 20 * 256);
        //
        cell = row.createCell(8);
        cell.setCellValue("رقم مجموعة الصلاحيات");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(8, 20 * 256);
        //
        cell = row.createCell(9);
        cell.setCellValue("البريد الإلكتروني");
        cell.setCellType(CellType.STRING);
        cell.setCellStyle(styleColumnHeader);
        sheet.setColumnWidth(9, 20 * 256);
        //
        cell = row.createCell(10);
        cell.setCellValue("كلمة المرور");
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
                cell.setCellValue("---");
                cell.setCellType(CellType.STRING);
                cell.setCellStyle(styleCellDate);
            }
        }
        //
        XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
        XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(Lists.newArrayList(teamService.findAll()).stream().map(team -> team.getName()).collect(Collectors.toList()).stream().toArray(String[]::new));
        CellRangeAddressList addressList = new CellRangeAddressList(1, 10, 8, 8);
        XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("المدير الذكي", "فضلاً اختر مجموعة الصلاحيات من القائمة.");
        sheet.addValidationData(validation);
        //
        dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(Lists.newArrayList(branchService.findAll()).stream().map(branch -> branch.getName()).collect(Collectors.toList()).stream().toArray(String[]::new));
        addressList = new CellRangeAddressList(1, 10, 7, 7);
        validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.createErrorBox("المدير الذكي", "فضلاً اختر الفرع من القائمة.");
        sheet.addValidationData(validation);
        //
        try {
            response.setContentType("application/xlsx");
            response.setHeader("Content-Disposition", "attachment; filename=\"SmartManager-HeavyWork-Person-Insert.xlsx\"");
            OutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }

    }

    @RequestMapping(value = "/api/heavy-work/person/read", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void readPersonFile(@RequestParam("file") MultipartFile multipartFile, Principal principal) {
        try {
            List<Person> personList = new ArrayList<>();
            //
            File tempFile = File.createTempFile(new BigInteger(130, random).toString(32), "." + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
            FileUtils.writeByteArrayToFile(tempFile, multipartFile.getBytes());
            FileInputStream fileInputStream = new FileInputStream(tempFile);
            //
            Workbook workbook = new XSSFWorkbook(fileInputStream);
            Sheet dataTypeSheet = workbook.getSheetAt(0);
            //
            Iterator<Row> iterator = dataTypeSheet.iterator();
            while (iterator.hasNext()) {
                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Person person = new Person();
                boolean accept = true;
                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
                    nextCell.setCellType(CellType.STRING);
                    int columnIndex = nextCell.getColumnIndex();
                    switch (columnIndex) {
                        case 0:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setNickname((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 1:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setName((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 2:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setNationality((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 3:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setIdentityNumber((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 4:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setMobile((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 5:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setQualification((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 6:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setAddress((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 7:
                            person.setBranch(branchService.findByName((String) getCellValue(nextCell)));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 8:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            Team team = teamService.findByName((String) getCellValue(nextCell));
                            if (team == null) {
                                accept = false;
                            }
                            person.setTeam(team);
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 9:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            if (personService.findByEmail(person.getEmail()) != null) {
                                accept = false;
                            }
                            person.setEmail((String) getCellValue(nextCell));
                            log.info((String) getCellValue(nextCell));
                            break;
                        case 10:
                            if (getCellValue(nextCell) == null) {
                                accept = false;
                            }
                            person.setHiddenPassword((String) getCellValue(nextCell));
                            person.setPassword(passwordEncoder.encode((String) getCellValue(nextCell)));
                            log.info((String) getCellValue(nextCell));
                            break;
                    }

                }
                if (accept) {
                    person.setEnabled(true);
                    person.setTokenExpired(false);
                    personList.add(person);
                }
            }
            personService.save(personList);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على حسابات المستخدمين")
                    .message("تم اضافة عدد " + personList.size() + " من المستخدمين بنجاح.")
                    .type("success")
                    .icon("fa-plus-circle")
                    .build(), principal.getName());
            workbook.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Object getCellValue(Cell cell) {
        switch (cell.getCellTypeEnum()) {
            case STRING:
                return cell.getRichStringCellValue().getString();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return null;
            default:
                return null;
        }
    }

}
