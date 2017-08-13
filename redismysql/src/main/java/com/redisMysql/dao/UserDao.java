package com.redisMysql.dao;

import com.redisMysql.entity.User;

/**
 * Created by lumi on 17-8-13.
 */

public interface UserDao {

    User getById(long id);

    boolean createUser(User user);

}
