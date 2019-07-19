package com.javaeeeee.filemanager.service;

import com.javaeeeee.filemanager.domain.FileMetadata;
import com.javaeeeee.filemanager.repository.FileMetadataRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FileMetadataServiceImplIT {

    private static final long FILE_SIZE = 1L;
    private static final String ORIGINAL_FILE_NAME = "file.txt";
    private static final String FILE_NAME = "shshsh";
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    private FileMetadataService fileMetadataService;

    @Before
    public void setUp() throws Exception {
        fileMetadataService = new FileMetadataServiceImpl(fileMetadataRepository);
    }

    @Test
    public void saveMetadata() {
        FileMetadata expected = FileMetadata.builder().fileName(FILE_NAME).originalFileName(ORIGINAL_FILE_NAME).fileSize(FILE_SIZE).build();
        expected = fileMetadataService.saveMetadata(expected);
        testEntityManager.flush();
        FileMetadata actual = testEntityManager.find(FileMetadata.class, expected.getId());
        assertNotNull(expected.getId());
        assertEquals(expected, actual);
    }

    @Test
    public void get() {
        FileMetadata expected = FileMetadata.builder().fileName(FILE_NAME).originalFileName(ORIGINAL_FILE_NAME).fileSize(FILE_SIZE).build();
        expected = testEntityManager.persistAndFlush(expected);
        Optional<FileMetadata> actual = fileMetadataService.get(FILE_NAME);
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
        assertNotNull(actual.get().getId());

    }

    @Test
    public void getAllFiles() {
        final int size = 5;
        List<FileMetadata> expected = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            FileMetadata fileMetadata = FileMetadata.builder().fileName(FILE_NAME + i).originalFileName(ORIGINAL_FILE_NAME + i).fileSize(FILE_SIZE + i).build();
            testEntityManager.persist(fileMetadata);
            expected.add(fileMetadata);
        }
        testEntityManager.flush();
        List<FileMetadata> actual = fileMetadataService.getAllFiles();
        assertEquals(size, actual.size());
        actual.sort(Comparator.comparing(FileMetadata::getFileName));
        assertEquals(expected, actual);
    }
}