package com.smart.lajim.service;

import com.smart.lajim.dao.FileDao;
import com.smart.lajim.domain.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FileService {

    private FileDao fileDao;
    @Autowired
    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public boolean insert(File file){
        return fileDao.insertFile(file);
    }

    @Transactional
    public List listAllFiles(){
        return fileDao.listAllFiles();
    }

    @Transactional
    public boolean deleteFile(int id){
        return fileDao.deleteFile(id);
    }

}
