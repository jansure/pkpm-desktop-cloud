package com.gatewayserver.gatewayserver.service;

import com.gatewayserver.gatewayserver.domain.CommonRequestBean;
import com.gatewayserver.gatewayserver.dto.product.Products;

public interface ProductPackageService {

	Products queryProductPackage(CommonRequestBean requestBean);

}
