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
		String cp=req.getContextPath();
		ShareDAO dao = new ShareDAO();

		
		if(uri.indexOf("list.sst")!=-1) {
			List<ShareDTO> list = null;
			list=dao.listShare();
			String articleUrl=cp+"/bbs/article.do?sharNum=";
			
			req.setAttribute("list", list);
			req.setAttribute("articleUrl", articleUrl);
			
			forward(req, resp, "/WEB-INF/views/bbs/list.jsp");
		} else if(uri.indexOf("article.sst")!=-1) {
			int shareNum=Integer.parseInt(req.getParameter("shareNum"));
			
			ShareDTO dto= dao.readShare(shareNum);
			
			if(dto==null){
				resp.sendRedirect(cp+"/bbs/list.sst");
				return;
			}
			
			dto.setShareContent(dto.getShareContent().replaceAll("\n", "<br>"));
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			
			forward(req, resp, "/WEB-INF/views/bbs/article.jsp");
		}
		
	}

}
