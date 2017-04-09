package com.besafx.app.controller;

import com.besafx.app.config.DropboxManager;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.Future;

@RestController
public class FileUploadController {

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
}
