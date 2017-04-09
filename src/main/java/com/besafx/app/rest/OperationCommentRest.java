package com.besafx.app.rest;

import com.besafx.app.entity.OperationComment;
import com.besafx.app.service.OperationCommentService;
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
@RequestMapping(value = "/api/operationComment/")
public class OperationCommentRest {

    @Autowired
    private OperationCommentService operationCommentService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_COMMENT_CREATE')")
    public OperationComment create(@RequestBody OperationComment operationComment, Principal principal) {
        Integer maxCode = operationCommentService.findMaxCode();
        if (maxCode == null) {
            operationComment.setCode(1);
        } else {
            operationComment.setCode(maxCode + 1);
        }
        operationComment = operationCommentService.save(operationComment);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على حركات المعاملات")
                .message("تم اضافة حركة معاملة جديدة بنجاح")
                .type("success")
                .icon("fa-exchange")
                .build(), principal.getName());
        return operationComment;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_COMMENT_UPDATE')")
    public OperationComment update(@RequestBody OperationComment operationComment, Principal principal) {
        OperationComment object = operationCommentService.findOne(operationComment.getId());
        if (object != null) {
            operationComment = operationCommentService.save(operationComment);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على حركات المعاملات")
                    .message("تم تعديل بيانات حركة المعاملة بنجاح")
                    .type("success")
                    .icon("fa-exchange")
                    .build(), principal.getName());
            return operationComment;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_OPERATION_COMMENT_DELETE')")
    public void delete(@PathVariable Long id) {
        OperationComment object = operationCommentService.findOne(id);
        if (object != null) {
            operationCommentService.delete(id);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OperationComment> findAll() {
        return Lists.newArrayList(operationCommentService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OperationComment findOne(@PathVariable Long id) {
        return operationCommentService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return operationCommentService.count();
    }

    @RequestMapping(value = "fetchTableData", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<OperationComment> fetchTableData(Principal principal) {
        return findAll();
    }

}
