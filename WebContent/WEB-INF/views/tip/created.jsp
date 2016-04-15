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

    	var str = f.tipSubject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.tipSubject.focus();
            return false;
        }

    	str = f.tipContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.tipContent.focus();
            return false;
        }
        
        str = f.tipContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.tipContent.focus();
            return false;
        }
        
        str = f.tipContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.tipContent.focus();
            return false;
        }
        
        str = f.tipContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.tipContent.focus();
            return false;
        }

    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/tip/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/tip/update_ok.sst";

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
	          <h3><span class="glyphicon glyphicon-book"></span> 개발 Tip 등록 </h3>
	    </div>
	    
	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> 자신만의 개발 노하우를 공유하는 게시판입니다.
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
										name="tipSubject" class="form-control input-sm"
										value="${dto.tipSubject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">개발분야</td>
									<td colspan="3" class="td3">
									<select name="tipHead" >
									<option value="java" ${dto.tipHead=="java"?"selected='selected'" : ""}>JAVA</option>
									<option value="css" ${dto.tipHead=="css"?"selected='selected'" : ""}>CSS</option>
									<option value="javascript" ${dto.tipHead=="javascript"?"selected='selected'" : ""}>JAVAScript</option>
									<option value="c" ${dto.tipHead=="c"?"selected='selected'" : ""}>C</option>
									<option value="cpp" ${dto.tipHead=="cpp"?"selected='selected'" : ""}>C++</option>
									<option value="c#" ${dto.tipHead=="c#"?"selected='selected'" : ""}>C#</option>
									<option value="sql" ${dto.tipHead=="sql"?"selected='selected'" : ""}>SQL</option>
									</select>
										</td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">내용</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="tipContent"
											class="form-control" rows="7" required="required">${dto.tipContent}</textarea>
									</td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">소스코드</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="tipSource"
											class="form-control" rows="7" required="required">${dto.tipSource}</textarea>
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
											onclick="javascript:location.href='<%=cp%>/tip/list.sst';"> 취소</button>
											 <c:if test="${mode=='update'}">
											      <input type="hidden" name="tipNum" value="${dto.tipNum}">
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