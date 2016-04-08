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
			List<MainDTO> listShare, listPfo, listWanted, listResume, listQuest, listTip, listCompany, listDocu, listTrend;
			
			listShare=dao.mainShare();
			listPfo=dao.mainPortfolio();
			listWanted=dao.mainWanted();
			listResume=dao.mainResume();
			listQuest=dao.mainQuest();
			listTip=dao.mainTip();
			listCompany=dao.mainCompany();
			listDocu=dao.mainDocu();
			listTrend=dao.mainTrend();
			
			Iterator<MainDTO> itShare= listShare.iterator();
			Iterator<MainDTO> itPfo= listPfo.iterator();
			Iterator<MainDTO> itWanted=listWanted.iterator();
			Iterator<MainDTO> itResume=listResume.iterator();
			Iterator<MainDTO> itQuest = listQuest.iterator();
			Iterator<MainDTO> itTip = listTip.iterator();
			Iterator<MainDTO> itCompany=listCompany.iterator();
			Iterator<MainDTO> itDocu=listDocu.iterator();
			Iterator<MainDTO> itTrend=listTrend.iterator();
			
			while(itShare.hasNext()){
				MainDTO dtoShare = itShare.next();
			}
			
			while(itPfo.hasNext()){
				MainDTO dtoPfo = itPfo.next();
			}
			
			while(itWanted.hasNext()){
				MainDTO dtoWanted=itWanted.next();
			}
			
			while(itResume.hasNext()){
				MainDTO dtoResume=itResume.next();
			}

			while(itQuest.hasNext()){
				MainDTO dtoQuest=itQuest.next();
			}
			
			while(itTip.hasNext()){
				MainDTO dtoTip = itTip.next();
			}
			
			while(itCompany.hasNext()){
				MainDTO dtoCompany=itCompany.next();
			}
			
			while(itDocu.hasNext()){
				MainDTO dtoDocu=itDocu.next();
			}
			
			while(itTrend.hasNext()){
				MainDTO dtoTrend=itTrend.next();
			}
			
			req.setAttribute("listShare", listShare);
			req.setAttribute("listPfo", listPfo);
			req.setAttribute("listWanted", listWanted);
			req.setAttribute("listResume", listResume);
			req.setAttribute("listQuest", listQuest);
			req.setAttribute("listTip", listTip);
			req.setAttribute("listCompany", listCompany);
			req.setAttribute("listDocu", listDocu);
			req.setAttribute("listTrend", listTrend);
			
			forward(req, resp, "/WEB-INF/views/main/main.jsp");
		}
	}

}
