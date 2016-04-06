package com.recruit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

		if(uri.indexOf("recruit.sst")!=-1){
			
			forward(req, resp, "/WEB-INF/views/recruit/recruit.jsp");
		}else if(uri.indexOf("created.sst")!=-1){
			
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
			
			String company=req.getParameter("recruitCompany");
			String head=req.getParameter("recruitHead");
			
			dto.setRecruitSubject(company + " [" + head + "]");
			
			int result=dao.insertRecruit(dto);
			if(result!=1){
				String message="일정 등록 실패";
				
				req.setAttribute("title", "회원 가입");
				req.setAttribute("mode", "created");
				req.setAttribute("message", message);
				forward(req, resp, "/WEB-INF/views/recruit/created.jsp");
				return;
			}
			
			StringBuffer sb=new StringBuffer();
			sb.append("자료 등록이 완료되었습니다.<br>");
			
			req.setAttribute("title", "일정 등록");
			req.setAttribute("message", sb.toString());
			
			forward(req, resp, "/WEB-INF/views/member/complete.jsp");
			
		}
	}

}
