package com.portfolio;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/portfolio/*")
public class portfolioServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		String cp = req.getContextPath();

		// 이미지를 저장할 경로(pathname)
		String root=getServletContext().getRealPath("/");
		String pathname = root+File.separator + "uploads" + File.separator + "portfolio";
		File f = new File(pathname);
		if (!f.exists()) { // 폴더가 존재하지 않으면
			f.mkdirs();
		}
		PortfolioDAO dao = new PortfolioDAO();
		MyUtil util = new MyUtil();

		// uri에 따른 작업 구분
		if (uri.indexOf("list.sst") != -1) {
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
				dto.setMemId(mreq.getParameter("MemId"));

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
		}else if(uri.indexOf("article.sst")!=-1) {
			int num=Integer.parseInt(req.getParameter("num"));
			String page=req.getParameter("page");
			
			PortfolioDTO dto=dao.readPortfolio(num);
			if(dto==null) {
				resp.sendRedirect(cp+"/portfolio/list.sst?page="+page);
				return;
			}
			
			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			
			String path="/WEB-INF/views/portfolio/article.jsp";
			forward(req, resp, path);
			
		} 

	}
}
