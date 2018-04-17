package com.gatewayserver.gatewayserver.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
class Product implements Serializable {
    private String descriptions;

    @JsonProperty(value = "flavor_id")
    private String flavorId;

    @JsonProperty(value = "os_type")
    private String osType;

    @JsonProperty(value = "product_id")
    private String productId;

    private String type;
}
