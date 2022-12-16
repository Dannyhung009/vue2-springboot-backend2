package com.example.service.impl;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    public Integer save(User user){
        if(user.getId() == null){ //user沒有 id 表示是新增 否則是更新
            return userMapper.insert(user);
        }else{  //更新
            return userMapper.update(user);
        }


//        return userMapper.insert(user);
    }



}
