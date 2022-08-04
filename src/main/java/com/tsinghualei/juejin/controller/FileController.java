package com.tsinghualei.juejin.controller;

import com.tsinghualei.juejin.common.result.GraceJSONResult;
import com.tsinghualei.juejin.model.param.BaseRequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Slf4j
@RestController
@RequestMapping("fileContoller")
public class FileController extends BaseController{

    @Value("${upload_dir}")
    String imgUpLoadDir;

    @PostMapping("uploadImage")
    public GraceJSONResult uploadImage(@RequestBody BaseRequestParam baseRequestParam,
                                       @RequestParam MultipartFile file
    ){
        if(file.isEmpty()){
            return GraceJSONResult.error("文件不能为空");
        }
        String filename = file.getOriginalFilename();
        logger.info("file name: "+filename);
        String suffix = filename.substring(filename.lastIndexOf("."));
        String filePath = imgUpLoadDir;
        File dest = new File(filePath,filename);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }
        try{
            file.transferTo(dest);
        }catch (Exception e){
            e.printStackTrace();
            return  GraceJSONResult.error("文件上传失败");
        }
        return GraceJSONResult.ok();
    }

    @GetMapping("downloadImage")
    public GraceJSONResult downloadImage(String imageName, HttpServletRequest request, HttpServletResponse response) {
        logger.info("the imageName is : "+imageName);
        String fileUrl = imgUpLoadDir+imageName;

        File file = new File(fileUrl);
        if (file.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + imageName);// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                System.out.println("success");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return GraceJSONResult.error("下载失败");
    }
}
