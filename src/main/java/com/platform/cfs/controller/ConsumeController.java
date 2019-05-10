package com.platform.cfs.controller;


import com.platform.cfs.entity.SystemUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consume")
public class ConsumeController {

    @PostMapping("/save")
    public String save(SystemUser user) {

        return "";
    }
}
