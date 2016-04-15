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

<script type="text/javascript">
    function check() {
        var f = document.qnaForm;

    	var str = f.qnaSubject.value;
        if(!str) {
            alert("제목을 입력하세요. ");
            f.qnaSubject.focus();
            return false;
        }

    	str = f.qnaContent.value;
        if(!str) {
            alert("내용을 입력하세요. ");
            f.qnaContent.focus();
            return false;
        }

    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/qna/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/qna/update_ok.sst";

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

			<div>
				<form name="qnaForm" method="post" onsubmit="return check();">
					<div class="bs-write">
						<table class="table">
							<tbody>
							<tr>
									<td class="td1">말머리</td>
									<td colspan="3" class="td3">
									<select name="qnaHead" >
									<option value="java" ${dto.qnaHead=="java"?"selected='selected'" : ""}>JAVA</option>
									<option value="css" ${dto.qnaHead=="css"?"selected='selected'" : ""}>CSS</option>
									<option value="javascript" ${dto.qnaHead=="javascript"?"selected='selected'" : ""}>JAVAScript</option>
									<option value="c" ${dto.qnaHead=="c"?"selected='selected'" : ""}>C</option>
									<option value="cpp" ${dto.qnaHead=="cpp"?"selected='selected'" : ""}>C++</option>
									<option value="c#" ${dto.qnaHead=="c#"?"selected='selected'" : ""}>C#</option>
									<option value="sql" ${dto.qnaHead=="sql"?"selected='selected'" : ""}>SQL</option>
									</select>
										</td>
							</tr>
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
										name="qnaSubject" class="form-control input-sm"
										value="${dto.qnaSubject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">설명</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="qnaContent"
											class="form-control" rows="7" required="required">${dto.qnaContent}</textarea>
									</td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">소스코드</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="qnaSource"
									 class="form-control" rows="7" >${dto.qnaSource}</textarea>
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
											onclick="javascript:location.href='<%=cp%>/qna/list.sst';">
											취소</button> <c:if test="${mode=='update'}">
											<input type="hidden" name="qnaNum" value="${dto.qnaNum}">
											<input type="hidden" name="memId" value="${dto.memId}">
											<input type="hidden" name="page" value="${page}">
											<input type="hidden" name="qnaHead" value="${dto.qnaHead}">
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