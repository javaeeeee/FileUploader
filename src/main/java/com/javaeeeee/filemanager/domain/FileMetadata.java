package com.javaeeeee.filemanager.domain;

import lombok.*;
import org.springframework.http.MediaType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Used to store file metadata as name, length, unique identifier etc.
 */
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Setter(value = AccessLevel.PACKAGE)
@Getter
public class FileMetadata {
    /**
     * The ID of a record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    private String mediaType;
    /**
     * The size of the file.
     */
    private long fileSize;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileMetadata that = (FileMetadata) o;
        return fileSize == that.fileSize &&
                fileName.equals(that.fileName) &&
                originalFileName.equals(that.originalFileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, originalFileName, fileSize);
    }
}
