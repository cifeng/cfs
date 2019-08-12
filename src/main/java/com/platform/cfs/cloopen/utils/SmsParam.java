package com.platform.cfs.cloopen.utils;

import lombok.Data;

/**
 * @ClassName SmsParam
 * @Description 短信发送参数类
 * @author cifeng
 * @date 2019-08-12 14:59
 */
@Data
public class SmsParam {

    /**
     * 用户名
     */
    private String name;

    /**
     * 充值或消费
     */
    private String  type;

    /**
     * 更新金额
     */
    private String updateMoney;

    /**
     * 账户余额
     */
    private String balance;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 模板id
     */
    private String templateId = "462075";


    public SmsParam() {
    }

    public SmsParam(String name, String type, String updateMoney, String balance, String mobile) {
        this.name = name;
        this.type = type;
        this.updateMoney = updateMoney;
        this.balance = balance;
        this.mobile = mobile;
    }

    public SmsParam(String name, String type, String updateMoney, String balance, String mobile, String templateId) {
        this.name = name;
        this.type = type;
        this.updateMoney = updateMoney;
        this.balance = balance;
        this.mobile = mobile;
        this.templateId = templateId;
    }
}
