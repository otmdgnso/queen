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
<title>study</title>
<!-- Bootstrap Core CSS -->
    <link href="<%=cp%>/res/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
// enctype="multipart/form-data" 파일첨부 안할건데 이거쓰면 에러남 1시간30분동안 못잡음
    function check() {
        var f = document.boardForm;

    	var str = f.resumeSubject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.resumeSubject.focus();
            return false;
        }

    	str = f.resumeCompany.value;
        if(!str) {
            alert("지원회사를 입력하세요. ");
            f.resumeCompany.focus();
            return false;
        }
        
        str = f.resumeDate.value;
        if(!str) {
            alert("지원시기를 입력하세요. ");
            f.resumeDate.focus();
            return false;
        }
        
        str = f.resumeJob.value;
        if(!str) {
            alert("지원직무를 입력하세요. ");
            f.resumeJob.focus();
            return false;
        }
        
        str = f.resumeSchool.value;
        if(!str) {
            alert("출신학교를 입력하세요. ");
            f.resumeSchool.focus();
            return false;
        }
        
        str = f.resumeMajor.value;
        if(!str) {
            alert("전공을 입력하세요. ");
            f.resumeMajor.focus();
            return false;
        }
        
        str = f.resumeScore.value;
        if(!str) {
            alert("학점을 입력하세요. ");
            f.resumeScore.focus();
            return false;
        }
        
        str = f.resumeLanguage.value;
        if(!str) {
            alert("어학성적을 입력하세요. ");
            f.resumeLanguage.focus();
            return false;
        }
        
        str = f.resumeEx.value;
        if(!str) {
            alert("대외활동 내용을 입력하세요. ");
            f.resumeEx.focus();
            return false;
        }
        
        str = f.resumeAbility.value;
        if(!str) {
            alert("강종 역량을 입력하세요. ");
            f.resumeAbility.focus();
            return false;
        }
        
        str = f.resumeContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.resumeContent.focus();
            return false;
        }

    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/resume/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/resume/update_ok.sst";

    	// image 버튼, submit은 submit() 메소드 호출하면 두번전송
        return true;
    }
</script>
</head>
<body>
   <!-- Navigation -->
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>

	
	<div class="container" role="main">
		<div class="bodyFrame col-sm-10"
			style="float: none; margin-left: auto; margin-right: auto;">


		<div class="body-title">
	          <h3><span class="glyphicon glyphicon-book"></span> 자소성 정보 등록 </h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> 합격 자기소개서의 상세 정보를 등록합니다.
	    </div>
	    
	    
			<div>
				<form name="boardForm" method="post" onsubmit="return check();">
					<div class="bs-write">
						<table class="table">
							<tbody>
								<tr>
									<td class="td1">작성자명</td>
									<td class="td2 col-md-5 col-sm-5">
										<p class="form-control-static">${sessionScope.member.memId}</p>
									</td>
									<td class="td1" align="center">&nbsp;</td>
									<td class="td2 col-md-5 col-sm-5">&nbsp;</td>

								</tr>
								<tr>
									<td class="td1">제목</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeSubject" class="form-control input-sm"
										value="${dto.resumeSubject}" required="required"></td>
								</tr>
								
								<tr>
									<td class="td1">지원회사</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeCompany" class="form-control input-sm"
										value="${dto.resumeCompany}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">지원시기</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeDate" class="form-control input-sm"
										value="${dto.resumeDate}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">지원직무</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeJob" class="form-control input-sm"
										value="${dto.resumeJob}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">출신학교</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeSchool" class="form-control input-sm"
										value="${dto.resumeSchool}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">전공</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeMajor" class="form-control input-sm"
										value="${dto.resumeMajor}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">학점</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeScore" class="form-control input-sm"
										value="${dto.resumeScore}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">어학성적</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeLanguage" class="form-control input-sm"
										value="${dto.resumeLanguage}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">대외활동</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeEx" class="form-control input-sm"
										value="${dto.resumeEx}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">강조역량</td>
									<td colspan="3" class="td3"><input type="text"
										name="resumeAbility" class="form-control input-sm"
										value="${dto.resumeAbility}" required="required"></td>
								</tr>
								
								
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">내용</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="resumeContent"
											class="form-control" rows="7" required="required">${dto.resumeContent}</textarea>
									</td>
								</tr>

							</tbody>
							<tfoot>
								<tr>
									<td colspan="4" style="text-align: center; padding-top: 15px;">
										<button type="submit" class="btn btn-primary">
											확인 <span class="glyphicon glyphicon-ok"></span>
										</button>
										<button type="button" class="btn btn-danger"
											onclick="javascript:location.href='<%=cp%>/resume/list.sst';"> 취소</button>
											 <c:if test="${mode=='update'}">
											      <input type="hidden" name="resumeNum" value="${dto.resumeNum}">
											      <input type="hidden" name="memId" value="${dto.memId}">
											      <input type="hidden" name="page" value="${page}">
										  </c:if>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>
				</form>
			</div>

		</div>
	</div>


	
    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>
</html>