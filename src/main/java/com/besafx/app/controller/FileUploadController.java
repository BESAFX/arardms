package com.besafx.app.controller;

import com.besafx.app.config.DropboxManager;
import com.besafx.app.entity.Person;
import com.besafx.app.service.PersonService;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Principal;
import java.security.SecureRandom;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class FileUploadController {

    @Autowired
    private PersonService personService;

    @Autowired
    private DropboxManager dropboxManager;

    private SecureRandom random;

    @PostConstruct
    public void init() {
        random = new SecureRandom();
    }

    @RequestMapping(value = "/uploadOperationAttach/{operationId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadOperationAttach(@PathVariable(value = "operationId") Long operationId, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/operations/" + operationId + "/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/arardms/operations/" + operationId + "/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadOperationCommentAttach/{operationCommentId}", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadFileAndGetShared(@PathVariable(value = "operationCommentId") Long operationCommentId, @RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/operationComments/" + operationCommentId + "/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/arardms/operationComments/" + operationCommentId + "/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadContactLogo", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadContactLogo(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/contacts/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/arardms/contacts/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadUserPhoto", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadUserPhoto(@RequestParam("file") MultipartFile file, Principal principal) throws JRException, IOException, ExecutionException, InterruptedException {
        Person person = personService.findByEmail(principal.getName());
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/Users/" + person.getId() + "/" + fileName);
        if(task.get()){
            Future<String> task11 = dropboxManager.shareFile("/arardms/Users/" + person.getId() + "/" + fileName);
            String photoLink = task11.get();
            person.setPhoto(photoLink);
            personService.save(person);
            return photoLink;
        }else{
            return null;
        }
    }
}
