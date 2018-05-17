package com.pkpmdesktopcloud.desktopcloudbusiness.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuhe
 * @description
 * @date 2018/5/17
 */
@Api("测试")
@RestController
@RequestMapping("test")
public class TestController {

    @ApiOperation("IndexHHH")
    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        return "Hello";
    }
}
