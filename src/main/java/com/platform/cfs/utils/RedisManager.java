package com.platform.cfs.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.util.*;


@Repository
public class RedisManager {
    static{
/*        ParserConfig.getGlobalInstance().addAccept("com.baidu.platform.idc.entity.");
        ParserConfig.getGlobalInstance().addAccept("com.baidu.platform.idc.web.bean.");
        ParserConfig.getGlobalInstance().addAccept("com.baidu.platform.idc.web.vo.");
        ParserConfig.getGlobalInstance().addAccept("com.baidu.platform.idc.web.dto.");*/
//        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    //@Autowired
    private JedisPool jedisPool;

    public Long del(String key) {
        Jedis jedis = jedisPool.getResource();

        try {
            return jedis.del(key);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public boolean containsKey(final String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.exists(key);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }

    /**
     * 获取Map值
     * @param key
     * @param filed
     * @return
     */
    public String hget(String key,String filed) {
        Jedis jedis = jedisPool.getResource();
        String str = "";
        try {
            str = jedis.hget(key,filed);
            return str;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return str;
    }
    public boolean hdel(String key,String filed) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hdel(key,filed);
            return true;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }


    /**
     * 获取Map值
     * @param key
     * @return
     */
    public Map<String, String> hGetAll(String key) {
        Jedis jedis = jedisPool.getResource();
        Map<String, String> map = new HashMap<String,String>();
        try {
            map = jedis.hgetAll(key);
            return map;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return map;
    }
    /**
     * 获取Map值
     * @param key
     * @return
     */
    public  Map<String, Object> getHash(String key) {
        Jedis jedis = jedisPool.getResource();
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            Map<byte[], byte[]> map = jedis.hgetAll(rawKey);
            return SerializerUtil.deserializeHashMap(map);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return res;
    }




    public boolean hexists(String key, String value){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hexists(key,value);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }

    /**
     * 设置Map值
     * @param key
     * @param value
     * @return
     */
    public boolean hset(String key,String filed, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset(key,filed,value);
            return true;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }
    public boolean hincrBy(String key,String filed, Integer value) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hincrBy(key,filed,value);
            return true;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }
    public boolean incr(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.incr(key);
            return true;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }

    public Long incrBy(String key,Integer value) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incrBy(key,value);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 设置Map值
     * @param key
     * @param value
     * @return
     */
    public boolean hsetObject(String key,String filed, Object value) {
        Jedis jedis = jedisPool.getResource();
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawFiled = SerializerUtil.rawKey(filed);
        final byte[] rawValue = SerializerUtil.rawValue(value);

        try {
            jedis.hset(rawKey,rawFiled,rawValue);

            return true;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }

    public Object hgetObject(String key,String filed) {
        Object obj=null;
        Jedis jedis = jedisPool.getResource();
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawFiled = SerializerUtil.rawKey(filed);
        try {
           obj=SerializerUtil.deserializeValue(jedis.hget(rawKey,rawFiled));
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return obj;
    }



    /**
     * 删除map指定值
     * @param key
     * @param filed
     * @return
     */
    public boolean hdelObject(String key,String filed) {
        Jedis jedis = jedisPool.getResource();
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawFiled = SerializerUtil.rawKey(filed);
        try {
            jedis.hdel(rawKey,rawFiled);
            return true;
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }



    /**
     * 设置HASH值(同时设置过期时间)
     * @param key
     * @param value
     * @return
     */
    public long hset(String key,String filed, String value,long unixTime) {
        if (StringUtils.isBlank(key)) {
            return 0;
        }
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hset(key,filed,value);
            return jedis.expireAt(key, unixTime);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0;
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.get(key);
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return "";
    }

    /**
     * 判断是否存在该key
     * @param key
     * @return true|false
     */
    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (JedisException e) {
           e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }


    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            return true;
        } catch (JedisException e) {
         e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }
    /**
     * 设置值
     * @param key
     * @param value
     * @return
     */
    public boolean setObject(String key, Object value) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawValue = SerializerUtil.rawValue(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(rawKey, rawValue);
            return true;
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }
    /**
     * 获取值
     * @param key
     * @return
     */
    public Object getObject(String key) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = jedisPool.getResource();
        try {
            return SerializerUtil.deserializeValue(jedis.get(rawKey));
        } catch (JedisException e) {

        } finally {
            jedisPool.returnResource(jedis);
        }
        return "";
    }

    /**
     * 移除有序集合中给定的排名区间的所有成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Long zremrangeByRank(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByRank(key, start, end);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }


    public Long zremrangeByScore(String key, Double start, Double end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zremrangeByScore(key, start, end);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 向有序集合添加一个成员
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zadd(String key, double score, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, score, member);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }
    /**
     * 向有序集合添加一个成员
     * @param key
     * @param score
     * @param member
     * @return
     */
    public Long zaddObject(String key, double score, Object member) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawFiled = SerializerUtil.rawValue(member);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(rawKey, score, rawFiled);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     * @param key
     * @param scoreMembers
     * @return
     */
    public Long zadd(String key, Map<String, Double> scoreMembers) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zadd(key, scoreMembers);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public Set<String> zrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrange(key, start, end);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }
    /**
     * 通过索引区间返回有序集合成指定区间内的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public LinkedHashSet<Object> zrangeObject(String key, long start, long end) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<byte[]> sets= jedis.zrange(rawKey, start, end);
            return  deserializeValues(sets, LinkedHashSet.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }
    public LinkedHashSet<Object> zrangeByScore(String key, double min, double max) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = jedisPool.getResource();
        try {
            Set<byte[]> sets= jedis.zrangeByScore(rawKey,min,max);
            return deserializeValues(sets, LinkedHashSet.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }
    public ArrayList<Object> zrangeArrByScore(String key, double min, double max) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = jedisPool.getResource();
        try {
            Set<byte[]> sets= jedis.zrangeByScore(rawKey,min,max);
            return deserializeValues(sets, ArrayList.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }
    /**
     * 通过索引区间返回有序集合成指定区间内的成员
     * @param key
     * @param start
     * @param end
     * @return
     */
    public ArrayList<Object> zrangeArrObject(String key, long start, long end) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<byte[]> sets= jedis.zrange(rawKey, start, end);
            return  deserializeValues(sets, ArrayList.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }

    public Set<String> zrevrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrange(key, start, end);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }





    /**
     *
     * @Title zrevrangeObj
     * @Description 从有序集合中按分数递减的方式数据
     * @author 刘非
     * @param key 对应地方key
     * @param start 开始位置
     * @param end   结束位置
     * @return java.util.LinkedHashSet<java.lang.Object>
     */
    public LinkedHashSet<Object> zrevrangeObj(String key, long start, long end) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<byte[]> sets= jedis.zrevrange(rawKey,start,end);
            return deserializeValues(sets, LinkedHashSet.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }
    /**
     *
     * @Title zrevrangeArrObj
     * @Description 从有序集合中按分数递减的方式数据
     * @param key 对应地方key
     * @param start 开始位置
     * @param end   结束位置
     * @return java.util.LinkedHashSet<java.lang.Object>
     */
    public ArrayList<Object> zrevrangeArrObj(String key, long start, long end) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Set<byte[]> sets= jedis.zrevrange(rawKey,start,end);
            return deserializeValues(sets, ArrayList.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }

    /**
     *
     * @param key
     * @param count
     * @return
     */
    public ArrayList<Object> srandmember (String key,int count) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<byte[]> lists= jedis.srandmember(rawKey,count);
            return  deserializeValues(lists, ArrayList.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }

    /**
     * 求指定集合的差集
     * @param dstkey
     * @param keys
     * @return
     */
    public Long sdiffstore  (String dstkey,String... keys) {
        final byte[] rawKey = SerializerUtil.rawKey(dstkey);
        final byte[][] rawKeys = SerializerUtil.rawValues(keys);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long num= jedis.sdiffstore(rawKey,rawKeys);
            return  num;
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }




    public Long lpush(String key, String string) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, string);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }
    public Long lPushObject(String key, Object value) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawValue = SerializerUtil.rawValue(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(rawKey, rawValue);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    public Long rpush(String key, String string) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(key, string);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }
    public Long rPushObject(String key, Object value) {
        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawValue = SerializerUtil.rawValue(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.rpush(rawKey, rawValue);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lrange(key, start, end);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return new ArrayList<>();
    }
    public List<Object> lRangeObject(String key, long start, long end) {
        Jedis jedis = null;
        try {
            final byte[] rawKey = SerializerUtil.rawKey(key);
            jedis = jedisPool.getResource();
            List<byte[]>  list= jedis.lrange(rawKey, start, end);
            return deserializeValues(list, List.class);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return new ArrayList<>();
    }
    public boolean lset(String key, long index, Object value) {
        Jedis jedis = null;
        try {
            final byte[] rawKey = SerializerUtil.rawKey(key);
            final byte[] rawValue = SerializerUtil.rawValue(value);
            jedis = jedisPool.getResource();
            String  res = jedis.lset(rawKey,index,rawValue);
            if(StringUtils.isNotBlank(res)&&"OK".equals(res)){
                return true;
            }
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }

    public Long llen(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.llen(key);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    /**
     * 设置失效时间
     * @param key
     * @param second  失效时间 单位秒
     * @return
     */
    public Long expire(String key, Integer second) {
        Jedis jedis = jedisPool.getResource();
        Long status =0L;
        try {
            status = jedis.expire(key,second);
            return status;
        } catch (JedisException e) {
        } finally {
            jedisPool.returnResource(jedis);
        }
        return status;
    }

    private <T extends Collection<?>> T deserializeValues(Collection<byte[]> rawValues, Class<T> type) {
        Collection<Object> values = List.class.isAssignableFrom(type) ? new ArrayList<Object>(rawValues.size()) : new LinkedHashSet<Object>(rawValues.size());
        for (byte[] bs : rawValues) {
            values.add(SerializerUtil.deserializeValue(bs));
        }
        return (T) values;
    }


    public boolean setnx(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        try {
            if (jedis.setnx(key, value) == 1) {
                return true;
            }
        } catch (JedisException e) {
        } finally {
            jedisPool.returnResource(jedis);
        }
        return false;
    }

    public String getSet(String key, String value) {
        String oldvalue="";
        Jedis jedis = jedisPool.getResource();
        try {
           oldvalue=jedis.getSet(key,value);
        } catch (JedisException e) {
        } finally {
            jedisPool.returnResource(jedis);
        }
        return  oldvalue;
    }


    public String rpop(String key) {
        Jedis jedis = jedisPool.getResource();
        try {
         return    jedis.rpop(key);
        } catch (JedisException e) {
        } finally {
            jedisPool.returnResource(jedis);
        }
        return  null;
    }

    public Long zcard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zcard(key);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return 0L;
    }

    public Set<String> keys(String pattern) {
        Jedis jedis = null;
        Set<String> set=null;
        try {
            jedis = jedisPool.getResource();
            set=jedis.keys(pattern);
        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return set;
    }


    /**
     * Redis Zrevrank 命令返回有序集中成员的排名。其中有序集成员按分数值递减(从大到小)排序。
     * 排名以 0 为底，也就是说， 分数值最大的成员排名为 0 。
     * 使用 ZRANK 命令可以获得成员按分数值递增(从小到大)排列的排名。
     * @param key
     * @param value
     * @return
     */
    public Long zrevrankObj(String key, Object value) {

        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawValue = SerializerUtil.rawValue(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zrevrank(rawKey, rawValue);

        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }

    /**
     * Redis Zscore 命令返回有序集中，成员的分数值。 如果成员元素不是有序集 key 的成员，或 key 不存在，返回 nil 。
     * @param key
     * @param value
     * @return
     */
    public Double zscoreObj(String key, Object value) {

        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawValue = SerializerUtil.rawValue(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zscore(rawKey, rawValue);

        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }


    /**
     * Redis Zincrby 命令对有序集合中指定成员的分数加上增量 increment
     * @param key
     * @param value
     * @return
     */
    public Double zincrbyObj(String key, Double score, Object value) {

        final byte[] rawKey = SerializerUtil.rawKey(key);
        final byte[] rawValue = SerializerUtil.rawValue(value);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.zincrby(rawKey, score, rawValue);

        } catch (JedisException e) {
            e.printStackTrace();
        } finally {
            jedisPool.returnResource(jedis);
        }
        return null;
    }

    public Long pttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long status =0L;
        try {
            status = jedis.pttl(key);
            return status;
        } catch (JedisException e) {
        } finally {
            jedisPool.returnResource(jedis);
        }
        return status;
    }


}
