package com.javaeeeee.filemanager.controller;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.dto.FileResponseDto;
import com.javaeeeee.filemanager.exception.FileNotFoundInStorageException;
import com.javaeeeee.filemanager.exception.FileStorageException;
import com.javaeeeee.filemanager.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

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
    public FileMetadata upload(@RequestParam("file") MultipartFile file) throws FileStorageException {
        return storageService.save(file);
    }

    @GetMapping(DOWNLOAD_URL + "/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename) throws FileStorageException, FileNotFoundInStorageException {
        Optional<FileResponseDto> fileResponseDto = storageService.loadFileAsResource(filename);
        if (!fileResponseDto.isPresent()) {
            throw new FileNotFoundInStorageException("Cant find file: " + filename);
        }
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileResponseDto.get().getFileMetadata().getOriginalFileName())
                .body(fileResponseDto.get().getResource());
    }

    @GetMapping("/")
    public List<FileMetadata> listStoredFiles() {
        return storageService.listAll();
    }

    @ExceptionHandler(FileNotFoundInStorageException.class)
    public ResponseEntity<?> handleFileNotFoundException(FileNotFoundInStorageException e) {
        LOG.warn("The requested file was not found: {}", e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<String> handleFileStorageException(FileStorageException e) {
        LOG.warn("Error storing file: {}", e.getMessage());
        return ResponseEntity.unprocessableEntity().body(e.getMessage());
    }
}
