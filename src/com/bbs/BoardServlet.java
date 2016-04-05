package com.bbs;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/bbs/*")
public class BoardServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		ShareDAO dao = new ShareDAO();
		ShareDTO dto = new ShareDTO();
		
		if(uri.indexOf("list.sst")!=-1) {
			List<ShareDTO> list = null;
			list=dao.listShare();
			req.setAttribute("list", list);
			
			forward(req, resp, "/WEB-INF/views/bbs/list.jsp");
		}
		
	}

}
