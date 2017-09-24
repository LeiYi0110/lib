package com.bjxc.json;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class TestBean {

	private String name;
	private Date day;
	
	public TestBean(){
		
	}
	
	public TestBean(String name){
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getDay() {
		return day;
	}
	
	@DateTimeFormat(pattern="yyyy/MM/dd")
	public void setDay(Date day) {
		this.day = day;
	}
	
	
}
