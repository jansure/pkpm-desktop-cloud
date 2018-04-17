package com.gatewayserver.gatewayserver.dto.record;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Records implements Serializable {

    private List<Record> records;

}


