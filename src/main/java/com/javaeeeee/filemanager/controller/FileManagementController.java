package com.javaeeeee.filemanager.controller;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileNotFoundInStorageException;
import com.javaeeeee.filemanager.service.StorageService;
import com.sun.net.httpserver.Headers;
import org.apache.catalina.loader.ResourceEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA;

/**
 * A REST controller that supports file management operations.
 */
@RestController
public class FileManagementController {

    private static final Logger LOG = LoggerFactory.getLogger(FileManagementController.class);

    protected static final String DOWNLOAD_URL = "/download";
    protected static final String UPLOAD_URL = "/upload";

    private final StorageService storageService;

    public FileManagementController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(value = UPLOAD_URL, consumes = {"multipart/form-data"})
    public FileMetadata upload(@RequestParam("file") MultipartFile file) {
        return storageService.save(file);
    }

    @GetMapping(DOWNLOAD_URL + "/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws FileNotFoundInStorageException {
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename).body(storageService.loadFileAsResource(filename));
    }

    @GetMapping("/")
    public List<FileMetadata> listStoredFiles() {
        return storageService.listAll();
    }

    @ExceptionHandler(FileNotFoundInStorageException.class)
    public ResponseEntity<?> handleFileNotFoundExceprion(FileNotFoundInStorageException e) {
        LOG.warn("The requested file was not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }
}
