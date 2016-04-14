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

    <title>글 보기</title>


    <!-- Bootstrap Core CSS -->
    <link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

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

<style type="text/css">
.docu-reply {
    font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
}

.docu-reply-write {
    border: #d5d5d5 solid 1px;
    padding: 10px;
    min-height: 50px;
}
</style>

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript">
<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
function deleteDocu(docuNum) {
	if(confirm("삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/docu/delete.sst?docuNum="+docuNum+"&page=${page}";
		location.href=url;
	}
}
</c:if>

function  recommCheck() {
		var c="${dataCount}";
		if(c=="")
			c="0";
		
		if(c=="1") {
			alert("이미 추천 하셨습니다.");
			return;
		} else {
			var url="<%=cp%>/docu/recomm.sst?page=${page}&docuNum=${dto.docuNum}";
			location.href=url;
		}
		
	} 

//-- 댓글 ------------------------------------
//댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/docu/listReply.sst";
	var docuNum="${dto.docuNum}";
	$.post(url, {docuNum:docuNum, pageNo:page}, function(data){
		$("#listReply").html(data);
	});
}
//리플 저장

function sendReply(){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	var docuNum="${dto.docuNum}"; //해당 게시물 번호
	var docuR_content=$.trim($("#docuR_content").val());
	if(! docuR_content){
		alert("댓글내용을  입력하세요!");
		$("#docuR_content").focus();
		return false;
	}
	
	var params="docuNum="+docuNum;
	params+="&docuR_content="+docuR_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/docu/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#docuR_content").val("");
			
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
function deleteReply(docuR_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url="<%=cp%>/docu/deleteReply.sst";
		$.post(url,{docuR_num:docuR_num, memId:memId}, function(data){
			var state=data.state;
			if(state=="loginFail"){
				login();
			}else{
				listPage(pageNo);
			}
			
		},"json");
	}
}
</script>

</head>

<body>

   <!-- Navigation -->
   <div>
     <jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   </div>

    <div class="container" role="main">
    <div class="bodyFrame col-sm-10"  style="float:none; margin-left: auto; margin-right: auto;">

       <div class="body-title">
             <h3><span class="glyphicon glyphicon-book"></span> 게시판 </h3>
       </div>
       
       <div class="alert alert-info">
           <i class="glyphicon glyphicon-info-sign"></i> 회원과 자유로이 토론할 수 있는 공간입니다.
       </div>
       
       <div class="table-responsive" style="clear: both;">
           <div class="docu-article">
               <table class="table">
                    <thead>
                        <tr>
                            <th colspan="3" style="text-align: center;">
                                    ${dto.docuSubject}
                            </th>
                        </tr>
                   <thead>
                    <tbody>
                        <tr>
                            <td style="text-align: left; width:200px; height:45px; "> 작성자: ${dto.memId}</td>
                            <td style="text-align: right;">
                             ${dto.docuCreated} 
                            </td>
                            <td style="text-align: right; width:100px;">
                                                                         조회 수: ${dto.docuHitCount}
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" style="height: 230px;">
                                 ${dto.docuContent}
                            </td>
                        </tr>
                        <tr>
	                      <td colspan="2">
		                        <span style="display: inline-block; min-width: 45px;">첨부</span> :
		                        <c:if test="${not empty dto.docuFile}">
		                            <a href="<%=cp%>/docu/download.do?docuNum=${dto.docuNum}">${dto.originalFilename}</a>
		                            (<fmt:formatNumber value="${dto.fileSize/1024}" pattern="0.00"/> KByte)
		                        </c:if>
		                  </td>
		               </tr>
                        
                        <tr>
                        <td style="align:center; font-weight:bold; font-size:13px;"><img src="<%=cp%>/res/image/social.png" onclick="recommCheck()">&nbsp;&nbsp;&nbsp;${dto.docuRecomm}</td>
                        </tr>
                        
                        <tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">이전글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty preReadDto}">
								<a href="<%=cp%>/docu/article.sst?docuNum=${preReadDto.docuNum}&${params}">${preReadDto.docuSubject }</a>
							</c:if>
						</td>
					</tr>
                        <tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">다음글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty nextReadDto}">
								<a href="<%=cp%>/docu/article.sst?docuNum=${nextReadDto.docuNum}&${params}">${nextReadDto.docuSubject }</a>
							</c:if>
					    </td>
					</tr>
                   </tbody>
                   <tfoot>
                    <tr>
					    <td align="left">
					    <c:if test="${dto.memId == sessionScope.member.memId}">
					          <input type="image" src="<%=cp%>/res/image/btn_modify.gif" onclick="javascript:location.href='<%=cp%>/docu/update.sst?docuNum=${dto.docuNum}&page=${page}';">
   						</c:if>
   						&nbsp;
   						<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
					          <input type="image" src="<%=cp%>/res/image/btn_delete.gif" onclick="deleteDocu('${dto.docuNum}')">
					    </c:if>
   						</td>
   						<td align="right" colspan="2">
					          <input type="image" src="<%=cp%>/res/image/btn_list.gif" onclick="javascript:location.href='<%=cp%>/docu/list.sst?${params}';">
					    </td>
					    </tr>
                   </tfoot>
               </table>
          </div>
          
          <div class="docu-reply">
              <div class="docu-reply-write">
                  <div style="clear: both;">
                        <div style="float: left;"><span style="font-weight: bold;">댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span></div>
                        <div style="float: right; text-align: right;"></div>
                  </div>
                  <div style="clear: both; padding-top: 10px;">
                      <textarea id="docuR_content" class="form-control" rows="3" required="required"></textarea>
                  </div>
                  <div style="text-align: right; padding-top: 10px;">
                      <button type="button" class="btn btn-primary btn-sm" onclick="sendReply();"> 댓글등록 <span class="glyphicon glyphicon-ok"></span></button>
                  </div>           
              </div>
          
              <div id="listReply"></div>
          </div>
          
      </div>

    </div>
</div>
<input type="hidden" name="memId" value="${dto.memId}">
 <!-- jQuery -->
    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>

</html>