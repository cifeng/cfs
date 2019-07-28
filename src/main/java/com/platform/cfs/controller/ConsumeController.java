package com.platform.cfs.controller;


import com.platform.cfs.common.exception.CommonException;
import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.config.database.PageVO;
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


    @RequestMapping("/input")
    public String input(){
        return "member/consumeRecord";
    }


    @RequestMapping("/record/input")
    public String queryInput(){
        return "member/record";
    }

    @ResponseBody
    @PostMapping("/save")
    public Response save(ConsumeRecord consumeRecord) {
        Response response = ResponseUtil.buildErrorResponse();
        try {
            int num = consumerRecordService.save(consumeRecord);
            if(num>0){
                response = ResponseUtil.buildResponse();
            }
        }catch (CommonException e) {
           response = ResponseUtil.buildErrorResponse(e.getCode(),e.getErrorMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @ResponseBody
    @PostMapping("/edit")
    public Response edit(ConsumeRecord consumeRecord) {
        Response response = ResponseUtil.buildErrorResponse();
        try {
            int num = consumerRecordService.edit(consumeRecord);
            if(num>0){
                response = ResponseUtil.buildResponse();
            }
        }catch (CommonException e) {
            response = ResponseUtil.buildErrorResponse(e.getCode(),e.getErrorMessage());
        }catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    @ResponseBody
    @RequestMapping("/list")
    public Response queryByList(ConsumeRecord consumeRecord) {
        PageVO pageInfo = consumerRecordService.queryByList(consumeRecord);
        return ResponseUtil.buildResponse(pageInfo);
    }
    @ResponseBody
    @RequestMapping("/querybyid")
    public Response queryById(String id) {
        ConsumeRecord record =  consumerRecordService.queryById(id);
        return ResponseUtil.buildResponse(record);
    }


    @ResponseBody
    @RequestMapping("/del")
    public Response delete(String id) {
        Response response = ResponseUtil.buildErrorResponse();
        int num = consumerRecordService.delete(id);
        if(num>0){
            response = ResponseUtil.buildResponse();
        }
        return response;
    }

}
