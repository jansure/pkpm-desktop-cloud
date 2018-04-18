package com.gateway.common.dto.product;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Products implements Serializable {

    private List<Product> products;
}

