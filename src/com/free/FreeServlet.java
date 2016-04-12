package com.free;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/free/*")
public class FreeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		FreeDAO dao=new FreeDAO();
		MyUtil util=new MyUtil();
		
		if(info==null) { // 로그인되지 않은 경우
			
			String msg2=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 로그인 후 사용하실 수 있습니다";
			req.setAttribute("message", msg2);
			
			String path="/WEB-INF/views/member/login.jsp";
			forward(req, resp, path);
			return ;
		}
		
		if(uri.indexOf("free.sst")!=-1) {
			// 넘어온 페이지
			String page=req.getParameter("page");
			int current_page=1;
			if(page!=null && page.length()!=0)
				current_page=Integer.parseInt(page);
			
			// 전체 데이터 개수
			int dataCount=dao.dataCount();
			
			// 전체페이지수 구하기
			int numPerPage=5;
			int total_page=util.pageCount(numPerPage, dataCount);
			
			// 전체페이지보다 표시할 페이지가 큰경우
			if(total_page<current_page)
				current_page=total_page;
			
			// 가져올데이터의 시작과 끝
			int start=(current_page-1)*numPerPage+1;
			int end=current_page*numPerPage;
			
			// 데이터 가져오기
			List<FreeDTO> list=dao.listFree(start, end);
			
			Iterator<FreeDTO> it=list.iterator();
			
			while (it.hasNext()) {
				FreeDTO dto=it.next();
				
				dto.setFreeContent(dto.getFreeContent().replaceAll(">", "&gt;"));
				dto.setFreeContent(dto.getFreeContent().replaceAll("<", "&lt;"));
				dto.setFreeContent(dto.getFreeContent().replaceAll("\n", "<br>"));
			}
			
			// 페이징처리
			String strUrl=cp+"/free/free.sst";
			String paging=util.paging(	current_page, total_page, strUrl);
			
			// guest.jsp에 넘겨줄 데이터
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("paging", paging);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			
			forward(req, resp, "/WEB-INF/views/free/free.jsp");
			
		} else if(uri.indexOf("free_ok.sst")!=-1) {
			// 방명록 저장
			
			FreeDTO dto=new FreeDTO();
			
			dto.setMemId(info.getMemId());
			dto.setFreeContent(req.getParameter("content"));
			
			dao.insertFree(dto);
			
			resp.sendRedirect(cp+"/free/free.sst");
			
		} else if(uri.indexOf("delete.sst")!=-1) {
			// 방명록 삭제
			
			int num=Integer.parseInt(req.getParameter("num"));
			String page=req.getParameter("page");
			String uid=req.getParameter("uid");

			if(! uid.equals(info.getMemId()) && ! info.getMemId().equals("admin")) {
				resp.sendRedirect(cp+"/free/free.sst?page="+page);
				return;
			}
			dao.deleteFree(num);			
			resp.sendRedirect(cp+"/free/free.sst?page="+page);
		}
		
		
		// 리플 리스트 -------------------------------------------------------------------------------
		/*else if (uri.indexOf("freeReply.sst")!=-1) {
			
			int freeNum= Integer.parseInt(req.getParameter("freeNum"));
			String pageNo= req.getParameter("pageNo");// 댓글의 페이지번호
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);
			
			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;
			
			dataCount= dao.dataCountFreeReply(freeNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
			
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			
			// 리스트에 출력할 댓글 데이터
			List<FreeReplyDTO> list= dao.listFreeReply(freeNum, start, end);
			
			// 엔터를 <br>
			Iterator<FreeReplyDTO> it= list.iterator();
			
			while(it.hasNext()) {
				FreeReplyDTO dto=it.next();
				dto.setFreeR_content(dto.getFreeR_content().replaceAll("\n", "<br>"));
			}
			
			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);
			
			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			// 포워딩
			String path = "/WEB-INF/views/free/freeReply.jsp";
			forward(req, resp, path);
			
		} 
		//리플 저장하기 ------------------------------------------------------------------------------------
		else if(uri.indexOf("insertReply.sst") != -1){
			
			String state="true";
			if(info == null){ //로그인 되지 않은 경우
				state="loginFail";
			}else {
				int freeNum = Integer.parseInt(req.getParameter("freeNum"));
				FreeReplyDTO dto= new FreeReplyDTO();
				dto.setFreeNum(freeNum);
				dto.setMemId(info.getMemId());
				dto.setFreeR_content(req.getParameter("freeR_content"));
				
				int result=dao.insertFreeReply(dto);
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
		} 
		//리플 삭제-------------------------------------------------------------------------------------
		else if(uri.indexOf("deleteReply.sst")!= -1){
			
			int freeR_num = Integer.parseInt(req.getParameter("freeR_num"));
			String memId=req.getParameter("memId");
			
			String state="false";
			if(info == null){ //로그인 되지 않은 경우
				state= "loginFail";				
			}
			else if(info.getMemId().equals("admin") || info.getMemId().equals(memId)){
				dao.deleteFreeReply(freeR_num);
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
 
	*/	
	}

}
