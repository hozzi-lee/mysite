package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {
		
		MysiteDao mDao = new MysiteDao();
		
		mDao.insert(new UserVo("abcd12345", "abcd1234", "lee", "male"));

	}

}
