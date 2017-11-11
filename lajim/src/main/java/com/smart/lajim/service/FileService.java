package com.smart.lajim.service;

import com.smart.lajim.dao.FileDao;
import com.smart.lajim.domain.File;
import com.smart.lajim.jqGridUtil.DataRequest;
import com.smart.lajim.jqGridUtil.DataResponse;
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

    public DataResponse<File> search(DataRequest request) {
        DataResponse<File> response = new DataResponse<File>();
        int count;//总记录数
        int limit = request.getRows() <= 0 ? 20 : request.getRows();//每页显示数量
        int totalPages;//总页数
        int page = request.getPage() <= 0 ? 1 : request.getPage();//当前显示页码
        List<File> list;

//        Set<Criterion> set = initSearchCondition(request.isSearch(), request.getSearchField(), request.getSearchOper(), request.getSearchString());
        count = fileDao.getFileCount();
        System.out.println("@@@@@"+count);
        totalPages = count / limit;
        if (count % limit != 0) {
            totalPages++;
        }
        int currPage = Math.min(totalPages, page);

        int start = currPage * limit - limit;
        start = start < 0 ? 0 : start;
        System.out.println(start);
        System.out.println(limit);
        String sql = "SELECT id,filename,filepath,createtime,createuser FROM t_files LIMIT ?,?";
        list = fileDao.list(sql, start, limit);
        response.setRecords(count);
        response.setTotal(totalPages);
        response.setPage(currPage);
        response.setRows(list);
        return response;
    }
}
