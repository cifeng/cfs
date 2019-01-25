package com.platform.cfs.utils;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

/**
 * <B>系统名称：</B>通用平台<BR>
 * <B>模块名称：</B>通用平台-公共服务<BR>
 * <B>中文类名：</B>KeyUtils<BR>
 * <B>概要说明：</B>主键生成策略-工具类<BR>
 * @author baihezhuo
 * @since 2017年2月21日 下午1:55:42
 */
public class KeyUtil {

	/**
	 * <B>方法名称：</B>generatorUUID<BR>
	 * <B>概要说明：</B>主键生成策略<BR>
	 * @author baihezhuo
	 * @since 2017年2月21日 下午2:00:06
	 * @return UUID String
	 */
	public static String generatorUUID(){
		TimeBasedGenerator timeBasedGenerator = Generators.timeBasedGenerator(EthernetAddress.fromInterface());
		return timeBasedGenerator.generate().toString();
	}
	
}
