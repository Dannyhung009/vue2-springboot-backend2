package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.User;
import com.example.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {


//先不使用UserService interface
//    @Autowired
//    private UserService userService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/")
    public String index() {
        return "Home";
    }

    @PostMapping("/user")
    public Boolean saveUser(@RequestBody User user) {

        return userServiceImpl.saveUser(user);
    }

    @GetMapping("/user")
    public List<User> findall() {
        return userServiceImpl.findAll();
    }


    //刪除
    @DeleteMapping("/user/{id}")
    public boolean delete(@PathVariable Integer id) {
        return userServiceImpl.removeById(id);

    }
    //批次刪除
    @PostMapping("/user/delBatch")
    public boolean deleteBatch(@RequestBody List<Integer> ids) {
        return userServiceImpl.removeBatchByIds(ids);

    }

    //分頁查詢 Mybatis-Plus的方式
    //接口路徑:/user/page
    //@RequestParam接收    ?pageNum=1&pageSize=10
    //limit 第一個參數= (pageNum-1) * pageSize
    //第二個參數 pageSize
    @GetMapping("/user/page")
    public IPage<User> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String username,
                                @RequestParam(defaultValue = "") String email,
                                @RequestParam(defaultValue = "") String address
    ) {
        IPage<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("username", username);
        queryWrapper.and(w -> w.like("email", email));
//        queryWrapper.like("nickname",nickname);
        queryWrapper.like("address", address);
        //使用or 方法
//        queryWrapper.or().like("address",address);
        queryWrapper.orderByDesc("create_time");
        IPage<User> userIPage = userServiceImpl.page(page, queryWrapper);
        return userIPage;
    }


    //Mybatis版
//    @Autowired
//    private UserMapper userMapper;

//    查詢所有
//    @GetMapping("/user/all")
//    public List<User> findAll(){
//        return userMapper.findAll();
//    }

    //分頁查詢
    //接口路徑:/user/page
    //@RequestParam接收    ?pageNum=1&pageSize=10
    //limit 第一個參數= (pageNum-1) * pageSize
    //第二個參數 pageSize
//    @GetMapping("/user/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum,
//                                       @RequestParam Integer pageSize,
//                                       @RequestParam String username
//                                       ){
//        pageNum = (pageNum -1) * pageSize;
//        username = '%'+username+'%';
//        List<User> data = userMapper.selectPage(pageNum, pageSize, username);
//        Integer total = userMapper.selectTotal(username);
//        Map<String,Object> result = new HashMap<>();
//        result.put("data",data);
//        result.put("total",total);
//        return result;
//    }

    //新增 或者 更新
//    @PostMapping("/user")
//    public Integer save(@RequestBody User user){
//
//        return userService.save(user);
//    }


//    @DeleteMapping("/user/{id}")
//    public Integer delete(@PathVariable Integer id){
//        return userMapper.deleteById(id);
//
//    }


}
