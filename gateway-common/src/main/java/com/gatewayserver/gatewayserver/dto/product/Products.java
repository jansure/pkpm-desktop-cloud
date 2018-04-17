package com.gatewayserver.gatewayserver.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Products implements Serializable {

    private List<Product> products;
}

