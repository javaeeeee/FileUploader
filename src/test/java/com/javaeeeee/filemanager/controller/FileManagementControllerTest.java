package com.javaeeeee.filemanager.controller;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileNotFoundInStorageException;
import com.javaeeeee.filemanager.exception.FileStorageException;
import com.javaeeeee.filemanager.service.StorageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class FileManagementControllerTest {

    private static final String FILENAME = "filename.file";
    private static final String ENCODED_FILE_NAME = "abs";
    @MockBean
    private StorageService storageService;
    @Mock
    private Resource resource;
    private FileManagementController sut;

    @Before
    public void setUp() throws Exception {
        sut = new FileManagementController(storageService);
        when(resource.getFilename()).thenReturn(FILENAME);
    }

    @Test
    public void upload() throws FileStorageException {
        FileMetadata fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME).originalFileName(FILENAME).build();
        when(storageService.save(any(MultipartFile.class))).thenReturn(fileMetadata);
        assertEquals(fileMetadata, sut.upload(Mockito.mock(MultipartFile.class)));
    }

    @Test
    public void download() throws FileNotFoundInStorageException {
//        assertEquals(DOWNLOADED, sut.download("name"));
    }

    @Test
    public void listStoredFiles() {
        List<FileMetadata> files = new ArrayList<>();
        FileMetadata fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME).fileSize(1L).originalFileName(FILENAME).build();
        files.add(fileMetadata);
        fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME + 2).fileSize(2L).originalFileName(FILENAME + 2).build();
        files.add(fileMetadata);
        fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME + 3).fileSize(3L).originalFileName(FILENAME + 3).build();
        files.add(fileMetadata);
        when(storageService.listAll()).thenReturn(unmodifiableList(files));
        assertEquals(files, sut.listStoredFiles());
    }
}