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
.resume-reply {
    font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
}

.resume-reply-write {
    border: #687ead solid 1px;
    padding: 10px;
    min-height: 50px;
}
</style>

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript">
<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
function deleteResume(resumeNum) {
	if(confirm("삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/resume/delete.sst?resumeNum="+resumeNum+"&page=${page}";
		location.href=url;
	}
}
</c:if>

function recommCheck() {
	var c="${recommCount}";
	if(c=="")
		c="0";
	if(c=="1") {
		alert("이미 추천 하셨습니다.");
		return;
	} else {
		var url="<%=cp%>/resume/recomm.sst?page=${page}&resumeNum=${dto.resumeNum}";
		location.href=url;
	}
	
} 


//-- 댓글 ------------------------------------
//댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/resume/listReply.sst";
	var resumeNum="${dto.resumeNum}";
	$.post(url, {resumeNum:resumeNum, pageNo:page}, function(data){
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
	
	var resumeNum="${dto.resumeNum}"; //해당 게시물 번호
	var resumeR_content=$.trim($("#resumeR_content").val());
	if(! resumeR_content){
		alert("댓글내용을  입력하세요!");
		$("#resumeR_content").focus();
		return false;
	}
	
	var params="resumeNum="+resumeNum;
	params+="&resumeR_content="+resumeR_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/resume/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#resumeR_content").val("");
			
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
function deleteReply(resumeR_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url="<%=cp%>/resume/deleteReply.sst";
		$.post(url,{resumeR_num:resumeR_num, memId:memId}, function(data){
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
    <div class="col-sm-10_2"  style="float:none; margin-left: auto; margin-right: auto;">

       <div class="body-title">
             <h3 style="font-size:30px;"><span class="glyphicon glyphicon-book"></span> 자소서 공유 </h3>
       </div>
       
       <div class="alert alert-info">
           <i class="glyphicon glyphicon-info-sign"></i> 합격한 자기소개서 정보를 공유하는 게시판입니다
       </div>
       
       <div class="table-responsive" style="clear: both;">
           <div class="resume-article">
               <table class="table">
                    <thead>
                        <tr>
                            <th colspan="4" style="text-align: center; background-color: #B2CCFF;">
                                    ${dto.resumeSubject}
                            </th>
                        </tr>
                   </thead>
                    <tbody class="table_td">
                        <tr>
                            <td style="text-align: left; width:200px; height:45px; "> 작성자 | ${dto.memId}</td>
                            <td style="text-align: left; width:400px; height:45px;"></td>
                            <td style="text-align: right; width:200px ;">
                                                                           작성일 | ${dto.resumeCreated} 
                            </td>
                            <td style="text-align: right; width:100px;">
                                                                         조회 수| ${dto.resumeHitCount}
                            </td>
                        </tr>
                        <tr>
                        	<td bgcolor="#B2CCFF" style="color:white; text-align: left; width:200px; height:45px; ">지원회사</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeCompany}</td>
                            <td bgcolor="#B2CCFF"style="color:white; text-align: left; width:200px; height:45px; ">지원시기</td>
                            <td  style="text-align: left; width:200px; height:45px; ">${dto.resumeDate}</td>
                        </tr>
                        <tr>
                        	<td bgcolor="#B2CCFF"style=" color:white;text-align: left; width:200px; height:45px; ">지원직무</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeJob}</td>
                            <td bgcolor="#B2CCFF"style="color:white;text-align: left; width:200px; height:45px; ">출신학교</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeSchool}</td>
                        </tr>
                        <tr>
                        	<td bgcolor="#B2CCFF"style="color:white;text-align: left; width:200px; height:45px; ">전공</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeMajor}</td>
                            <td bgcolor="#B2CCFF"style="color:white;text-align: left; width:200px; height:45px; ">학점</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeScore}</td>
                        </tr>
                        <tr>
                        	<td bgcolor="#B2CCFF"style="color:white;text-align: left; width:200px; height:45px; ">어학성적</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeLanguage}</td>
                            <td bgcolor="#B2CCFF"style="color:white;text-align: left; width:200px; height:45px; ">대외활동</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeEx}</td>
                        </tr>
                        <tr>
                        	<td bgcolor="#B2CCFF"style="color:white;text-align: left; width:200px; height:45px; ">강조역량</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.resumeAbility}</td>
                            <td style="text-align: left; width:200px; height:45px; "></td>
                            <td style="text-align: left; width:200px; height:45px; "></td>
                        </tr>
                        <tr>
                            <td colspan="4" style="height: 230px;">
                                 ${dto.resumeContent}
                            </td>
                        </tr>
                        
                        <tr>
                        <td><img id="btnLike" src="<%=cp%>/res/image/social.png" onclick="recommCheck()">&nbsp;${dto.resumeRecomm}</td>
                        </tr>
                        
                        <tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">이전글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty preReadDto}">
								<a href="<%=cp%>/resume/article.sst?resumeNum=${preReadDto.resumeNum}&${params}">${preReadDto.resumeSubject }</a>
							</c:if>
						</td>
					</tr>
                        <tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">다음글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty nextReadDto}">
								<a href="<%=cp%>/resume/article.sst?resumeNum=${nextReadDto.resumeNum}&${params}">${nextReadDto.resumeSubject }</a>
							</c:if>
					    </td>
					</tr>
                   </tbody>
                   <tfoot>
                    <tr>
					    <td align="left">
					    <c:if test="${dto.memId == sessionScope.member.memId}">
					          <input type="image" src="<%=cp%>/res/image/btn_modify.gif" onclick="javascript:location.href='<%=cp%>/resume/update.sst?resumeNum=${dto.resumeNum}&page=${page}';">
   						</c:if>
   						&nbsp;
   						<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
					          <input type="image" src="<%=cp%>/res/image/btn_delete.gif" onclick="deleteResume('${dto.resumeNum}')">
					    </c:if>
   						</td>
   						<td align="right" colspan="3">
					          <input type="image" src="<%=cp%>/res/image/btn_list.gif" onclick="javascript:location.href='<%=cp%>/resume/list.sst?${params}';">
					    </td>
					    </tr>
                   </tfoot>
               </table>
          </div>
          
          <div class="resume-reply">
              <div class="resume-reply-write">
                  <div style="clear: both;">
                        <div style="float: left;"><span style="font-weight: bold;">댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span></div>
                        <div style="float: right; text-align: right;"></div>
                  </div>
                  <div style="clear: both; padding-top: 10px;">
                      <textarea id="resumeR_content" class="form-control" rows="3" required="required"></textarea>
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

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>

</html>