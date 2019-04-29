package com.platform.cfs.common.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.platform.cfs.common.exception.GenericBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

@Slf4j
public class Jackson2Helper {
	private static final ObjectMapper mapper = new ObjectMapper();

	public static final String toJsonString(Object obj) {
		if (null == obj) {
			return null;
		}
		try {
			// Writer strWriter = new StringWriter();
			// mapper.writeValue(strWriter, obj);
			// return strWriter.toString();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	public static final <T> T parsingObject(String jsonString, Class<T> cls) throws GenericBusinessException
	{
		if (StringUtils.isBlank(jsonString) || null == cls) {
			return null;
		}

		try {
			return mapper.readValue(jsonString, cls);
		} catch (JsonParseException e) {
			throw new GenericBusinessException(e);
		} catch (JsonMappingException e) {
			throw new GenericBusinessException(e);
		} catch (IOException e) {
			throw new GenericBusinessException(e);
		}
	}
	
	public static final JsonNode parsingObject(String jsonString) throws GenericBusinessException {
		if (StringUtils.isBlank(jsonString)) {
			return null;
		}
		try {
			return mapper.readTree(jsonString);
		} catch (JsonParseException e) {
			throw new GenericBusinessException(e);
		} catch (JsonMappingException e) {
			throw new GenericBusinessException(e);
		} catch (IOException e) {
			throw new GenericBusinessException(e);
		}
	}

	/**
	 * 支持泛型
	 *
	 */
	public static final <T> T parsonObject(String jsonString, TypeReference<T> valueTypeRef) {
		try {

			// 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(jsonString, valueTypeRef);
		} catch (Exception e) {
			log.error("parsonObject error ", e);
		}
		return null;
	}

    public static JsonNode readJson(String json){
        try {
            return mapper.readTree(json);
        } catch (Exception e) {
            log.error("readJson error ", e);
        }
        return null;
    }

    public static <T extends Object> List<T> jsonToList(String json, Class<T> bean){
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, bean);
        try {
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            log.error("jsonToList error ", e);
        }
        return null;
    }
}
