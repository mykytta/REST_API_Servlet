package com.mykyta.restapi.service;

import com.mykyta.restapi.model.File;
import com.mykyta.restapi.repository.FileRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class FileServiceTest extends TestCase {

    private final FileRepository fileRepository = Mockito.mock(FileRepository.class);

    private FileService fileService;

    @Before
    public void setUp(){
        fileService = new FileService(fileRepository);
    }


    public void testGetAllFiles() {
        List<File> files = Arrays.asList(new File(1, "Document", "/main"),
                new File(2, "UserInfo", "/bin"));

        given(fileRepository.getAll()).willReturn(files);
        List<File> testResult = fileService.getAllFiles();
        assertNotNull(testResult);
        assertEquals(files, testResult);
        assertEquals(2, testResult.size());
    }

    public void testGetFileById() {
        given(fileRepository.getById(1)).willReturn(new File(1, "Document", "/main"));
        File fileTest = fileService.getFileById(1);
        assertNotNull(fileTest);
        assertEquals(fileRepository.getById(1), fileTest);
        assertNull(fileService.getFileById(2));
    }

    public void testCreateFile() {
        File expected = new File(1, "Document", "/main");
        given(fileRepository.create(expected)).willReturn(expected);
        File actual = fileService.createFile(expected);

        assertNotNull(expected);
        assertEquals(expected, actual);
    }

    public void testUpdateFile() {
        File withoutChangesExpected = new File(1, "Document", "/main");
        given(fileRepository.getById(1)).willReturn(withoutChangesExpected);
        given(fileRepository.update(withoutChangesExpected)).willReturn(withoutChangesExpected);
        File withoutChangesActual = fileService.getFileById(1);

        assertNotNull(withoutChangesActual);
        assertEquals(withoutChangesExpected, withoutChangesActual);

        String filePath = "/documents";
        withoutChangesActual.setFilePath(filePath);
        fileService.updateFile(withoutChangesActual);
        assertEquals(filePath, withoutChangesActual.getFilePath());
    }

    public void testDeleteFileById() {
        fileService.deleteFileById(2);
        verify(fileRepository).deleteById(2);
    }
}