package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileNotFoundInStorageException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * A facade used to manipulate stored files.
 */
@Service
public class FileStorageService implements StorageService {
    @Override
    public List<FileMetadata> listAll() {
        return new ArrayList<>();
    }

    @Override
    public Resource loadFileAsResource(String filename) throws FileNotFoundInStorageException {
        return null;
    }

    @Override
    public FileMetadata save(MultipartFile file) {
        return FileMetadata.builder().build();
    }
}
