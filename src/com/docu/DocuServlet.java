package com.docu;

import java.io.File;
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
import com.docu.DocuDTO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/docu/*")
public class DocuServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();
		
		
		//로그인 정보를 세션에서 가져오기
		HttpSession session=req.getSession();
		SessionInfo info=(SessionInfo)session.getAttribute("member");
		
		// 파일을 저장할 경로(pathname)
				String root=session.getServletContext().getRealPath("/");
				String pathname=root+File.separator+"uploads"+File.separator+"docu";
				File f=new File(pathname);
				if(! f.exists()) {
					f.mkdirs();
				}
				
				DocuDAO dao = new DocuDAO();
				MyUtil util = new MyUtil();
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
				searchKey = "docuSubject";
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
 			List<DocuDTO> list;
			if(searchValue.length()==0)
				list=dao.listDocu(start, end);
			else
				list=dao.listDocu(start, end, searchKey, searchValue);

			// 리스트 글번호
			int listDocuNum, n = 0;
			Iterator<DocuDTO> it = list.iterator();
			while (it.hasNext()) {
				DocuDTO dto = it.next();
				listDocuNum = dataCount - (start + n - 1);
				dto.setListDocuNum(listDocuNum);
				n++;
			}

			String params = "";
			if(searchValue.length()!=0) {
				params="searchKey=" +searchKey+ "&searchValue="
						+ URLEncoder.encode(searchValue, "UTF-8");
			}
			//페이징 처리
			String listUrl = cp + "/docu/list.sst";
			String articleUrl = cp + "/docu/article.sst?page=" + current_page;
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

			forward(req, resp, "/WEB-INF/views/docu/list.jsp");
		} else if(uri.indexOf("created.sst")!=-1) {
			
			// 글쓰기폼
			if(info == null){ //로그인 되지 않은경우 ,메인페이지로 넘어감
				
				resp.sendRedirect(cp+"/");
				return;
			}
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/docu/created.jsp");
		} else if(uri.indexOf("created_ok.sst")!=-1) {
			// 게시물 저장
			if(info == null){
			    
				resp.sendRedirect(cp+"/"); //로그인 되지 않은경우 메인페이지로넘어감
				return;
			}
			
			String encType="utf-8";
			int maxFilesize=10*1024*1024;
			
		    MultipartRequest mreq=new MultipartRequest(
		    		req, pathname, maxFilesize, encType,
		    		new DefaultFileRenamePolicy()
		    		);
		    
			DocuDTO dto= new DocuDTO();
			
			// userId는 세션에 저장된 정보
			dto.setMemId(info.getMemId());
			
			dto.setDocuContent(mreq.getParameter("docuContent"));
			dto.setDocuSubject(mreq.getParameter("docuSubject"));
			
			if(mreq.getFile("upload")!=null){
				dto.setDocuFile(mreq.getFilesystemName("upload"));
				dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
			    dto.setFileSize(mreq.getFile("upload").length());
				
			}
			dao.insertDocu(dto);
			req.setAttribute("mode", "created");
			resp.sendRedirect(cp+ "/docu/list.sst");
		} else if(uri.indexOf("recomm.sst")!=-1) {  
	          //////// 추천수 증가
	         int docuNum= Integer.parseInt(req.getParameter("docuNum"));
	         String page= req.getParameter("page");
	         
	         int dataCount= dao.dataCount(docuNum, info.getMemId());
	         
	         if (dataCount==0)
	            dao.DocuRecomm(docuNum, info.getMemId());
	         
	         resp.sendRedirect(cp + "/docu/article.sst?page="+page+"&docuNum="+docuNum);
	      } else if (uri.indexOf("article.sst") != -1) {
	    	  if(info==null) { // 로그인되지 않은 경우
					
					String msg2=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 로그인 후 사용하실 수 있습니다";
					req.setAttribute("message", msg2);
					
					String path="/WEB-INF/views/member/login.jsp";
					forward(req, resp, path);
					return ;
				}
	    	  
	        //파라미터 : docuNum, page,[searchKey,searchValue]
			int docuNum = Integer.parseInt(req.getParameter("docuNum"));
			int dataCount= dao.dataCount(docuNum, info.getMemId());
			String page = req.getParameter("page");
			String searchKey=req.getParameter("searchKey");
			String searchValue=req.getParameter("searchValue");
			if(searchKey==null) {
				searchKey="docuSubject";
				searchValue="";
			}
			searchValue=URLDecoder.decode(searchValue,"UTF-8");
            //조회수 증가
			dao.DocuHitCount(docuNum);
           //게시물 가져오기
			DocuDTO dto = dao.readDocu(docuNum);

			if (dto == null) { //게시물 없으면 다시 리스트로 감
				resp.sendRedirect(cp + "/docu/list.sst?page=" + page);
				return;
			}

			int linesu = dto.getDocuContent().split("\n").length;
			dto.setDocuContent(dto.getDocuContent().replaceAll("\n", "<br>"));
			
			//이전글 다음글
			DocuDTO preReadDto=dao.preReadDocu(docuNum, searchKey, searchValue);
			DocuDTO nextReadDto=dao.nextReadDocu(docuNum, searchKey, searchValue);
			
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
			req.setAttribute("dataCount", dataCount);

			forward(req, resp, "/WEB-INF/views/docu/article.jsp");
		} else if (uri.indexOf("update.sst") != -1) {
			//수정 폼
			int docuNum = Integer.parseInt(req.getParameter("docuNum"));
			String page=req.getParameter("page");
			
			DocuDTO dto = dao.readDocu(docuNum);
			//글을 등록한 사람이거나 관리자만 삭제가능
			if(dto==null || ! dto.getMemId().equals(info.getMemId())) {
				resp.sendRedirect(cp+"/docu/list.sst?page=" +page);
				return;
			}
		    
			req.setAttribute("docuNum", docuNum);
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("page", page);
			forward(req, resp, "/WEB-INF/views/docu/created.jsp");
		} else if (uri.indexOf("update_ok.sst") != -1) {
			String encType="utf-8";
			int maxFilesize=10*1024*1024;
			
		    MultipartRequest mreq=new MultipartRequest(
		    		req, pathname, maxFilesize, encType,
		    		new DefaultFileRenamePolicy()
		    		);
			
		    DocuDTO dto = new DocuDTO();
		    
			String page=mreq.getParameter("page");
			
			dto.setDocuNum(Integer.parseInt(mreq.getParameter("docuNum")));
			dto.setDocuSubject(mreq.getParameter("docuSubject"));
			dto.setDocuContent(mreq.getParameter("docuContent"));
			dto.setDocuFile(mreq.getParameter("docuFile"));
			dto.setOriginalFilename(mreq.getParameter("originalFilename"));
			dto.setFileSize(Long.parseLong(mreq.getParameter("fileSize")));
			
			if(mreq.getFile("upload")!=null) {
				// 기존 파일 삭제
				FileManager.doFiledelete(pathname, mreq.getParameter("docuFile"));
				
		    	dto.setDocuFile(mreq.getFilesystemName("upload"));
		    	dto.setOriginalFilename(mreq.getOriginalFileName("upload"));
			    dto.setFileSize(mreq.getFile("upload").length());
			}
			dao.DocuUpdate(dto);
			
			resp.sendRedirect(cp + "/docu/article.sst?page="+page+ "&docuNum="+dto.getDocuNum());
		}  else if(uri.indexOf("deleteFile.sst")!=-1) {
			int docuNum=Integer.parseInt(req.getParameter("docuNum"));
			String page=req.getParameter("page");
			
			DocuDTO dto=dao.readDocu(docuNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/docu/list.sst?page="+page);
				return;
			}
			
			if(info==null || ! info.getMemId().equals(dto.getMemId())) {
				resp.sendRedirect(cp+"/docu/list.sst?page="+page);
				return;
			}
			
			FileManager.doFiledelete(pathname, dto.getDocuFile());
			dto.setOriginalFilename("");
			dto.setDocuFile("");
			dto.setFileSize(0);
			
			dao.DocuUpdate(dto);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			req.setAttribute("mode", "update");
			String path="/WEB-INF/views/docu/created.jsp";
			forward(req, resp, path);			
			
		}else if (uri.indexOf("delete.sst")!=-1) { //삭제
			String page=req.getParameter("page");
			int docuNum= Integer.parseInt(req.getParameter("docuNum"));
			
			DocuDTO dto=dao.readDocu(docuNum);
			
			//글을 등록한 사람이거나 관리자만 삭제가능
			if(dto==null || (!dto.getMemId().equals(info.getMemId())
					&& !info.getMemId().equals("admin"))) {
				resp.sendRedirect(cp +"/docu/list.sst?page=" +page);
				return;
			}
			if(dto.getDocuFile()!=null && dto.getDocuFile().length()!=0)
				   FileManager.doFiledelete(pathname, dto.getDocuFile());
			
			dao.deleteDocu(docuNum);
			
			resp.sendRedirect(cp +"/docu/list.sst?page=" +page);
			
		} else if(uri.indexOf("download.do")!=-1) {
			// 파일 다운로드
			if(info==null) {
				resp.sendRedirect(cp+"/member/login.do");
				return;
			}
			
			int docuNum=Integer.parseInt(req.getParameter("docuNum"));
			String page=req.getParameter("page");
			
			DocuDTO dto=dao.readDocu(docuNum);
			if(dto==null) {
				resp.sendRedirect(cp+"/docu/list.sst"+page);
				return;
			}
			
			boolean b = FileManager.doFiledownload(dto.getDocuFile(),
					dto.getOriginalFilename(), pathname, resp);
			
			if(! b) {
				resp.setContentType("text/html;charset=utf-8");
		    	PrintWriter pw=resp.getWriter();
		    	pw.print("<script>alert('파일다운로드가 실패했습니다. !!!');history.back();</script>");
		    	return;
			}
		} else if (uri.indexOf("listReply.sst")!=-1) {
			// 리플 리스트 ---------------------------------------
			int docuNum= Integer.parseInt(req.getParameter("docuNum"));
			String pageNo= req.getParameter("pageNo");// 댓글의 페이지번호
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);
			
			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;
			
			dataCount= dao.dataCountDocuReply(docuNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
			
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			
			// 리스트에 출력할 댓글 데이터
			List<DocuReplyDTO> list= dao.listDocuReply(docuNum, start, end);
			
			// 엔터를 <br>
			Iterator<DocuReplyDTO> it= list.iterator();
			while(it.hasNext()) {
				DocuReplyDTO dto=it.next();
				dto.setDocuR_content(dto.getDocuR_content().replaceAll("\n", "<br>"));
			}
			
			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);
			
			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			// 포워딩
			String path = "/WEB-INF/views/docu/listReply.jsp";
			forward(req, resp, path);
		} else if(uri.indexOf("insertReply.sst") != -1){
			//리플 저장하기 ------
			String state="true";
			if(info == null){ //로그인 되지 않은 경우
				state="loginFail";
			}else {
				int docuNum = Integer.parseInt(req.getParameter("docuNum"));
				DocuReplyDTO dto= new DocuReplyDTO();
				dto.setDocuNum(docuNum);
				dto.setMemId(info.getMemId());
				dto.setDocuR_content(req.getParameter("docuR_content"));
				
				int result=dao.insertDocuReply(dto);
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
			int docuR_num = Integer.parseInt(req.getParameter("docuR_num"));
			String memId=req.getParameter("memId");
			
			String state="false";
			if(info == null){ //로그인 되지 않은 경우
				state= "loginFail";				
			} else if(info.getMemId().equals("admin") || info.getMemId().equals(memId)){
				dao.deleteDocuReply(docuR_num);
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
