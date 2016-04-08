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
				<h1 class="page-header">Portfolio</h1>
				<ol class="breadcrumb">
					<li class="active">Portfolio</li>
				</ol>
			</div>
		</div>


		<div class="table-responsive" style="clear: both;">
			<div class="bbs-article">
				<table class="table">
					<thead>
						<tr>
							<th colspan="2" style="text-align: center;">${dto.subject}</th>
						</tr>
					<thead>
					<tbody>
						<tr>
							<td style="text-align: left;">이름 : ${dto.memId}</td>
							<td style="text-align: right;">${dto.created}</td>
						</tr>
						<tr style="border-bottom: none;">
							<td colspan="2"><img
								src="<%=cp%>/uploads/portfolio/${dto.imageFilename}"
								style="max-width: 100%; height: auto; resize: both;"></td>
						</tr>
						<tr>
							<td colspan="2" style="min-height: 30px;">${dto.content}</td>
						</tr>
					</tbody>
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
		</div>
	</div>
	<!-- jQuery -->
	<script src="<%=cp%>/res/js/jquery.js"></script>

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