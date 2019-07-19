package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.repository.FileMetadataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * {@inheritDoc}
 */
@Slf4j
@Service
public class FileMetadataServiceImpl implements FileMetadataService {

    private FileMetadataRepository fileMetadataRepository;

    public FileMetadataServiceImpl(FileMetadataRepository fileMetadataRepository) {
        this.fileMetadataRepository = fileMetadataRepository;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public FileMetadata saveMetadata(FileMetadata metadata) {
        return fileMetadataRepository.save(metadata);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<FileMetadata> get(String filename) {
        return fileMetadataRepository.findByFileName(filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileMetadata> getAllFiles() {
        List<FileMetadata> result = new ArrayList<>();
        fileMetadataRepository.findAll().forEach(result::add);
        return result;
    }
}
