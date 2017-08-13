package com.redisMysql.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.redisMysql.entity.User;
import com.redisMysql.entity.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by lumi on 17-8-13.
 */
public class RedisDao {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    private RuntimeSchema<User> userSchema = RuntimeSchema
            .createFrom(User.class);

    private RuntimeSchema<Weather> weatherSchema = RuntimeSchema.createFrom(Weather.class);

    public User getUser(long userId) {
        // redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "user:" + userId;
                // 并没有实现内部序列化操作
                // get -> byte[] -> 反序列化 -> Object(User)
                // 采用自定义序列化
                // protostuff : pojo
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    // 空对象
                    User user = userSchema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, user, userSchema);
                    // user 被反序列化
                    return user;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putUser(User user) {
        // set Object(User) -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "User:" + user.getId();
                byte[] bytes = ProtostuffIOUtil
                        .toByteArray(user, userSchema, LinkedBuffer
                                .allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存
                int timeout = 10;// 10秒
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Weather getWeather(long weatherId) {
        // redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "weather:" + weatherId;
                // 并没有实现内部序列化操作
                // get -> byte[] -> 反序列化 -> Object(User)
                // 采用自定义序列化
                // protostuff : pojo
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    // 空对象
                    Weather weather = weatherSchema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, weather, weatherSchema);
                    // user 被反序列化
                    return weather;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putWeather(Weather weather) {
        // set Object(User) -> 序列化 -> byte[]
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "weather:" + weather.getId();
                byte[] bytes = ProtostuffIOUtil
                        .toByteArray(weather, weatherSchema, LinkedBuffer
                                .allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                // 超时缓存
                int timeout = 60 * 60 * 24;// 1天
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
