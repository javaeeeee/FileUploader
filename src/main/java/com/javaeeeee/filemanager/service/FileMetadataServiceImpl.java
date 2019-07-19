package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
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
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveMetadata(FileMetadata metadata) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<FileMetadata> get(String filename) {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileMetadata> getAllFiles() {
        return new ArrayList<>();
    }
}
