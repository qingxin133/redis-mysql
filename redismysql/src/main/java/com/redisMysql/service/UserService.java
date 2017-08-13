package com.redisMysql.service;

import com.redisMysql.entity.User;

/**
 * Created by lumi on 17-8-13.
 */
public interface UserService {

    User getById(long id);

    boolean createUser(String name, String password);

}
