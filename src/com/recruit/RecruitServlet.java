package com.recruit;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.CompanyDTO;
import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/recruit/*")
public class RecruitServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		RecruitDAO dao=new RecruitDAO();
		MyUtil util=new MyUtil();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		if(info==null) { // 로그인되지 않은 경우
			
			String msg2=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 로그인 후 사용하실 수 있습니다";
			req.setAttribute("message", msg2);
			
			String path="/WEB-INF/views/member/login.jsp";
			forward(req, resp, path);
			return ;
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
			
			CompanyDTO cdto=dao.readCompany(dto.getRecruitCompany());
			
			dto.setRecruitQual(dto.getRecruitQual().replaceAll("\n", "<br>"));
			dto.setRecruitStep(dto.getRecruitStep().replaceAll("\n", "<br>"));
			
			String companyUrl=cp + "/company/article.sst?companyNum="+cdto.getCompanyNum();
			
			req.setAttribute("dto", dto);
			req.setAttribute("companyUrl", companyUrl);
			
			String path = "/WEB-INF/views/recruit/article.jsp";
			forward(req, resp, path);
				
		}else if(uri.indexOf("update.sst")!=-1){
			int recruitNum=Integer.parseInt(req.getParameter("recruitNum"));
			RecruitDTO dto=dao.readRecruit(recruitNum);
			
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			String path="/WEB-INF//views/recruit/created.jsp";
			forward(req, resp, path);
			
		}else if(uri.indexOf("update_ok.sst")!=-1){
			
			String enctype="utf-8";
			int maxSize=5*1024*1024;
			
			MultipartRequest mreq=new MultipartRequest(req, pathname, maxSize, enctype, new DefaultFileRenamePolicy());
			
			String recruitImg=mreq.getParameter("recruitImg");
			
			RecruitDTO dto=new RecruitDTO();
			
			dto.setRecruitNum(Integer.parseInt(mreq.getParameter("recruitNum")));
			dto.setRecruitHead(mreq.getParameter("recruitHead"));
			dto.setRecruitStart(mreq.getParameter("recruitStart"));
			dto.setRecruitEnd(mreq.getParameter("recruitEnd"));
			dto.setRecruitQual(mreq.getParameter("recruitQual"));
			dto.setRecruitStep(mreq.getParameter("recruitStep"));
			
			String company=mreq.getParameter("recruitCompany");
			String head=mreq.getParameter("recruitHead");
			
			dto.setRecruitSubject(company+"["+head+"]");
			
			// 이미지 파일을 업로드 한경우
			if(mreq.getFile("upload")!=null) {
				// 기존 이미지 파일 지우기
				FileManager.doFiledelete(pathname, recruitImg);
							
				// 서버에 저장된 파일명
				String saveFilename=mreq.getFilesystemName("upload");
							
				// 파일 이름 변경
				saveFilename = FileManager.doFilerename(pathname, saveFilename);
							
				dto.setRecruitImg(saveFilename);
			} else {
				// 새로운 이미지 파일을 올리지 않은 경우 기존 이미지 파일로
				dto.setRecruitImg(recruitImg);
			}
						
			dao.updateRecruit(dto);
						
			resp.sendRedirect(cp+"/recruit/recruit.sst");	
			
		}else if(uri.indexOf("delete.sst")!=-1){
			
			// 삭제 완료
			int recruitNum=Integer.parseInt(req.getParameter("recruitNum"));
			
			RecruitDTO dto=dao.readRecruit(recruitNum);
			
			// 이미지 파일 지우기
			FileManager.doFiledelete(pathname, dto.getRecruitImg());
						
			// 테이블 데이터 삭제
			dao.deleteRecruit(recruitNum);
						
			resp.sendRedirect(cp+"/recruit/recruit.sst");			
			
		} else if (uri.indexOf("listReply.sst")!=-1) {
			// 리플 리스트 ---------------------------------------
			int recruitNum= Integer.parseInt(req.getParameter("recruitNum"));
			String pageNo= req.getParameter("pageNo");// 댓글의 페이지번호
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);
			
			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;
			
			dataCount= dao.dataCountRecruitReply(recruitNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
			
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			
			// 리스트에 출력할 댓글 데이터
			List<RecruitReplyDTO> list= dao.listRecruitReply(recruitNum, start, end);
			
			// 엔터를 <br>
			Iterator<RecruitReplyDTO> it= list.iterator();
			while(it.hasNext()) {
				RecruitReplyDTO dto=it.next();
				dto.setRecruitR_content(dto.getRecruitR_content().replaceAll("\n", "<br>"));
			}
			
			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);
			
			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			// 포워딩
			String path = "/WEB-INF/views/recruit/listReply.jsp";
			forward(req, resp, path);
		} else if(uri.indexOf("insertReply.sst") != -1){
			//리플 저장하기 ------
			String state="true";
			if(info != null){ 
				int recruitNum = Integer.parseInt(req.getParameter("recruitNum"));
				RecruitReplyDTO dto= new RecruitReplyDTO();
				dto.setRecruitNum(recruitNum);
				dto.setMemId(info.getMemId());
				dto.setRecruitR_content(req.getParameter("recruitR_content"));
				
				int result=dao.insertRecruitReply(dto);
				if(result==0)
					state="false";
			}
			StringBuffer sb=new StringBuffer();
			sb.append("{");
			sb.append("\"state\":"+"\""+state+"\"");
			sb.append("}");
			
			resp.setContentType("text/html);charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println(sb.toString());
		} else if(uri.indexOf("deleteReply.sst")!= -1){
			//리플 삭제-----------------------
			int recruitR_num = Integer.parseInt(req.getParameter("recruitR_num"));
			String memId=req.getParameter("memId");
			
			String state="false";
			if(info.getMemId().equals("admin") || info.getMemId().equals(memId)){
				dao.deleteRecruitReply(recruitR_num);
				state="true";
				
			}
			StringBuffer sb=new StringBuffer();
			sb.append("{");
			sb.append("\"state\":"+"\""+state+"\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println(sb.toString());
		}
 
	}

}
