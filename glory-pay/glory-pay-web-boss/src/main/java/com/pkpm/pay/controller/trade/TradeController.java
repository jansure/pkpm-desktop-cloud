/*
 * Copyright 2015-2102 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pkpm.pay.controller.trade;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pkpm.pay.common.core.enums.PayTypeEnum;
import com.pkpm.pay.common.core.enums.PayWayEnum;
import com.pkpm.pay.common.core.page.PageBean;
import com.pkpm.pay.common.core.page.PageParam;
import com.pkpm.pay.trade.enums.TradeStatusEnum;
import com.pkpm.pay.trade.enums.TrxTypeEnum;
import com.pkpm.pay.trade.service.RpTradePaymentQueryService;
import com.pkpm.pay.trade.vo.PaymentOrderQueryParam;
import com.pkpm.pay.user.enums.FundInfoTypeEnum;

/**
 * 交易管理
 * glory-cloud
 * @author：jansure
 */
@Controller
@RequestMapping("/trade")
public class TradeController {

    @Autowired
    private RpTradePaymentQueryService rpTradePaymentQueryService;
    @RequiresPermissions("trade:order:view")
    @RequestMapping(value = "/listPaymentOrder", method ={RequestMethod.POST,RequestMethod.GET})
    public String listPaymentOrder(HttpServletRequest request,PaymentOrderQueryParam paymentOrderQueryParam,PageParam pageParam, Model model) {
        PageBean pageBean = rpTradePaymentQueryService.listPaymentOrderPage(pageParam, paymentOrderQueryParam);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("paymentOrderQueryParam", paymentOrderQueryParam);//查询条件

        model.addAttribute("statusEnums", TradeStatusEnum.toMap());//状态
        model.addAttribute("payWayNameEnums", PayWayEnum.toMap());//支付方式
        model.addAttribute("payTypeNameEnums", PayTypeEnum.toMap());//支付类型
        model.addAttribute("fundIntoTypeEnums", FundInfoTypeEnum.toMap());//支付类型

        return "trade/listPaymentOrder";
    }

    @RequiresPermissions("trade:record:view")
    @RequestMapping(value = "/listPaymentRecord", method ={RequestMethod.POST,RequestMethod.GET})
    public String listPaymentRecord(HttpServletRequest request,PaymentOrderQueryParam paymentOrderQueryParam,PageParam pageParam, Model model) {
        PageBean pageBean = rpTradePaymentQueryService.listPaymentRecordPage(pageParam, paymentOrderQueryParam);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("paymentOrderQueryParam", paymentOrderQueryParam);

        model.addAttribute("statusEnums", TradeStatusEnum.toMap());//状态
        model.addAttribute("payWayNameEnums", PayWayEnum.toMap());//支付方式
        model.addAttribute("payTypeNameEnums", PayTypeEnum.toMap());//支付类型
        model.addAttribute("fundIntoTypeEnums", FundInfoTypeEnum.toMap());//支付类型
        model.addAttribute("trxTypeEnums", TrxTypeEnum.toMap());//支付类型
        return "trade/listPaymentRecord";
    }
}
