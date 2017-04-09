package com.besafx.app.rest;

import com.besafx.app.entity.Operation;
import com.besafx.app.service.OperationService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/operation/")
public class OperationRest {

    @Autowired
    private OperationService operationService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_CREATE')")
    public Operation create(@RequestBody Operation operation, Principal principal) {
        Integer maxCode = operationService.findMaxCode();
        if (maxCode == null) {
            operation.setCode(1);
        } else {
            operation.setCode(maxCode + 1);
        }
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

}
