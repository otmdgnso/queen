package com.recruit;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
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
		if(info==null) { // 로그인되지 않은 경우
			resp.sendRedirect(cp+"/member/login.sst");
			return;
		}
		
		// 이미지를 저장할 경로(pathname)
		String root=session.getServletContext().getRealPath("/");
		String pathname = root+File.separator + "uploads" + File.separator + "recruit";
		File f = new File(pathname);
		if (!f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}
				

		if(uri.indexOf("recruit.sst")!=-1){
			String articleUrl = cp + "/recruit/article.sst";
			req.setAttribute("articleUrl", articleUrl);
			forward(req, resp, "/WEB-INF/views/recruit/recruit.jsp");
		}else if(uri.indexOf("created.sst")!=-1){
			
			req.setAttribute("mode", "created");			
			forward(req, resp, "/WEB-INF/views/recruit/created.jsp");
		}else if(uri.indexOf("created_ok.sst")!=-1){
			
			RecruitDTO dto=new RecruitDTO();
			
			String encType = "utf-8";
			int maxSize = 5 * 1024 * 1024;

			MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());
			
			dto.setRecruitCompany(mreq.getParameter("recruitCompany"));
			dto.setRecruitHead(mreq.getParameter("recruitHead"));
			dto.setRecruitStart(mreq.getParameter("recruitStart"));
			dto.setRecruitEnd(mreq.getParameter("recruitEnd"));
			dto.setRecruitQual(mreq.getParameter("recruitQual"));
			dto.setRecruitStep(mreq.getParameter("recruitStep"));
			dto.setRecruitImg(mreq.getParameter("recruitImg"));
			dto.setMemId(info.getMemId());
		
			String company=mreq.getParameter("recruitCompany");
			String head=mreq.getParameter("recruitHead");
			
			dto.setRecruitSubject(company+"["+head+"]");
			
			if (mreq.getFile("upload") != null) {

				// 서버에 저장된 파일명
				String saveFilename = mreq.getFilesystemName("upload");

				// 파일이름변경
				saveFilename = FileManager.doFilerename(pathname, saveFilename);

				dto.setRecruitImg(saveFilename);
			}
		
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
