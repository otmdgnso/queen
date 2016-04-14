package com.trend;

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
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/trend/*")
public class TrendServlet extends MyServlet {
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
		String pathname = root + File.separator + "uploads" + File.separator + "trend";
		File f = new File(pathname);
		if (!f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}
		
		TrendDAO dao = new TrendDAO();
		MyUtil util = new MyUtil();

		// uri에 따른 작업
		if (uri.indexOf("list.sst") != -1) {

			if (info == null) { // 로그인되지 않은 경우

				String msg2 = " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 로그인 후 사용하실 수 있습니다";
				req.setAttribute("message", msg2);

				String path = "/WEB-INF/views/member/login.jsp";
				forward(req, resp, path);
				return;
			}

			// 게시물 리스트
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null)
				current_page = Integer.parseInt(page);

			// 검색
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "trendSubject";
				searchValue = "";
			}
			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}

			// 데이터 개수 구하기(전체)
			int dataCount;
			if (searchValue.length() == 0)
				dataCount = dao.dataCount();
			else
				dataCount = dao.dataCount(searchKey, searchValue);
			// 전체 페이지 수
			int numPerPage = 10;
			int total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;
			// 게시물 가져올 시작과 끝
			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;
			// 게시물 가져오기
			List<TrendDTO> list;
			if (searchValue.length() == 0)
				list = dao.listTrend(start, end);
			else
				list = dao.listTrend(start, end, searchKey, searchValue);

			// 리스트 글번호
			int listTrendNum, n = 0;
			Iterator<TrendDTO> it = list.iterator();
			while (it.hasNext()) {
				TrendDTO dto = it.next();
				listTrendNum = dataCount - (start + n - 1);
				dto.setListTrendNum(listTrendNum);
				n++;
			}

			String params = "";
			if (searchValue.length() != 0) {
				params = "searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
			}
			// 페이징 처리
			String listUrl = cp + "/trend/list.sst";
			String articleUrl = cp + "/trend/article.sst?page=" + current_page;
			if (params.length() != 0) {
				listUrl += "?" + params;
				articleUrl += "&" + params;
			}
			String paging = util.paging(current_page, total_page, listUrl);
			// 포워딩할 JSP로 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("paging", paging);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("articleUrl", articleUrl);

			forward(req, resp, "/WEB-INF/views/trend/list.jsp");
		} else if (uri.indexOf("created.sst") != -1) {

			// 글쓰기폼
			if (info == null) { // 로그인 되지 않은경우 ,메인페이지로 넘어감

				resp.sendRedirect(cp + "/");
				return;
			}
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/trend/created.jsp");
		} else if (uri.indexOf("created_ok.sst") != -1) {
			// 게시물 저장
			if (info == null) {

				resp.sendRedirect(cp + "/"); // 로그인 되지 않은경우 메인페이지로넘어감
				return;
			}

			TrendDTO dto = new TrendDTO();
			String encType = "utf-8";
			int maxSize = 5 * 1024 * 1024;

			MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, encType,
					new DefaultFileRenamePolicy());
			// userId는 세션에 저장된 정보
			dto.setMemId(info.getMemId());

			// 파라미터
			dto.setTrendContent(mreq.getParameter("trendContent"));
			dto.setTrendSubject(mreq.getParameter("trendSubject"));
			dto.setTrendHead(mreq.getParameter("trendHead"));
			dto.setImageFilename(mreq.getParameter("imageFilename"));

			if (mreq.getFile("upload") != null) {

				// 서버에 저장된 파일명
				String saveFilename = mreq.getFilesystemName("upload");

				// 파일이름변경
				saveFilename = FileManager.doFilerename(pathname, saveFilename);

				dto.setImageFilename(saveFilename);
			}

			dao.insertTrend(dto);
			req.setAttribute("mode", "created");
			resp.sendRedirect(cp + "/trend/list.sst");
		} else if (uri.indexOf("article.sst") != -1) {
			// 글보기
			if (info == null) {

				resp.sendRedirect(cp + "/"); // 로그인 되지 않은경우 메인페이지로넘어감
				return;
			}

			// 파라미터 : trendNum, page,[searchKey,searchValue]
			int trendNum = Integer.parseInt(req.getParameter("trendNum"));
			String page = req.getParameter("page");
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "trendSubject";
				searchValue = "";
			}
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
			// 게시물 가져오기
			TrendDTO dto = dao.readTrend(trendNum);

			if (dto == null) { // 게시물 없으면 다시 리스트로 감
				resp.sendRedirect(cp + "/trend/list.sst?page=" + page);
				return;
			}

			int linesu = dto.getTrendContent().split("\n").length;
			dto.setTrendContent(dto.getTrendContent().replaceAll("\n", "<br>"));

			// 이전글 다음글
			TrendDTO preReadDto = dao.preReadTrend(trendNum, searchKey, searchValue);
			TrendDTO nextReadDto = dao.nextReadTrend(trendNum, searchKey, searchValue);

			// 리스트나 이전글/다음글에서 사용할 파라미터
			String params = "page=" + page;
			if (searchValue.length() != 0) {
				params += "&searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
			}

			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("params", params);
			req.setAttribute("linesu", linesu);
			req.setAttribute("page", page);

			forward(req, resp, "/WEB-INF/views/trend/article.jsp");
		} else if (uri.indexOf("update.sst") != -1) {
			// 수정 폼
			int trendNum = Integer.parseInt(req.getParameter("trendNum"));
			String page = req.getParameter("page");

			TrendDTO dto = dao.readTrend(trendNum);
			if (dto == null || !dto.getMemId().equals(info.getMemId())) {
				resp.sendRedirect(cp + "/trend/list.sst?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("page", page);
			forward(req, resp, "/WEB-INF/views/trend/created.jsp");
		} else if (uri.indexOf("update_ok.sst") != -1) {
			String enctype = "utf-8";
			int maxSize = 5 * 1024 * 1024;

			MultipartRequest mreq = new MultipartRequest(req, pathname, maxSize, enctype,
					new DefaultFileRenamePolicy());

			String imageFilename = mreq.getParameter("imageFilename");
			String page = mreq.getParameter("page");

			TrendDTO dto = new TrendDTO();
			dto.setTrendNum(Integer.parseInt(mreq.getParameter("trendNum")));
			dto.setTrendSubject(mreq.getParameter("trendSubject"));
			dto.setTrendContent(mreq.getParameter("trendContent"));
			dto.setTrendHead(mreq.getParameter("trendHead"));

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

			dao.TrendUpdate(dto);

			resp.sendRedirect(cp + "/trend/article.sst?page=" + page + "&trendNum=" + dto.getTrendNum());
		} else if (uri.indexOf("delete.sst") != -1) {
			String page = req.getParameter("page");
			int trendNum = Integer.parseInt(req.getParameter("trendNum"));

			TrendDTO dto = dao.readTrend(trendNum);
			// 이미지 파일 지우기
			FileManager.doFiledelete(pathname, dto.getImageFilename());
	
			
			if (dto == null || (!dto.getMemId().equals(info.getMemId()) && !info.getMemId().equals("admin"))) {
				resp.sendRedirect(cp + "/trend/list.sst?page=" + page);
				return;
			}

			dao.deleteTrend(trendNum);
			resp.sendRedirect(cp + "/trend/list.sst?page=" + page);
		} else if (uri.indexOf("listReply.sst") != -1) {
			// 리플 리스트 ---------------------------------------
			int trendNum = Integer.parseInt(req.getParameter("trendNum"));
			String pageNo = req.getParameter("pageNo");// 댓글의 페이지번호
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);

			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;

			dataCount = dao.dataCountTrendReply(trendNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;

			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;

			// 리스트에 출력할 댓글 데이터
			List<TrendReplyDTO> list = dao.listTrendReply(trendNum, start, end);

			// 엔터를 <br>
			Iterator<TrendReplyDTO> it = list.iterator();
			while (it.hasNext()) {
				TrendReplyDTO dto = it.next();
				dto.setTrendR_content(dto.getTrendR_content().replaceAll("\n", "<br>"));
			}

			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);

			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);

			// 포워딩
			String path = "/WEB-INF/views/trend/listReply.jsp";
			forward(req, resp, path);
		} else if (uri.indexOf("insertReply.sst") != -1) {
			// 리플 저장하기 ------
			String state = "true";
			if (info == null) { // 로그인 되지 않은 경우
				state = "loginFail";
			} else {
				int trendNum = Integer.parseInt(req.getParameter("trendNum"));
				TrendReplyDTO dto = new TrendReplyDTO();
				dto.setTrendNum(trendNum);
				dto.setMemId(info.getMemId());
				dto.setTrendR_content(req.getParameter("trendR_content"));

				int result = dao.insertTrendReply(dto);
				if (result == 0)
					state = "false";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"state\":" + "\"" + state + "\"");
			sb.append("}");

			resp.setContentType("text/html);charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		} else if (uri.indexOf("deleteReply.sst") != -1) {
			// 리플 삭제-----------------------
			int trendR_num = Integer.parseInt(req.getParameter("trendR_num"));
			String memId = req.getParameter("memId");

			String state = "false";
			if (info == null) { // 로그인 되지 않은 경우
				state = "loginFail";
			} else if (info.getMemId().equals("admin") || info.getMemId().equals(memId)) {
				dao.deleteTrendReply(trendR_num);
				state = "true";

			}
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"state\":" + "\"" + state + "\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		}

	}

}
