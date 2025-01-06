package com.example.controller;

import cn.hutool.core.io.FileUtil;
import com.example.common.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
public class FileController {
    //D:\canteen\files\
    private static final String filePath=System.getProperty("user.dir")+"/files/";

    @PostMapping("upload")
    public Result upload(MultipartFile file) throws IOException {
        if(!FileUtil.exist(filePath)){
            FileUtil.mkdir(filePath);
        }
        String originFilename=file.getOriginalFilename();
        String realFilePath=filePath+originFilename;
        if(FileUtil.exist(realFilePath)){
            originFilename=FileUtil.mainName(originFilename)+"_"+System.currentTimeMillis()+"."+FileUtil.extName(originFilename);
            realFilePath=filePath+originFilename;
        }
        File localfile=new File(realFilePath);
        file.transferTo(localfile);
        String url="http://localhost:9090/files/download/"+originFilename;
        return Result.success(url);
    }

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName,HttpServletResponse response) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename="+ URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        String realFilePath=filePath+fileName;
        byte[] bytes=FileUtil.readBytes(realFilePath);
        ServletOutputStream os=response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }
}

