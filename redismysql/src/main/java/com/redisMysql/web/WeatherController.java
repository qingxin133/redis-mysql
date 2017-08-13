package com.redisMysql.web;

import com.redisMysql.entity.Weather;
import com.redisMysql.service.WeatherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

/**
 * Created by lumi on 17-8-13.
 */
@Controller
@RequestMapping("/weather")
public class WeatherController {

    @Resource(name = "weatherService")
    private WeatherService weatherService;

    @RequestMapping(value = "/today", method = RequestMethod.GET)
    public String getWeather(long id){
        /**
         * 查询天气不需要很高的时效性，一天更新一次，redis过期时间设置为24小时
         */
        Weather weather = weatherService.getById(id);
        if (weather != null){
            return "ok";
        }else {
            return "fail";
        }
    }
}
