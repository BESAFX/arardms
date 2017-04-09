package com.besafx.app.rest;

import com.besafx.app.entity.OperationType;
import com.besafx.app.service.OperationTypeService;
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
@RequestMapping(value = "/api/operationType/")
public class OperationTypeRest {

    @Autowired
    private OperationTypeService operationTypeService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_TYPE_CREATE')")
    public OperationType create(@RequestBody OperationType operationType, Principal principal) {
        operationType = operationTypeService.save(operationType);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على أنواع المعاملات")
                .message("تم اضافة نوع معاملة جديدة بنجاح")
                .type("success")
                .icon("fa-exchange")
                .build(), principal.getName());
        return operationType;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_TYPE_UPDATE')")
    public OperationType update(@RequestBody OperationType operationType, Principal principal) {
        OperationType object = operationTypeService.findOne(operationType.getId());
        if (object != null) {
            operationType = operationTypeService.save(operationType);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على أنواع المعاملات")
                    .message("تم تعديل بيانات نوع المعاملة بنجاح")
                    .type("success")
                    .icon("fa-exchange")
                    .build(), principal.getName());
            return operationType;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_TYPE_DELETE')")
    public void delete(@PathVariable Long id) {
        OperationType object = operationTypeService.findOne(id);
        if (object != null) {
            operationTypeService.delete(id);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OperationType> findAll() {
        return Lists.newArrayList(operationTypeService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OperationType findOne(@PathVariable Long id) {
        return operationTypeService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return operationTypeService.count();
    }

}
