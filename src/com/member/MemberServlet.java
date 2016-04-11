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
		
		if(uri.indexOf("member.sst")!=-1) {
			// 회원가입폼
			req.setAttribute("title", "회원 가입");
			req.setAttribute("mode", "created");
			String path="/WEB-INF/views/member/member.jsp";
			forward(req, resp, path);
			
		} else if(uri.indexOf("member_ok.sst")!=-1) {
			// 회원가입 처리
			MemberDTO dto = new MemberDTO();
		
			dto.setMemId(req.getParameter("memId"));
			dto.setMemPwd(req.getParameter("memPwd"));
			dto.setMemName(req.getParameter("memName"));
			
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
				String message = "회원가입에 실패했어요! 다른 아이디를 기입해주세요  "; //나중에 중복 확인 
				
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
		//////////////////////////////////////////////////////////////////////////////////	로그인 ///////////////////
		//dao. readMember 필요 
		else if(uri.indexOf("login.sst")!=-1) {
			// 로그인 폼
			String path="/WEB-INF/views/member/login.jsp";
			forward(req, resp, path);
			
		}
		else if(uri.indexOf("login_ok.sst")!=-1) {
						
			// 로그인 처리
			String memId=req.getParameter("memId");
			String memPwd=req.getParameter("memPwd");
			
			MemberDTO dto=dao.readMember(memId);
			
			// 로그인 성공했다면 : 로그인정보를 서버에 저장
			if(dto!=null) {

				if(memPwd.equals(dto.getMemPwd()) && dto.getEnabled()==1) {

					System.out.println("login_ok이다 ");
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
						
				String msg="아이디 또는 패스워드가 일치하지 않습니다";
				req.setAttribute("message", msg);
				
				String path="/WEB-INF/views/member/login.jsp";
				forward(req, resp, path);
			
			
		} else if(uri.indexOf("logout.sst")!=-1) {
			// 로그아웃
			// 세션에 저장된 정보를 지운다
			session.removeAttribute("member");
			
			// 세션에 저장된 모든 정보를 지우고 세션을 초기화 한다.
			session.invalidate();
			
			// 루트로 리다이렉트
			resp.sendRedirect(cp);
			
		}
		///////////////////////////////////////////////////////////////////////// 정보수정 혹은 삭제 //////////////////////////
		else if(uri.indexOf("pwd.sst")!=-1) {
			// 패스워드 확인 폼
			SessionInfo info=(SessionInfo)session.getAttribute("member");
			System.out.println("pwd.sst이다");
			if(info==null) {
				// 로그아웃상태이면
				forward(req, resp, "/WEB-INF/views/member/login.jsp");
			}
			
			String mode=req.getParameter("mode");
			if(mode.equals("update"))
				
				req.setAttribute("title", "회원 정보 수정");
			else
				req.setAttribute("title", "회원 탈퇴");
			
			req.setAttribute("mode", mode);
			forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
			
		} 
		
		else if(uri.indexOf("pwd_ok.sst")!=-1) {
			// 패스워드 확인
			System.out.println("pwd_ok.sst이다");
			//////////////////info에 로그인한 사람의 정보를 가져온다 !
			
			//info에 session에 저장된 로그인정보를 넘김
			SessionInfo info=(SessionInfo)session.getAttribute("member");
			
			if(info==null) { //로그아웃 된 경우
				resp.sendRedirect(cp+"/member/login.sst");
				return;
			}
			
			// DB에서 해당 회원 정보 가져오기
			MemberDTO dto=dao.readMember(info.getMemId());
			
			if(dto==null) {
				session.invalidate();
				resp.sendRedirect(cp);
				return;
			}
			
			String memPwd=req.getParameter("memPwd");
			String mode=req.getParameter("mode");
			System.out.println("비밀번호를 저장함 ");
			
			if(! dto.getMemPwd().equals(memPwd)) {
				
				System.out.println("비밀번호가 같지 않은 경우  ");
				
				if(mode.equals("update"))
					req.setAttribute("title", "회원 정보 수정");
				else
					req.setAttribute("title", "회원 탈퇴");
				
				req.setAttribute("mode", mode);
				req.setAttribute("message", "<span style='text-align:center; color:tomato; font-weight:bold; font-size:13px;' class='form-control-static '>"
						+ "패스워드가 일치하지 않습니다 !</span>");
				
				forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
				
				return;
			}
			
			if(mode.equals("delete")) {
				// 회원탈퇴
				dao.deleteMember(info.getMemId());
				
				session.removeAttribute("member");
				session.invalidate();
				
				StringBuffer sb=new StringBuffer();
				sb.append("<b>"+dto.getMemName()+ "</b>님 회원탈퇴가 정상처리되었습니다.<br>");
				sb.append("그동안 이용해 주셔서 감사합니다.<br>");
				
				req.setAttribute("title", "회원 탈퇴");
				req.setAttribute("message", sb.toString());
				
				forward(req, resp, "/WEB-INF/views/member/complete.jsp");
				
				return;
			}
			
			// 회원정보수정
			if(dto.getTel()!=null) {
				String []s=dto.getTel().split("-");
				if(s.length==3) {
					dto.setTel1(s[0]);
					dto.setTel2(s[1]);
					dto.setTel3(s[2]);
				}
			}			
			
			if(dto.getMemCourse()!=null) {
				String []s=dto.getMemCourse().split("_");
				System.out.println(s);
				if(s.length==3) {
					dto.setCourse(s[0]);
					dto.setKisu(s[1]);
					
				}
			}
			
			// 회원수정폼으로 이동
			req.setAttribute("title", "회원 정보 수정");

			req.setAttribute("dto", dto);

			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			
		}
		 else if(uri.indexOf("update_ok.sst")!=-1) {
				// 회원정보 수정 완료
				SessionInfo info=(SessionInfo)session.getAttribute("member");
				if(info==null) { //로그아웃 된 경우
					resp.sendRedirect(cp+"/member/login.sst");
					return;
				}
				
				MemberDTO dto = new MemberDTO();

				dto.setMemId(req.getParameter("memId"));
				dto.setMemPwd(req.getParameter("memPwd"));
				dto.setMemName(req.getParameter("memName"));
				
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
				
				System.out.println("비밀번호: "+dto.getMemPwd());
				
				int return1= dao.updateMember(dto);
				System.out.println("결과:"+return1);
				
				StringBuffer sb=new StringBuffer();
				sb.append("<b>"+dto.getMemName()+ "</b>님 회원 정보가 수정 되었습니다.<br>");
				sb.append("메인화면으로 이동 하시기 바랍니다.<br>");
				
				req.setAttribute("title", "회원 정보 수정");
				req.setAttribute("message", sb.toString());
				
				forward(req, resp, "/WEB-INF/views/member/complete.jsp");
			}
		}
	
	
	}

