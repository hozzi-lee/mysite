package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		BoardDao boardDao = new BoardDao();
		
		HttpSession session = request.getSession();

		String action = request.getParameter("action");

		if ("list".equals(action)) {
			String keyword = request.getParameter("keyword");
			
			request.setAttribute("gList", boardDao.getList(keyword));

			request.setAttribute("listCount", boardDao.listCount(keyword));

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("writeForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

		} else if ("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = ((UserVo)session.getAttribute("authUser")).getNo();
			
			boardDao.insert(new BoardVo(title, content, userNo));

			WebUtil.sendRedirect(response, "/mysite/board?action=list");

		} else if ("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			
			boardDao.countHit(no);
			// feedback fix --> read.jsp
			request.setAttribute("boardVo", boardDao.boardVo(no));

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));

			// feedback fix
			request.setAttribute("modifyInfo", boardDao.boardVo(no));

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("modify".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));

			boardDao.update(new BoardVo(no, title, content));

			WebUtil.sendRedirect(response, "/mysite/board?action=list");

		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));

			boardDao.delete(no);

			WebUtil.sendRedirect(response, "/mysite/board?action=list");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
