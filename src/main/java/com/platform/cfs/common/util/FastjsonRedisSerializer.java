package com.platform.cfs.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

public class FastjsonRedisSerializer implements RedisSerializer<Object> {

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    private static SerializerFeature[] features = {SerializerFeature.WriteClassName};
    
    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return JSON.parse(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Could not read JSON: "
                    + ex.getMessage(), ex);
        }
    }

    @Override
    public byte[] serialize(Object t) throws SerializationException {
        if (t == null) {
            return new byte[0];
        }
        try {
//        	return JSON.toJSONBytes(t);
            return JSON.toJSONBytes(t, features);
        } catch (Exception ex) {
            throw new SerializationException("Could not write JSON: "
                    + ex.getMessage(), ex);
        }
    }

}
