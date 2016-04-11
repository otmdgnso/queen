package com.portfolio;

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

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/portfolio/*")
public class PortfolioServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		String cp = req.getContextPath();
		// 로그인 정보를 세션에서 가져오기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		// 이미지를 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		String pathname = root + File.separator + "uploads" + File.separator + "portfolio";
		File f = new File(pathname);
		if (!f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}
		PortfolioDAO dao = new PortfolioDAO();
		MyUtil util = new MyUtil();

		// uri에 따른 작업 구분
		if (uri.indexOf("list.sst") != -1) {
			
			if(info==null) { // 로그인되지 않은 경우
				
				String msg2=" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 로그인 후 사용하실 수 있습니다";
				req.setAttribute("message", msg2);
				
				String path="/WEB-INF/views/member/login.jsp";
				forward(req, resp, path);
				return ;
			}
			
			// 게시물 리스트
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null)
				current_page = Integer.parseInt(page);

			// 전체데이터 개수
			int dataCount = dao.dataCount();

			// 전체페이지수
			int numPerPage = 6;
			int total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;

			// 게시물 가져올 시작과 끝위치
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;

			// 게시물 가져오기
			List<PortfolioDTO> list = dao.listPortfolio(start, end);

			// 페이징 처리
			String listUrl = cp + "/portfolio/list.sst";
			String articleUrl = cp + "/portfolio/article.sst?page=" + current_page;
			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 list.jsp에 넘길 값
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);

			String path = "/WEB-INF/views/portfolio/list.jsp";
			forward(req, resp, path);

		} else if (uri.indexOf("created.sst") != -1) {
			// 글쓰기 폼
			req.setAttribute("mode", "created");
			String path = "/WEB-INF/views/portfolio/created.jsp";
			forward(req, resp, path);
		} else if (uri.indexOf("created_ok.sst") != -1) {
			// 게시물 저장
			// <form enctype="multipart/form-data"....
			// 이어야 파일이 업로드 가능하고 request를 이용하여
			// 파라미터를 넘겨 받을 수 없다.
			String encType = "utf-8";
			int maxSize = 5 * 1024 * 1024;

			MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());

			PortfolioDTO dto = new PortfolioDTO();

			// 이미지 파일을 업로드 한경우
			if (mreq.getFile("upload") != null) {
				dto.setMemId(info.getMemId());

				dto.setSubject(mreq.getParameter("subject"));
				dto.setContent(mreq.getParameter("content"));

				// 서버에 저장된 파일명
				String saveFilename = mreq.getFilesystemName("upload");

				// 파일이름변경
				saveFilename = FileManager.doFilerename(pathname, saveFilename);

				dto.setImageFilename(saveFilename);

				// 저장
				dao.insertPortfolio(dto);
			}
			resp.sendRedirect(cp + "/portfolio/list.sst");

		} else if (uri.indexOf("article.sst") != -1) {
			int num = Integer.parseInt(req.getParameter("num"));
			String page = req.getParameter("page");

			dao.updateHitCount(num);

			PortfolioDTO dto = dao.readPortfolio(num);
			if (dto == null) {
				resp.sendRedirect(cp + "/portfolio/list.sst?page=" + page);
				return;
			}

			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);

			String path = "/WEB-INF/views/portfolio/article.jsp";
			forward(req, resp, path);

		} else if (uri.indexOf("update.sst") != -1) {
			String page = req.getParameter("page");
			int num = Integer.parseInt(req.getParameter("num"));
			PortfolioDTO dto = dao.readPortfolio(num);

			if (dto == null) {
				resp.sendRedirect(cp + "/portfolio/list.sst?page=" + page);
				return;
			}

			if (!dto.getMemId().equals(info.getMemId())) {
				resp.sendRedirect(cp + "/portfolio/list.sst?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("page", page);

			req.setAttribute("mode", "update");
			String path = "/WEB-INF/views/portfolio/created.jsp";
			forward(req, resp, path);

		} else if (uri.indexOf("update_ok.sst") != -1) {
			// 수정 완료
			String encType = "utf-8";
			int maxSize = 5 * 1024 * 1024;

			MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());

			String page = mreq.getParameter("page");
			String imageFilename = mreq.getParameter("imageFilename");

			PortfolioDTO dto = new PortfolioDTO();
			int num=Integer.parseInt(mreq.getParameter("num"));
			dto.setNum(num);
			dto.setSubject(mreq.getParameter("subject"));
			dto.setContent(mreq.getParameter("content"));

			// 이미지 파일을 업로드 한경우
			if (mreq.getFile("upload") != null) {
				// 기존 이미지 파일 지우기
				FileManager.doFiledelete(pathname, imageFilename);

				// 서버에 저장된 파일명
				String saveFilename = mreq.getFilesystemName("upload");

				// 파일 이름 변경
				saveFilename = FileManager.doFilerename(pathname, saveFilename);

				dto.setImageFilename(saveFilename);
			} else {
				// 새로운 이미지 파일을 올리지 않은 경우 기존 이미지 파일로
				dto.setImageFilename(imageFilename);
			}

			dao.updatePortfolio(dto);

			resp.sendRedirect(cp + "/portfolio/article.sst?page=" + page+"&num="+num);
			
			

		} else if (uri.indexOf("delete.sst") != -1) {
			// 삭제 완료
			int num = Integer.parseInt(req.getParameter("num"));
			String page = req.getParameter("page");

			PortfolioDTO dto = dao.readPortfolio(num);
			if (dto == null) {
				resp.sendRedirect(cp + "/portfolio/list.sst?page=" + page);
				return;
			}

			// 게시물을 올린 사용자나 admin이 아니면
			if (!dto.getMemId().equals(info.getMemId()) && !info.getMemId().equals("admin")) {
				resp.sendRedirect(cp + "/portfolio/list.sst?page=" + page);
				return;
			}

			// 이미지 파일 지우기
			FileManager.doFiledelete(pathname, dto.getImageFilename());

			// 테이블 데이터 삭제
			dao.deletePortfolio(num);

			resp.sendRedirect(cp + "/portfolio/list.sst?page=" + page);
		}else if (uri.indexOf("listReply.sst") != -1) {
			// 리플 리스트 ---------------------------------------
			int num = Integer.parseInt(req.getParameter("num"));
			String pageNo = req.getParameter("pageNo");
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);

			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;

			dataCount = dao.dataCountReply(num);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;

			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;

			// 리스트에 출력할 데이터
			List<PortfolioReplyDTO> list = dao.listReply(num, start, end);

			// 엔터를 <br>
			Iterator<PortfolioReplyDTO> it = list.iterator();
			while (it.hasNext()) {
				PortfolioReplyDTO dto = it.next();
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			}

			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);

			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);

			// 포워딩
			String path = "/WEB-INF/views/portfolio/portfolioReply.jsp";
			forward(req, resp, path);
		} else if (uri.indexOf("insertReply.sst") != -1) {
			// 리플 저장하기 ---------------------------------------
			String state="true";
			if (info == null) { // 로그인되지 않은 경우
				state="loginFail";
			} else {
				int num = Integer.parseInt(req.getParameter("num"));
				PortfolioReplyDTO rdto = new PortfolioReplyDTO();
				rdto.setNum(num);
				rdto.setMemId(info.getMemId());
				rdto.setContent(req.getParameter("content"));

				int result=dao.insertReply(rdto);
				if(result==0)
					state="false";
			}

			StringBuffer sb=new StringBuffer();
			sb.append("{");
			sb.append("\"state\":"+"\""+state+"\"");
			sb.append("}");
			
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out=resp.getWriter();
			out.println(sb.toString());
		} else if (uri.indexOf("deleteReply.sst") != -1) {
			// 리플 삭제 ---------------------------------------
			int replyNum = Integer.parseInt(req.getParameter("replyNum"));
			String memId=req.getParameter("memId");
			
			String state="false";
			if (info == null) { // 로그인되지 않은 경우
				state="loginFail";
			} else if(info.getMemId().equals("admin") || info.getMemId().equals(memId)) {
				dao.deleteReply(replyNum);
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
