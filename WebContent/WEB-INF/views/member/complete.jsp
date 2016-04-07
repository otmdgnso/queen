<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
   String cp = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 완료 페이지 </title>


<script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script>

<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>

<link rel="stylesheet" href="<%=cp%>/res/css/bootstrap-theme.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>

<link rel="stylesheet" href="<%=cp%>/res/css/bootstrap.min.css" type="text/css"/>


</head>
<body>

<div class="layoutMain">
	<div class="layoutHeader">
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
	
	<div class="layoutBody">

		<div style="min-height: 90%">
				<div style="margin-top:150px; width:100%;	height: 40px;  line-height:40px;clear: both; border-top: 1px solid #DAD9FF;border-bottom: 1px solid #DAD9FF;">
				    <div style="width:600px; height:30px; line-height:30px; margin:5px auto;">
				        
				        <span style="text-align:center; font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">
				        	${title}</span>
				    </div>
				</div>
				
				<div style="margin: 30px auto; width: 400px; min-height: 400px;">
					<div style="margin: 0px auto; padding:10px; min-height: 50px; line-height: 130%;  text-align: center;">${message}</div>
					<div style="height: 50px; text-align: center;">
					    <input type="button" value=" 메인화면으로 이동 >> "
					              class="moveButton"
					              onclick="javascript:location.href='<%=cp%>/';">
					</div>  
				</div>
		</div>

    </div>
	<!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->
 <!-- /.container -->

    <!-- jQuery -->
    <script src="res/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="res/js/bootstrap.min.js"></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>
   
</body>
</html>