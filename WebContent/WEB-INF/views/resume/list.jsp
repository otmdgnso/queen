<%@page import="com.resume.ResumeDAO"%>
<%@page import="com.resume.ResumeDTO"%>
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

    <title>Modern Business - Start Bootstrap Template</title>

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
    
 <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>
    
<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
	function searchList() {
		var f=document.searchForm;
		f.action="<%=cp%>/resume/list.sst";
		f.submit();
	}
	function checkWrite(resumeNum){
		var cnt="${cnt}";
		cnt=parseInt(cnt);
		if(cnt>0){
		   var url="${articleUrl}&resumeNum="+resumeNum;
		   location.href=url;
		} else {
			alert("한 번 이상 글을 작성하셔야 볼 수 있습니다.")
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
	          <h3><span class="glyphicon glyphicon-book"></span> 자소서 공유 게시판  </h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> 합격자기소개서 정보를 공유하는 게시판입니다.
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
	                        <th class="text-center" style="width: 70px;">직무</th>
	                        <th >제목</th>
	                        <th class="text-center" style="width: 100px;">글쓴이</th>
	                        <th class="text-center" style="width: 100px;">작성일</th>
	                        <th class="text-center" style="width: 70px;">조회수</th> 
	                    </tr>
	                </thead>
	                <tbody>
	                <c:forEach var="dto" items="${listBestResume}">
	                    <tr style="background-color: #FFA7A7">
                            <td class="text-center"><span style="display: inline-block;width: 28px;height:18px;line-height:18px; background: #ED4C00;color: #FFFFFF">Best</span></td>
                            <td class="text-center">${dto.resumeRecomm}</td>
	                        <td class="text-center">${dto.resumeJob}</td>
	                        <td><a href="javascript:checkWrite('${dto.resumeNum}')">${dto.resumeSubject}</a></td>
	                        <td class="text-center">${dto.memId}</td>
	                        <td class="text-center">${dto.resumeCreated}</td>
	                        <td class="text-center">${dto.resumeHitCount}</td> 
	                    </tr>
    				 </c:forEach>         
	                
	                
					  <c:forEach var="dto" items="${list}">
	                    <tr>
	                        <td class="text-center">${dto.listResumeNum}</td>
	                        <td class="text-center">${dto.resumeRecomm}</td>
	                        <td class="text-center">${dto.resumeJob}</td>
	                        <td><a href="javascript:checkWrite('${dto.resumeNum}')">${dto.resumeSubject}</a></td>
	                        <td class="text-center">${dto.memId}</td>
	                        <td class="text-center">${dto.resumeCreated}</td>
	                        <td class="text-center">${dto.resumeHitCount}</td> 
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
	        		    <button type="button" class="btn btn-default btn-sm wbtn" onclick="javascript:location.href='<%=cp%>/resume/list.sst';">새로고침</button>
	        		</div>
	        		<div style="float: left; width: 60%; text-align: center;">
	        		     <form name="searchForm" method="post" class="form-inline">
							  <select class="form-control input-sm" name="searchKey" >
							      <option value="resumeSubject">제목</option>
							      <option value="memId">작성자</option>
							      <option value="resumeContent">내용</option>
							      <option value="resumeCompany">회사명</option>
							      <option value="resumeDate">지원시기</option>
							      <option value="resumeJob">지원직무</option>
							      <option value="resumeSchool">출신학교</option>
							      <option value="resumeMajor">전공</option>
							      <option value="resumeScore">학점</option>
							      <option value="resumeLanguage">어학성적</option>
							      <option value="resumeEx">경험</option>
							      <option value="resumeAbility">강조역량</option>
							      
							  </select>
							  <input type="text" class="form-control input-sm input-search" name="searchValue">
							  <button type="button" class="btn btn-info btn-sm btn-search" onclick="searchList();"><span class="glyphicon glyphicon-search"></span> 검색</button>
	        		     </form>
	        		</div>
	        		<div style="float: left; width: 20%; min-width: 85px; text-align: right;">
	        		    <input type="image" src="<%=cp%>/res/image/btn_write.gif" onclick="javascript:location.href='<%=cp%>/resume/created.sst';">
	        		</div>
	        </div>
	    </div>

    </div>
</div>


    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>

</html>
