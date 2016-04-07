<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("utf-8");
	String cp=request.getContextPath();
%>

<link rel="stylesheet" href="<%=cp%>/res/css/login.css" type="text/css"/>

<script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script> 

<script type="text/javascript">


function bgLabel(ob, id) {
	    if(!ob.value) {
		    document.getElementById(id).style.display="";
	    } else {
		    document.getElementById(id).style.display="none";
	    }
}

function sendLogin() {
	
        var f = document.loginForm;

    	var str = f.userId.value;
        if(!str) {
            f.userId.focus();
            return false;
        }

        str = f.userPwd.value;
        
        if(!str) {
            f.userPwd.focus();
            return false;
        }

        f.action ="<%=cp%>/member/login_ok.do";
       
        return true;
  }

</script>

<!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
       <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="<%=cp%>/main.sst">Queen</a>
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="about.jsp">About</a>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">과정별 <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="full-width.jsp">수업자료</a>
                            </li>
                            <li>
                                <a href="sidebar.jsp">주절주절</a>
                            </li>
                            <li>
                                <a href="lIst.jsp">포트폴리오</a>
                            </li>                                                     
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">취업정보 <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="<%=cp%>/recruit/recruit.sst">취업달력</a>
                            </li>
                            <li>
                                <a href="portfolio-2-col.jsp">자소서 공유 </a>
                            </li>
                            <li>
                                <a href="portfolio-3-col.jsp">회사정보</a>
                            </li>
                            <li>
                                <a href="<%=cp%>/bbs/list.sst">취업정보나눔 </a>
                            </li>
                            <li>
                                <a href="portfolio-item.jsp">팀원구하기 </a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">IT정보<b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="blog-home-1.jsp">IT동향</a>
                            </li>
                            <li>
                                <a href="blog-home-2.jsp">개발Tip </a>
                            </li>
                            <li>
                                <a href="blog-post.jsp">개발Q&nbsp;&#38;&nbsp;A</a>
                            </li>
                        </ul>
                    </li>
                    <!-- 로그인/ 혹은 000님 -->
                     <li class="dropdown">
                    
	                    <c:if test="${empty sessionScope.member}">
	             			   <button  style="margin-top:10px; border-radius:4px;" type="button" class="btn btn-default btn-sm btn-block" 
	                        			data-toggle="modal" data-target="#login-modal" >
							 로그인
							</button>
	            		</c:if>
	            		
	          			<c:if test="${not empty sessionScope.member}">
	                		
	                		<c:if test="${sessionScope.member.memId=='admin'}">
		                		<a href="#" style="color:white; font-weight:bold;" class="dropdown-toggle" data-toggle="dropdown">관리자</a>
	                			
	                		</c:if>
	                		<c:if test="${sessionScope.member.memId!='admin' }" >
	                		  <a href="#" style="color:white; font-weight:bold;" class="dropdown-toggle" data-toggle="dropdown">${sessionScope.member.memName}</a>
	                			             		 
	                		</c:if>
	            		</c:if>
                      
                    </li>
                    <!-- 로그아웃 / 혹은 회원가입 -->
                    <li >
						<c:if test="${empty sessionScope.member}">  
					   		<a href="<%=cp%>/member/member.do" >회원가입</a>
					   </c:if>
                 
                      <c:if test="${not empty sessionScope.member}">
                        <a href="<%=cp%>/member/logout.do"> 로그아웃</a>
                      </c:if>
                   
                   </li>
                   
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
    
    	
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
    	<div class="modal-dialog">
			<div class="modal-content" style="border-radius:0px; padding:5px; padding-left:30px; 
					padding-right:30px;">
				<div style=""class="modal-header" align="center">
					<img class="img-circle" id="img_logo" src="<%=cp%>/res/image/사자.png">
					
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
				<!-- 	<h4 style="color:gray; "> 사자의 심장을 가져라 ! </h4> -->
				</div>
                
                <!-- Begin # DIV Form -->
                <div id="div-forms">
                
                    <!-- Begin # Login Form -->
                    <form id="login-form" name="loginForm" method="post" onsubmit="return sendLogin();">
		                <div class="modal-body">
				    		<div id="div-login-msg">
				    		
                             <div id="icon-login-msg" class="glyphicon glyphicon-chevron-right"></div>
                             	
                                <span id="text-login-msg" style="font-size:13px;"> 아이디와 비밀번호를 입력해주세요  </span>
                            </div>
				    		<input name="userId" id="login_username" class="form-control" type="text" placeholder="ID" required>
				    		<input name="userPwd" id="login_password" class="form-control" type="password" placeholder="Password" required>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Remember me
                                </label>
                            </div>
        		    	</div>
				        <div class="modal-footer" style=" border: none; margin-top:-8px;">
                            <div>
                                <button style="font-size:16px; margin-top:0px; border-radius:0px; "
                                type="submit" class="btn btn-warning btn-lg btn-block">로그인</button>
                            </div>
				    	    
				        </div>
                    </form>
                    <!-- End # Login Form -->
                    
                  
                </div>
                <!-- End # DIV Form -->
                
			</div>
		</div>
	</div>
