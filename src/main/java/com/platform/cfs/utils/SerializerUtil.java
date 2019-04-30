package com.platform.cfs.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.redis.connection.DefaultTuple;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class SerializerUtil {

	/**
	 * key
	 */
	private static RedisSerializer<String> keySerializer = new StringRedisSerializer();
	/**
	 * Object value
	 */
	private static RedisSerializer<Object> valueSerializer = new FastjsonRedisSerializer();
	/**
	 * String value
	 */
	private static RedisSerializer<String> stringValueSerializer = new StringRedisSerializer();

	/**
	 * @Description:反序列key
	 * @Date:
	 * @return String
	 * @throws 
	 */
	public static String deserializeHashKey(byte[] bytes) {
		return keySerializer.deserialize(bytes);
	}

	/**
	 * @Description:反序列hash
	 * @param entries
	 * @return Map<String,Object>
	 * @throws 
	 */
	public static Map<String, Object> deserializeHashMap(Map<byte[], byte[]> entries) {
		// connection in pipeline/multi mode
		if (entries == null) {
			return null;
		}
		Map<String, Object> map = new LinkedHashMap<String, Object>(entries.size());
		for (Map.Entry<byte[], byte[]> entry : entries.entrySet()) {
			map.put(  deserializeHashKey(entry.getKey()),   deserializeValue(entry.getValue()));
		}
		return map;
	}
	
	/**
	 * @Description:反序列值
	 * @param bs
	 * @return Object
	 * @throws 
	 */
	public static Object deserializeValue(byte[] bs) {
		return valueSerializer.deserialize(bs);
	}
	/**
	 * @Description:反序列值
	 * @param bs
	 * @return Object
	 * @throws 
	 */
	public static String deserializeStringValue(byte[] bs) {
		return stringValueSerializer.deserialize(bs);
	}
	
	/**
	 * @Description:
	 * @param key
	 * @return byte[]
	 * @throws 
	 */
	public static byte[] rawKey(String key) {
		return keySerializer.serialize(key);
	}

	/**
	 * @Description:序列化值
	 * @param o
	 * @return byte[]
	 * @throws 
	 */
	public static byte[] rawValue(Object o) {
		return valueSerializer.serialize(o);
	}
	
	public static byte[][] rawValues(Object... values) {
		return rawValues(null,values);
	}
	/**
	 * 设置没有 @Type的数据
	 */
	
	public static byte[][] rawValues( SerializerFeature[] features,Object... values) {
		final byte[][] rawValues = new byte[values.length][];
		int i = 0;
		for (Object value : values) {
			if(null==features){
				rawValues[i++] = rawValue(value);  
			}else{
			    try {
			    	rawValues[i++] = JSON.toJSONBytes(value);
		        } catch (Exception ex) {
		            throw new SerializationException("Could not write JSON: "
		                    + ex.getMessage(), ex);
		        }
				
			}
		}
		return rawValues;
	}
	
	
	/**
	 * @Description:序列化值
	 * @param o
	 * @return byte[]
	 * @throws 
	 */
	public static byte[] rawStringValue(String o) {
		return stringValueSerializer.serialize(o);
	}

	public static byte[][] rawKeys(String... keys) {
		final byte[][] rawKeys = new byte[keys.length][];
		int i = 0;
		for (String key : keys) {
			rawKeys[i++] = rawKey(key);
		}
		return rawKeys;
	}
	/**
	 * @Description:
	 * @return Object
	 * @throws
	 */
	public static Object deserializeHashValue(byte[] bs) {
		return deserializeValue(bs);
	}

	/**
	 * @Description:
	 * @param hashKey
	 * @return byte[]
	 * @throws
	 */
	public static byte[] rawHashKey(String hashKey) {
		return rawKey(hashKey);
	}

	public static <T> Set<Tuple> rawTupleValues(Set<TypedTuple<T>> values) {
		if (values == null) {
			return null;
		}
		Set<Tuple> rawTuples = new LinkedHashSet<Tuple>(values.size());
		for (TypedTuple<T> value : values) {
			byte[] rawValue;
			if (valueSerializer == null && value.getValue() instanceof byte[]) {
				rawValue = (byte[]) value.getValue();
			} else {
				rawValue = valueSerializer.serialize(value.getValue());
			}
			rawTuples.add(new DefaultTuple(rawValue, value.getScore()));
		}
		return rawTuples;
	}
}
