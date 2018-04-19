package com.pkpmdesktopcloud.redis;

import lombok.Data;

import java.io.Serializable;

@Data
public class Person implements Serializable{
    private Integer id;
    private String name;
    private Integer age;
    private String address;
}
