package com.example.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Constants;
import com.example.common.Result;
import com.example.entity.dto.UserDto;
import com.example.utils.TokenUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.example.service.IUserService;
import com.example.entity.User;


import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * User控制器
 * 使用者資料接口(End point)
 * </p>
 *
 * @author danny
 * @since 2022-12-20
 */
@RestController
@RequestMapping("/user")
@Api(tags = "User資料接口")
public class UserController {

    @Resource
    private IUserService userService;

    //新增或更新
    @PostMapping
    public Result saveUser(@RequestBody User user) {
        boolean b = userService.saveOrUpdate(user);

        return Result.success(b);
    }

    //刪除單筆
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        boolean b = userService.removeById(id);
        return Result.success(b);

    }
    //批次刪除
    @PostMapping("/delBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        boolean b = userService.removeBatchByIds(ids);

        return Result.success(b);

    }

    //查詢所有
    @GetMapping
    public Result findAll() {
        List<User> list = userService.list();
        return Result.success(list);
    }

    //根據ID查詢單筆會員
    @GetMapping("/{id}")
    public Result findByUserId(@PathVariable Integer id) {
        User user = userService.getById(id);
        return Result.success(user);
    }
    //根據username查詢單筆會員
    @GetMapping("/username/{username}")
    public Result findByUsername(@PathVariable String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User one = userService.getOne(queryWrapper);
        return Result.success(one);
    }

    //分頁查詢
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String username,
                           @RequestParam(defaultValue = "") String email,
                           @RequestParam(defaultValue = "") String address) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        //使用 and 方法 範例
        queryWrapper.and(w -> w.like("email", email));
//        queryWrapper.like("nickname",nickname);
        queryWrapper.like("address", address);
        //使用 or 方法 範例
//        queryWrapper.or().like("address",address);
        queryWrapper.orderByDesc("create_time");
        Page<User> page = userService.page(new Page<>(pageNum, pageSize), queryWrapper);

        //測試獲取當前使用者資訊
        if (TokenUtils.getCurrentUser() != null) {
            User currentUser = TokenUtils.getCurrentUser();
            System.out.println(currentUser);

        }
        return Result.success(page);


//        return userService.page(new Page<>(pageNum, pageSize));
    }



    @GetMapping("/export")
    public Result exportExcel(HttpServletResponse response) throws Exception {
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

        return Result.success();


    }

    @PostMapping("/import")
    public Result importExcel(MultipartFile file) throws Exception {
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

        boolean b = userService.saveBatch(users);
        return Result.success(b);
    }

    //舊版本
    //    @PostMapping("/login")
//    public Boolean login(@RequestBody UserDto userdto){
//        String username = userdto.getUsername();
//        String password = userdto.getPassword();
//        //檢驗前端傳入資料
//        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
//            return false;
//        }
//
//        return userService.login(userdto);
//    }
    //新版
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        //檢驗前端傳入資料
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "參數錯誤");
        }
        UserDto dto = userService.login(userDto);


        return Result.success(dto);
    }

    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        //檢驗前端傳入資料
        if (StrUtil.isBlank(username) || StrUtil.isBlank(password)) {
            return Result.error(Constants.CODE_400, "參數錯誤");
        }
        User user = userService.register(userDto);

        return Result.success(user);
    }


}

