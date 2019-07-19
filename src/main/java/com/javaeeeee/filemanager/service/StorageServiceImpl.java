package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.dto.FileResponseDto;
import com.javaeeeee.filemanager.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * A facade used to manipulate stored files.
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    private final FileStorageService fileStorageService;
    private final FileMetadataService fileMetadataService;

    public StorageServiceImpl(FileStorageService fileStorageService, FileMetadataService fileMetadataService) {
        this.fileStorageService = fileStorageService;
        this.fileMetadataService = fileMetadataService;
    }

    @Override
    public List<FileMetadata> listAll() {
        return fileMetadataService.getAllFiles();
    }

    @Override
    public Optional<FileResponseDto> loadFileAsResource(String filename) throws FileStorageException {
        Optional<FileMetadata> fileMetadata = fileMetadataService.get(filename);
        if (!fileMetadata.isPresent()) {
            return Optional.empty();
        }
        Path path = Paths.get(".").resolve(fileMetadata.get().getFileName()).toAbsolutePath().normalize();
        try {
            return fileStorageService.retrieve(path)
                    .map(value -> new FileResponseDto(fileMetadata.get(), value));
        } catch (MalformedURLException e) {
            log.warn("Can't save file: {}", e.getMessage());
            throw new FileStorageException("Can't retrieve tha file: " + filename);
        }
    }

    @Override
    @Transactional
    public FileMetadata save(MultipartFile file) throws FileStorageException {
        try {
            Path path = Paths.get(".")
                    .toAbsolutePath().normalize();
            FileMetadata fileMetadata = FileMetadata.builder().mediaType(file.getContentType()).fileSize(file.getSize()).originalFileName(file.getName()).fileName(UUID.randomUUID().toString()).build();
            path.resolve(fileMetadata.getFileName());
            fileStorageService.store(file, path);
            return fileMetadataService.saveMetadata(fileMetadata);
        } catch (IOException e) {
            log.warn("Error saving file.");
            throw new FileStorageException("You file can't be save right now. Please try again later.");
        }
    }
}
