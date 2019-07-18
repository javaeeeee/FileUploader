package com.javaeeeee.filemanager.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.MediaType;

/**
 * Used to store file metadata as name, length, unique identifier etc.
 */
@Data
@Builder

public class FileMetadata {
    /**
     * The name of the file as it's stored in the bucket.
     */
    private String fileName;
    /**
     * The name of the file at the uploading time.
     */
    private String originalFileName;
    /**
     * A Url used to download a file.
     */
    private String downloadUrl;
    /**
     * The media type of the file.
     */
    private MediaType mediaType;
    /**
     * The size of the file.
     */
    private long fileSize;
}
