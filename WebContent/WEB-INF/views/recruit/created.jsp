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

<title>일정 등록</title>

<script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>

<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/login.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/bootstrap-theme.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>

<link rel="stylesheet" href="<%=cp%>/res/css/bootstrap.min.css" type="text/css"/>


<script type="text/javascript">

function check() {
   var f = document.memberForm;
   var str;
   
   str = f.recruitCompany.value;
   if(!str) {
      $("#recruitCompany + .help-block").html("<span style='color:red;'>기엄명을 기입하세요!<span>");
       f.recruitCompany.focus();
       return false;
   }else {
      $("#recruitCompany + .help-block").html("");
   }
   
   str = f.recruitHead.value;
   if(!str) {
      $("#recruitHead + .help-block").html("<span style='color:red;'>채용 구분을 선택해주세요!<span>");
       f.recruitHead.focus();
       return false;
   }else {
     $("#recruitHead + .help-block").html("");
  }
   
   str = f.recruitStart.value;
   if(!isValidDateFormat(str)) {
      $("#recruitStart + .help-block").html("<span style='color:red;'>시작일 형식을 확인해주세요!<span>");
       f.recruitStart.focus();
       return false;
   } else {
     $("#recruitStart + .help-block").html("  시작일은 2000-01-01 형식으로 입력 합니다.");
   }
   
   str = f.recruitEnd.value;
   if(!isValidDateFormat(str)) {
      $("#recruitEnd + .help-block").html("<span style='color:red;'>종료일 형식을 확인해주세요!<span>");
       f.recruitEnd.focus();
       return false;
   } else {
     $("#recruitEnd + .help-block").html("  종료일은 2000-01-01 형식으로 입력 합니다.");
   }
   
   str = f.recruitQual.value;
   if(!str) {
      $("#recruitQual + .help-block").html("<span style='color:red;'>지원자격을 기입하세요!<span>");
       f.recruitQual.focus();
       return false;
   }else {
      $("#recruitQual + .help-block").html("");
   }
   
   str = f.recruitStep.value;
   if(!str) {
      $("#recruitStep + .help-block").html("<span style='color:red;'>지원절차를 기입하세요!<span>");
       f.recruitStep.focus();
       return false;
   }else {
      $("#recruitStep + .help-block").html("");
   }
   
   var mode="${mode}";
	  if(mode=="created"||mode=="update" && f.upload.value!="") {
		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload.value)) {
			alert('이미지 파일만 가능합니다. !!!');
			f.upload.focus();
			return false;
		}
	  }

   
    if(mode=="created") {
       f.action = "<%=cp%>/recruit/created_ok.sst";
    } else if(mode=="update") {
       f.action = "<%=cp%>/recruit/update_ok.sst";
    }
    
    return true;
}
</script>
<style>
.form-group{
}
</style>
</head>
<body>

<div class="container" role="main" style="margin-top:50px;">
  <div class="jumbotron">
    <h1><span class="glyphicon glyphicon-user" style="color:gray; margin-right:10px;" ></span>채용 일정 입력 </h1>
  </div>

  <div class="bodyFrame">
  <form class="form-horizontal" name="memberForm" method="post" onsubmit="return check();" enctype="multipart/form-data">
    <div class="form-group" style="margin-bottom:0px;">
        <label class="col-sm-2 control-label" for="recruitCompany">기업 이름</label>
        <div class="col-sm-7">
            <input style="width:200px;"class="form-control" id="recruitCompany" name="recruitCompany" type="text" 
                  placeholder="기업 이름" value="${dto.recruitCompany}" ${mode=="update" ? "readonly='readonly'
                  style='border:none;' ":""}>
            <p class="help-block"> 기업 이름을 입력해주세요. 예시.쌍용정보통신</p>
        </div>
    </div>
    
    
     <div class="form-group">
        <label class="col-sm-2 control-label" for="recruitHead">채용 구분</label>
         <div class="col-sm-10">
         
           <select name="recruitHead" id="recruitHead" class="form-control" 
                 style="width:320px; float: left; margin-right:-40px; "  >
                    
            <option value="">선 택</option>
            <option value="신입">신입 </option>
            <option value="경력">경력 </option>
            <option value="인턴">인턴</option>
            <option value="비정규직">비정규직</option>
         </select>
             
         </div>
    </div>
    
    
    <div style=""class="form-group">
        <label class="col-sm-2 control-label" for="recruitStart">채용 시작일</label>
        <div class="col-sm-7">
            <input style="width:200px; " class="form-control" id="recruitStart" name="recruitStart" type="text" placeholder="채용 시작일" value="${dto.recruitStart}">
            <p class="help-block">시작일은 2000-01-01 식으로 입력해주세요.</p>
        </div>
    </div>
    
        <div style=""class="form-group">
        <label class="col-sm-2 control-label" for="recruitEnd">채용 종료일</label>
        <div class="col-sm-7">
            <input style="width:200px; " class="form-control" id="recruitEnd" name="recruitEnd" type="text" placeholder="채용 종료일" value="${dto.recruitEnd}">
            <p class="help-block">종료일은 2000-01-01 식으로 입력해주세요.</p>
        </div>
    </div>
    
    
    
    <div class="form-group">
        <label class="col-sm-2 control-label" for="recruitQual">지원 자격</label>
        <div class="col-sm-7">
            <input style="width:200px; "class="form-control" id="recruitQual" name="recruitQual" type="text" placeholder="지원 자격" value="${dto.recruitQual}">
            <p class="help-block">지원 자격을 입력해주세요.</p>
        </div>
    </div>
 
    <div class="form-group">
        <label class="col-sm-2 control-label" for="recruitStep">채용 절차</label>
        <div class="col-sm-7">
            <input style="width:200px; " class="form-control" id="recruitStep" name="recruitStep" 
                  type="text" placeholder="채용 절차" value="${dto.recruitStep}">>
        <p class="help-block">채용 절차를 입력해주세요.</p>
        </div>  
    </div>
    
    <div>
    	<label class="col-sm-2 control-label" for="recruitImg">이미지</label>
    	<div class="col-sm-7">
    	<input type="file" name="upload" class="form-control input-sm">
    	 <p class="help-block">jpg, png, gif의 확장자를 가진 이미지 파일만 가능합니다.</p>
    	</div>
   </div>


<div class="form-group">
  <div class="col-sm-offset-2 col-sm-10">
   <c:if test="${mode=='created'}">
            <button type="submit" name="sendButton" class="btn btn-primary" style="margin-right:20px; height:40px; width:130px;">등록완료 <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-danger" onclick="javascript:location.href='<%=cp%>/';" style="margin-right:20px; height:40px; width:130px;">등록취소 <span class="glyphicon glyphicon-remove"></span></button>
   </c:if>
   <c:if test="${mode=='update'}">
            <button type="submit" class="btn btn-primary">일정수정 <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-danger" onclick="javascript:location.href='<%=cp%>/';">수정취소 <span class="glyphicon glyphicon-remove"></span></button>
            <input type="hidden" name="recruitNum" value="${dto.recruitNum}">
   </c:if>            
    </div>
</div>

    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
                <p class="form-control-static">${message}</p>
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