package com.gateway.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Flavor implements Serializable {
    private String id;
    private List<Link> links;
}
