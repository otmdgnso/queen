package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

@WebServlet("/company/*")
public class CompanyServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		CompanyDAO dao = new CompanyDAO();
		MyUtil util = new MyUtil();
		
		//로그인 정보를 세션에서 가져오기
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		
		//uri에 따른 작업
		if (uri.indexOf("list.sst") != -1) {
			
			if(info==null) { // 로그인되지 않은 경우
				
				String msg2=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 로그인 후 사용하실 수 있습니다";
				req.setAttribute("message", msg2);
				
				String path="/WEB-INF/views/member/login.jsp";
				forward(req, resp, path);
				return ;
			}
			
			//게시물 리스트
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null)
				current_page = Integer.parseInt(page);
            
			//검색
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "companySubject";
				searchValue = "";
			}
			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}

			// 데이터 개수 구하기(전체)
			int dataCount;
			if (searchValue.length()==0)
				dataCount=dao.dataCount();
			else
				dataCount=dao.dataCount(searchKey, searchValue);
            //전체 페이지 수 
			int numPerPage = 10;
			int total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
            //게시물 가져올 시작과 끝
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
            //게시물 가져오기
 			List<CompanyDTO> list;
			if(searchValue.length()==0)
				list=dao.listCompany(start, end);
			else
				list=dao.listCompany(start, end, searchKey, searchValue);

			
			//추천글
			List<CompanyDTO> listBestCompany=null;
			listBestCompany=dao.listBestCompany();
			
			
			
			// 리스트 글번호
			int listCompanyNum, n = 0;
			Iterator<CompanyDTO> it = list.iterator();
			while (it.hasNext()) {
				CompanyDTO dto = it.next();
				listCompanyNum = dataCount - (start + n - 1);
				dto.setListCompanyNum(listCompanyNum);
				n++;
			}

			String params = "";
			if(searchValue.length()!=0) {
				params="searchKey=" +searchKey+ "&searchValue="
						+ URLEncoder.encode(searchValue, "UTF-8");
			}
			//페이징 처리
			String listUrl = cp + "/company/list.sst";
			String articleUrl = cp + "/company/article.sst?page=" + current_page;
			if (params.length() != 0) {
				listUrl += "?" + params;
				articleUrl += "&" + params;
			}
			String paging = util.paging(current_page, total_page, listUrl);
            //포워딩할 JSP로 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("listBestCompany", listBestCompany);
			req.setAttribute("paging", paging);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("articleUrl", articleUrl);

			forward(req, resp, "/WEB-INF/views/company/list.jsp");
		} else if(uri.indexOf("created.sst")!=-1) {
			
			// 글쓰기폼
			if(info == null){ //로그인 되지 않은경우 ,메인페이지로 넘어감
				
				resp.sendRedirect(cp+"/");
				return;
			}
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/company/created.jsp");
		} else if(uri.indexOf("created_ok.sst")!=-1) {
			// 게시물 저장
			if(info == null){
			    
				resp.sendRedirect(cp+"/"); //로그인 되지 않은경우 메인페이지로넘어감
				return;
			}
			
			CompanyDTO dto= new CompanyDTO();
			
			// userId는 세션에 저장된 정보
			dto.setMemId(info.getMemId());
			
			//파라미터
			dto.setCompanyContent(req.getParameter("companyContent"));
			dto.setCompanySubject(req.getParameter("companySubject"));
			
			dao.insertCompany(dto);
			
			req.setAttribute("mode", "created");
			resp.sendRedirect(cp+ "/company/list.sst");
		} else if (uri.indexOf("article.sst") != -1) {
			//글보기
	        if(info == null){
			    
				resp.sendRedirect(cp+"/"); //로그인 되지 않은경우 메인페이지로넘어감
				return;
			}
			
	        //파라미터 : CompanyNum, page,[searchKey,searchValue]
			int companyNum = Integer.parseInt(req.getParameter("companyNum"));
			int recommCount=dao.recommCount(companyNum, info.getMemId());
			String page = req.getParameter("page");
			String searchKey=req.getParameter("searchKey");
			String searchValue=req.getParameter("searchValue");
			if(searchKey==null) {
				searchKey="companySubject";
				searchValue="";
			}
			searchValue=URLDecoder.decode(searchValue,"UTF-8");
            //조회수 증가
			dao.companyHitCount(companyNum);
           //게시물 가져오기
			CompanyDTO dto = dao.readCompany(companyNum);

			if (dto == null) { //게시물 없으면 다시 리스트로 감
				resp.sendRedirect(cp + "/company/list.sst?page=" + page);
				return;
			}

			int linesu = dto.getCompanyContent().split("\n").length;
			dto.setCompanyContent(dto.getCompanyContent().replaceAll("\n", "<br>"));
			
			//이전글 다음글
			CompanyDTO preReadDto=dao.preReadCompany(companyNum, searchKey, searchValue);
			CompanyDTO nextReadDto=dao.nextReadCompany(companyNum, searchKey, searchValue);
			
			//리스트나 이전글/다음글에서 사용할 파라미터
			String params = "page=" + page;
			if(searchValue.length()!=0) {
				params+="&searchKey=" +searchKey+ "&searchValue=" 
						+URLEncoder.encode(searchValue,"UTF-8");
			}

			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("params", params);
			req.setAttribute("linesu", linesu);
			req.setAttribute("page", page);
			req.setAttribute("recommCount", recommCount);

			forward(req, resp, "/WEB-INF/views/company/article.jsp");
		} else if (uri.indexOf("update.sst") != -1) {
			//수정 폼
			int companyNum = Integer.parseInt(req.getParameter("companyNum"));
			String page=req.getParameter("page");
			
			CompanyDTO dto = dao.readCompany(companyNum);
			if(dto==null || ! dto.getMemId().equals(info.getMemId())) {
				resp.sendRedirect(cp+"/company/list.sst?page=" +page);
				return;
			}
		    
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("page", page);
			forward(req, resp, "/WEB-INF/views/company/created.jsp");
		} else if (uri.indexOf("update_ok.sst") != -1) {
			String page=req.getParameter("page");
			
			CompanyDTO dto = new CompanyDTO();
			dto.setCompanyNum(Integer.parseInt(req.getParameter("companyNum")));
			dto.setCompanySubject(req.getParameter("companySubject"));
			dto.setCompanyDate(req.getParameter("companyDate"));
			dto.setCompanyContent(req.getParameter("companyContent"));
			dto.setCompanyName(req.getParameter("companyName"));
			dto.setCompanySales(req.getParameter("companySales"));
			dto.setCompanyForm(req.getParameter("companyForm"));
			dto.setCompanyIndustry(req.getParameter("companyIndustry"));
			dto.setCompanyPlanet(req.getParameter("companyPlanet"));
			dto.setCompanyWeb(req.getParameter("companyWeb"));
			dto.setCompanySalary(req.getParameter("companySalary"));
			dto.setCompanyScore(req.getParameter("companyScore"));
		
			dao.companyUpdate(dto);
			
			resp.sendRedirect(cp + "/company/article.sst?page="+page+ "&companyNum="+dto.getCompanyNum());
		} else if (uri.indexOf("delete.sst")!=-1) {
			String page=req.getParameter("page");
			int companyNum= Integer.parseInt(req.getParameter("companyNum"));
			
			CompanyDTO dto=dao.readCompany(companyNum);
			if(dto==null || (!dto.getMemId().equals(info.getMemId())
					&& !info.getMemId().equals("admin"))) {
				resp.sendRedirect(cp +"/company/list.sst?page=" +page);
				return;
			}
			
			dao.deleteCompany(companyNum);
			resp.sendRedirect(cp +"/company/list.sst?page=" +page);
		} else if (uri.indexOf("listReply.sst")!=-1) {
			// 리플 리스트 ---------------------------------------
			int companyNum= Integer.parseInt(req.getParameter("companyNum"));
			String pageNo= req.getParameter("pageNo");// 댓글의 페이지번호
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);
			
			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;
			
			dataCount= dao.dataCountCompanyReply(companyNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
			
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			
			// 리스트에 출력할 댓글 데이터
			List<CompanyReplyDTO> list= dao.listCompanyReply(companyNum, start, end);
			
			// 엔터를 <br>
			Iterator<CompanyReplyDTO> it= list.iterator();
			while(it.hasNext()) {
				CompanyReplyDTO dto=it.next();
				dto.setCompanyR_content(dto.getCompanyR_content().replaceAll("\n", "<br>"));
			}
			
			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);
			
			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			// 포워딩
			String path = "/WEB-INF/views/company/listReply.jsp";
			forward(req, resp, path);
		} else if(uri.indexOf("insertReply.sst") != -1){
			//리플 저장하기 ------
			String state="true";
			if(info == null){ //로그인 되지 않은 경우
				state="loginFail";
			}else {
				int companyNum = Integer.parseInt(req.getParameter("companyNum"));
				CompanyReplyDTO dto= new CompanyReplyDTO();
				dto.setCompanyNum(companyNum);
				dto.setMemId(info.getMemId());
				dto.setCompanyR_content(req.getParameter("companyR_content"));
				
				int result=dao.insertCompanyReply(dto);
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
			int companyR_num = Integer.parseInt(req.getParameter("companyR_num"));
			String memId=req.getParameter("memId");
			
			String state="false";
			if(info == null){ //로그인 되지 않은 경우
				state= "loginFail";				
			} else if(info.getMemId().equals("admin") || info.getMemId().equals(memId)){
				dao.deleteCompanyReply(companyR_num);
				state="true";
				
			}
			StringBuffer sb=new StringBuffer();
			sb.append("{");
			sb.append("\"state\":"+"\""+state+"\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println(sb.toString());
		}else if(uri.indexOf("recomm.sst")!=-1) {  
	          //////// 추천수 증가
	         int companyNum= Integer.parseInt(req.getParameter("companyNum"));
	         String page= req.getParameter("page");
	         
	         int recommCount= dao.recommCount(companyNum, info.getMemId());
	         
	         if (recommCount==0)
	            dao.CompanyRecomm(companyNum, info.getMemId());
	         
	         resp.sendRedirect(cp + "/company/article.sst?page="+page+"&companyNum="+companyNum);
		
	}
 
		
		
		
		
		
		
		
		
		
		
	}

}
