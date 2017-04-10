package com.besafx.app.rest;

import com.besafx.app.controller.FileUploadController;
import com.besafx.app.entity.*;
import com.besafx.app.search.OperationSearch;
import com.besafx.app.service.*;
import com.besafx.app.util.DateConverter;
import com.besafx.app.util.WrapperUtil;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@RestController
@RequestMapping(value = "/api/operation/")
public class OperationRest {

    @Autowired
    private FileUploadController fileUploadController;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private RegionService regionService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private PersonService personService;

    @Autowired
    private OperationService operationService;

    @Autowired
    private OperationAttachService operationAttachService;

    @Autowired
    private OperationSearch operationSearch;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_CREATE')")
    public Operation create(@RequestBody Operation operation, Principal principal) {
        Integer maxCode = null;
        switch (operation.getStructure()) {
            case Incoming:
                maxCode = operationService.findMaxCodeByStructureAndToTypeAndToId(Operation.Structure.Incoming, operation.getToType(), operation.getToId());
                break;
            case Outgoing:
                maxCode = operationService.findMaxCodeByStructureAndFromTypeAndFromId(Operation.Structure.Outgoing, operation.getFromType(), operation.getFromId());
                break;
        }
        if (maxCode == null) {
            operation.setCode(1);
        } else {
            operation.setCode(maxCode + 1);
        }
        operation.setPerson(personService.findByEmail(principal.getName()));
        operation = operationService.save(operation);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على المعاملات")
                .message("تم اضافة معاملة جديدة بنجاح")
                .type("success")
                .icon("fa-exchange")
                .build(), principal.getName());
        return operation;
    }

    @RequestMapping(value = "createOperationAttach/{operationId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_CREATE')")
    public OperationAttach createOperationAttach(@PathVariable(value = "operationId") Long operationId, @RequestParam(value = "file") MultipartFile file, Principal principal) throws Exception {
        OperationAttach attach = new OperationAttach();
        attach.setLink(fileUploadController.uploadOperationAttach(operationId, file));
        attach.setName(file.getOriginalFilename());
        attach.setOperation(findOne(operationId));
        return operationAttachService.save(attach);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_UPDATE')")
    public Operation update(@RequestBody Operation operation, Principal principal) {
        Operation object = operationService.findOne(operation.getId());
        if (object != null) {
            operation = operationService.save(operation);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على المعاملات")
                    .message("تم تعديل بيانات المعاملة بنجاح")
                    .type("success")
                    .icon("fa-exchange")
                    .build(), principal.getName());
            return operation;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_DELETE')")
    public void delete(@PathVariable Long id) {
        Operation object = operationService.findOne(id);
        if (object != null) {
            operationService.delete(id);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Operation> findAll() {
        return Lists.newArrayList(operationService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Operation findOne(@PathVariable Long id) {
        return operationService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return operationService.count();
    }

    @RequestMapping(value = "fetchTableData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Operation> fetchTableData(Principal principal) {
        return findAll();
    }

    @RequestMapping(value = "filter", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Operation> filter(
            @RequestParam(value = "codeFrom", required = false) final Long codeFrom,
            @RequestParam(value = "codeTo", required = false) final Long codeTo,
            @RequestParam(value = "title", required = false) final String title,
            @RequestParam(value = "deliveryManFrom", required = false) final String deliveryManFrom,
            @RequestParam(value = "deliveryManTo", required = false) final String deliveryManTo,
            @RequestParam(value = "deliveryWay", required = false) final Operation.DeliveryWay deliveryWay,
            @RequestParam(value = "deliveryAddress", required = false) final String deliveryAddress,
            @RequestParam(value = "importance", required = false) final Operation.Importance importance,
            @RequestParam(value = "structure", required = false) final Operation.Structure structure,
            @RequestParam(value = "locked", required = false) final Boolean locked,
            @RequestParam(value = "startDateFrom", required = false) final Long startDateFrom,
            @RequestParam(value = "startDateTo", required = false) final Long startDateTo,
            @RequestParam(value = "endDateFrom", required = false) final Long endDateFrom,
            @RequestParam(value = "endDateTo", required = false) final Long endDateTo,
            @RequestParam(value = "fromId", required = false) final Long fromId,
            @RequestParam(value = "fromType", required = false) final Operation.IdType fromType,
            @RequestParam(value = "toId", required = false) final Long toId,
            @RequestParam(value = "toType", required = false) final Operation.IdType toType,
            @RequestParam(value = "person", required = false) final Long person,
            @RequestParam(value = "operationType", required = false) final Long operationType
    ) {
        return operationSearch.search(codeFrom, codeTo, title, deliveryManFrom, deliveryManTo, deliveryWay, deliveryAddress, importance, structure, locked, startDateFrom, startDateTo, endDateFrom, endDateTo, fromId, fromType, toId, toType, person, operationType);
    }

    @RequestMapping(value = "filterEnhanced", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<WrapperUtil> filterEnhanced(
            @RequestParam(value = "codeFrom", required = false) final Long codeFrom,
            @RequestParam(value = "codeTo", required = false) final Long codeTo,
            @RequestParam(value = "title", required = false) final String title,
            @RequestParam(value = "deliveryManFrom", required = false) final String deliveryManFrom,
            @RequestParam(value = "deliveryManTo", required = false) final String deliveryManTo,
            @RequestParam(value = "deliveryWay", required = false) final Operation.DeliveryWay deliveryWay,
            @RequestParam(value = "deliveryAddress", required = false) final String deliveryAddress,
            @RequestParam(value = "importance", required = false) final Operation.Importance importance,
            @RequestParam(value = "structure", required = false) final Operation.Structure structure,
            @RequestParam(value = "locked", required = false) final Boolean locked,
            @RequestParam(value = "startDateFrom", required = false) final Long startDateFrom,
            @RequestParam(value = "startDateTo", required = false) final Long startDateTo,
            @RequestParam(value = "endDateFrom", required = false) final Long endDateFrom,
            @RequestParam(value = "endDateTo", required = false) final Long endDateTo,
            @RequestParam(value = "fromId", required = false) final Long fromId,
            @RequestParam(value = "fromType", required = false) final Operation.IdType fromType,
            @RequestParam(value = "toId", required = false) final Long toId,
            @RequestParam(value = "toType", required = false) final Operation.IdType toType,
            @RequestParam(value = "person", required = false) final Long person,
            @RequestParam(value = "operationType", required = false) final Long operationType
    ) {
        List<Operation> list = operationSearch.search(codeFrom, codeTo, title, deliveryManFrom, deliveryManTo, deliveryWay, deliveryAddress, importance, structure, locked, startDateFrom, startDateTo, endDateFrom, endDateTo, fromId, fromType, toId, toType, person, operationType);
        List<WrapperUtil> tempList = new ArrayList<>();
        Company tempCompany;
        Region tempRegion;
        Branch tempBranch;
        Department tempDepartment;
        Person tempPerson;
        ListIterator<Operation> listIterator = list.listIterator();
        while (listIterator.hasNext()) {
            Operation operation = listIterator.next();
            WrapperUtil wrapperUtil = new WrapperUtil();
            wrapperUtil.setObj0(operation.getId());
            wrapperUtil.setObj2(operation.getTitle());
            switch (operation.getFromType()) {
                case Company:
                    tempCompany = companyService.findOne(operation.getFromId());
                    wrapperUtil.setObj1(operation.getCode() + "/" + tempCompany.getCode() + "/" + DateConverter.getYearShortcut(operation.getStartDate()));
                    wrapperUtil.setObj3("شركة / " + tempCompany.getName());
                    break;
                case Region:
                    tempRegion = regionService.findOne(operation.getFromId());
                    wrapperUtil.setObj1(operation.getCode() + "/" + tempRegion.getCode() + "/" + DateConverter.getYearShortcut(operation.getStartDate()));
                    wrapperUtil.setObj3("منطقة / " + tempRegion.getName());
                    break;
                case Branch:
                    tempBranch = branchService.findOne(operation.getFromId());
                    wrapperUtil.setObj1(operation.getCode() + "/" + tempBranch.getCode() + "/" + DateConverter.getYearShortcut(operation.getStartDate()));
                    wrapperUtil.setObj3("فرع / " + tempBranch.getName());
                    break;
                case Department:
                    tempDepartment = departmentService.findOne(operation.getFromId());
                    wrapperUtil.setObj1(operation.getCode() + "/" + tempDepartment.getCode() + "/" + DateConverter.getYearShortcut(operation.getStartDate()));
                    wrapperUtil.setObj3("قسم / " + tempDepartment.getName());
                    break;
                case Person:
                    tempPerson = personService.findOne(operation.getFromId());
                    wrapperUtil.setObj1(operation.getCode() + "/" + tempPerson.getCode() + "/" + DateConverter.getYearShortcut(operation.getStartDate()));
                    wrapperUtil.setObj3(tempPerson.getType() ? "الفرد" : "جهة خارجية" + tempPerson.getName());
                    break;
            }
            switch (operation.getToType()) {
                case Company:
                    tempCompany = companyService.findOne(operation.getToId());
                    wrapperUtil.setObj4("شركة / " + tempCompany.getName());
                    break;
                case Region:
                    tempRegion = regionService.findOne(operation.getToId());
                    wrapperUtil.setObj4("منطقة / " + tempRegion.getName());
                    break;
                case Branch:
                    tempBranch = branchService.findOne(operation.getToId());
                    wrapperUtil.setObj4("فرع / " + tempBranch.getName());
                    break;
                case Department:
                    tempDepartment = departmentService.findOne(operation.getToId());
                    wrapperUtil.setObj4("قسم / " + tempDepartment.getName());
                    break;
                case Person:
                    tempPerson = personService.findOne(operation.getToId());
                    wrapperUtil.setObj4(tempPerson.getType() ? "الفرد" : "جهة خارجية" + tempPerson.getName());
                    break;
            }
            wrapperUtil.setObj5(operation.getStartDate());
            wrapperUtil.setObj6(operation.getLocked());
            wrapperUtil.setObj7(operation.getPerson().getName());
            wrapperUtil.setObj8(operation.getOperationType().getName());
            tempList.add(wrapperUtil);
        }
        return tempList;
    }

}
