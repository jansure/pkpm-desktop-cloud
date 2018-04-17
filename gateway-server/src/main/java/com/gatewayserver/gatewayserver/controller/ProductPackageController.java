package com.gatewayserver.gatewayserver.controller;

import com.desktop.utils.page.ResultObject;
import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.product.Products;
import com.gatewayserver.gatewayserver.service.ProductPackageService;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanBuilder;
import com.gatewayserver.gatewayserver.utils.CommonRequestBeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductPackageController {

    @Resource
    CommonRequestBeanBuilder commonRequestBeanBuilder;
    @Resource
    private ProductPackageService productPackageService;

    /**
     * 查询产品套餐列表
     *
     * @param commonRequestBean
     * @return ResultObject
     */
    @PostMapping(value = "queryProductPackage")
    public ResultObject queryProductPackage(@RequestBody CommonRequestBean commonRequestBean) {

        CommonRequestBeanUtil.checkCommonRequestProjectId(commonRequestBean);
        Products products = productPackageService.queryProductPackage(commonRequestBean);
        return ResultObject.success(products, "查询产品套餐列表成功");

    }

}
