package com.recruit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/recruit/*")
public class RecruitServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		RecruitDAO dao=new RecruitDAO();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");

		if(uri.indexOf("recruit.sst")!=-1){
			String articleUrl = cp + "/recruit/article.sst";
			req.setAttribute("articleUrl", articleUrl);
			forward(req, resp, "/WEB-INF/views/recruit/recruit.jsp");
		}else if(uri.indexOf("created.sst")!=-1){
			
			req.setAttribute("mode", "created");			
			forward(req, resp, "/WEB-INF/views/recruit/created.jsp");
		}else if(uri.indexOf("created_ok.sst")!=-1){
			
			RecruitDTO dto=new RecruitDTO();
			
			dto.setRecruitCompany(req.getParameter("recruitCompany"));
			dto.setRecruitHead(req.getParameter("recruitHead"));
			dto.setRecruitStart(req.getParameter("recruitStart"));
			dto.setRecruitEnd(req.getParameter("recruitEnd"));
			dto.setRecruitQual(req.getParameter("recruitQual"));
			dto.setRecruitStep(req.getParameter("recruitStep"));
			dto.setRecruitImg(req.getParameter("recruitImg"));
			dto.setMemId(info.getMemId());
	
		
			
			String company=req.getParameter("recruitCompany");
			String head=req.getParameter("recruitHead");
			
			
			dto.setRecruitSubject(company+"["+head+"]");
			
			dao.insertRecruit(dto);
			
			resp.sendRedirect(cp + "/recruit/recruit.sst");
		}else if(uri.indexOf("article.sst")!=-1){
			
			int recruitNum=Integer.parseInt(req.getParameter("recruitNum"));
			
			RecruitDTO dto=dao.readRecruit(recruitNum);
			
			if(dto==null){
				resp.sendRedirect(cp+"/recruit/recruit.jsp");
				return;
			}
			
			dto.setRecruitQual(dto.getRecruitQual().replaceAll("\n", "<br>"));
			dto.setRecruitStep(dto.getRecruitStep().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			
			String path = "/WEB-INF/views/recruit/article.jsp";
			forward(req, resp, path);
				
		}
	}

}
