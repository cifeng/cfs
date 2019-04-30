package com.platform.cfs.controller;

import com.platform.cfs.common.response.Response;
import com.platform.cfs.common.response.ResponseUtil;
import com.platform.cfs.entity.MenuEntity;
import com.platform.cfs.service.IMenuService;
import com.platform.cfs.utils.ReturnControllerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MenuController
 * @Description 菜单管理控制层-Controller
 * @author 刘非
 * @date 2017-09-25 12:00
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {
    @Autowired
    public IMenuService menuService;

    @RequestMapping("/querypage")
    public String qryInput() {
        return "system/systemMenu";
    }

    @RequestMapping("/selectimg")
    public String qrySelectImg() {
        return "system/selectImg";
    }

    @ResponseBody
    @RequestMapping(value = {"/querylist"})
    public Response getList(MenuEntity entity) {
        List<MenuEntity> list= menuService.queryByList(entity);
        Integer count =  menuService.queryByCount(entity);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("result",list);
        map.put("count",count);
        return ResponseUtil.buildResponse(map);
    }


    @RequestMapping("/menulist")
    @ResponseBody
    public Response menulist(){
        List<MenuEntity> data = menuService.menuList();
        return ResponseUtil.buildResponse(data);
    }


    @ResponseBody
    @RequestMapping(value = {"/save"})
    public Response saveTestQuestion(MenuEntity entity) {
        Integer num = menuService.save(entity);
        return ReturnControllerUtil.getCud(num,ReturnControllerUtil.CREATE);
    }

    @ResponseBody
    @RequestMapping("/querybyid")
    public Response queryById(Integer id){
        MenuEntity authorityEntity= menuService.queryById(id);
        return ResponseUtil.buildResponse(authorityEntity);
    }


    @ResponseBody
    @RequestMapping(value = {"/del"})
    public Response del(String ids) {
        int num= menuService.delete(ids);
        return ReturnControllerUtil.getCud(num,ReturnControllerUtil.DELETE);
    }
    @ResponseBody
    @RequestMapping("/edit")
    public Response edit(MenuEntity entity){
        int num= menuService.edit(entity);
        return ReturnControllerUtil.getCud(num,ReturnControllerUtil.UPDATE);
    }




}
