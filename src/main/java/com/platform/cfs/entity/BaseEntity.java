package com.platform.cfs.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Transient;

@Data
public class BaseEntity {

   @Transient
   protected  Integer pageIndex;

   @Transient
   protected  Integer PageSize;
}
