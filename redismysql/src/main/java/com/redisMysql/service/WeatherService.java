package com.redisMysql.service;

import com.redisMysql.entity.Weather;

import java.util.Date;

/**
 * Created by lumi on 17-8-13.
 */
public interface WeatherService {

    Weather getById(long id);

    boolean insertWeather(String address, String now, Date time);

}
