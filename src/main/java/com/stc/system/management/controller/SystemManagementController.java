package com.stc.system.management.controller;

import com.stc.system.management.Vo.ResponseModel;
import com.stc.system.management.entity.Item;
import com.stc.system.management.service.SystemManagementService;
import org.springframework.core.io.Resource;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/management")
@Getter
public class SystemManagementController {

    @Autowired
    SystemManagementService systemManagementService;

    @PostMapping("/create/space")
    public ResponseModel createSpace(@RequestBody Item item){
        return getSystemManagementService().createSpace(item);
    }

    @PostMapping("/create/folder")
    public ResponseModel createFolder(@RequestBody Item item){
        return getSystemManagementService().createFolder(item);
    }

    @PostMapping("create/file")
    public ResponseModel createFile(@RequestParam MultipartFile file, @RequestParam String fileName, @RequestParam Long parentId) {
        return getSystemManagementService().createFile(file, fileName, parentId);
    }

    @GetMapping("/file/{fileId}/metaData")
    public ResponseModel viewFiles(@PathVariable("fileId") Long fileId) {
        return getSystemManagementService().viewFiles(fileId);
    }

    @GetMapping("/file/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) {
        return getSystemManagementService().downloadFile(fileId);
    }
}
