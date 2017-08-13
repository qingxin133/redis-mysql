package com.redisMysql.service;

import com.redisMysql.dao.WeatherDao;
import com.redisMysql.dao.cache.RedisDao;
import com.redisMysql.entity.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by lumi on 17-8-13.
 */

@Service("weatherService")
public class WeatherServiceImpl implements WeatherService {
    /**
     * Lazy: 写入的时候不会同步写入到redis，而是在查询的时候，若redis中没有则从mysql里查出来，再写入redis
     */
    @Autowired
    private WeatherDao weatherDao;

    @Autowired
    private RedisDao redisDao;

    public Weather getById(long id) {
        Weather weather = null;
        if (id <= 0){
            return null;
        }
        weather = redisDao.getWeather(id);
        if (weather == null){
            weather = weatherDao.getById(id);
            redisDao.putWeather(weather);
        }
        return weather;
    }

    public boolean insertWeather(String address, String now, Date time) {
        Weather weather = null;
        if (address == null && now == null && time == null){
            return false;
        }
        weather.setAddress(address);
        weather.setNow(now);
        weather.setTime(time);
        weatherDao.insertWeather(weather);
        if (weather != null){
            return true;
        }
        return false;
    }

}
