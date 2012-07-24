package com.lvls.cmis.pojo;

public class MyBean {

	private Integer id;
	private String name;
	private String type;
	private String dynamicContent;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getBigName(String name) {
		return "BIG" + name;
	}
	public final String getType() {
		return type;
	}
	public final void setType(String type) {
		this.type = type;
	}
	public final String getDynamicContent() {
		return dynamicContent;
	}
	public final void setDynamicContent(String dynamicContent) {
		this.dynamicContent = dynamicContent;
	}
}
