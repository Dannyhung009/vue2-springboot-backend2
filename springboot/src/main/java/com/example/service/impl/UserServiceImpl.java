package com.example.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.Constants;
import com.example.entity.dto.UserDto;
import com.example.entity.User;
import com.example.exception.ServiceException;
import com.example.mapper.UserMapper;
import com.example.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.utils.TokenUtils;
import org.springframework.stereotype.Service;

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
    public UserDto login(UserDto userDto) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", userDto.getUsername());
//        queryWrapper.eq("password", userDto.getPassword());
        //如果有髒數據(重複的資料)會報錯!
//        User one = getOne(queryWrapper);
//        return one != null;
        //改善方式
        //方式1
//        List<User> list = list(queryWrapper);
//        return list.size() != 0;
        //方式2
//        User one;
//        try {
//            one = getOne(queryWrapper);//從資料庫查詢
//
//        } catch (Exception e) {
////            e.printStackTrace();
//            LOG.error(e);
//            throw new ServiceException(Constants.CODE_500,"系統錯誤");
//        }

        //重複程式碼封裝成 private User getUserInfo(UserDto userDto){}方法
        User one = getUserInfo(userDto);
        if(one != null){
            BeanUtil.copyProperties(one,userDto,true);//把one(User)物件轉換到userDto物件
            //設置token
            String token = TokenUtils.generateToken(one.getId().toString(), one.getPassword());
            userDto.setToken(token);

            return userDto;
        }else{
            throw new ServiceException(Constants.CODE_600,"用戶名或密碼錯誤");
        }

    }

    @Override
    public User register(UserDto userDto) {
        User one = getUserInfo(userDto);
        if(one == null){//確認資料庫中沒有傳入的userDto資料
            one = new User();
            BeanUtil.copyProperties(userDto,one,true);//把userDto物件轉換到User物件
            save(one);
        }else{
            throw new ServiceException(Constants.CODE_600,"用戶名已存在");
        }


        return one;
    }

    private User getUserInfo(UserDto userDto){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
//        User one = getOne(queryWrapper);
        User one;
        try {
            one = getOne(queryWrapper);//從資料庫查詢

        } catch (Exception e) {
//            e.printStackTrace();
            LOG.error(e);
//            return false;
            throw new ServiceException(Constants.CODE_500,"系統錯誤");
        }
        return one;
    }
}
