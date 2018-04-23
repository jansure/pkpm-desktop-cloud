package com.cabr.pkpm.entity;

import java.io.Serializable;
import java.util.List;

public class Demo implements Serializable {
   private Integer id;
   private String username;
   
   private List<Demo1> list;
   
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Demo() {
		super();
	}
	public Demo(Integer id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	public List<Demo1> getList() {
		return list;
	}
	public void setList(List<Demo1> list) {
		this.list = list;
	}
    
	
}
