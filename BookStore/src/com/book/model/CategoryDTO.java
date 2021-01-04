package com.book.model;

public class CategoryDTO {
	private String cate_name;
	private int cate_code;
	private int cate_code_ref;
	
	public String getCate_name() {
		return cate_name;
	}
	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}
	public int getCate_code() {
		return cate_code;
	}
	public void setCate_code(int cate_code) {
		this.cate_code = cate_code;
	}
	public int getCate_code_ref() {
		return cate_code_ref;
	}
	public void setCate_code_ref(int cate_code_ref) {
		this.cate_code_ref = cate_code_ref;
	}
}
