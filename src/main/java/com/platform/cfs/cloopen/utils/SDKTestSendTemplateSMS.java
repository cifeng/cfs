package com.platform.cfs.cloopen.utils;

import java.util.HashMap;
import java.util.Set;

import com.platform.cfs.cloopen.CCPRestSDK;
import com.platform.cfs.cloopen.CCPRestSDK.BodyType;

public class SDKTestSendTemplateSMS {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, Object> result = null;

		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init("app.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount("8aaf0708584c07c20158569bd3c008f4", "4d071d079761453aac1c81794a59d369");// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId("8aaf0708584c07c20158569bd57b08fb");// 初始化应用ID
		result = restAPI.sendTemplateSMS("15204994156","1" ,new String[]{"123456","5"});

		System.out.println("Sms result=" + result);
		
		if("000000".equals(result.get("statusCode"))){
			//正常返回输出data包体信息（map）
			HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				System.out.println(key +" = "+object);
			}
		}else{
			//异常返回输出错误码和错误信息
			System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
		}
	}

}
