package com.idserver.controller;

import com.desktop.utils.page.ResultObject;
import com.google.common.base.Preconditions;
import com.idserver.service.IdentifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuhe
 * @description
 * @date 2018/5/7
 */
@RequestMapping("/test")
@Api("API")
@RestController
public class IdentifyController {

    @Autowired
    private IdentifyService identifyService;

    @GetMapping("/get")
    public ResultObject getAvailableComputerName(@RequestParam(value = "productName",required = true) String productName, @RequestParam(value = "adId",required = true) Integer adId){
        Preconditions.checkArgument(productName.length()>0&&productName.length()<=13,"产品名长度必须大于0不大于13");
        //Todo 校验adId是否正确
        Preconditions.checkArgument(adId!=0);
        String newName= identifyService.getAvailableIdentity(productName, adId);
        return ResultObject.success(null, newName);
    }

    @ApiOperation(value = "INDEX",httpMethod = "POST",notes = "Note")
    @GetMapping("/index")
    public String index(){
        return "Hello";
    }
}
