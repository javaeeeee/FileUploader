package com.javaeeeee.filemanager;

import com.javaeeeee.filemanager.controller.FileManagementController;
import org.junit.Assert;
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

    @Test
    public void contextLoads() {
        assertNotNull(fileManagementController);
    }

}
