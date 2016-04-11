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
 <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
<title>사자의 심장을 가져롸 </title>
 <!-- Bootstrap Core CSS -->
    <link href="<%=cp%>/res/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>
<style type="text/css">
.form-signin {
  max-width: 400px;
  padding: 15px;
  margin: 0 auto;
}

@media (min-width: 768px) {
  .form-signin {
    padding-top: 70px;
  }
}

.form-signin-heading {
  text-align: center;
  font-weight:bold;  
  font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", sans-serif;
  margin-bottom: 30px;
}

.lbl {
   position:absolute; 
   margin-left:15px; margin-top: 13px;
   color: #999999;
   font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
}

.loginTF {
  max-width: 370px; height:45px;
  padding: 5px;
  padding-left: 15px;
  margin-top:5px; margin-bottom:15px;
}
</style>

<script type="text/javascript">

function bgLabel(ob, id) {
	    if(!ob.value) {
		    document.getElementById(id).style.display="";
	    } else {
		    document.getElementById(id).style.display="none";
	    }
}

//양희의 에러노트: navigation에서의 폼이름과 함수이름이 같아서 계속 뭔지도 모르고 에러가 났던 것이었다..!
function sendLogin2() {
	
        var f = document.loginForm2;

    	var str = f.memId.value;
        if(!str) {
            f.memId.focus();
            return false;
        }

        str = f.memPwd.value;
        
        if(!str) {
            f.memPwd.focus();
            return false;
        }

        f.action ="<%=cp%>/member/login_ok.sst";

        return true;
  }
</script>
</head>
<body>

	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
	

<div class="container" role="main" style="min-height:80%">

    <div class="bodyFrame">
    <form class="form-signin" name="loginForm2" method="post"  onsubmit="return sendLogin2();">
        <h2 class="form-signin-heading">Log In</h2>
        
        <label for="memId" id="lblUserId" class="lbl">아이디</label>
        <input type="text" id="memId" name="memId" class="form-control loginTF" autofocus="autofocus"
                  onfocus="document.getElementById('lblUserId').style.display='none';"
	              onblur="bgLabel(this, 'lblUserId');">
	              
        <label for="memPwd" id="lblUserPwd" class="lbl">패스워드</label>
        <input type="password" id="memPwd" name="memPwd" class="form-control loginTF"
                  onfocus="document.getElementById('lblUserPwd').style.display='none';"
	              onblur="bgLabel(this, 'lblUserPwd');">
        
        <button class="btn btn-lg btn-primary btn-block" type="submit" >로그인 
        	<span class="glyphicon glyphicon-ok"></span></button>
        
       
      	<div style=" margin-left:57px;margin-top:20px; width:370px;">
              <span style=" text-align:center; color:tomato; font-weight:bold; font-size:13px; " class="form-control-static">
              ${message}</span>
         </div>
         
        <div style="margin-top:10px; text-align: center;">
            <button type="button" class="btn btn-link" onclick="location.href='<%=cp%>/member/member.do';">
            		아직 회원이 아니신가요?
            </button>
        </div>
    </form>
    </div>
</div>
<!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12" style="margin-left: 50px;">
                    <p>Copyright &copy; Your Website 2014</p>
                </div>
            </div>
        </footer>

 <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
   
</body>
</html>