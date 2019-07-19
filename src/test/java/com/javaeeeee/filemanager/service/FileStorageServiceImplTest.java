package com.javaeeeee.filemanager.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileStorageServiceImplTest {
    private static final String CONTENT = "Hello World.";
    private static final String ORIGINAL_FILE_NAME = "file.txt";
    private static final String CONTENT_TYPE = MediaType.TEXT_PLAIN_VALUE;
    private static final byte[] BYTES = CONTENT.getBytes();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void retrieve() throws IOException {
        final String name = UUID.randomUUID() + ".txt";
        final Path path = Paths.get(temporaryFolder.newFolder().toURI()).resolve(name);
        Files.copy(new ByteArrayInputStream(BYTES), path, StandardCopyOption.REPLACE_EXISTING);
        Optional<Resource> resource = fileStorageService.retrieve(path);
        assertTrue(resource.isPresent());
        String actual = StreamUtils.copyToString(resource.get().getInputStream(), Charset.forName("UTF-8"));
        assertEquals(CONTENT, actual);
    }

    @Test
    public void store() throws IOException {
        final String name = UUID.randomUUID() + ".txt";
        final Path path = Paths.get(temporaryFolder.newFolder().toURI()).resolve(name);
        MultipartFile file = new MockMultipartFile(name,
                ORIGINAL_FILE_NAME, CONTENT_TYPE, BYTES);

        fileStorageService.store(file, path);

        List<String> allLines = Files.readAllLines(path);
        assertFalse(allLines.isEmpty());
        String actual = allLines.get(0);
        assertEquals(CONTENT, actual);
    }
}