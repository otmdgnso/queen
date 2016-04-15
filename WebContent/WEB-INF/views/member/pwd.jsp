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

<title>사자의 심장을 가져라 </title>
<!-- Bootstrap Core CSS -->
    <link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    
<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>


 <style type="text/css">
.form-signin {
  max-width: 440px;
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

.boxLayout {
    max-width:420px;
    padding:20px;
    border: 1px solid #DAD9FF;
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

function sendOk(){
	
        var f = document.confirmForm;
		
    	var str = f.memId.value;
    	
    	if(!str) {
            f.memId.focus();
            alert("아이디를 입력하세요");
            return false;
        }

        str = f.memPwd.value;
        
        if(!str) {
            f.memPwd.focus();
            return false;
        }

        f.action ="<%=cp%>/member/pwd_ok.sst";
       	f.submit();
  }
</script>

</head>
<body>

	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>

<div class="container" role="main" >
    <div class="bodyFrame" style="min-height:850px;">

    <form class="form-signin" name="confirmForm" method="post">
       
        <h2 class="form-signin-heading">패스워드 재확인</h2>
       
        <div class="boxLayout">
        
            <div style="text-align: left; padding-bottom: 10px; ">정보보호를 위해 패스워드를 다시 한 번 입력해주세요</div>
            
            <input type="text" id="memId" name="memId" class="form-control loginTF"
	              value="${sessionScope.member.memId}"
                  readonly="readonly"
	              >
            <label for="memPwd" id="lblUserPwd" class="lbl">패스워드</label>
            <input type="password" id="memPwd" name="memPwd" class="form-control loginTF" autofocus="autofocus"
                  onfocus="document.getElementById('lblUserPwd').style.display='none';"
	              onblur="bgLabel(this, 'lblUserPwd');">
	              
            <button class="btn btn-lg btn-primary btn-block" type="button" onclick="sendOk();">확인 <span class="glyphicon glyphicon-ok"></span></button>
            <input type="hidden" name="mode" value="${mode}">
            
        </div>
        
        <div style="margin-top:15px; text-align: center;">${message}</div>
        
    </form>
    </div>
    

<!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12"  style="margin-left: 50px;">
                    <p>Copyright &copy; SIST Comm 2016</p>
                </div>
            </div>
        </footer>
	</div>
	<script src="<%=cp%>/res/js/jquery.js"></script>

    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
<<%-- script type="text/javascript" src="<%=cp%>/res/jquery/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/jquery/js/jquery.ui.datepicker-ko.js"></script> --%>
</body>
</html>