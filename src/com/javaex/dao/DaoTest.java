package com.javaex.dao;

public class DaoTest {

	public static void main(String[] args) {
		
		UserDao userDao = new UserDao();
		 /* 
		 * mDao.insert(new UserVo("abcd12345", "abcd1234", "lee", "male"));
		 */
		
		userDao.getUser("asdasd", "123123");
		System.out.println(userDao.getUser("asdasd", "123123"));
		
	}

}
