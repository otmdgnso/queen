<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	request.setCharacterEncoding("utf-8");
	String cp=request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">

<head>
   <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    
<title>  </title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=cp%>/res/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    
<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>

</head>
<body>

<div class="layoutMain">
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
	
	<div class="layoutBody">

		<div style="min-height: 100%">
				<div style="margin-top:150px; height: 40px;  line-height:40px; ">
				   <div style="margin:0 auto; width: 600px; border-top: 2px solid #FFCC66 ; border-bottom: 2px solid #FFCC66; ">
				    <div style="width:600px; height:30px; line-height:30px; margin:0 auto; 
				    	margin-top:10px; margin-bottom:30px;">
				        
				        <span style="margin-left:43%; font-weight: bold;font-size:18pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">
				        	${title}</span>
				    </div>
					</div>
				</div>
				
				<div style="margin: 30px auto; width: 400px; min-height: 400px;">
					
					<div style="margin: 0px auto; padding:10px; min-height: 50px; line-height: 130%;  text-align: center;">
						${message}
					</div>
					<div style="height: 50px; text-align: center; margin-bottom:30px;">
					   <button type="button" onclick="javascript:location.href='<%=cp%>/';" class="moveButton"><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span> 메인화면으로 이동 </button>
				<%-- 	    <input type="button" value=" 메인화면으로 이동 "
					              class="moveButton"
					              onclick="javascript:location.href='<%=cp%>/';"> --%>
					</div>  
				</div>
		</div>

    </div>
<!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12"  style="margin-left: 50px;">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
        </footer>
	
    </div>
    <!-- /.container -->
  <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>
</html>