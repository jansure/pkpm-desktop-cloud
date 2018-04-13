package com.cabr.pkpm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cabr.pkpm.entity.Demo;
import com.cabr.pkpm.entity.Demo1;
import com.cabr.pkpm.service.IDemoService;

@RestController
@RequestMapping("/demo")
public class DemoController {
	
	 @Autowired
	 public IDemoService demoService;
    
	 @RequestMapping("/findAll")
	 public List<Demo> findAll(){
		return demoService.findAll();
		 
	 }
	 
	 @RequestMapping("/save")
	 public String saveDemo(){
		 Demo demo = new Demo(636,"hehe");
		 demoService.saveDemo(demo);
		 
		 return "ok";
		 
	 } 
	 
	 @RequestMapping(value="/findDemoById/{id}",method=RequestMethod.GET)
	 public Demo findDemoById(@PathVariable String id){
		 return demoService.findDemoById(Integer.parseInt(id));
	 } 
	 
	// @RequestMapping(value="/deleteDemoById/{id}",method = RequestMethod.DELETE)  前端ajax请求，data:{_method："delete"}
	@RequestMapping(value="/deleteDemoById/{id}")
    public String deleteDemoById(@PathVariable String id) {
	    demoService.deleteDemoById(id);
        return "删除成功";
    }
	@RequestMapping(value="/a")
	public String DemoAndDemo1(Demo d,Demo1 d1) {
		
	System.out.println(d.getList().get(0).getName()+d.getList().get(0).getAge());
		
		return "删除成功";
	}
	
	
}
