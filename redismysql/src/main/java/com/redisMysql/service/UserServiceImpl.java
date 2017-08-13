package com.redisMysql.service;

import com.redisMysql.dao.UserDao;
import com.redisMysql.dao.cache.RedisDao;
import com.redisMysql.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lumi on 17-8-13.
 */

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisDao redisDao;

    public User getById(long id) {
        if (id <= 0){
            return null;
        }
        User user = redisDao.getUser(id);
        if (user != null){
            return user;
        }else{
            user = userDao.getById(id);
            return user;
        }

    }

    public boolean createUser(String name, String password) {
        /**
         * 写入用户操作，需要高一致性
         * 把user信息写入mysql和redis
         */
        User user = null;
        try{
            if (name == null && password == null){
                return false;
            }
            user = new User();
            user.setName(name);
            user.setPassword(password);
        }catch (Exception e){
            e.printStackTrace();
        }
        userDao.createUser(user);
        redisDao.putUser(user);
        return true;
    }
}
