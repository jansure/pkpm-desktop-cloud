package com.gatewayserver.gatewayserver.service;

import com.gateway.common.domain.CommonRequestBean;
import com.gateway.common.dto.product.Products;

public interface ProductPackageService {

	Products queryProductPackage(CommonRequestBean requestBean);

}
