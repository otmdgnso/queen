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
    <link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

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
        var f = document.wantedForm;

    	var str = f.wantedSubject.value;
        if(!str) {
            f.wantedSubject.focus();
            return false;
        }
        
    	str = f.wantedContent.value;
        if(!str) {
            f.wantedContent.focus();
            return false;
        }

    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/wanted/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/wanted/update_ok.sst";

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

			<div>
				<form name="wantedForm" method="post" onsubmit="return check();">
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
										name="wantedSubject" class="form-control input-sm"
										value="${dto.wantedSubject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1">말머리</td>
									<td colspan="3" class="td3">
									<select name="wantedHead" >
									<option value="자바" ${dto.wantedHead=="자바"?"selected='selected'" : ""}>자바</option>
									<option value="스프링" ${dto.wantedHead=="스프링"?"selected='selected'" : ""}>스프링</option>
									<option value="JSP" ${dto.wantedHead=="JSP"?"selected='selected'" : ""}>JSP</option>
									<option value="오라클" ${dto.wantedHead=="오라클"?"selected='selected'" : ""}>오라클</option>
									</select>
										</td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">내용</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="wantedContent"
											class="form-control" rows="7" required="required">${dto.wantedContent}</textarea>
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
											onclick="javascript:location.href='<%=cp%>/wanted/list.sst';"> 취소</button>
											 <c:if test="${mode=='update'}">
											      <input type="hidden" name="wantedNum" value="${dto.wantedNum}">
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


	<div class="modal fade" id="imageViewModal" tabindex="-1" role="dialog"
		aria-labelledby="imageViewModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>
</html>