package com.book.model;

public class CommuDTO {
	/*c_num number(30) primary key,
	c_group number(30),
	c_step number(30),
	c_indent number(30),
	c_title varchar2(30) not null,
	c_id_nickname varchar2(100) not null,
	c_pwd varchar2(100) not null,
	c_content clob,
	c_like number(30)*/
	private int c_num;
	private int c_group;
	private int c_step;
	private int c_indent;
	private String c_title;
	private String c_id_nickname;
	private String c_pwd;
	private String c_content;
	private int c_like;
	private int c_hit;
	private String c_date;
	public String getC_date() {
		return c_date;
	}
	public void setC_date(String c_date) {
		this.c_date = c_date;
	}
	public int getC_num() {
		return c_num;
	}
	public void setC_num(int c_num) {
		this.c_num = c_num;
	}
	public int getC_group() {
		return c_group;
	}
	public void setC_group(int c_group) {
		this.c_group = c_group;
	}
	public int getC_step() {
		return c_step;
	}
	public void setC_step(int c_step) {
		this.c_step = c_step;
	}
	public int getC_indent() {
		return c_indent;
	}
	public void setC_indent(int c_indent) {
		this.c_indent = c_indent;
	}
	public String getC_title() {
		return c_title;
	}
	public void setC_title(String c_title) {
		this.c_title = c_title;
	}
	public String getC_id_nickname() {
		return c_id_nickname;
	}
	public void setC_id_nickname(String c_id_nickname) {
		this.c_id_nickname = c_id_nickname;
	}
	public String getC_pwd() {
		return c_pwd;
	}
	public void setC_pwd(String c_pwd) {
		this.c_pwd = c_pwd;
	}
	public String getC_content() {
		return c_content;
	}
	public void setC_content(String c_content) {
		this.c_content = c_content;
	}
	public int getC_like() {
		return c_like;
	}
	public void setC_like(int c_like) {
		this.c_like = c_like;
	}
	public int getC_hit() {
		return c_hit;
	}
	public void setC_hit(int c_hit) {
		this.c_hit = c_hit;
	}
	
}
