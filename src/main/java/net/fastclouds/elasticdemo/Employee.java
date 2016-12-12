package net.fastclouds.elasticdemo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	@JsonProperty("name")
	private String name;
	@JsonProperty("salary")
	private long salary;
	@JsonProperty("emp_code")
	private int emp_code;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSalary() {
		return salary;
	}
	public void setSalary(long salary) {
		this.salary = salary;
	}
	public int getEmp_code() {
		return emp_code;
	}
	public void setEmp_code(int emp_code) {
		this.emp_code = emp_code;
	}
	
}
