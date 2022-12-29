package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.Result;
import com.example.entity.UploadFile;
import com.example.entity.User;
import com.example.mapper.FileMapper;
import com.example.utils.TokenUtils;
import io.swagger.annotations.Api;
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
@Api(tags = "文件上傳接口")
public class FileController {

//    @Value("${files.upload.path}")//提取在config裡面設定的檔案上傳位置
//    private String fileUploadPath;


    private String fileUploadPath = "D:\\work\\JAVA\\vue1\\springboot\\files\\";

    @Resource
    private FileMapper fileMapper;


    /**
     * 文件上傳接口
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
////        先建立新文件
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
        file.transferTo(uploadFile);//上傳文件到磁盤
        md5 = SecureUtil.md5(uploadFile);//生成文件的md5
        UploadFile dbFile = getFileByMd5(md5);//從資料庫查詢文件的md5是否存在相同的紀錄

        //取得文件的url
        if (dbFile != null) {//有重複文件
            url = dbFile.getUrl();
            uploadFile.delete();//由於文件已存在，所以刪除剛才上傳的重複文件
        } else {//沒有重複文件
            //資料庫若不存在重複文件，則不刪除剛才上傳的文件
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

//        UploadFile one = fileMapper.selectOne(queryWrapper);//使用selectOne方法，注意取到兩筆以上將會報錯
        List<UploadFile> fileList = fileMapper.selectList(queryWrapper);//使用List
//        if(fileList.size() == 0){
//            fileList = null;
//        }else{
//            fileList.get(0);
//        }
        return fileList.size() == 0 ? null : fileList.get(0);
    }

    /**
     * 分頁查詢接口
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name
                           ) {
        QueryWrapper<UploadFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete",false);//查詢未刪除的紀錄
        queryWrapper.orderByDesc("id");
        if(!"".equals(name)){
            queryWrapper.like("name", name);
        }

        Page<UploadFile> objectPage = new Page<>(pageNum, pageSize);

        Page<UploadFile> uploadFilePage = fileMapper.selectPage(objectPage, queryWrapper);

        return Result.success(uploadFilePage);

    }

    //刪除
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        UploadFile uploadFile = fileMapper.selectById(id);
        uploadFile.setIsDelete(true);
        fileMapper.updateById(uploadFile);
        return Result.success();


//        boolean b = fileMapper.deleteById(id);
//        return Result.success(b);

    }

    //批次刪除
    @PostMapping("/delBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        //select * from sys_file where id in (id,id,id...)
        QueryWrapper<UploadFile> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        List<UploadFile> uploadFiles = fileMapper.selectList(queryWrapper);
        for(UploadFile file : uploadFiles){
            file.setIsDelete(true);
            fileMapper.updateById(file);
        }

        return Result.success();

    }

    //新增或更新
    @PostMapping("/update")
    public Result update(@RequestBody UploadFile uploadFile) {
        int i = fileMapper.updateById(uploadFile);

        return Result.success(i);
    }

}
