package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上傳接口(End point)
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;


    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();
        //先存儲到磁盤
        File uploadParentFile = new File(fileUploadPath);
        //判斷配置的文件目錄是否存在，若不存在則創建一個新的文件目錄
        if(!uploadParentFile.exists()){
            uploadParentFile.mkdirs();
        }
        //定義一個唯一的文件標示碼
        String uuid = IdUtil.simpleUUID();//使用Hutool的Idutil工具類別生成一個uuid
        File uploadFile = new File(fileUploadPath + uuid);

        //把獲取到的文件存儲到磁盤目錄
        file.transferTo(uploadFile);
        //存儲數據庫





    }
    @PostMapping
    public String download(){

    }

}
