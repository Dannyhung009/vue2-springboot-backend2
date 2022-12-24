package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.entity.UploadFile;
import com.example.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 文件上傳接口(End point)
 */
@RestController
@RequestMapping("/file")
public class FileController {

//    @Value("${files.upload.path}")//提取在config裡面設定的檔案上傳位置
//    private String fileUploadPath;


    private String fileUploadPath = "D:\\work\\JAVA\\vue1\\springboot\\files\\";

    @Resource
    private FileMapper fileMapper;


    /**
     * 文件上傳街口
     *
     * @param file 前端傳遞過來的文件
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize() / 1024;
////        先存儲到磁盤
////        File uploadParentFile = new File(fileUploadPath);
////        判斷配置的文件目錄是否存在，若不存在則創建一個新的文件目錄
//        if (!uploadParentFile.exists()) {
//            uploadParentFile.mkdirs();
//        }
        //定義一個唯一的文件標示碼
        String uuid = IdUtil.simpleUUID();//使用Hutool的Idutil工具類別生成一個uuid
        String fileUUID = uuid + StrUtil.DOT + type;
//        String url = "http://localhost:9090/file/"+fileUUID;


        File uploadFile = new File(fileUploadPath + fileUUID);
        //判斷配置的文件目錄是否存在，若不存在則創建一個新的文件目錄
        File parentFile = uploadFile.getParentFile();
        if (!parentFile.exists()) {//如果上傳檔案目錄不存在
            parentFile.mkdirs();//則新建一個資料夾
        }
        //當文件存在的時候獲取文件的md5
        String md5;
        String url;
        if (uploadFile.exists()) {//當文件存在
            //獲取文件的md5，通過對比文件md5，避免重複上傳相同內容的文件
            md5 = SecureUtil.md5(uploadFile);//處理重複的檔案，首先先生成檔案的md5
            System.out.println("=================md5: " +md5);//印出md5
            UploadFile dbFile = getFileByMd5(md5);//從資料庫查詢文件的md5是否存在相同的紀錄

            //取得文件的url
            if (dbFile != null) {
                url = dbFile.getUrl();
            } else {//文件url不存在
            //把獲取到的文件存儲到磁盤目錄
            file.transferTo(uploadFile);
            url = "http://localhost:9090/file/" + fileUUID;
            }
        } else {//當文件不存在
            //把獲取到的文件存儲到磁盤目錄
            file.transferTo(uploadFile);
            md5 = SecureUtil.md5(uploadFile);
            url = "http://localhost:9090/file/" + fileUUID;
        }


        //存儲數據庫
        UploadFile saveFile = new UploadFile();
        saveFile.setName(originalFilename);
        saveFile.setType(type);
        saveFile.setSize(size);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileMapper.insert(saveFile);


        return url;


    }

    /**
     * 文件下載接口  http://localhost:9090/file/{fileUUID};
     *
     * @param fileUUID
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileUUID}")
    public void download(@PathVariable String fileUUID, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUUID);//根據文件的唯一標示碼uuid取得文件

        ServletOutputStream outputStream = response.getOutputStream();//建立一個輸出流物件
        //設定輸出流的格式
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUUID, "UTF-8"));
        response.setContentType("application/octet-stream");

        //讀取文件的字節流
        outputStream.write(FileUtil.readBytes(uploadFile));//使用Hutool寫入
        outputStream.flush();
        outputStream.close();


    }

    /**
     * 通過文件的md5查詢文件
     *
     * @param md5
     * @return
     */
    private UploadFile getFileByMd5(String md5) {
        //查詢文件的md5是否存在
        QueryWrapper<UploadFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("md5", md5);
//        List<UploadFile> fileList = fileMapper.selectList(queryWrapper);//也可使用List
        UploadFile one = fileMapper.selectOne(queryWrapper);//使用selectOne方法，須將資料庫md5欄位設定為unique，取兩筆將會報錯
        return one;
    }

}
