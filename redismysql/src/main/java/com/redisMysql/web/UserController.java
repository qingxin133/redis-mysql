package com.redisMysql.web;

import com.redisMysql.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by lumi on 17-8-13.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Resource(name="userService")
    private UserService userService;

    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    public String signUp(String name, String password){
        /**
         * 用户注册：写入操作，需要很高的时效性，会同步写入mysql和redis
         */
        try{
            boolean flag = userService.createUser(name, password);
            if (flag){
                return "ok";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "fail";
    }

}
