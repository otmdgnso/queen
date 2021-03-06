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
<title>사자의 심장을 가져라 </title>

<!-- Bootstrap Core CSS -->
<link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">


<script type="text/javascript">
	function article(num) {
		var url = "${articleUrl}&num=" + num;
		location.href = url;
	}
</script>

</head>
<body>

	<div>
    <jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
 <!-- Page Content -->
    <div class="container" role="main">

		<div class="col-sm-10_2"  style="float:none; margin-left: auto; margin-right: auto;">
        
	    <div class="body-title">
	          <h3 style="font-size:30px;"><span class="glyphicon glyphicon-book"></span>&nbsp; 포트폴리오</h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> &nbsp;&nbsp;&nbsp;지난 수강생들의 포트폴리오를 공유하는 곳입니다
	    </div>
	
       <%--  <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Portfolio
                    <small>Subheading</small>
                </h1>
                <ol class="breadcrumb">
				<li><a href="<%=cp %>/index.jsp">Home</a></li>
                    <li class="active">Portfolio</li>
                </ol>
            </div>
        </div> --%>
        <!-- /.row -->

			<div>
				<c:if test="${dataCount!=0 }">
					<div style="clear: both; height: 30px; line-height: 30px;">
						<div style="float: left;">${dataCount}개(${page}/${total_page}
							페이지)</div>
						<div style="float: right;">&nbsp;</div>
					</div>

					<div style="clear: both;">
						<c:forEach var="dto" items="${list}" varStatus="status">
							<c:if test="${status.index==0}">
								<c:out
									value="<div style='clear: both; margin: 0px auto;'>"
									escapeXml="false" />
							</c:if>
							<c:if test="${status.index!=0 && status.index%3==0}">
								<c:out
									value="</div><div style='clear: both; margin: 0px auto;'>"
									escapeXml="false" />
							</c:if>
							<div class="col-md-4 img-portfolio" >
		                     <img class="img-responsive img-hover" src="<%=cp%>/uploads/portfolio/${dto.imageFilename}" onclick="javascript:article('${dto.num}');" style="height: 150px">
				             	<h3><span class="subject" onclick="javascript:article('${dto.num}');" >
				                   ${dto.subject}
				             </span></h3>
				             <p>'${dto.content}'</p>
				     	  </div>
						</c:forEach>
					</div>
				</c:if>

				<div class="paging"
					style="text-align: center; min-height: 50px; line-height: 50px;">
					<c:if test="${dataCount==0 }">
	                  등록된 게시물이 없습니다.
	            </c:if>
					<c:if test="${dataCount!=0 }">
	                ${paging}
	            </c:if>
				</div>

				<div style="clear: both;">
					<%-- <div style="float: left; width: 20%; min-width: 85px;">
	        		    <button type="button" class="btn btn-default btn-sm wbtn" onclick="javascript:location.href='<%=cp%>/docu/list.sst';">새로고침</button>
	        		</div> --%>
					<!-- <div style="float: left; width: 20%; min-width: 85px;">
						&nbsp;</div>
					<div style="float: left; width: 60%; text-align: center;">
						&nbsp;</div> -->
					<div
						style="float: left; margin-left: 700px;  width: 20%; min-width: 85px; text-align: right;">
						<button style=""type="button" class="write" onclick="javascript:location.href='<%=cp%>/portfolio/created.sst';">
	        		 		 <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 글쓰기 </button>
			<%-- 			<button type="button" class="btn btn-primary btn-sm bbtn"
							onclick="javascript:location.href='<%=cp%>/portfolio/created.sst';">
							<span class="glyphicon glyphicon glyphicon-pencil"></span> 글쓰기
						</button> --%>
					</div>
				</div>
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
	</div>

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