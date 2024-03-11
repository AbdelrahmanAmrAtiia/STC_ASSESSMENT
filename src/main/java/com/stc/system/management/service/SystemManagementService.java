package com.stc.system.management.service;

import com.stc.system.management.Vo.ResponseModel;
import com.stc.system.management.entity.File;
import com.stc.system.management.entity.Item;
import com.stc.system.management.entity.Permission;
import com.stc.system.management.enums.ItemTypeEnum;
import com.stc.system.management.enums.PermissionLevelEnum;
import com.stc.system.management.exception.EntityNotFoundException;
import com.stc.system.management.exception.PermissionNotAllowedException;
import com.stc.system.management.projection.FileMetadataProjection;
import com.stc.system.management.repo.FileRepo;
import com.stc.system.management.repo.ItemRepo;
import com.stc.system.management.repo.PermissionGroupRepo;
import com.stc.system.management.repo.PermissionRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Getter
public class SystemManagementService {

    @Autowired
    PermissionRepo permissionRepo;
    @Autowired
    PermissionGroupRepo permissionGroupRepo;
    @Autowired
    ItemRepo itemRepo;

    @Autowired
    FileRepo fileRepo;

    public ResponseModel createSpace(Item item) {
        if (item.getPermissionGroup().getPermissions() != null && !item.getPermissionGroup().getPermissions().isEmpty()) {
            getPermissionRepo().saveAll(item.getPermissionGroup().getPermissions());
        }
        if (item.getPermissionGroup() != null) {
            getPermissionGroupRepo().save(item.getPermissionGroup());
        }
        if (!ItemTypeEnum.SPACE.name().equals(item.getType().name())) {
            return new ResponseModel(HttpStatus.BAD_REQUEST, "Wrong Item Type", "1", null);
        }
        Item savedItem = getItemRepo().save(item);
        return new ResponseModel(HttpStatus.OK, "Space Created Successfully", "0", savedItem);
    }

    public ResponseModel createFolder(Item item) {
        Item space = getItemRepo().findById(item.getParent().getId()).orElseThrow( () -> new EntityNotFoundException("Space"));

        if (!checkEditPermission(space)) {
            throw new PermissionNotAllowedException();
        }
        item.setParent(space);
        item.setType(ItemTypeEnum.FOLDER);
        Item savedItem = getItemRepo().save(item);
        return new ResponseModel(HttpStatus.OK, "Folder Created Successfully", "0", savedItem);
    }

    public ResponseModel createFile(MultipartFile file, String fileName, Long parentId) {
        Item parent = getItemRepo().findById(parentId).orElseThrow( () -> new EntityNotFoundException("Parent"));

        Item item = new Item();
        item.setType(ItemTypeEnum.FILE);
        item.setName(fileName);
        item.setPermissionGroup(parent.getPermissionGroup());
        item.setParent(parent);

        if (!checkEditPermission(parent)) {
            throw new PermissionNotAllowedException();
        }

        Item savedItem = getItemRepo().save(item);
        saveFile(file, savedItem);

        return new ResponseModel(HttpStatus.OK, "File Saved Successfully", "0", savedItem);
    }

    public ResponseEntity<Resource> downloadFile(Long fileId) {
        try {
            File file = getFileRepo().findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));

            boolean hasAccess = checkFileAccess();
            if (!hasAccess) {
                throw new PermissionNotAllowedException();
            }

            byte[] fileContent = file.getFileBinary();
            ByteArrayResource resource = new ByteArrayResource(fileContent);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "file");

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    public ResponseModel viewFiles(Long fileId) {
        FileMetadataProjection fileMetaData = getFileRepo().getFileMetadata(fileId);
        return new ResponseModel(HttpStatus.OK, "File Returned Successfully", "0", fileMetaData);
    }

    private boolean checkEditPermission(Item item) {
        List<Permission> permissionList = getPermissionRepo().findByPermissionGroupId(item.getPermissionGroup().getId());
        if (permissionList != null && !permissionList.isEmpty()) {
            if (permissionList.stream().anyMatch(permission -> PermissionLevelEnum.EDIT.equals(permission.getLevel()))) {
                return true;
            }
        }
        return false;
    }

    private void saveFile(MultipartFile file, Item item) {
        try {
            File itemFile = new File();
            itemFile.setFileBinary(file.getBytes());
            itemFile.setItem(item);
            getFileRepo().save(itemFile);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean checkFileAccess() {
        return true;
    }
}
