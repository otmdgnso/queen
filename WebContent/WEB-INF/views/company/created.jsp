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
<title>사자의 심장을 가져라 </title>
<!-- Bootstrap Core CSS -->
    <link href="<%=cp%>/res/css/bootstrap.css" rel="stylesheet">

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

    	var str = f.companySubject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.companySubject.focus();
            return false;
        }

    	str = f.companyContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.companyContent.focus();
            return false;
        }
        
        str = f.companyName.value;
        if(!str) {
            alert("회사명을 입력하세요. ");
            f.companyName.focus();
            return false;
        }
        
        str = f.companyWeb.value;
        if(!str) {
            alert("웹사이트를 입력하세요. ");
            f.companyWeb.focus();
            return false;
        }
        
        str = f.companyForm.value;
        if(!str) {
            alert("기업형태를 입력하세요. ");
            f.companyForm.focus();
            return false;
        }
        
        str = f.companyDate.value;
        if(!str) {
            alert("설립일을 입력하세요. ");
            f.companyDate.focus();
            return false;
        }
        
        str = f.companyIndustry.value;
        if(!str) {
            alert("산업군을 입력하세요. ");
            f.companyIndustry.focus();
            return false;
        }
        
        str = f.companySales.value;
        if(!str) {
            alert("매출액을 입력하세요. ");
            f.companySales.focus();
            return false;
        }
        
        str = f.companySalary.value;
        if(!str) {
            alert("초봉 평균을 입력하세요. ");
            f.companySalary.focus();
            return false;
        }
        
        str = f.companyScore.value;
        if(!str) {
            alert("평점을 입력하세요. ");
            f.companyScore.focus();
            return false;
        }
        
        str = f.companyPlanet.value;
        if(!str) {
            alert("잡플래닛 연결 url을 입력하세요. ");
            f.companyPlanet.focus();
            return false;
        }
        
    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/company/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/company/update_ok.sst";

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
		<div class="col-sm-10_2"
			style="float: none; margin-left: auto; margin-right: auto;">


		<div class="body-title">
	          <h3 style="font-size:30px;" ><span class="glyphicon glyphicon-book"></span> 회사정보 등록 </h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> 기업의 상세 정보를 등록합니다.
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
										name="companySubject" class="form-control input-sm"
										value="${dto.companySubject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">기업형태</td>
									<td colspan="3" class="td3">
									<select name="companyForm" >
									<option value="대기업" ${dto.companyForm=="대기업"?"selected='selected'" : ""}>대기업</option>
									<option value="중소기업" ${dto.companyForm=="중소기업"?"selected='selected'" : ""}>중소기업</option>
									<option value="중견기업" ${dto.companyForm=="중견기업"?"selected='selected'" : ""}>중견기업</option>
									</select>
										</td>
								</tr>
								
								
								
								<tr>
									<td class="td1">회사명</td>
									<td colspan="3" class="td3"><input type="text"
										name="companyName" class="form-control input-sm"
										value="${dto.companyName}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">웹사이트</td>
									<td colspan="3" class="td3"><input type="text"
										name="companyWeb" class="form-control input-sm"
										value="${dto.companyWeb}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">설립일</td>
									<td colspan="3" class="td3"><input type="text"
										name="companyDate" class="form-control input-sm"
										value="${dto.companyDate}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">산업군</td>
									<td colspan="3" class="td3"><input type="text"
										name="companyIndustry" class="form-control input-sm"
										value="${dto.companyIndustry}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">매출액</td>
									<td colspan="3" class="td3"><input type="text"
										name="companySales" class="form-control input-sm"
										value="${dto.companySales}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">초봉평균</td>
									<td colspan="3" class="td3"><input type="text"
										name="companySalary" class="form-control input-sm"
										value="${dto.companySalary}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">평점</td>
									<td colspan="3" class="td3"><input type="text"
										name="companyScore" class="form-control input-sm"
										value="${dto.companyScore}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">잡플래닛</td>
									<td colspan="3" class="td3"><input type="text"
										name="companyPlanet" class="form-control input-sm"
										value="${dto.companyPlanet}" required="required"></td>
								</tr>
								
								
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">내용</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="companyContent"
											class="form-control" rows="7" required="required">${dto.companyContent}</textarea>
									</td>
								</tr>

							</tbody>
							<tfoot>
								<tr>
									<td colspan="4" style="text-align: center; padding-top: 15px;">
										<button type="submit" class="btn btn-info btn-sm btn-search">
											확인 <span class="glyphicon glyphicon-ok"></span>
										</button>
										<button type="button" class="btn btn-default btn-sm wbtn"
											onclick="javascript:location.href='<%=cp%>/company/list.sst';"> 취소</button>
											 <c:if test="${mode=='update'}">
											      <input type="hidden" name="companyNum" value="${dto.companyNum}">
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

</body>
</html>