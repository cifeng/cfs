package com.platform.cfs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ConsumeRecord extends BaseEntity {
    private String id;

    private String userId;

    private String balance;

    private Integer frequency;

    private Date lastTime;

    private String discount;

    private String startTime;

    private String endTime;
}