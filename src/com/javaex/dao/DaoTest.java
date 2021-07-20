package com.javaex.dao;

public class DaoTest {

	public static void main(String[] args) {

		/*
		 * UserDao userDao = new UserDao();
		 * 
		 * mDao.insert(new UserVo("abcd12345", "abcd1234", "lee", "male"));
		 * 
		 * userDao.getUser("asdasd", "123123");
		 * System.out.println(userDao.getUser("asdasd", "123123"));
		 * 
		 * userDao.update(new UserVo(5, "6666", "6666", "6666"));
		 */
		
		BoardDao boardDao = new BoardDao();
		
//		boardDao.insert(new BoardVo("asd", "asd", "asd", 1));
		
		System.out.println(boardDao.getList());
		
		System.out.println(boardDao.readList(2));
		
		System.out.println(boardDao.countHit(2));
	}

}
