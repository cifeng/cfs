package com.platform.cfs.controller;


import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.entity.ConsumeRecord;
import com.platform.cfs.entity.SystemUser;
import com.platform.cfs.service.IConsumerRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/consume")
public class ConsumeController {

    @Autowired
    private IConsumerRecordService consumerRecordService;

    @ResponseBody
    @PostMapping("/save")
    public Response save(ConsumeRecord consumeRecord) {
        Response response = ResponseUtil.buildErrorResponse();
        int num = consumerRecordService.save(consumeRecord);
        if(num>0){
            response = ResponseUtil.buildResponse();
        }
        return response;
    }
}
