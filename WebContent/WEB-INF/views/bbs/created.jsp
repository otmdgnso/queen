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
        var f = document.shareForm;

    	var str = f.shareSubject.value;
        if(!str) {
            f.shareSubject.focus();
            return false;
        }
        
    	str = f.shareContent.value;
        if(!str) {
            f.shareContent.focus();
            return false;
        }

    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/bbs/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/bbs/update_ok.sst";

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
	          <h3  style="font-size:30px;"><span class="glyphicon glyphicon-book"></span>취업정보 나눔 게시판 글쓰기 </h3>
	    </div>
        <div class="alert alert-info" >
	         <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span> &nbsp;&nbsp;&nbsp; 글을 작성해주세요.
	    </div>
			<div>
				<form name="shareForm" method="post" onsubmit="return check();">
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
										name="shareSubject" class="form-control input-sm"
										value="${dto.shareSubject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">설명</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="shareContent"
											class="form-control" rows="7" required="required">${dto.shareContent}</textarea>
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
											onclick="javascript:location.href='<%=cp%>/bbs/list.sst';"> 취소</button>
											 <c:if test="${mode=='update'}">
											      <input type="hidden" name="shareNum" value="${dto.shareNum}">
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