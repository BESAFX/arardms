package com.besafx.app.controller;
import com.besafx.app.config.DropboxManager;
import com.besafx.app.entity.Person;
import com.besafx.app.service.PersonService;
import net.sf.jasperreports.engine.JRException;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger log = LoggerFactory.getLogger(FileUploadController.class);

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

    @RequestMapping(value = "/uploadCompanyLogo", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadCompanyLogo(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/Companies/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/arardms/Companies/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadBranchLogo", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadBranchLogo(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/Branches/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/arardms/Branches/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadContactLogo", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadContactLogo(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/Contacts/" + fileName);
        if (task.get()) {
            Future<String> task11 = dropboxManager.shareFile("/arardms/Contacts/" + fileName);
            return task11.get();
        } else {
            return null;
        }
    }

    @RequestMapping(value = "/uploadUserPhoto", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String uploadUserPhoto(@RequestParam("file") MultipartFile file, Principal principal) throws Exception{
        Person person = personService.findByEmail(principal.getName());
        String fileName = new BigInteger(130, random).toString(32) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        Future<Boolean> task = dropboxManager.uploadFile(file, "/arardms/Users/" + fileName);
        if(task.get()){
            Future<String> task11 = dropboxManager.shareFile("/arardms/Users/" + fileName);
            String photoLink = task11.get();
            person.setPhoto(photoLink);
            personService.save(person);
            return photoLink;
        }else{
            return null;
        }
    }
}
