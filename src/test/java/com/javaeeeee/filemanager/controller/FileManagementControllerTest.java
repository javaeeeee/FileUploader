package com.javaeeeee.filemanager.controller;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.exception.FileNotFoundInStorageException;
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
    public void upload() {
        FileMetadata fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME).originalFileName(FILENAME).build();
        when(storageService.save(any(MultipartFile.class))).thenReturn(fileMetadata);
        assertEquals(fileMetadata, sut.upload(Mockito.mock(MultipartFile.class)));
    }

    @Test
    public void download() throws FileNotFoundInStorageException {
//        assertEquals(DOWNLOADED, sut.download("name"));
    }
}