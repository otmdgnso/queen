package com.qna;

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

@WebServlet("/qna/*")
public class QnaServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtil();

		// 로그인 정보를 세션에서 가져오기
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

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
				searchKey = "qnaSubject";
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
			List<QnaDTO> list;
			if (searchValue.length() == 0)
				list = dao.listQna(start, end);
			else
				list = dao.listQna(start, end, searchKey, searchValue);

			// 리스트 글번호
			int listQnaNum, n = 0;
			Iterator<QnaDTO> it = list.iterator();
			while (it.hasNext()) {
				QnaDTO dto = it.next();
				listQnaNum = dataCount - (start + n - 1);
				dto.setListQnaNum(listQnaNum);
				n++;
			}

			String params = "";
			if (searchValue.length() != 0) {
				params = "searchKey=" + searchKey + "&searchValue=" + URLEncoder.encode(searchValue, "UTF-8");
			}
			// 페이징 처리
			String listUrl = cp + "/qna/list.sst";
			String articleUrl = cp + "/qna/article.sst?page=" + current_page;
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

			forward(req, resp, "/WEB-INF/views/qna/list.jsp");
		} else if (uri.indexOf("created.sst") != -1) {

			// 글쓰기폼
			if (info == null) { // 로그인 되지 않은경우 ,메인페이지로 넘어감

				resp.sendRedirect(cp + "/");
				return;
			}
			req.setAttribute("mode", "created");
			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
		} else if (uri.indexOf("created_ok.sst") != -1) {
			// 게시물 저장
			if (info == null) {

				resp.sendRedirect(cp + "/"); // 로그인 되지 않은경우 메인페이지로넘어감
				return;
			}

			QnaDTO dto = new QnaDTO();

			// userId는 세션에 저장된 정보
			dto.setMemId(info.getMemId());

			// 파라미터
			dto.setQnaSubject(req.getParameter("qnaSubject"));
			dto.setQnaHead(req.getParameter("qnaHead"));
			dto.setQnaContent(req.getParameter("qnaContent"));
			dto.setQnaSource(req.getParameter("qnaSource"));
			System.out.println(dto.getQnaSubject());
			dao.insertQna(dto);

			req.setAttribute("mode", "created");
			resp.sendRedirect(cp + "/qna/list.sst");
		} else if (uri.indexOf("article.sst") != -1) {
			// 글보기
			if (info == null) {

				resp.sendRedirect(cp + "/"); // 로그인 되지 않은경우 메인페이지로넘어감
				return;
			}
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
			String page = req.getParameter("page");
			String searchKey = req.getParameter("searchKey");
			String searchValue = req.getParameter("searchValue");
			if (searchKey == null) {
				searchKey = "qnaSubject";
				searchValue = "";
			}
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
			// 조회수 증가
			dao.QnaHitCount(qnaNum);
			// 게시물 가져오기
			QnaDTO dto = dao.readQna(qnaNum);

			if (dto == null) { // 게시물 없으면 다시 리스트로 감
				resp.sendRedirect(cp + "/qna/list.sst?page=" + page);
				return;
			}

			int linesu = dto.getQnaContent().split("\n").length;
			dto.setQnaContent(dto.getQnaContent().replaceAll("\n", "<br>"));

			// 이전글 다음글
			QnaDTO preReadDto = dao.preReadQna(qnaNum, searchKey, searchValue);
			QnaDTO nextReadDto = dao.nextReadQna(qnaNum, searchKey, searchValue);

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

			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
		} else if (uri.indexOf("update.sst") != -1) {
			// 수정 폼
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
			String page = req.getParameter("page");

			QnaDTO dto = dao.readQna(qnaNum);
			if (dto == null || !dto.getMemId().equals(info.getMemId())) {
				resp.sendRedirect(cp + "/qna/list.sst?page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			req.setAttribute("page", page);
			forward(req, resp, "/WEB-INF/views/qna/created.jsp");
		} else if (uri.indexOf("update_ok.sst") != -1) {
			String page = req.getParameter("page");

			QnaDTO dto = new QnaDTO();
			dto.setQnaNum(Integer.parseInt(req.getParameter("qnaNum")));
			dto.setQnaSubject(req.getParameter("qnaSubject"));
			dto.setQnaContent(req.getParameter("qnaContent"));

			dao.qnaUpdate(dto);

			resp.sendRedirect(cp + "/qna/article.sst?page=" + page + "&qnaNum=" + dto.getQnaNum());
		} else if (uri.indexOf("delete.sst") != -1) {
			String page = req.getParameter("page");
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));

			QnaDTO dto = dao.readQna(qnaNum);
			if (dto == null || (!dto.getMemId().equals(info.getMemId()) && !info.getMemId().equals("admin"))) {
				resp.sendRedirect(cp + "/qna/list.sst?page=" + page);
				return;
			}

			dao.deleteQna(qnaNum);
			resp.sendRedirect(cp + "/qna/list.sst?page=" + page);
		} else if (uri.indexOf("listReply.sst") != -1) {
			// 리플 리스트 ---------------------------------------
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
			String pageNo = req.getParameter("pageNo");// 댓글의 페이지번호
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);

			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;

			dataCount = dao.dataCountQnaReply(qnaNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;

			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;

			// 리스트에 출력할 댓글 데이터
			List<QnaReplyDTO> list = dao.listQnaReply(qnaNum, start, end);

			// 엔터를 <br>
			Iterator<QnaReplyDTO> it = list.iterator();
			while (it.hasNext()) {
				QnaReplyDTO dto = it.next();
				dto.setQnaR_content(dto.getQnaR_content().replaceAll("\n", "<br>"));
			}

			// 페이징처리(인수2개 짜리 js로 처리)
			String paging = util.paging(current_page, total_page);

			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);

			// 포워딩
			String path = "/WEB-INF/views/qna/listReply.jsp";
			forward(req, resp, path);
		} else if (uri.indexOf("insertReply.sst") != -1) {
			// 리플 저장하기 ------
			String state = "true";
			if (info == null) { // 로그인 되지 않은 경우
				state = "loginFail";
			} else {
				int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
				QnaReplyDTO dto = new QnaReplyDTO();
				dto.setQnaNum(qnaNum);
				dto.setMemId(info.getMemId());
				dto.setQnaR_content(req.getParameter("qnaR_content"));

				int result = dao.insertQuestionReply(dto);
				if (result == 0)
					state = "false";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"state\":" + "\"" + state + "\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		} else if (uri.indexOf("deleteReply.sst") != -1) {
			// 리플 삭제-----------------------
			int qnaR_num = Integer.parseInt(req.getParameter("qnaR_num"));
			String memId = req.getParameter("memId");

			String state = "false";
			if (info == null) { // 로그인 되지 않은 경우
				state = "loginFail";
			} else if (info.getMemId().equals("admin") || info.getMemId().equals(memId)) {
				dao.deleteQuestionReply(qnaR_num);
				state = "true";

			}
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"state\":" + "\"" + state + "\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		} else if (uri.indexOf("listAnswer.sst") != -1) {
			int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
			String pageNo = req.getParameter("pageNo");
			int current_page = 1;
			if (pageNo != null)
				current_page = Integer.parseInt(pageNo);

			int numPerPage = 5;
			int total_page = 0;
			int dataCount = 0;

			dataCount = dao.dataCountAnswer(qnaNum);
			total_page = util.pageCount(numPerPage, dataCount);
			if (current_page > total_page)
				current_page = total_page;

			int start = (current_page - 1) * numPerPage + 1;
			int end = current_page * numPerPage;


			List<AnswerDTO> list = dao.listAnswer(qnaNum, start, end);

			Iterator<AnswerDTO> it = list.iterator();
			while (it.hasNext()) {
				AnswerDTO dto = it.next();
				dto.setA_content(dto.getA_content().replaceAll("\n", "<br>"));
			}

			String paging = util.paging(current_page, total_page);

			req.setAttribute("list", list);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);

			String path = "/WEB-INF/views/qna/listAnswer.jsp";
			forward(req, resp, path);
		} else if (uri.indexOf("insertAnswer.sst") != -1) {
			String state = "true";
			if (info == null) { 
				state = "loginFail";
			} else {
				int qnaNum = Integer.parseInt(req.getParameter("qnaNum"));
				AnswerDTO dto = new AnswerDTO();
				dto.setQnaNum(qnaNum);
				dto.setMemId(info.getMemId());
				dto.setA_content(req.getParameter("a_content"));

				int result = dao.insertAnswer(dto);
				if (result == 0)
					state = "false";
			}
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"state\":" + "\"" + state + "\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		} else if (uri.indexOf("deleteAnswer.sst") != -1) {
			int a_num = Integer.parseInt(req.getParameter("a_num"));
			String memId = req.getParameter("memId");

			String state = "false";
			if (info == null) { // 로그인 되지 않은 경우
				state = "loginFail";
			} else if (info.getMemId().equals("admin") || info.getMemId().equals(memId)) {
				dao.deleteAnswer(a_num);
				state = "true";

			}
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"state\":" + "\"" + state + "\"");
			sb.append("}");

			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.println(sb.toString());
		} else if (uri.indexOf("selectAnswer.sst") != -1) {
			int a_Num = Integer.parseInt(req.getParameter("a_num"));
				String memId = req.getParameter("memId");

				String state = "false";
				if (info == null) { // 로그인 되지 않은 경우
					state = "loginFail";
				} else if (info.getMemId().equals("admin") || info.getMemId().equals(memId)) {
					dao.selectAnswer(a_Num);
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
