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

<!-- Bootstrap Core CSS -->
<link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">

<style type="text/css">
</style>

<script type="text/javascript">
function check() {
      var f = document.portfolioForm;

  	var str = f.subject.value;
      if(!str) {
          f.subject.focus();
          return false;
      }

  	  str = f.content.value;
      if(!str) {
          f.content.focus();
          return false;
      }

      var mode="${mode}";
  	  if(mode=="created"||mode=="update" && f.upload.value!="") {
  		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload.value)) {
  			alert('1이미지 파일만 가능합니다. !!!');
  			f.upload.focus();
  			return false;
  		}
  		 if(f.upload2.value!="") {
  	  		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload2.value)) {
  	  			alert('2이미지 파일만 가능합니다. !!!');
  	  			f.upload2.focus();
  	  			return false;
  	  		}
  		 }
  	  		 if(f.upload3.value!="") {
  	    		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload3.value)) {
  	      			alert('3이미지 파일만 가능합니다. !!!');
  	      			f.upload.focus();
  	      			return false;
  	      		}
  	  		 }
  	  		 if(f.upload4.value!="") {
  	  		if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload4.value)) {
  	  			alert('4이미지 파일만 가능합니다. !!!');
  	  			f.upload.focus();
  	  			return false;
  	  	  }
  	  		 }
  	  	 }
  	  
      
  	  if(mode=="created")
  		f.action="<%=cp%>/portfolio/created_ok.sst";
  	  else if(mode=="update")
  		f.action="<%=cp%>/portfolio/update_ok.sst";
		return true;
	}

	function imageViewer(img) {
		var preViewer = $("#imageViewModal .modal-body");
		var s = "<img src='"+img+"' width='570' height='450'>";
		preViewer.html(s);

		$('#imageViewModal').modal('show');
	}
</script>

</head>
<body>

	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>

	<div class="container" role="main">
		<div class="bodyFrame col-sm-10"
			style="float: none; margin-left: auto; margin-right: auto;">

			<div>
				<form name="portfolioForm" method="post" onsubmit="return check();"
					enctype="multipart/form-data">
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
										name="subject" class="form-control input-sm"
										value="${dto.subject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">설명</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="content"
											class="form-control" rows="7" required="required">${dto.content}</textarea>
									</td>
								</tr>

								<tr>
									<td class="td1">이미지</td>
									<td colspan="3" class="td3"><input type="file"
										name="upload" class="form-control input-sm"></td>
								</tr>
								<tr>
									<td class="td1">이미지</td>
									<td colspan="3" class="td3"><input type="file"
										name="upload2" class="form-control input-sm"></td>
								</tr>
								<tr>
									<td class="td1">이미지</td>
									<td colspan="3" class="td3"><input type="file"
										name="upload3" class="form-control input-sm"></td>
								</tr>
								<tr>
									<td class="td1">이미지</td>
									<td colspan="3" class="td3"><input type="file"
										name="upload4" class="form-control input-sm"></td>
								</tr>

								<c:if test="${mode=='update'}">
									<tr>
										<td class="td1">등록이미지</td>
										<td class="td3"><img
											src="<%=cp%>/uploads/portfolio/${dto.imageFilename}"
											width="30" height="30" border="0"
											onclick="imageViewer('<%=cp%>/uploads/portfolio/${dto.imageFilename}');"
											style="cursor: pointer;">
											<c:if test="${dto.imgCnt>1}">
											<img
											src="<%=cp%>/uploads/portfolio/${dto.imageFilename2}"
											height="30" border="0"
											onclick="imageViewer('<%=cp%>/uploads/portfolio/${dto.imageFilename2}');"
											style="cursor: pointer;">
											</c:if>
											<c:if test="${dto.imgCnt>2}">
											<img
											src="<%=cp%>/uploads/portfolio/${dto.imageFilename3}"
											height="30" border="0"
											onclick="imageViewer('<%=cp%>/uploads/portfolio/${dto.imageFilename3}');"
											style="cursor: pointer;">
											</c:if>
											<c:if test="${dto.imgCnt>3}">
											<img
											src="<%=cp%>/uploads/portfolio/${dto.imageFilename4}"
											height="30" border="0"
											onclick="imageViewer('<%=cp%>/uploads/portfolio/${dto.imageFilename4}');"
											style="cursor: pointer;">
											</c:if>
											</td>
									</tr>
								</c:if>

							</tbody>
							<tfoot>
								<tr>
									<td colspan="4" style="text-align: center; padding-top: 15px;">
										<button type="submit" class="btn btn-primary">
											확인 <span class="glyphicon glyphicon-ok"></span>
										</button>
										<button type="button" class="btn btn-danger"
											onclick="javascript:location.href='<%=cp%>/portfolio/list.sst';">
											취소</button> <c:if test="${mode=='update'}">
											<input type="hidden" name="num" value="${dto.num}">
											<input type="hidden" name="memId" value="${dto.memId}">
											<input type="hidden" name="imageFilename"
												value="${dto.imageFilename}">
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
					<h4 class="modal-title" id="imageViewModalLabel"
						style="font-family: 나눔고딕, 맑은 고딕, sans-serif; font-weight: bold;">등록
						이미지</h4>
				</div>
				<div class="modal-body"></div>
			</div>
		</div>
	</div>

	<!-- jQuery -->
	<script src="<%=cp%>/res/js/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>
</html>