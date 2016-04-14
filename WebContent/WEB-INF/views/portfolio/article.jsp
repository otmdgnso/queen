<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
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


<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<!-- Bootstrap Core CSS -->
<link href="<%=cp%>/res/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<script type="text/javascript">
function updatePortfolio(num) {
   <c:if test="${sessionScope.member.memId==dto.memId}">
        var url="<%=cp%>/portfolio/update.sst?num="+num+"&page=${page}";
        location.href=url;
   </c:if>
}

function deletePortfolio(num) {
   <c:if test="${sessionScope.member.memId==dto.memId || sessionScope.member.memId=='admin'}">
         if(confirm("게시물을 삭제 하시겠습니까 ?")) {
            var url="<%=cp%>/portfolio/delete.sst?num="+num+"&page=${page}";
            location.href=url;
         }   
   </c:if>
}
//-- 댓글 ------------------------------------
function login() {
	location.href="<%=cp%>/member/login.sst";
}

//댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/portfolio/listReply.sst";
	var num="${dto.num}";
	$.post(url, {num:num, pageNo:page}, function(data){
		$("#listReply").html(data);
	});
}

//리플 저장
function sendReply() {
	var uid="${sessionScope.member.memId}";
	if(! uid) {
		login();
		return false;
	}

	var num="${dto.num}"; // 해당 게시물 번호
	var content=$.trim($("#content").val());
	if(! content ) {
		alert("내용을 입력하세요 !!! ");
		$("#content").focus();
		return false;
	}
	
	var params="num="+num;
	params+="&content="+content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/portfolio/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#content").val("");
			
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

// 리플 삭제
function deleteReply(replyNum, pageNo, memId) {
	var uid="${sessionScope.member.memId}";
	if(! uid) {
		login();
		return false;
	}
	
	if(confirm("게시물을 삭제하시겠습니까 ? ")) {	
		var url="<%=cp%>/portfolio/deleteReply.sst";
		$.post(url, {replyNum:replyNum, memId:memId}, function(data){
		        var state=data.state;
				if(state=="loginFail") {
					login();
				} else {
					listPage(pageNo);
				}
		}, "json");
	}
}
</script>

</head>

<body>

	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>

	<!-- Page Content -->
	<div class="container">

		<!-- Page Heading/Breadcrumbs -->
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">
					Portfolio Item <small>Subheading</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="index.html">Home</a></li>
					<li class="active">Portfolio Item</li>
				</ol>
			</div>
		</div>
		<!-- /.row -->

		<!-- Portfolio Item Row -->
		<div class="row">

			<div class="col-md-8">
				<div id="carousel-example-generic" class="carousel slide" 	data-ride="carousel" >
					<!-- Indicators -->
					<ol class="carousel-indicators">
						<li data-target="#carousel-example-generic" data-slide-to="0"	class="active"></li>
						<c:if test="${dto.imgCnt>1}">
						<li data-target="#carousel-example-generic" data-slide-to="1"></li>
						</c:if>
						<c:if test="${dto.imgCnt>2}">
						<li data-target="#carousel-example-generic" data-slide-to="2"></li>
						</c:if>
						<c:if test="${dto.imgCnt>3}">
						<li data-target="#carousel-example-generic" data-slide-to="3"></li>
						</c:if>
					</ol>

					<!-- Wrapper for slides -->
					<div class="carousel-inner">
						<div class="item active">
							<img class="img-responsive" src="<%=cp%>/uploads/portfolio/${dto.imageFilename}" alt="" style="width: 100%; height: 500px">
						</div>
						<c:if test="${dto.imgCnt>1}">
						<div class="item">
							<img class="img-responsive" src="<%=cp%>/uploads/portfolio/${dto.imageFilename2}" alt=""style="width: 100%; height: 500px" >
						</div>
						</c:if>
						<c:if test="${dto.imgCnt>2}">
						<div class="item">
							<img class="img-responsive" src="<%=cp%>/uploads/portfolio/${dto.imageFilename3}" alt=""style="width: 100%; height: 500px">
						</div>
						</c:if>
						<c:if test="${dto.imgCnt>3}">
						<div class="item">
							<img class="img-responsive" src="<%=cp%>/uploads/portfolio/${dto.imageFilename4}" alt="" style="width: 100%; height: 500px">
						</div>
						</c:if>
					</div>

					<!-- Controls -->
					<a class="left carousel-control"  href="#carousel-example-generic"	data-slide="prev">
					     <span class="glyphicon glyphicon-chevron-left"></span>
					</a> 
					<a class="right carousel-control"  href="#carousel-example-generic" 	data-slide="next"> 
					    <span class="glyphicon glyphicon-chevron-right"></span>
					</a>
				</div>
			</div>

			<div class="col-md-4">
				<h3>Project Name</h3>
				<p>${dto.subject}</p>
				<h3>Project Details</h3>
				<p>${dto.content}</p>
			</div>
		</div>
		<hr>
		<!-- /.row -->
		<div class="row">
		<table>
			<tfoot>
				<tr>
					<td><c:if test="${sessionScope.member.memId==dto.memId}">
							<button type="button" class="btn btn-default btn-sm wbtn"
								onclick="updatePortfolio(${dto.num});">수정</button>
						</c:if> <c:if
							test="${sessionScope.member.memId==dto.memId || sessionScope.member.memId=='admin'}">
							<button type="button" class="btn btn-default btn-sm wbtn"
								onclick="deletePortfolio(${dto.num});">삭제</button>
						</c:if></td>
					<td align="right">
						<button type="button" class="btn btn-default btn-sm wbtn"
							onclick="javascript:location.href='<%=cp%>/portfolio/list.sst?page=${page}';">
							목록으로</button>
					</td>
				</tr>
			</tfoot>
		</table>
		</div>
		<hr>
			<div style="float: left;">
				<span style="font-weight: bold;">댓글쓰기</span>
				<div style="float: right; text-align: right;"></div>
			</div>
			<div style="clear: both; padding-top: 10px;">
				<textarea id="content" class="form-control" rows="3"
					required="required"></textarea>
			</div>
			<div style="text-align: right; padding-top: 10px;">
				<button type="button" class="btn btn-primary btn-sm"
					onclick="sendReply();">
					댓글등록 <span class="glyphicon glyphicon-ok"></span>
				</button>
			</div>
								<div id="listReply"></div>

	</div>
	<!-- /.container -->

   	<!-- Bootstrap Core JavaScript -->
	<script src="<%=cp%>/res/js/bootstrap.min.js"></script>

	<!-- Script to Activate the Carousel -->
	<script>
		$('.carousel').carousel({
			interval : 5000
		//changes the speed
		})
	</script>
</body>

</html>
