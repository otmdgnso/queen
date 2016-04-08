package com.share;

import java.io.IOException;
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

@WebServlet("/bbs/*")
public class ShareServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		ShareDAO dao = new ShareDAO();
		MyUtil util = new MyUtil();
		
		//로그인 정보를 세션에서 가져오기
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		
		//uri에 따른 작업
		if (uri.indexOf("list.sst") != -1) {
			//게시물 리스트
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null)
				current_page = Integer.parseInt(page);
            
			//검색
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "shareSubject";
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
 			List<ShareDTO> list;
			if(searchValue.length()==0)
				list=dao.listShare(start, end);
			else
				list=dao.listShare(start, end, searchKey, searchValue);

			// 리스트 글번호
			int listShareNum, n = 0;
			Iterator<ShareDTO> it = list.iterator();
			while (it.hasNext()) {
				ShareDTO dto = it.next();
				listShareNum = dataCount - (start + n - 1);
				dto.setListShareNum(listShareNum);
				n++;
			}

			String params = "";
			if(searchValue.length()!=0) {
				params="searchKey=" +searchKey+ "&searchValue="
						+ URLEncoder.encode(searchValue, "UTF-8");
			}
			//페이징 처리
			String listUrl = cp + "/bbs/list.sst";
			String articleUrl = cp + "/bbs/article.sst?page=" + current_page;
			if (params.length() != 0) {
				listUrl += "?" + params;
				articleUrl += "&" + params;
			}
			String paging = util.paging(current_page, total_page, listUrl);
            //포워딩할 JSP로 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("paging", paging);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("articleUrl", articleUrl);

			forward(req, resp, "/WEB-INF/views/bbs/list.jsp");
		} else if(uri.indexOf("created.sst")!=-1) {
			
			// 글쓰기폼
			if(info == null){ //로그인 되지 않은경우 ,메인페이지로 넘어감
				
				resp.sendRedirect(cp+"/");
				return;
			}
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/bbs/created.jsp");
		} else if(uri.indexOf("created_ok.sst")!=-1) {
			// 게시물 저장
			if(info == null){
			    
				resp.sendRedirect(cp+"/"); //로그인 되지 않은경우 메인페이지로넘어감
				return;
			}
			
			ShareDTO dto= new ShareDTO();
			
			// userId는 세션에 저장된 정보
			dto.setMemId(info.getMemId());
			
			//파라미터
			dto.setShareContent(req.getParameter("shareContent"));
			dto.setShareSubject(req.getParameter("shareSubject"));
			
			dao.insertShare(dto);
			
			req.setAttribute("mode", "created");
			resp.sendRedirect(cp+ "/bbs/list.sst");
		} else if (uri.indexOf("article.sst") != -1) {
			//글보기
	        if(info == null){
			    
				resp.sendRedirect(cp+"/"); //로그인 되지 않은경우 메인페이지로넘어감
				return;
			}
			
	        //파라미터 : shareNum, page,[searchKey,searchValue]
			int shareNum = Integer.parseInt(req.getParameter("shareNum"));
			String page = req.getParameter("page");
			String searchKey=req.getParameter("searchKey");
			String searchValue=req.getParameter("searchValue");
			if(searchKey==null) {
				searchKey="shareSubject";
				searchValue="";
			}
			searchValue=URLDecoder.decode(searchValue,"UTF-8");
            //조회수 증가
			dao.ShareHitCount(shareNum);
           //게시물 가져오기
			ShareDTO dto = dao.readShare(shareNum);

			if (dto == null) { //게시물 없으면 다시 리스트로 감
				resp.sendRedirect(cp + "/bbs/list.sst?page=" + page);
				return;
			}

			int linesu = dto.getShareContent().split("\n").length;
			dto.setShareContent(dto.getShareContent().replaceAll("\n", "<br>"));
			
			//이전글 다음글
			ShareDTO preReadDto=dao.preReadShare(shareNum, searchKey, searchValue);
			ShareDTO nextReadDto=dao.nextReadShare(shareNum, searchKey, searchValue);
			
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

			forward(req, resp, "/WEB-INF/views/bbs/article.jsp");
		} else if (uri.indexOf("update.sst") != -1) {
			//수정 폼
			int shareNum = Integer.parseInt(req.getParameter("shareNum"));
			String page=req.getParameter("page");
			
			ShareDTO dto = dao.readShare(shareNum);
			if(dto==null || ! dto.getMemId().equals(info.getMemId())) {
				resp.sendRedirect(cp+"/bbs/list.sst?page=" +page);
				return;
			}
		    
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("page", page);
			forward(req, resp, "/WEB-INF/views/bbs/created.jsp");
		} else if (uri.indexOf("update_ok.sst") != -1) {
			String page=req.getParameter("page");
			
			ShareDTO dto = new ShareDTO();
			dto.setShareNum(Integer.parseInt(req.getParameter("shareNum")));
			dto.setShareSubject(req.getParameter("shareSubject"));
			dto.setShareContent(req.getParameter("shareContent"));

			dao.ShareUpdate(dto);
			
			resp.sendRedirect(cp + "/bbs/article.sst?page="+page+ "&shareNum="+dto.getShareNum());
		} else if (uri.indexOf("delete.sst")!=-1) {
			String page=req.getParameter("page");
			int shareNum= Integer.parseInt(req.getParameter("shareNum"));
			
			ShareDTO dto=dao.readShare(shareNum);
			if(dto==null || (!dto.getMemId().equals(info.getMemId())
					&& !info.getMemId().equals("admin"))) {
				resp.sendRedirect(cp +"/bbs/list.sst?page=" +page);
				return;
			}
			
			dao.deleteShare(shareNum);
			resp.sendRedirect(cp +"/bbs/list.sst?page=" +page);
		}

	}

}
