package com.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyServlet;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		String cp=req.getContextPath();
		
		MemberDAO dao=new MemberDAO();
		
		// 세션객체. 세션 정보는 서버에 저장(로그인정보, 권한등을저장)
		HttpSession session=req.getSession();
		
		if(uri.indexOf("member.do")!=-1) {
			// 회원가입폼
			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			String path="/WEB-INF/views/member/member.jsp";
			forward(req, resp, path);
			
		} else if(uri.indexOf("member_ok.do")!=-1) {
			// 회원가입 처리
			MemberDTO dto = new MemberDTO();
		
			dto.setMemId(req.getParameter("userId"));
			dto.setMemPwd(req.getParameter("userPwd"));
			dto.setMemName(req.getParameter("userName"));
			
			String course = req.getParameter("selectCourse");
			String kisu= req.getParameter("kisu");
			if(course!=null && kisu!=null && course.length() != 0 && kisu.length() != 0 )
				dto.setMemCourse(course+"_"+kisu+"기_"+dto.getMemName());
			
			dto.setBirth(req.getParameter("birth"));
			
			String email1 = req.getParameter("email1");
			String email2 = req.getParameter("email2");
			if (email1 != null && email1.length() != 0 && email2 != null && email2.length() != 0 ) {
				dto.setEmail(email1 + email2);
			}
			String tel1 = req.getParameter("tel1");
			String tel2 = req.getParameter("tel2");
			String tel3 = req.getParameter("tel3");
			if (tel1 != null && tel1.length() != 0 && tel2 != null
					&& tel2.length() != 0 && tel3 != null && tel3.length() != 0) {
				dto.setTel(tel1 + "-" + tel2 + "-" + tel3);
			}
			
			dto.setJob(req.getParameter("job"));
			//주소는 일단 이렇게 
			dto.setAddr2(req.getParameter("addr2"));
			
			//////////////////////////////////////////////////INSERT MEMBER 멤버추가 
			int result = dao.insertMember(dto);
			
			if (result != 1) {
				String message = "회원 가입에 실패했어요 ㅠ..ㅠ;; ";
				
				req.setAttribute("title", "회원 가입");
				req.setAttribute("mode", "created");
				req.setAttribute("message", message);
				
				req.setAttribute("failed", "true");
				req.setAttribute("message", message);
				
				forward(req, resp, "/WEB-INF/views/member/member.jsp");
				return;
			}
			System.out.println("회원가입 완료 !!!!!!");
			StringBuffer sb=new StringBuffer();
			sb.append("<b>"+dto.getMemName()+ " </b>님 회원가입이 되었습니다.<br>");
			sb.append("메인화면으로 이동하여 로그인 하시기 바랍니다.<br>");
			
			req.setAttribute("title", "회원 가입");
			req.setAttribute("message", sb.toString());
			
			forward(req, resp, "/WEB-INF/views/member/complete.jsp");
			
		}
		//dao. readMember 필요 
		else if(uri.indexOf("login_ok.do")!=-1) {
	
			// 로그인 처리
			String memId=req.getParameter("userId");
			String memPwd=req.getParameter("userPwd");
			
			MemberDTO dto=dao.readMember(memId);
			
			
			// 로그인 성공 : 로그인정보를 서버에 저장
			if(dto!=null) {
				if(memPwd.equals(dto.getMemPwd()) && dto.getEnabled()==1) {

					// 세션의 유지시간을 20분설정(기본 30분)
					session.setMaxInactiveInterval(20*60);
					
					// 세션에 저장할 내용
					SessionInfo info=new SessionInfo();
					
					info.setMemId(dto.getMemId());
					info.setMemName(dto.getMemName());
					
					// 세션에 member이라는 이름으로 저장
					session.setAttribute("member", info);
					System.out.println("성공로그인  ");
					// 메인화면으로 리다이렉트
					resp.sendRedirect(cp);
					return;
				} 
			}
			
			// 로그인 실패인 경우(다시 로그인 폼으로)
			else {				
				String msg="아이디 또는 패스워드가 일치하지 않습니다.";
				req.setAttribute("message", msg);
				
				String path="/WEB-INF/views/member/login.jsp";
				forward(req, resp, path);
			}
			
		} else if(uri.indexOf("logout.do")!=-1) {
			// 로그아웃
			// 세션에 저장된 정보를 지운다
			session.removeAttribute("member");
			
			// 세션에 저장된 모든 정보를 지우고 세션을 초기화 한다.
			session.invalidate();
			
			// 루트로 리다이렉트
			resp.sendRedirect(cp);
			
		} 
	}
}
