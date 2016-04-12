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
<title>spring</title>

<style type="text/css">
.guest {
    font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
}
.guest-write {
    border: #d5d5d5 solid 1px;
    padding: 10px;
    min-height: 50px;
}

.table td {
    font-weight: normal;
    border-top: none;
}
.table .guest-header{
    border: #d5d5d5 solid 1px;
    background: #eeeeee; color: #787878;
} 
</style>

<link rel="stylesheet" href="<%=cp%>/res/css/jquery-ui.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/bootstrap-theme.min.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css"/>
<link rel="stylesheet" href="<%=cp%>/res/css/bootstrap.min.css" type="text/css"/>

<script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>

<script type="text/javascript">

function sendGuest() {
	var uid="${sessionScope.member.memId}";
	if(! uid) {
		location.href="<%=cp%>/member/login.sst";
		return;
	}
	
	var f=document.freeForm;
	var str;
	
	str=f.content.value;
	if(!str) {
		alert("내용을 입력 하세요 !!!");
		f.content.focus();
		return;
	}
	
	f.action="<%=cp%>/free/free_ok.sst";
	f.submit();
	
}

function deleteFree(num, uid) {
	var url="<%=cp%>/free/delete.sst?num="+num+"&page=${page}&uid="+uid;
	
	if(confirm("삭제 하시겠습니까 ?"))
		location.href=url;
}

//-- 댓글 ------------------------------------
//댓글 리스트 보여주기 
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp %>/free/freeReply.sst";
	var freeNum="${dto.freeNum}";
	
	$.post(url, {freeNum:freeNum, pageNo:page}, function(data){
		$("#listReply").html(data);
	});
}


//리플 저장- 댓글 등록하기 
function sendReply(){
	
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	var freeNum="${dto.freeNum}"; //해당 게시물 번호
	var freeR_content= $.trim($("#freeR_content").val());
	if(! companyR_content){
		alert("댓글내용을  입력하세요!");
		$("#freeR_content").focus();
		return false;
	}
	
	var params="freeNum="+freeNum;
	params+="&freeR_content="+freeR_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/free/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#freeR_content").val("");
			
			var state=data.state;
			if(state=="true") {
			
				listPage(1); //1- page번호
				
			} 
			else if(state=="false") {
				alert("댓글을 등록하지 못했습니다. !!!");
			}
			else if(state=="loginFail") {
				login();
			}
		}
		,error:function(e) {
			alert(e.responseText);
		}
	});
}

//댓글 삭제
function deleteReply(freeR_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("댓글을 삭제하시겠습니까?")){
		var url="<%=cp%>/free/deleteReply.sst";
		
		$.post(url,{freeR_num:freeR_num, memId:memId}, function(data){
			
			var state=data.state;
			if(state=="loginFail"){
				login(); //?
				
			}else{
				listPage(pageNo);
			}
			
		},"json");
	}
}
</script>

</head>
<body>

	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
	
<div class="container" role="main">
    <div class="bodyFrame col-sm-10"  style="float:none; margin-left: auto; margin-right: auto; margin-top:100px;">
        
	    <div class="body-title">
	          <h3><span class="glyphicon glyphicon-pencil"></span> 주절주절  <small>자유게시판</small></h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> 수강생들과 자유롭게 대화를 나눠보세요
	    </div>
	
	    <div class="guest">
	            <div class="guest-write">
	               <div style="clear: both;">
	           	       <div style="float: left;">
	           	       		<span style="font-weight: bold;">게시물작성</span><span> - 오늘의 하고 싶은 말!</span></div>
	           	       <div style="float: right; text-align: right;"></div>
	               </div>
	               <div style="clear: both; padding-top: 10px;">
	                   
	                   <form name="freeForm" method="post" action="">
	                   
	                       <textarea name="content" id="content" class="form-control" rows="3" required="required"></textarea>
	                   
	                    </form>
	               </div>
	               <div style="text-align: right; padding-top: 10px;">
	                   <button type="button" class="btn btn-primary btn-sm" onclick="sendGuest();"> 등록하기 <span class="glyphicon glyphicon-ok"></span></button>
	               </div>           
	           </div>
	       
	           <div id="listGuest">
	               <div style='clear: both; padding-top: 20px;'>
	                   <div style='float: left;'><span style='color: #3EA9CD; font-weight: bold;'>방명록 ${dataCount}개</span> <span>[목록, ${page}/${total_page} 페이지]</span></div>
	                   <div style='float: right; text-align: right;'></div>
	               </div>           
	               <div class='table-responsive' style='clear: both; padding-top: 5px;'>
	                   
	                   <table class='table'>
	                   
	                   <c:forEach var="dto" items="${list}">
	                       <tr class='guest-header'>
	                           <td style='width: 50%;'>
	                               ${dto.memName }
	                           </td>
	                           <td style='width: 50%; text-align: right;'>
	                               ${dto.freeCreated}
	                               <c:if test="${sessionScope.member.memId==dto.memId || sessionScope.member.memId=='admin'}">	
								        | <a href="javascript:deleteFree('${dto.freeNum}', '${dto.memId}');">삭제</a>
								   </c:if>
	                           </td>
	                       </tr>
	                       <tr style='height: 50px;'>
	                           <td colspan='2'>
	                               ${dto.freeContent}
	                           </td>
	                          
	                           
	                       </tr>
	                      <!--  <tr class="company-reply" style="width:100%;"> 댓글을 달려고 했으나 F A I L 
	                            <td class="company-reply-write" style="width:100%;">
						                  <div style="clear: both;">
						                        <div style="float: left;"><span style="font-weight: bold;">댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span></div>
						                        <div style="float: right; text-align: right;"></div>
						                  </div>
						                  <div style="clear: both; padding-top: 10px;">
						                      <textarea id="companyR_content" class="form-control" rows="3" required="required"></textarea>
						                  </div>
						                  <div style="text-align: right; padding-top: 10px;">
						                      <button type="button" class="btn btn-primary btn-sm" onclick="sendReply();"> 댓글등록 <span class="glyphicon glyphicon-ok"></span></button>
						                  </div>           
						        </td>
	                       </tr>
	                       <tr >
		                        <td id="listReply">
	                           </td>
	                       </tr> -->
	                    </c:forEach>
	                       
	                       <tr style='height: 30px;'>
	                           <td colspan='2' style='text-align: center;'>
	                               ${paging}
	                           </td>
	                       </tr>
	                   </table>
	               </div>           
	           </div>
	    </div>

    </div>
</div>

<div>
    <jsp:include page="/WEB-INF/views/layout/footer.jsp"></jsp:include>
</div>

<script type="text/javascript" src="<%=cp%>/res/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/jquery.ui.datepicker-ko.js"></script>
<script type="text/javascript" src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>
</html>