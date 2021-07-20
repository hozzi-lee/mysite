package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

//		HttpSession session = request.getSession();

		BoardDao boardDao = new BoardDao();

		String action = request.getParameter("action");

		if ("list".equals(action)) {
			request.setAttribute("gList", boardDao.getList());

			request.setAttribute("listCount", boardDao.listCount());

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		} else if ("writeForm".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");

		} else if ("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			String name = request.getParameter("name");
			int userNo = Integer.parseInt(request.getParameter("userNo"));

			boardDao.insert(new BoardVo(title, content, name, userNo));

			WebUtil.sendRedirect(response, "/mysite/board?action=list");

		} else if ("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));

			request.setAttribute("readList", boardDao.readList(no));
			boardDao.countHit(no);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

		} else if ("modifyForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));

			request.setAttribute("modifyInfo", boardDao.readList(no));

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");

		} else if ("modify".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));

			boardDao.update(new BoardVo(title, content, no));

			WebUtil.sendRedirect(response, "/mysite/board?action=list");

		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));

			boardDao.delete(no);

			WebUtil.sendRedirect(response, "/mysite/board?action=list");

		} else if ("search".equals(action)) {
			String keyword = request.getParameter("keyword");

			request.setAttribute("gList", boardDao.searchList(keyword));

			request.setAttribute("listCount", boardDao.listCount(keyword));

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
