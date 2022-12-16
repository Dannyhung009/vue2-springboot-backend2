package com.example.controller;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(){
        return "Home";
    }

    //查詢所有
    @GetMapping("/user/all")
    public List<User> findAll(){
        return userMapper.findAll();
    }

    //新增 或者 更新
    @PostMapping("/user")
    public Integer save(@RequestBody User user){

        return userService.save(user);
    }

    @DeleteMapping("/user/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);

    }






}
