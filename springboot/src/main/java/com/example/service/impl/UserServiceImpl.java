package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> {

    public Boolean saveUser(User user) {
//        if(user.getId()==null){
//            return save(user);// mybatis-plus提供的方法，表示插入數據
//        }else{
//            return updateById(user);
//        }
        return saveOrUpdate(user);
    }


    public List<User> findAll() {
        return list();
    }



    //給mybatis用
//    @Autowired
//    private UserMapper userMapper;
//
//
//    public Integer save(User user){
//        if(user.getId() == null){ //user沒有 id 表示是新增 否則是更新
//            return userMapper.insert(user);
//        }else{  //更新
//            return userMapper.update(user);
//        }
//    }



}
