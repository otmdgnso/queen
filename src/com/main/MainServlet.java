package com.main;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/main.sst")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		MainDAO dao = new MainDAO();
		
		if(uri.indexOf("main.sst")!=-1) {
			List<MainDTO> listShare, listPfo, listWanted;
			
			listShare=dao.mainShare();
			listPfo=dao.mainPortfolio();
			listWanted=dao.mainWanted();
			
			Iterator<MainDTO> itShare= listShare.iterator();
			Iterator<MainDTO> itPfo= listPfo.iterator();
			Iterator<MainDTO> itWanted=listWanted.iterator();
			
			while(itShare.hasNext()){
				MainDTO dtoShare = itShare.next();
			}
			
			while(itPfo.hasNext()){
				MainDTO dtoPfo = itPfo.next();
			}
			
			while(itWanted.hasNext()){
				MainDTO dtoWanted=itWanted.next();
			}
			
			req.setAttribute("listShare", listShare);
			req.setAttribute("listPfo", listPfo);
			req.setAttribute("listWanted", listWanted);
			
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
		}
	}

}
