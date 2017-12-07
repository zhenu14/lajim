package com.smart.lajim.controller;

import com.smart.lajim.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
            String remarks = request.getParameter("remarks");
            System.out.println(remarks);
            System.out.println(path);
            System.out.println(fileName);
            try {
                File dir = new File(path,fileName);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                //MultipartFile自带的解析方法
                file.transferTo(dir);
                //插入文件信息到数据库
                com.smart.lajim.domain.File domain = new com.smart.lajim.domain.File();
                domain.setFilepath(path+"\\"+fileName);
                domain.setFilename(fileName);
                domain.setCreatetime(new Date());
                domain.setCreateuser(1);
                domain.setRemarks(remarks);
                fileService.insert(domain);
            }catch (Exception e){
                e.printStackTrace();
            }
        return "/file/fileManage";

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




    @RequestMapping(value = "/listFile.html")
    @ResponseBody
    public List listFile(HttpServletRequest request,HttpServletResponse response)throws Exception{
        return fileService.listAllFiles();
    }

    @RequestMapping(value = "/fileDownload1.html")
    public void fileDownload1(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String filepath = request.getParameter("filepath");
        String filename = request.getParameter("filename");
        System.out.println(filepath);
        System.out.println(filename);
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(filepath)));
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

    @RequestMapping(value = "/fileDownload.html")
    public void fileDownload(HttpServletRequest request,HttpServletResponse response) throws Exception{
        request.setCharacterEncoding("utf-8");
        System.out.println(request.getParameter("filepath"));
        System.out.println(request.getParameter("filename"));
        String filepath = request.getParameter("filepath");
        String filename = request.getParameter("filename");
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(filepath)));
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

    @RequestMapping(value = "/deleteFile.html")
    @ResponseBody
    public Map<String,Object> deleteFile(HttpServletRequest request, HttpServletResponse response)throws Exception{
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if(fileService.deleteFile(id)){
                result.put("success","true");
                result.put("msg","删除成功");
            }else{
                result.put("success","false");
                result.put("msg","删除失败");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //    @RequestMapping(value = "/listFile.html")
//    @ResponseBody
//    public DataResponse<com.smart.lajim.domain.File> list(@RequestParam(defaultValue="1",value="page") String page,
//                                                          @RequestParam(defaultValue="20",value="rows") String rows,
//                                                          @RequestParam("sidx") String sidx,
//                                                          @RequestParam("sord") String sord,
//                                                          @RequestParam("_search") boolean search
//    ){
//        try {
//            DataRequest request = new DataRequest();
//            request.setPage(StringUtils.isEmpty(page) ? 1 : Integer.valueOf(page));
//            request.setRows(StringUtils.isEmpty(rows) ? 20 : Integer.valueOf(rows));
//            request.setSidx(sidx);
//            request.setSord(sord);
//            request.setSearch(search);
//            return fileService.search(request);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
