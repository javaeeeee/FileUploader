package com.javaeeeee.filemanager.controller;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.dto.FileResponseDto;
import com.javaeeeee.filemanager.exception.FileStorageException;
import com.javaeeeee.filemanager.service.StorageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.javaeeeee.filemanager.controller.FileManagementController.DOWNLOAD_URL;
import static com.javaeeeee.filemanager.controller.FileManagementController.UPLOAD_URL;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("Local")
public class FileManagementControllerIT {
    private static final String FILENAME = "filename.file";
    private static final String ENCODED_FILE_NAME = "abs";
    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    private int port;

    @Mock
    private Resource resource;

    @MockBean
    private StorageService storageService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void download() throws FileStorageException {
        FileMetadata fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME).fileSize(1L).originalFileName(FILENAME).build();
        when(resource.getFilename()).thenReturn(FILENAME);
        when(storageService.loadFileAsResource(FILENAME)).thenReturn(Optional.of(new FileResponseDto(fileMetadata, resource)));
        Resource actual = this.restTemplate.getForObject(BASE_URL + port + DOWNLOAD_URL + "/" + FILENAME, Resource.class);
        assertEquals(resource.getFilename(), actual.getFilename());
        verify(storageService).loadFileAsResource(FILENAME);
    }

    @Test
    public void downloadFileNotFount() throws FileStorageException {
        when(resource.getFilename()).thenReturn(FILENAME);
        when(storageService.loadFileAsResource(FILENAME)).thenReturn(Optional.empty());
        assertEquals(HttpStatus.NOT_FOUND, this.restTemplate.getForEntity(BASE_URL + port + DOWNLOAD_URL + "/" + FILENAME, ResponseEntity.class).getStatusCode());
        verify(storageService).loadFileAsResource(FILENAME);
    }

    @Test
    public void downloadFileError() throws FileStorageException {
        when(resource.getFilename()).thenReturn(FILENAME);
        when(storageService.loadFileAsResource(FILENAME)).thenThrow(new FileStorageException());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, this.restTemplate.getForEntity(BASE_URL + port + DOWNLOAD_URL + "/" + FILENAME, ResponseEntity.class).getStatusCode());
        verify(storageService).loadFileAsResource(FILENAME);
    }

    @Test
    public void upload() throws FileStorageException {
        // https://medium.com/red6-es/uploading-a-file-with-a-filename-with-spring-resttemplate-8ec5e7dc52ca
        FileMetadata fileMetadata = FileMetadata.builder().fileName(ENCODED_FILE_NAME).fileSize(1L).originalFileName(FILENAME).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultiValueMap<String, String> fileMap = new LinkedMultiValueMap<>();
        ContentDisposition contentDisposition = ContentDisposition
                .builder("form-data")
                .name("file")
                .filename(FILENAME)
                .build();
        fileMap.add(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString());
        HttpEntity<byte[]> fileEntity = new HttpEntity<>("Hello World".getBytes(), fileMap);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileEntity);

        HttpEntity<MultiValueMap<String, Object>> requestEntity =
                new HttpEntity<>(body, headers);
        when(storageService.save(any(MultipartFile.class))).thenReturn(fileMetadata);
        FileMetadata actual = this.restTemplate.postForObject(BASE_URL + port + UPLOAD_URL, requestEntity, FileMetadata.class);
        assertEquals(fileMetadata, actual);
        verify(storageService).save(any());
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
        when(storageService.listAll()).thenReturn(files);
        List<FileMetadata> actual = restTemplate.exchange(
                BASE_URL + port + "/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<FileMetadata>>() {
                }).getBody();
        assertEquals(files, actual);
        verify(storageService).listAll();
        files.clear();
    }

}