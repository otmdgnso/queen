<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
   String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>사자의 심장을 가져라 </title>


    <!-- Bootstrap Core CSS -->
    <link href="<%=cp%>/res/css/bootstrap.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->



	<!-- 신택스 하이라이터 (css) -->
	<link type="text/css" rel="stylesheet" href="<%=cp%>/res/sh/shCoreEclipse.css"/>
	<link type="text/css" rel="stylesheet" href="<%=cp%>/res/sh/shThemeEclipse.css"/>
	
<style type="text/css">
.qna-reply {
     font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
    border-top: #687ead solid 2px; 
    border-bottom: #687ead solid 2px; padding:15px;
    margin-bottom:70px;
}

.qna-reply-write {
      border-bottom: #ddd solid 2px; 
    padding: 10px;
    min-height: 50px;
}
</style>

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<!-- 신택스 하이라이터 (js) -->
<script type="text/javascript" src="<%=cp %>/res/sh/shCore.js"></script>
<script type="text/javascript" src="<%=cp %>/res/sh/shBrushCpp.js"></script>
<script type="text/javascript" src="<%=cp %>/res/sh/shBrushCSharp.js"></script>
<script type="text/javascript" src="<%=cp %>/res/sh/shBrushCss.js"></script>
<script type="text/javascript" src="<%=cp %>/res/sh/shBrushJava.js"></script>
<script type="text/javascript" src="<%=cp %>/res/sh/shBrushJScript.js"></script>
<script type="text/javascript" src="<%=cp %>/res/sh/shBrushSql.js"></script>
<script>SyntaxHighlighter.all();</script>

<script type="text/javascript">
<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
function deleteQna(qnaNum) {
	if(confirm("삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/qna/delete.sst?qnaNum="+qnaNum+"&page=${page}";
		location.href=url;
	}
}
</c:if>
var flag=1;
//-- 댓글 ------------------------------------
//답변
$(function(){
	listAnswerPage(1);
	replyCtrl();
});

function replyCtrl(){
		if(flag==1){
			hideReply();
		}
		else
			showReply();
}
function hideReply(){
	 $(".qna-reply").hide(); 
	 flag=0;
}
function showReply(){	
		$(".qna-reply").show();
		listPage(1);

		flag=1;
}
function listPage(page) {
	var url="<%=cp%>/qna/listReply.sst";
	var qnaNum="${dto.qnaNum}";
	$.post(url, {qnaNum:qnaNum, pageNo:page}, function(data){
		$("#listReply").html(data);
	});
}
function listAnswerPage(page) {
	var url="<%=cp%>/qna/listAnswer.sst";
	var qnaNum="${dto.qnaNum}";
	$.post(url, {qnaNum:qnaNum, pageNo:page}, function(data){
		$("#listAnswer").html(data);
	});
}
//리플 저장

function sendReply(){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	var qnaNum="${dto.qnaNum}"; //해당 게시물 번호
	var qnaR_content=$.trim($("#qnaR_content").val());
	if(! qnaR_content){
		alert("댓글내용을  입력하세요!");
		$("#qnaR_content").focus();
		return false;
	}
	
	var params="qnaNum="+qnaNum;
	params+="&qnaR_content="+qnaR_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/qna/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#qnaR_content").val("");
			
  			var state=data.state;
			if(state=="true") {
				listPage(1);
			} else if(state=="false") {
				alert("댓글을 등록하지 못했습니다. !!!");
			} else if(state=="loginFail") {
				login();
			}
		}
		,error:function(e) {
			alert(e.responseText);
		}
	});
}
// 댓글 삭제
function deleteReply(qnaR_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url="<%=cp%>/qna/deleteReply.sst";
		$.post(url,{qnaR_num:qnaR_num, memId:memId}, function(data){
			var state=data.state;
			if(state=="loginFail"){
				login();
			}else{
				listPage(pageNo);
			}
			
		},"json");
	}
}
//------------------------------ 답변
function sendAnswer(){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	var qnaNum="${dto.qnaNum}"; //해당 게시물 번호
	var a_content=$.trim($("#a_content").val());
	if(! a_content){
		alert("답변내용을  입력하세요!");
		$("#a_content").focus();
		return false;
	}
	
	var params="qnaNum="+qnaNum;
	params+="&a_content="+a_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/qna/insertAnswer.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#a_content").val("");
			
  			var state=data.state;
			if(state=="true") {
				listAnswerPage(1);
			} else if(state=="false") {
				alert("답변을 등록하지 못했습니다. !!!");
			} else if(state=="loginFail") {
				login();
			}
		}
		,error:function(e) {
			alert(e.responseText);
		}
	});
}
function deleteAnswer(a_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("답변을 삭제하시겠습니까?")){
		var url="<%=cp%>/qna/deleteAnswer.sst";
		$.post(url,{a_num:a_num, memId:memId}, function(data){
			var state=data.state;
			if(state=="loginFail"){
				login();
			}else{
				listAnswerPage(pageNo);
			}
			
		},"json");
	}
}

function selectAnswer(a_num){
	var url="<%=cp%>/qna/selectAnswer.sst";
	$.post(url,{a_num:a_num}, function(data){
			listAnswerPage(1);
	},"json");
}
</script>

</head>

<body>

   <!-- Navigation -->
   <div>
     <jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   </div>

    <div class="container" role="main">
    <div class="col-sm-10_2"  style="float:none; margin-left: auto; margin-right: auto;">

       <div class="body-title">
             <h3 style="font-size:30px;"><span class="glyphicon glyphicon-book"></span> 개발 Q&A </h3>
       </div>
       
       <div class="alert alert-info">
           <i class="glyphicon glyphicon-info-sign"></i> &nbsp;&nbsp;&nbsp;개발 관련 질문과 답변을 위한 게시판입니다 - 코딩과 관련한 내용만 올려주세요
       </div>
       
       <div class="table-responsive" style="clear: both;">
           <div class="qna-article">
               <table class="table">
                    <thead>
                        <tr>
                       		<th colspan="1" style="text-align: center; background-color: #B2CCFF;">
                                    ${dto.qnaHead}
                            </th>
                            <th colspan="3" style="text-align: center; background-color: #B2CCFF;">
                                    ${dto.qnaSubject}
                            </th>
                        </tr>
                   </thead>
                    <tbody>
                        <tr>
                            <td style="text-align: left; width:200px; height:45px; "> 작성자: ${dto.memId}</td>
                            <td style="text-align: right;">
                             ${dto.qnaCreated} 
                            </td>
                            <td style="text-align: right; width:100px;">
                                                                         조회 수: ${dto.qnaHitCount}
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" style="height: 230px;">
                                 ${dto.qnaContent}
                            </td>
                        </tr>
                        <tr >
                            <td colspan="3" style="width: 920px; color:#5478BE;">
                                 <b>소스코드</b>
                                 <pre class="brush:${dto.qnaHead}">${dto.qnaSource}</pre>
                            </td>
                        </tr>
                         <tr><td colspan="3"><a href="#" onclick="replyCtrl();">댓글</a></td></tr> 
                  
                   </tbody>
                   <tfoot>
                    <tr>
					    <td style="clear: both" align="left">
					    <c:if test="${dto.memId == sessionScope.member.memId}">
					          <input type="image" src="<%=cp%>/res/image/btn_modify.gif" onclick="javascript:location.href='<%=cp%>/qna/update.sst?qnaNum=${dto.qnaNum}&page=${page}';">
   						</c:if>
   						&nbsp;
   						<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
					          <input type="image" src="<%=cp%>/res/image/btn_delete.gif" onclick="deleteQna('${dto.qnaNum}')">
					    </c:if>
   						</td>
   						<td align="right" colspan="2">
					          <input type="image" src="<%=cp%>/res/image/btn_list.gif" onclick="javascript:location.href='<%=cp%>/qna/list.sst?${params}';">
					    </td>
					    </tr>
                   </tfoot>
               </table>
          </div>
			              
			
          <div class="qna-reply">
              <div class="qna-reply-write">
              <div id="listReply"></div>
                  <div style="clear: both;">
                        <div style="float: left;"><span style="font-weight: bold;">댓글쓰기</span></div>
                        <div style="float: right; text-align: right;"></div>
                  </div>
                  <div style="clear: both; padding-top: 10px;">
                      <textarea id="qnaR_content" class="form-control" rows="3" required="required"></textarea>
                  </div>
                  <div style="text-align: right; padding-top: 10px;">
                      <button type="button" class="btn btn-primary btn-sm" onclick="sendReply();"> 댓글등록 <span class="glyphicon glyphicon-ok"></span></button>
                  </div>           
              </div>
              
                  </div>
                               
                  
              <div class="qna-answer">
              <div class="qna-answer-write">
               <div id="listAnswer"></div>
                  <div style="clear: both;">
                        <div style="float: left;"><span style="font-weight: bold;">답변하기</span></div>
                        <div style="float: right; text-align: right;"></div>
                  </div>
                  <div style="clear: both; padding-top: 10px;">
                      <textarea id="a_content" class="form-control" rows="3" required="required"></textarea>
                  </div>
                  <div style="text-align: right; padding-top: 10px; margin-bottom:50px;">
                      <button type="button" class="btn btn-primary btn-sm" onclick="sendAnswer();"> 답변등록 <span class="glyphicon glyphicon-ok"></span></button>
                  </div>           
              </div>
          </div>
          
  

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
</div>
    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

    
</body>

</html>