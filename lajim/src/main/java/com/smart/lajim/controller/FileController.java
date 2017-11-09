package com.smart.lajim.controller;

import com.smart.lajim.jqGridUtil.DataRequest;
import com.smart.lajim.jqGridUtil.DataResponse;
import com.smart.lajim.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
@RequestMapping(value = "/file")
public class FileController {

    private FileService fileService;
    @Autowired
    void setFileService(FileService fileService){
        this.fileService = fileService;
    }

    @RequestMapping(value = "/toUpload.html")
    public String toUpload(){
        return "file/upload";
    }

    /**
     * 文件上传功能
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/upload.html")
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String fileName = file.getOriginalFilename();
            System.out.println(path);
            System.out.println(fileName);
            File dir = new File(path,fileName);
            if(!dir.exists()){
                dir.mkdirs();
            }
//          MultipartFile自带的解析方法
            file.transferTo(dir);
            return "file/upload";
    }

    /**
     * 文件下载功能
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/download.html")
    public void download(HttpServletRequest request,HttpServletResponse response) throws Exception{
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = request.getSession().getServletContext().getRealPath("upload")+"/shiro.txt";
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = "shiro.txt";
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    @RequestMapping(value = "/fileManage.html")
    public String fileManage(){
        return "file/fileManage";
    }

    @RequestMapping
    public String listFile(){
        List files = fileService.listAllFiles();
        return "";
    }

    @RequestMapping(value = "/listFile.html")
    @ResponseBody
    public DataResponse<com.smart.lajim.domain.File> list(@RequestParam(defaultValue="1",value="page") String page,
                                                          @RequestParam(defaultValue="20",value="rows") String rows,
                                                          @RequestParam("sidx") String sidx,
                                                          @RequestParam("sord") String sord,
                                                          @RequestParam("_search") boolean search
    ){
        try {
            DataRequest request = new DataRequest();
            request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
            request.setRows(StringUtils.isEmpty(rows) ? 20 : Integer.valueOf(rows));
            request.setSidx(sidx);
            request.setSord(sord);
            request.setSearch(search);
            return fileService.search(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
