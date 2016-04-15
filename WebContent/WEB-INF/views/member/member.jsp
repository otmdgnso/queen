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
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>사자의 심장을 가져라 </title>

<script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>

<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>


<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>


<link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

<script type="text/javascript">

function check() {
	var f = document.memberForm;
	var str;

	str=f.memId.value;
	if(!/^[a-z][a-z0-9_]{4,9}$/i.test(str)) { 
		$("#memId + .help-block").html("<span style='color:red;'>아이디를 확인해주세요! <span>");
		f.memId.focus();
		return false;
	}else {
		$("#memId + .help-block").html("아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.");
	}
	
	str = f.memPwd.value;
	if(!/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str)) { 
		$("#memPwd + .help-block").html("<span style='color:red;'>비밀번호 형식을 확인해주세요! <span>");
		f.memPwd.focus();
		return false;
	}else {
		$("#memPwd + .help-block").html("패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.");
	}
	
	if(f.memPwdCheck.value != str) {
		$("#memPwdCheck + .help-block").html("<span style='color:red;'>비밀번호가 일치하지 않습니다! <span>");
		f.memPwdCheck.focus();
		return false;
	} else {
		$("#memPwdCheck + .help-block").html("비밀번호를 한번 더 입력해주세요");
	}
	
    str = f.memName.value;
	str = $.trim(str);

    if(!/^[\uac00-\ud7a3]{2,4}$/g.test(str)) {
    	$("#memName + .help-block").html("<span style='color:red;'>이름을 확인해주세요! <span>");
        f.memName.focus();
        return false;
    } 
    else {
		$("#memName + .help-block").html("이름은 한글로 2자이상 4자 이하입니다.");
	}   
    
    str = f.selectCourse.value;
            
    if(!str) {
    	$("#course + .help-block").html("<span style='color:red;'>과정을 선택해주세요!<span>");
        f.selectCourse.focus();
        return false;
    }else {
		$("#course + .help-block").html("");
	}
    
    str = f.kisu.value;
    if(!str) {
    	$("#course + .help-block").html("<span style='color:red;'>기수를 기입하세요!<span>");
        f.kisu.focus();
        return false;
    }else if(!/^[0-9]{2}$/g.test(str)) {
		$("#course + .help-block").html("기수는 숫자만 입력가능합니다");
	}
        
    str = f.birth.value;
    if(!isValidDateFormat(str)) {
    	$("#birth + .help-block").html("<span style='color:red;'>생일 형식을 확인해주세요!<span>");
        f.birth.focus();
        return false;
    } else {
		$("#birth + .help-block").html("  생년월일은 2000-01-01 형식으로 입력 합니다.");
	}
  
    str = f.email2.value;
    if(!/@[0-9a-zA-Z]([0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i.test(str)) {
    	$("#email + .help-block").html("<span style='color:red;'> 이메일 형식을 확인해주세요!<span>");
        f.email2.focus();
        return false;
    }else {
		$("#email + .help-block").html("");
	}
    
    str = f.tel1.value;
    if(!str) {
    	$("#tel3 + .help-block").html("<span style='color:red;'>전화번호를 확인해주세요!<span>");
        f.tel1.focus();
        return false;
    }else {
		$("#tel3 + .help-block").html("");
	}

    str = f.tel2.value;
    if(!/^[0-9]{4}$/g.test(str)) {
    	$("#tel3 + .help-block").html("<span style='color:red;'>전화번호를 확인해주세요!<span>");
        f.tel2.focus();
        return false;
    }else {
		$("#tel3 + .help-block").html("");
	}
    
    str = f.tel3.value;
    if(!/^[0-9]{4}$/g.test(str)) {
    	$("#tel3 + .help-block").html("<span style='color:red;'>전화번호를 확인해주세요!<span>");
        f.tel3.focus();
        return false;
    }else {
		$("#tel3 + .help-block").html("");
	}
    
    var failed="${failed}";
	if(failed=="true"){
		alert("회원가입에 실패했습니다!");
	}
	
    var mode="${mode}"
    if(mode=="created") {
       	f.action = "<%=cp%>/member/member_ok.sst";
    } else if(mode=="update") {
    	f.action = "<%=cp%>/member/update_ok.sst";
    }

    return true;
}
</script>
<script>

function changeEmail() {
    var f = document.memberForm;
    
 	var str = f.selectEmail.value;
    if(str!="direct") {
         f.email2.value=str; 
         f.email2.readOnly = true;
         f.email1.focus(); 
    }
    else {
        f.email2.value="@";
        f.email2.readOnly = false;
        f.email1.focus();
    }
}
</script>

</head>
<body>
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
<br><br>
<div class="container" role="main" style="margin-top:50px;">
  <div class="jumbotron">
    <h1><span class="glyphicon glyphicon-user" style="color:gray; margin-right:10px;" ></span> ${mode=="created"?"회원 가입":"회원정보 수정"} </h1>
    <p> ${mode=="created"?"사자의 심장을 가져라! 쌍용 수강생만 가입 가능합니다 ":"사자의 심장을 가져라! 아이디와 이름을 제외한 정보 수정이 가능합니다"}</p>
  </div>

  <div class="bodyFrame">
  <form class="form-horizontal" name="memberForm" method="post" onsubmit="return check();">
    <div class="form-group" style="margin-bottom:0px;">
        <label class="col-sm-2 control-label" for="memId">아이디</label>
        <div class="col-sm-7">
            <input style="width:200px;"class="form-control" id="memId" name="memId" type="text" 
            		placeholder="아이디"  value="${dto.memId}"
              ${mode=="update" ? "readonly='readonly' style='border:none;'":""}>
            <p class="help-block"> 아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.</p>
        </div>
    </div>
 
    <div class="form-group" style="margin-bottom:0px;">
        <label class="col-sm-2 control-label" for="memPwd">패스워드</label>
        <div class="col-sm-7">
            <input style="width:200px;  " class="form-control" id="memPwd" name="memPwd" type="password" placeholder="비밀번호">
            <p class="help-block">패스워드는 5~10자이며 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</p>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-2 control-label" for="memPwdCheck">패스워드 확인</label>
        <div class="col-sm-7">
            <input style="width:200px; "class="form-control" id="memPwdCheck" name="memPwdCheck" type="password" placeholder="비밀번호 확인">
            <p class="help-block">패스워드를 한번 더 입력해주세요.</p>
        </div>
    </div>
 
    <div class="form-group">
        <label class="col-sm-2 control-label" for="memName">이름</label>
        <div class="col-sm-7">
            <input style="width:200px; " class="form-control" id="memName" name="memName" 
		            type="text" placeholder="이름"   value="${dto.memName}" ${mode=="update" ? "readonly='readonly'
		            style='border:none;' ":""}>
        <p class="help-block">이름은 한글로 2자이상 4자 이하입니다.</p>
        </div>
        
    </div>
    
 	<div class="form-group">
        <label class="col-sm-2 control-label" for="course">과정</label>
         <div class="col-sm-10" style="margin-top:0px;">
         
           <select name="selectCourse" id="course" class="form-control" 
           		style="width:320px; float: left; margin-right:0px; clear:both; margin:0px; padding:0px;"  >
           			
				<option value="">선 택</option>
				<option value="빅데이터 개발자 과정" ${dto.course=="빅데이터 개발자 과정" ? "selected='selected'" : ""}>
						Framework를 활용한 빅데이터 개발자 과정 </option>
				<option value="웹 플랫폼 표준능력 과정" ${dto.course=="웹 플랫폼 표준능력 과정" ? "selected='selected'" : ""}>
						웹 플랫폼 표준능력 개발자 양성과정 </option>
				<option value="애플리케이션 과정" ${dto.course=="애플리케이션 과정" ? "selected='selected'" : ""}>
						애플리케이션 성능 최적화 구현 개발자과정 </option>
				<option value="웹기획 개발자 과정" ${dto.course=="웹기획 개발자 과정" ? "selected='selected'" : ""}>
						쌍용 웹기획 개발자 과정 </option>
			</select>
			
			<label class="col-sm-2 control-label" for="kisu">기수</label>
			<input style="width:100px; float:left; margin-bottom:8px;" class="form-control" id="course" name="kisu" 
		            type="text" placeholder="기수입력" maxlength="2" value="${dto.kisu}" >
		  	
        		<p style="clear:both; " class="help-block">자신이 수강 중인 과정과 기수를 기입하세요. </p>
        	  
         </div>
         
        </div>
        
   
    
    <div style=""class="form-group">
        <label class="col-sm-2 control-label" for="birth">생년월일</label>
        <div class="col-sm-7">
            <input style="width:200px; " class="form-control" id="birth" name="birth" type="text" placeholder="생년월일" value="${dto.birth}">
            <p class="help-block">생년월일은 2000-01-01 형식으로 입력 합니다.</p>
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label" for="email">이메일</label>
        <div class="col-sm-10" style="margin-top:0px;">
           <select name="selectEmail" onchange="changeEmail();" class="form-control" style="width:130px; float: left; margin-right:10px; margin:0px; padding:0px;" >
											<option value="">선 택</option>
											<option value="@naver.com" ${dto.email2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
											<option value="@hanmail.net" ${dto.email2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
											<option value="@hotmail.com" ${dto.email2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
											<option value="@gmail.com" ${dto.email2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
											<option value="direct">직접입력</option>
		</select>
		 <input style="width:150px; float:left; margin-right:10px;" type="text" name="email1" size="13" 
		 		maxlength="30" id="email"  class="form-control" value="${dto.email1}">
			
		<input style="width:150px;  float:left; margin-right:10px; " type="text" name="email2" size="13"
				 maxlength="30" id="email"  class="form-control" value="@${dto.email2}" readonly="readonly">
         <p class="help-block"> </p>
         </div>
    </div>
    
    <div class="form-group" >
        <label class="col-sm-2 control-label" for="tel1">전화번호</label>
        <div class="col-sm-7">
             <div class="row" >
                  <div class="col-sm-3" style="padding-right: 5px;">
						  <select class="form-control" style="width:130px; float: left; margin:0px; padding:0px;"id="tel1" name="tel1" >
								<option value="">선 택</option>
								<option value="010" ${dto.tel1=="010" ? "selected='selected'" : ""}>010</option>
								<option value="011" ${dto.tel1=="011" ? "selected='selected'" : ""}>011</option>
								<option value="016" ${dto.tel1=="016" ? "selected='selected'" : ""}>016</option>
								<option value="017" ${dto.tel1=="017" ? "selected='selected'" : ""}>017</option>
								<option value="018" ${dto.tel1=="018" ? "selected='selected'" : ""}>018</option>
								<option value="019" ${dto.tel1=="019" ? "selected='selected'" : ""}>019</option>
						  </select>
                  </div>

                  <div class="col-sm-1" style="width: 1%; padding-left: 5px; padding-right: 10px;">
                         <p class="form-control-static">-</p>
                  </div>
                 <div class="col-sm-2" style="padding-left: 5px; padding-right: 5px;">
 						  <input class="form-control" id="tel2" name="tel2" type="text" value="${dto.tel2}" maxlength="4">
                  </div>
                  <div class="col-sm-1" style="width: 1%; padding-left: 5px; padding-right: 10px;">
                         <p class="form-control-static">-</p>
                  </div>
                  <div class="col-sm-6" style="padding-left: 5px; padding-right: 5px;">
						  <input style="width:100px;float:left; margin-right:10px; " class="form-control" id="tel3" 
						  		name="tel3" type="text" value="${dto.tel3}" maxlength="4">
                 		  <span style="float:left; width:170px;" class="help-block" > </span>
                 
                  </div>
             </div>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-2 control-label" for="zip">우편번호</label>
        <div class="col-sm-7">
             <div class="row">
                  <div class="col-sm-3" style="padding-right: 0px;">
						  <input class="form-control" id="zip" name="zip" type="text" value="${dto.zip}" maxlength="7" readonly="readonly">
                  </div>
                  <div class="col-sm-1" style="width: 1%; padding-left: 5px; padding-right: 5px;">
                         <button type="button" class="btn btn-default">우편번호</button>
                  </div>
             </div>
        </div>
    </div>
    
    <div class="form-group">
        <label class="col-sm-2 control-label" for="addr1">주소</label>
        <div class="col-sm-7">
            <input class="form-control" id="addr1" name="addr1" type="text" placeholder="기본주소" readonly="readonly" value="${dto.addr1}">
            <input class="form-control" id="addr2" name="addr2" type="text" placeholder="나머지주소" value="${dto.addr2}" style="margin-top: 5px;">
        </div>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label" for="job">직업</label>
        <div class="col-sm-2">
            <input class="form-control" id="job" name="job" type="text" placeholder="직업" value="${dto.job}">
        </div>
    </div>
    


    <div class="form-group">
        <label class="col-sm-2 control-label" for="agree">약관 동의</label>
        <div class="col-sm-7 checkbox">
            <label>
                <input id="agree" name="agree" type="checkbox" checked="checked"
                         onchange="form.sendButton.disabled = !checked"> <a href="#">이용약관</a>에 동의합니다.
            </label>
        </div>
    </div>

    
<div class="form-group">
  <div class="col-sm-offset-2 col-sm-10">
		<c:if test="${mode=='created'}">
            <button type="submit" name="sendButton" class="btn btn-info btn-sm btn-search" style="margin-right:20px; height:40px; width:130px;">
            		회원가입<span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default btn-sm wbtn" onclick="javascript:location.href='<%=cp%>/';" style="margin-right:20px; height:40px; width:130px;">
            		가입취소 <span class="glyphicon glyphicon-remove"></span></button>
	 	</c:if>
	<c:if test="${mode=='update'}">
            <button type="submit" class="btn btn-info btn-sm btn-search" style="margin-right:20px; height:40px; width:130px;" >
            		정보수정 <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default btn-sm wbtn" onclick="javascript:location.href='<%=cp%>/';" style="margin-right:20px; height:40px; width:130px;">
            		수정취소 <span class="glyphicon glyphicon-remove"></span></button>
	</c:if>            
    </div>
</div>

  <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
                <p style=" text-align:center; color:tomato; font-weight:bold; font-size:13px; "class="form-control-static">${message}</p>
        </div>
    </div>  
     
  </form>
  </div>
 </div>

 <div class="container">
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

<script type="text/javascript" src="<%=cp%>/res/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>
</html>