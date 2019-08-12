package com.platform.cfs.cloopen;

import com.platform.cfs.cloopen.utils.SmsParam;
import com.platform.cfs.cloopen.utils.encoder.BASE64Encoder;
import com.platform.cfs.utils.Jackson2Helper;
import com.platform.cfs.utils.Utils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Set;

@Slf4j
@Data
@Repository
public class Sms {

	/**
	 *  短信发送成功的标识
	 */
	private static  final String SUCESS = "000000";


	@Value("${sms.serverip}")
	private String serverIp;
	@Value("${sms.port}")
	private String port;
	@Value("${sms.sid}")
	private String sid;
	@Value("${sms.token}")
	private String token;
	@Value("${sms.appid}")
	private String appId;
	@Value("${sms.enabled}")
	private Boolean enabled;

	/**
	 *
	 * @Title 
	 * @Description 发送短信通知，模板：您的账户admin，因为充值，金额变化22，账户余额82
	 * @author cifeng
	  * @param param 短信模板中使用的参数
	 * @return 结果
	 */
	public HashMap<String, Object> send(SmsParam param) {
		if(enabled){
			String[] array = new String[4];
			array[0] = Utils.isEmpty(param.getName())?"":param.getName();
			array[1] = param.getType();
			array[2] = param.getUpdateMoney()+"元";
			array[3] = param.getBalance()+"元";
			return send(param.getMobile(),param.getTemplateId(),array);
		}
		return null;
	}

	private  HashMap<String, Object> send(String to,String templateId,String[] param) {
		HashMap<String, Object> result = null;
		CCPRestSDK restAPI = new CCPRestSDK();
		restAPI.init(serverIp, port);// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
		restAPI.setAccount(sid, token);// 初始化主帐号和主帐号TOKEN
		restAPI.setAppId(appId);// 初始化应用ID
		result = restAPI.sendTemplateSMS(to,templateId ,param);
		log.info("Sms result={}" ,Jackson2Helper.toJsonString(result));
		final String statusCode = "statusCode";
		if(SUCESS.equals(result.get(statusCode))){
			//正常返回输出data包体信息（map）
			final String mydata = "data";
			HashMap<String,Object> data = (HashMap<String, Object>) result.get(mydata);
			Set<String> keySet = data.keySet();
			for(String key:keySet){
				Object object = data.get(key);
				log.info("{}={}",key , Jackson2Helper.toJsonString(object));
			}
		}else{
			//异常返回输出错误码和错误信息
			final String statusMsg = "statusMsg";
			log.info("错误码={}  错误信息= {} ",result.get(statusCode) ,result.get(statusMsg));
		}
		return result;
	}

}
