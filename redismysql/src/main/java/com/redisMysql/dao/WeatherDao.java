package com.redisMysql.dao;

import com.redisMysql.entity.Weather;

/**
 * Created by lumi on 17-8-13.
 */
public interface WeatherDao {

    Weather getById(long id);

    boolean insertWeather(Weather weather);

}
