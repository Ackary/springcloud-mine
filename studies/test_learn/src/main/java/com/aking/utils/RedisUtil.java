package com.aking.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 使用RedisTemplate需要频繁调用.opForxxx然后才能进行对应的操作，这样使用起来代码效率低下，
 * 将这些常用的公共API抽取出来封装成为一个工具类，然后直接使用工具类来间接操作Redis.
 *
 * @author ak
 * @version 1.0
 * @date 2021/5/11
 */
@SuppressWarnings("all")
public final class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static RedisUtil instance = null;

    private RedisUtil() {

    }

    public static RedisUtil getInstance() {
        // 延时加载
        if (instance == null) {
            // 同步锁
            synchronized (RedisUtil.class) {
                if (instance == null) {
                    instance = new RedisUtil();
                }
            }
        }
        return instance;
    }

    // ===============Common===============

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (expire != null) {
            return expire;
        }
        throw new NullPointerException();
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true存在 false不存在
     */
    public boolean hasKey(String key) {
        Boolean hasKey;
        try {
            hasKey = redisTemplate.hasKey(key);
            if (hasKey != null) {
                return hasKey;
            }
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ===============String===============

    /**
     * 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 放入缓存并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return 递增后的值
     */
    public long incr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }

        Long increment = redisTemplate.opsForValue().increment(key, delta);
        if (increment != null) {
            return increment;
        }
        throw new NullPointerException();
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return 递减后的值
     */
    public long decr(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }

        Long decrement = redisTemplate.opsForValue().increment(key, -delta);
        if (decrement != null) {
            return decrement;
        }
        throw new NullPointerException();
    }

    // ===============Map===============

    /**
     * 获取key对应单个键值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取key对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 放入key的多个键值
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 放入key的多个键值 并设置过期时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建,并设置过期时间
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以有多个 不能为null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有某个值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true存在 false不存在
     */
    public boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在则创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return 递增后的值
     */
    public double hincr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少几(小于0)
     * @return 递减后的值
     */
    public double hdecr(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    // ===============Set===============

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sset(String key, Object... values) {
        try {
            Long add = redisTemplate.opsForSet().add(key, values);
            if (add != null) {
                return add;
            }
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取set中的所有值
     *
     * @param key 键
     * @return set中的所有值
     */
    public Set<Object> sget(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断set中是否存在某个值
     *
     * @param key   键
     * @param value 值
     * @return true存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        try {
            Boolean member = redisTemplate.opsForSet().isMember(key, value);
            if (member != null) {
                return member;
            }
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将一个或多个值放入set 并设置缓存失效时间
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            if (count != null) {
                return count;
            }
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据key获取set缓存长度
     *
     * @param key 键
     * @return set长度
     */
    public long sGetSetSize(String key) {
        try {
            Long size = redisTemplate.opsForSet().size(key);
            if (size != null) {
                return size;
            }
            throw new NullPointerException();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除某个key的部分值
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long remove = redisTemplate.opsForSet().remove(key, values);
            if (remove != null) {
                return remove;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ===============List===============

    /**
     * 获取list缓存值
     *
     * @param key   键
     * @param start 开始
     * @param end   结束(0到-1代表所有值)
     * @return list中指定长度的值
     */
    public List<Object> lget(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return list长度
     */
    public long lsize(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            if (size != null) {
                return size;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时,0表头,1第二个元素,依次类推;index<0时,-1表尾,-2倒数第二个元素,依次类推
     * @return 值
     */
    public Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean lset(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存 并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true成功 false失败
     */
    public boolean lset(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从尾部往list中放入数据
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean lset(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从尾部往list中放入数据 并设置过期时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return true成功 false失败
     */
    public boolean lset(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 修改key中某个位置的数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return true成功 false失败
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 从list中移除某个值，并指定移除多少个
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            if (remove != null) {
                return remove;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
