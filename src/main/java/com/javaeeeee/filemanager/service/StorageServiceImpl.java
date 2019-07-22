package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.dto.FileResponseDto;
import com.javaeeeee.filemanager.exception.FileStorageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

/**
 * A facade used to manipulate stored files.
 */
@Service
@Slf4j
public class StorageServiceImpl implements StorageService {
    private static final SecureRandom RANDOM = new SecureRandom();
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
            log.warn("Metadata for file: {} was not found", filename);
            return Optional.empty();
        }
        String path = fileStorageService.generatePath(filename);
        try {
            return fileStorageService.retrieve(path)
                    .map(value -> new FileResponseDto(fileMetadata.get(), value));
        } catch (MalformedURLException e) {
            log.warn("Can't load file: {}", e.getMessage());
            throw new FileStorageException("Can't retrieve tha file: " + filename);
        }
    }

    @Override
    @Transactional
    public FileMetadata save(MultipartFile file) throws FileStorageException {
        FileMetadata fileMetadata = FileMetadata.builder().mediaType(file.getContentType()).fileSize(file.getSize()).originalFileName(file.getOriginalFilename()).fileName(generateRandomFileName()).build();
        String path = fileStorageService.generatePath(fileMetadata.getFileName());
        fileStorageService.store(file, path);
        return fileMetadataService.saveMetadata(fileMetadata);
    }

    /**
     * Generates a random fileName.
     *
     * @return
     */
    private String generateRandomFileName() {
        int integer = RANDOM.nextInt(9999);
        return "file" + integer + System.currentTimeMillis();
    }
}
