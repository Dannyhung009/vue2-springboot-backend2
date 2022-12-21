package com.example.service.impl;

import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.controller.dto.UserDto;
import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author danny
 * @since 2022-12-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();
    @Override
    public Boolean login(UserDto userdto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userdto.getUsername());
        queryWrapper.eq("password", userdto.getPassword());
        //如果有髒數據(重複的資料)會報錯!
//        User one = getOne(queryWrapper);
//        return one != null;
        //改善方式
        //方式1
//        List<User> list = list(queryWrapper);
//        return list.size() != 0;
        //方式2
        try {
            User one = getOne(queryWrapper);
            return one != null;
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e);
            return false;
        }

    }
}
