package com.cabr.pkpm.service;

import java.util.List;

import com.cabr.pkpm.entity.Demo;

public interface IDemoService {

	List<Demo> findAll();

	void saveDemo(Demo demo);

	Demo findDemoById(Integer id);

	void deleteDemoById(String id);

	
	
}
