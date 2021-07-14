package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 한글 깨짐 해결
		request.setCharacterEncoding("UTF-8");
		
		// DB - Dao instance
		GuestbookDao guestbookDao = new GuestbookDao();
		
		// 주소 파라미터 값
		String action = request.getParameter("action");
		
		if ("addList".equals(action)) {
			// DB SELECT
			List<GuestbookVo> gList = guestbookDao.getList();
			
			// setAttribute
			request.setAttribute("gList", gList);
			
			// FORWARD
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		} else if ("add".equals(action)) {
			// DB INSERT
			String name = request.getParameter("name");
			String pw = request.getParameter("pw");
			String content = request.getParameter("content");
			
			guestbookDao.insert(new GuestbookVo(name, pw, content));
			
			// sendRedirect
			WebUtil.sendRedirect(response, "/mysite/guest?action=addList");
		} else if ("deleteForm".equals(action)) {
			// FORWARD
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		} else if ("delete".equals(action)) {
			// DB DELETE
			int no = Integer.parseInt(request.getParameter("no"));
			String pw = request.getParameter("pw");
			
			guestbookDao.delete(new GuestbookVo(no, pw));
			
			// sendRedirect
			WebUtil.sendRedirect(response, "/mysite/guest?action=addList");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
