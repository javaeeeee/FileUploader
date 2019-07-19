package com.javaeeeee.filemanager;

import com.javaeeeee.filemanager.controller.FileManagementController;
import com.javaeeeee.filemanager.service.FileMetadataService;
import com.javaeeeee.filemanager.service.FileStorageService;
import com.javaeeeee.filemanager.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilemanagerApplicationTests {
    @Autowired
    private FileManagementController fileManagementController;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private FileMetadataService fileMetadataService;
    @Autowired
    private StorageService storageService;

    @Test
    public void contextLoads() {
        assertNotNull(fileManagementController);
        assertNotNull(fileStorageService);
        assertNotNull(fileMetadataService);
        assertNotNull(storageService);
    }

}
