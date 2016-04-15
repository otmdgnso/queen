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
.company-reply {
    font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
}

.company-reply-write {
    border: #d5d5d5 solid 1px;
    padding: 10px;
    min-height: 50px;
}
</style>

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript">
<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
function deleteCompany(companyNum) {
	if(confirm("삭제 하시겠습니까 ?")) {
		var url="<%=cp%>/company/delete.sst?companyNum="+companyNum+"&page=${page}";
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
		var url="<%=cp%>/company/recomm.sst?page=${page}&companyNum=${dto.companyNum}";
		location.href=url;
	}
	
} 



//-- 댓글 ------------------------------------
//댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/company/listReply.sst";
	var companyNum="${dto.companyNum}";
	$.post(url, {companyNum:companyNum, pageNo:page}, function(data){
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
	
	var companyNum="${dto.companyNum}"; //해당 게시물 번호
	var companyR_content=$.trim($("#companyR_content").val());
	if(! companyR_content){
		alert("댓글내용을  입력하세요!");
		$("#companyR_content").focus();
		return false;
	}
	
	var params="companyNum="+companyNum;
	params+="&companyR_content="+companyR_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/company/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#companyR_content").val("");
			
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
function deleteReply(companyR_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url="<%=cp%>/company/deleteReply.sst";
		$.post(url,{companyR_num:companyR_num, memId:memId}, function(data){
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
             <h3  style="font-size:30px;"><span class="glyphicon glyphicon-book"></span> 회사정보 게시판 </h3>
       </div>
       
       <div class="alert alert-info">
           <i class="glyphicon glyphicon-info-sign"></i> 기업의 상세 정보를 알려주는 게시판입니다.
       </div>
       
       <div class="table-responsive" style="clear: both;">
           <div class="company-article">
               <table class="table">
                    <thead>
                        <tr>
                            <th colspan="4" style="text-align: center; background-color: #B2CCFF;">
                                    [${dto.companyForm}] ${dto.companySubject}
                            </th>
                        </tr>
                   </thead>
                    <tbody>
                        <tr>
                            <td style="text-align: left; width:200px; height:45px; "> 작성자: ${dto.memId}</td>
                            <td style="text-align: left; width:200px; height:45px; "></td>
                            <td style="text-align: right; width:200px;">
                             ${dto.companyCreated} 
                            </td>
                            <td style="text-align: right; width:200px;">
                                                                         조회 수: ${dto.companyHitCount}
                            </td>
                        </tr>
                        <tr>
                        	<td style="text-align: left; width:200px; height:45px; ">회사명</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companyName}</td>
                            <td style="text-align: left; width:200px; height:45px; ">웹사이트</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companyWeb}</td>
                        </tr>
                        <tr>
                        	<td style="text-align: left; width:200px; height:45px; ">기업형태</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companyForm}</td>
                            <td style="text-align: left; width:200px; height:45px; ">설립일</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companyDate}</td>
                        </tr>
                        <tr>
                        	<td style="text-align: left; width:200px; height:45px; ">산업군</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companyIndustry}</td>
                            <td style="text-align: left; width:200px; height:45px; ">매출액</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companySales}</td>
                        </tr>
                        <tr>
                        	<td style="text-align: left; width:200px; height:45px; ">초봉 평균</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companySalary}</td>
                            <td style="text-align: left; width:200px; height:45px; ">평점</td>
                            <td style="text-align: left; width:200px; height:45px; ">${dto.companyScore}</td>
                        </tr>
                        <tr>
                        	<td style="text-align: left; width:200px; height:45px; ">잡 플래닛</td>
                            <td style="text-align: left; width:200px; height:45px; "><a href="http://${dto.companyPlanet}">${dto.companyPlanet}</a></td>
                            <td style="text-align: left; width:200px; height:45px; "></td>
                            <td style="text-align: left; width:200px; height:45px; "></td>
                        </tr>
                        <tr>
                            <td colspan="4" style="height: 230px;">
                                 ${dto.companyContent}
                            </td>
                        </tr>
                        
                     	<tr>
                        <td><img id="btnLike" src="<%=cp%>/res/image/social.png" onclick="recommCheck()">&nbsp;${dto.companyRecomm}</td>
                        </tr>
                        
                        <tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">이전글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty preReadDto}">
								<a href="<%=cp%>/company/article.sst?companyNum=${preReadDto.companyNum}&${params}">${preReadDto.companySubject }</a>
							</c:if>
						</td>
					</tr>
                        <tr height="30">
					    <td width="80" bgcolor="#EEEEEE" align="center">다음글</td>
					    <td width="520" align="left" style="padding-left:10px;" colspan="3">
							<c:if test="${not empty nextReadDto}">
								<a href="<%=cp%>/company/article.sst?companyNum=${nextReadDto.companyNum}&${params}">${nextReadDto.companySubject }</a>
							</c:if>
					    </td>
					</tr>
                   </tbody>
                   <tfoot>
                    <tr>
					    <td align="left">
					    <c:if test="${dto.memId == sessionScope.member.memId}">
					          <input type="image" src="<%=cp%>/res/image/btn_modify.gif" onclick="javascript:location.href='<%=cp%>/company/update.sst?companyNum=${dto.companyNum}&page=${page}';">
   						</c:if>
   						&nbsp;
   						<c:if test="${dto.memId == sessionScope.member.memId || sessionScope.member.memId=='admin'}">
					          <input type="image" src="<%=cp%>/res/image/btn_delete.gif" onclick="deleteCompany('${dto.companyNum}')">
					    </c:if>
   						</td>
   						<td align="right" colspan="3">
					          <input type="image" src="<%=cp%>/res/image/btn_list.gif" onclick="javascript:location.href='<%=cp%>/company/list.sst?${params}';">
					    </td>
					    </tr>
                   </tfoot>
               </table>
          </div>
          
          <div class="company-reply">
              <div class="company-reply-write">
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