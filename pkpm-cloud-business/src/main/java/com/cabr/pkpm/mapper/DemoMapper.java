package com.cabr.pkpm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cabr.pkpm.entity.Demo;
@Mapper
public interface DemoMapper {
     
	public List<Demo> findAll();
} 
