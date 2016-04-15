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

 <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>
    
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.action="<%=cp%>/qna/list.sst";
		f.submit();
	}
</script>

</head>

<body>

   <!-- Navigation -->
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>


<div class="container" role="main">
    <div class=" col-sm-10_2"  style="float:none; margin-left: auto; margin-right: auto;">
        
	    <div class="body-title">
	          <h3 style="font-size:30px;"><span class="glyphicon glyphicon-book"></span> 개발 Q&A </h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> &nbsp;&nbsp;&nbsp;개발 관련 질문과 답변을 위한 게시판입니다 - 코딩과 관련한 내용만 올려주세요
	    </div>
	
	    <div>
	        <div style="clear: both; height: 30px; line-height: 30px;">
	            <div style="float: left;">${dataCount}개(${page}/${total_page} 페이지)</div>
	            <div style="float: right;">&nbsp;</div>
	        </div>
	        
	        <div class="table-responsive" style="clear: both;"> <!-- 테이블 반응형 -->
	            <table class="table table-hover">
	                <thead>
	                    <tr>
	                        <th class="text-center" style="width: 70px;">번호</th>
	                        <th class="text-center" style="width: 70px;">추천수</th>
	                        <th class="text-center" style="width: 70px;">말머리</th>
	                        <th >제목</th>
	                        <th class="text-center" style="width: 100px;">글쓴이</th>
	                        <th class="text-center" style="width: 100px;">날짜</th>
	                        <th class="text-center" style="width: 70px;">조회수</th> 
	                    </tr>
	                </thead>
	                <tbody>
					  <c:forEach var="dto" items="${list}">
	                    <tr>
	                        <td class="text-center">${dto.listQnaNum}</td>
	                        <td class="text-center">${dto.qnaRecomm}</td>
	                        <td class="text-center">${dto.qnaHead}</td>
	                        <td><a href='${articleUrl}&qnaNum=${dto.qnaNum}'>${dto.qnaSubject}</a></td>
	                        <td class="text-center">${dto.memId}</td>
	                        <td class="text-center">${dto.qnaCreated}</td>
	                        <td class="text-center">${dto.qnaHitCount}</td> 
	                    </tr>
	                   </c:forEach> 
	                </tbody>
	            </table>
	        </div>
	
	        <div class="paging" style="text-align: center; min-height: 50px; line-height: 50px;">
			        <c:if test="${dataCount==0 }">
			                   등록된 게시물이 없습니다.
			         </c:if>
			        <c:if test="${dataCount!=0 }">
			               ${paging}
			         </c:if>
	        </div>        
	        
	        <div style="clear: both;">
	        		<div style="float: left; width: 20%; min-width: 85px;">
	        		    <button type="button" class="btn btn-default btn-sm wbtn" onclick="javascript:location.href='<%=cp%>/qna/list.sst';">새로고침</button>
	        		</div>
	        		<div style="float: left; width: 60%; text-align: center;">
	        		     <form name="searchForm" method="post" class="form-inline">
							  <select class="form-control input-sm" name="searchKey" >
							      <option value="questSubject">제목</option>
							      <option value="memId">작성자</option>
							      <option value="questContent">내용</option>
							      <option value="questHead">말머리</option>
							  </select>
							  <input type="text" class="form-control input-sm input-search" name="searchValue">
							  <button type="button" class="btn btn-info btn-sm btn-search" onclick="searchList();"><span class="glyphicon glyphicon-search"></span> 검색</button>
	        		     </form>
	        		</div>
	        		<div style="float: left; width: 20%; min-width: 85px; text-align: right;">
	        			<button style=""type="button" class="write" onclick="javascript:location.href='<%=cp%>/qna/created.sst';">
	        		 		 <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> 글쓰기 </button>
	        		</div>
	        </div>
	    </div>

    </div>
</div>


    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>

</html>
