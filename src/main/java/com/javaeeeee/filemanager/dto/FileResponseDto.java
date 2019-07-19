package com.javaeeeee.filemanager.dto;

import com.javaeeeee.filemanager.domain.FileMetadata;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

/**
 * Used to pass all the required data about a file downloaded.
 */
@Data
@AllArgsConstructor
public class FileResponseDto {
    final private FileMetadata fileMetadata;
    final private Resource resource;
}
