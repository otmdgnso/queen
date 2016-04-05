<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	request.setCharacterEncoding("utf-8");
	String cp=request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
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
                                <a href="portfolio-1-col.jsp">취업달력</a>
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
                                <a href="blog-post.jsp">개발Q&A</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="about.jsp">로그인</a>
                    </li>
                    <li>
                        <a href="about.jsp">회원가입</a>
                    </li>
                  
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>
</body>
</html>