package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		HttpSession session = request.getSession();
		UserDao userDao = new UserDao();

		String action = request.getParameter("action");

		if ("joinForm".equals(action)) {
			// FORWARD - joinForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {
			// DB INSERT
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			userDao.insert(new UserVo(id, pw, name, gender));

			// FORWARD - joinOk.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
		} else if ("loginForm".equals(action)) {
			request.setAttribute("fail", "fail");
			
			// FORWARD - loginForm.jsp
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
			
		} else if ("login".equals(action)) {
			// getParameter
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			
			// DB getUser
			// userDao.getUser(id, pw); // return == no, name
//			UserVo userVo = userDao.getUser(id, pw);
			if (userDao.getUser(id, pw) != null) {
				// if (id != null || pw != null) SAVE in SESSION
				session.setAttribute("authUser", userDao.getUser(id, pw));
				
				// sendRedirect
				WebUtil.sendRedirect(response, "/mysite/main");
			} else {
				// if (id = null && pw = null) SESSION FAIL
				// sendRedirect
				WebUtil.sendRedirect(response, "/mysite/user?action=loginForm&result=fail");
			}
			
		} else if ("logout".equals(action)) {
			// delete SESSION
			session.removeAttribute("authUser");
			session.invalidate();
			
			// sendRedirect
			WebUtil.sendRedirect(response, "/mysite/main");
			
		} else if ("modifyForm".equals(action)) {
			// getParameter
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			request.setAttribute("male", "male");
			
			//DB getUser
//			UserVo userVo = userDao.getUser(authUser.getNo());
			
			session.setAttribute("modifyUser", userDao.getUser(authUser.getNo()));
			// FORWARD
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		} else if ("modify".equals(action)) {
			// getParameter
			int no = Integer.parseInt(request.getParameter("no"));
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			// DB UPDATE
			userDao.update(new UserVo(no, id, pw, name, gender));
			
			// SESSION
//			UserVo userVo = userDao.getUser(id, pw);
			session.setAttribute("authUser", userDao.getUser(id, pw));
			
			// sendRedirect
			WebUtil.sendRedirect(response, "/mysite/main");
			
		} /* else if ("idOver".equals(action)) {
			// getParameter
			String id = request.getParameter("id");
			
			// DB idOver
			userDao.idOver(id);
			
			// setAttribute
			request.setAttribute("idOver", userDao.idOver(id));
			request.setAttribute("id", id);
			
			// FORWARD
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		} */

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
