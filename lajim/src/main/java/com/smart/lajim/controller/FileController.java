package com.smart.lajim.controller;

import com.smart.lajim.jqGridUtil.DataRequest;
import com.smart.lajim.jqGridUtil.DataResponse;
import com.smart.lajim.service.FileService;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/upload.html")
    public String upload(MultipartFile file, HttpServletRequest request) throws IOException {
            System.out.println("###########upload");
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

    @RequestMapping(value = "/addFile.html")
    @ResponseBody
    public Map addFile(HttpServletRequest request, HttpServletResponse response){
        return null;
    }

    @RequestMapping(value = "uploadFile.html")
    @ResponseBody //ajax请求必填
    public Map<String, Object> uploadFile(HttpServletRequest request,HttpServletResponse response) throws Exception {

        System.out.println("###########uploadFile");
        Map<String, Object> map = new HashMap<String,Object>();

        com.smart.lajim.domain.File vo = new com.smart.lajim.domain.File();

        //取得上传的文件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("filename");

        //得到文件名称

        String fileName = file.getOriginalFilename();

        System.out.println("###########fileName:" + fileName);

        String fileRealPath = request.getSession().getServletContext().getRealPath("/upload/");

        System.out.println("###########fileRealPath:" + fileRealPath);

        //判断文件夹是否存在
        File targerFile = new File(fileRealPath);
        //判断是否存在目录
        if(!targerFile.exists()) {
            targerFile.mkdirs();
        }

        //保存文件
        File uploadFile = new File(fileRealPath+fileName);
        FileCopyUtils.copy(file.getBytes(), uploadFile);

        //配置文件实体信息
//        vo.setFilepath(webPath+randomName);//路径
        vo.setFilepath(fileRealPath+fileName);//路径
        vo.setFilename(fileName);//文件名
        vo.setCreatetime(new Date());
        vo.setCreateuser(1);
        //返回上传信息
        fileService.insert(vo);
        Map<String, String> data = new HashMap<String,String>();
        data.put("msg","上传成功");
        data.put("error","上传失败");
        map.put("data", data);
        return map;
    }
}

