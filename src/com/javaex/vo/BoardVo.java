package com.javaex.vo;

public class BoardVo {

	private int no;
	private String title;
	private String content;
	private int hit;
	private String date;
	private int userNo;
	private String name;

	public BoardVo() {

	}
	
	public BoardVo(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public BoardVo(String title, String content, int no) {
		this.title = title;
		this.content = content;
		this.no = no;
	}
	
	public BoardVo(String title, String content, String name, int userNo) {
		this.title = title;
		this.content = content;
		this.name = name;
		this.userNo = userNo;
	}
	
	public BoardVo(int no, String title, String name, int hit, String date) {
		this.no = no;
		this.title = title;
		this.name = name;
		this.hit = hit;
		this.date = date;
	}
	
	public BoardVo(String name, int hit, String date, String title, String content) {
		this.name = name;
		this.hit = hit;
		this.date = date;
		this.title = title;
		this.content = content;
	}
	
	public BoardVo(int no, String title, String name, int hit, String date, int userNo) {
		this.no = no;
		this.title = title;
		this.name = name;
		this.hit = hit;
		this.date = date;
		this.userNo = userNo;
	}

	public BoardVo(String name, int hit, String date, String title, String content, int userNo, int no) {
		this.name = name;
		this.hit = hit;
		this.date = date;
		this.title = title;
		this.content = content;
		this.userNo = userNo;
		this.no = no;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", title=" + title + ", content=" + content + ", hit=" + hit + ", date=" + date
				+ ", userNo=" + userNo + ", name=" + name + "]";
	}

}
