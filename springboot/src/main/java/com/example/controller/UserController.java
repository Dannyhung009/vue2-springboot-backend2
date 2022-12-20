package com.example.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.service.IUserService;
import com.example.entity.User;


import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import static cn.hutool.poi.excel.sax.ElementName.row;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author danny
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    //新增或更新
    @PostMapping
    public Boolean saveUser(@RequestBody User user) {

        return userService.saveOrUpdate(user);
    }

    //刪除
    @DeleteMapping("/{id}")
    public Boolean delete(@PathVariable Integer id) {
        return userService.removeById(id);

    }

    //查詢所有
    @GetMapping
    public List<User> findall() {
        return userService.list();
    }

    //根據ID查詢
    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id) {
        return userService.getById(id);
    }

    //分頁查詢
    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.and(w -> w.like("email", email));
//        queryWrapper.like("nickname",nickname);
        queryWrapper.like("address", address);
        //使用or 方法
//        queryWrapper.or().like("address",address);
        queryWrapper.orderByDesc("create_time");
        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
//        return userService.page(new Page<>(pageNum, pageSize));
    }

    //批次刪除
    @PostMapping("/delBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userService.removeBatchByIds(ids);

    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        //從資料庫查出所有的數據
        List<User> list = userService.list();
        //通過工具類創建writer 寫出到磁盤路徑
//        ExcelWriter writer = ExcelUtil.getWriter(filesUploadPath + "/用戶信息.xlsx")
        //在內存操作，寫出到磁盤
        ExcelWriter writer = ExcelUtil.getWriter(true);
        //自定義標題別名
        writer.addHeaderAlias("username", "使用者帳號");
        writer.addHeaderAlias("password", "使用者密碼");
        writer.addHeaderAlias("nickname", "使用者暱稱");
        writer.addHeaderAlias("phone", "使用者電話");
        writer.addHeaderAlias("email", "電子信箱");
        writer.addHeaderAlias("address", "地址");
        writer.addHeaderAlias("createTime", "創建時間");
        writer.addHeaderAlias("avatarUrl", "頭像");

        //一次性寫出list內的物件到excel，使用默認樣式，強制輸出標題
        writer.write(list, true);

        //設置瀏覽器響應的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用戶訊息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();


    }

    @PostMapping("/import")
    public Boolean importExcel(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        //方式一
        //自動讀取全部，表頭需要跟資料庫table 英文名一致
        //通過java bean 的方式讀取Excel內的對象，但是要求表頭是英文，跟java bean的屬性要對應起來
//        List<User> list = reader.readAll(User.class);

//        System.out.println(list);//測試有是否讀取成功
//        return userService.saveBatch(list);


        //方式二
        //手動方式讀取，忽略表頭中文，直接讀取表的內容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();
        for (List<Object> objectList : list) {
            User user = new User();
            user.setUsername(objectList.get(0).toString());
            user.setPassword(objectList.get(1).toString());
            user.setNickname(objectList.get(2).toString());
            user.setEmail(objectList.get(3).toString());
            user.setPhone(objectList.get(4).toString());
            user.setAddress(objectList.get(5).toString());
            user.setAvatarUrl(objectList.get(6).toString());
            users.add(user);
        }

//        System.out.println(list); //測試有是否讀取成功

        return userService.saveBatch(users);
    }


}

