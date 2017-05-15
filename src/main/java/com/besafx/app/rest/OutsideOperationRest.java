package com.besafx.app.rest;
import com.besafx.app.entity.OutsideOperation;
import com.besafx.app.service.OutsideOperationService;
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
@RequestMapping(value = "/api/outsideOperation/")
public class OutsideOperationRest {

    @Autowired
    private OutsideOperationService outsideOperationService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_INSIDE_OPERATION_CREATE')")
    public OutsideOperation create(@RequestBody OutsideOperation outsideOperation, Principal principal) {
        outsideOperation = outsideOperationService.save(outsideOperation);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على المعاملات الخارجية")
                .message("تم اضافة معاملة جديدة بنجاح")
                .type("success")
                .icon("fa-plus-circle")
                .build(), principal.getName());
        return outsideOperation;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_INSIDE_OPERATION_UPDATE')")
    public OutsideOperation update(@RequestBody OutsideOperation outsideOperation, Principal principal) {
        OutsideOperation object = outsideOperationService.findOne(outsideOperation.getId());
        if (object != null) {
            outsideOperation = outsideOperationService.save(outsideOperation);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على المعاملات الخارجية")
                    .message("تم تعديل بيانات المعاملة بنجاح")
                    .type("success")
                    .icon("fa-edit")
                    .build(), principal.getName());
            return outsideOperation;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_INSIDE_OPERATION_DELETE')")
    public void delete(@PathVariable Long id) {
        OutsideOperation object = outsideOperationService.findOne(id);
        if (object != null) {
            outsideOperationService.delete(id);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OutsideOperation> findAll() {
        return Lists.newArrayList(outsideOperationService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OutsideOperation findOne(@PathVariable Long id) {
        return outsideOperationService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return outsideOperationService.count();
    }

}
