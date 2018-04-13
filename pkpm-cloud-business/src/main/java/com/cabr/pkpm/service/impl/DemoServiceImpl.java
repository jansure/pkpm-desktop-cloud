package com.cabr.pkpm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cabr.pkpm.entity.Demo;
import com.cabr.pkpm.mapper.DemoMapper;
import com.cabr.pkpm.service.IDemoService;

@Service
public class DemoServiceImpl implements IDemoService {
    
	@Autowired
	private DemoMapper demoMapper;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	@Override
	public List<Demo> findAll() {
		String demoList = stringRedisTemplate.opsForValue().get("demoList");
		if(StringUtils.isNotBlank(demoList)){
			List<Demo> dList = JSON.parseArray(demoList,Demo.class);	
			return dList;
		}
		
		List<Demo> list = demoMapper.findAll();
		stringRedisTemplate.opsForValue().set("demoList", JSON.toJSONString(list));
		return demoMapper.findAll();
	}

	@Override
	public void saveDemo(Demo demo) {
		
		stringRedisTemplate.opsForValue().set(demo.getId()+"", JSON.toJSONString(demo));;
	}

	@Override
	public Demo findDemoById(Integer id) {
		String str = stringRedisTemplate.opsForValue().get(id+"");
		return JSON.parseObject(str, Demo.class);
	}

	@Override
	public void deleteDemoById(String id) {
		 stringRedisTemplate.delete(id);
		
	}

     
    
}
