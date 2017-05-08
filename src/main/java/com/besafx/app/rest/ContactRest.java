package com.besafx.app.rest;
import com.besafx.app.config.CustomException;
import com.besafx.app.entity.Contact;
import com.besafx.app.service.ContactService;
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
@RequestMapping(value = "/api/contact/")
public class ContactRest {

    private final static Logger log = LoggerFactory.getLogger(ContactRest.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTACT_CREATE')")
    public Contact create(@RequestBody Contact contact, Principal principal) {
        if(contactService.findByCode(contact.getCode()) != null){
            throw new CustomException("هذا الكود مستخدم سابقاً، فضلاً قم بتغير الكود.");
        }
        contact = contactService.save(contact);
        notificationService.notifyOne(Notification
                .builder()
                .title("العمليات على الفروع")
                .message("تم اضافة حساب جهة خارجية جديد بنجاح")
                .type("success")
                .icon("fa-plus-circle")
                .build(), principal.getName());
        return contact;
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTACT_UPDATE')")
    public Contact update(@RequestBody Contact contact, Principal principal) {
        if(contactService.findByCodeAndIdIsNot(contact.getCode(), contact.getId()) != null){
            throw new CustomException("هذا الكود مستخدم سابقاً، فضلاً قم بتغير الكود.");
        }
        Contact object = contactService.findOne(contact.getId());
        if (object != null) {
            contact = contactService.save(contact);
            notificationService.notifyOne(Notification
                    .builder()
                    .title("العمليات على الفروع")
                    .message("تم تعديل بيانات حساب الجهة خارجية بنجاح")
                    .type("success")
                    .icon("fa-edit")
                    .build(), principal.getName());
            return contact;
        } else {
            return null;
        }
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_CONTACT_DELETE')")
    public void delete(@PathVariable Long id) {
        Contact object = contactService.findOne(id);
        if (object != null) {
            contactService.delete(id);
        }
    }

    @RequestMapping(value = "findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Contact> findAll() {
        return Lists.newArrayList(contactService.findAll());
    }

    @RequestMapping(value = "findOne/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Contact findOne(@PathVariable Long id) {
        return contactService.findOne(id);
    }

    @RequestMapping(value = "count", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Long count() {
        return contactService.count();
    }

}
