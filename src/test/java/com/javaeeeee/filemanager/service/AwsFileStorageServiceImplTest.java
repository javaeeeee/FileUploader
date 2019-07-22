package com.javaeeeee.filemanager.service;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AwsFileStorageServiceImplTest {

    @Captor
    public ArgumentCaptor<String> stringArgumentCaptor;

    @Mock
    private AmazonS3 s3Client;
    @Mock
    private ResourceLoader resourceLoader;
    @Mock
    private Resource resource;

    private FileStorageService fileStorageService;

    @Before
    public void setUp() throws Exception {
        fileStorageService = new AwsFileStorageServiceImpl(s3Client, resourceLoader, "1");
    }

    @Test
    public void retrieve() throws MalformedURLException {
        when(resource.exists()).thenReturn(true);
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        Optional<Resource> actual = fileStorageService.retrieve("file.txt");
        verify(resourceLoader).getResource(stringArgumentCaptor.capture());
        assertTrue(actual.isPresent());
        assertEquals("s3://1/file.txt", stringArgumentCaptor.getValue());
    }

    @Test
    public void retrieveEmpty() throws MalformedURLException {
        when(resource.exists()).thenReturn(false);
        when(resourceLoader.getResource(anyString())).thenReturn(resource);
        Optional<Resource> actual = fileStorageService.retrieve("file.txt");
        assertFalse(actual.isPresent());
    }
}