package com.platform.cfs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ConsumeRecord {

    /**
     * 主键
     */
    private String id;
    /**
     *  用户id
     */
    private String userId;

    /**
     *  消费金额
     */
    private String balance;

    /**
     *  消费次数
     */
    private Integer frequency;

    /**
     * 消费时间
     */
    private Date lastTime;

    /**
     * 折扣 暂时先不用
     */
    private String discount;

}
