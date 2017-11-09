package com.smart.lajim.service;

import com.smart.lajim.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

@ContextConfiguration("classpath*:/application-context.xml")
public class FileServiceTest extends AbstractTransactionalTestNGSpringContextTests {
    @Autowired
    private FileService fileService;

    @Test
    public void testListAllFiles()throws Exception{
        List files  = fileService.listAllFiles();
        File file = (File)files.get(0);
        assertEquals(files.isEmpty(), false);
    }
}
