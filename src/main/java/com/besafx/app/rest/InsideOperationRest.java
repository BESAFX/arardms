package com.besafx.app.rest;
import com.besafx.app.entity.InsideOperation;
import com.besafx.app.entity.Person;
import com.besafx.app.entity.enums.Direction;
import com.besafx.app.service.InsideOperationService;
import com.besafx.app.service.PersonService;
import com.besafx.app.ws.Notification;
import com.besafx.app.ws.NotificationService;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/api/insideOperation/")
public class InsideOperationRest {

    private final Logger log = LoggerFactory.getLogger(InsideOperation.class);

    @Autowired
    private InsideOperationService insideOperationService;

    @Autowired
    private PersonService personService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_INSIDE_OPERATION_CREATE')")
    public InsideOperation create(@RequestBody InsideOperation insideOperation, Principal principal) {
        Person person = personService.findByEmail(principal.getName());
        log.info("Direction " + insideOperation.getDirection());
        InsideOperation topInsideOperation = insideOperationService.findTopByDirectionAndOperationTypeAndBranchFromAndBranchToOrderByCodeDesc(insideOperation.getDirection(), insideOperation.getOperationType(), insideOperation.getBranchFrom(), insideOperation.getBranchTo());
        if (topInsideOperation == null) {
            insideOperation.setCode(1);
        } else {
            insideOperation.setCode(topInsideOperation.getCode() + 1);
        }
        insideOperation.setPerson(person);
        insideOperation = insideOperationService.save(insideOperation);

        String message = "";
        if(insideOperation.getDirection().equals(Direction.Outgoing)){
            message = "تم انشاء المعاملة الداخلية الصادرة بنجاح";
        }else{
            message = "تم انشاء المعاملة الداخلية الواردة بنجاح";
        }
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على المعاملات الداخلية")
                .message(message)
                .type("success")
                .icon("fa-plus-circle")
                .build(), principal.getName());
        return insideOperation;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_INSIDE_OPERATION_UPDATE')")
    public InsideOperation update(@RequestBody InsideOperation insideOperation, Principal principal) {
        InsideOperation object = insideOperationService.findOne(insideOperation.getId());
        if (object != null) {
            insideOperation = insideOperationService.save(insideOperation);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على المعاملات الداخلية")
                    .message("تم تعديل بيانات المعاملة بنجاح")
                    .type("success")
                    .icon("fa-edit")
                    .build(), principal.getName());
            return insideOperation;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_INSIDE_OPERATION_DELETE')")
    public void delete(@PathVariable Long id) {
        InsideOperation object = insideOperationService.findOne(id);
        if (object != null) {
            insideOperationService.delete(id);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<InsideOperation> findAll() {
        return Lists.newArrayList(insideOperationService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public InsideOperation findOne(@PathVariable Long id) {
        return insideOperationService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return insideOperationService.count();
    }

}
