package com.platform.cfs.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * @ClassName KeyUtil
 * @Description 主键工具类
 * @author 刘非
 * @date 2019-01-25 15:31
 */
public class KeyUtil {

	/**
	 * 生成主键
	 * @return
	 */
	public static String generatorUUID(){
		TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		return timeBasedGenerator.generate().toString().replaceAll("-","");
	}
	
}
