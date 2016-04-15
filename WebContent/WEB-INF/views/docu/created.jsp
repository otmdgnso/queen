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

	<!-- SYNTAX HIGHLIGHT -->
	<link rel="stylesheet" href="<%=cp%>/res/css/prettify.css" />
<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
// enctype="multipart/form-data" 파일첨부 안할건데 이거쓰면 에러남 1시간30분동안 못잡음
    function check() {
        var f = document.docuForm;

    	var str = f.docuSubject.value;
        if(!str) {
            f.docuSubject.focus();
            return false;
        }
        
    	str = f.docuContent.value;
        if(!str) {
            f.docuContent.focus();
            return false;
        }

    	var mode="${mode}";
    	if(mode=="created")
    		f.action="<%=cp%>/docu/created_ok.sst";
    	else if(mode=="update")
    		f.action="<%=cp%>/docu/update_ok.sst";

    	// image 버튼, submit은 submit() 메소드 호출하면 두번전송
        return true;
    }
    <c:if test="${mode=='update'}">
    function deleteFile(docuNum) {
  
  	  var url="<%=cp%>/docu/deleteFile.sst?docuNum="+docuNum+"&page=${page}";
  	  location.href=url;
    }
  </c:if>
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
	          <h3><span class="glyphicon glyphicon-book"></span>수업자료 게시판 글쓰기 </h3>
	    </div>
        <div class="alert alert-info" >
	         <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span> &nbsp;&nbsp;&nbsp;수업과 관련된 글과 자료만 작성해주세요
	    </div>
			<div>
			 
				<form name="docuForm" method="post" onsubmit="return check();" enctype="multipart/form-data">
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
										name="docuSubject" class="form-control input-sm"
										value="${dto.docuSubject}" required="required"></td>
								</tr>
								<tr>
									<td class="td1" colspan="4" style="padding-bottom: 0px;">설명</td>
								</tr>
								<tr>
									<td colspan="4" class="td4"><textarea name="docuContent"
											class="form-control" rows="7" required="required">${dto.docuContent}</textarea>
									</td>
								</tr>
                             <tr>
			                    <td class="td1">첨부</td>
				                <td colspan="3" class="td3">
					                <input type="file" name="upload" class="form-control input-sm">
						        </td>
							</tr>

		                   <c:if test="${mode=='update'}">
		                    <tr> 
		                        <td class="td1">첨부된파일</td>
		                        <td colspan="3" class="td3"> 
		                            <c:if test="${not empty dto.docuFile}">
		                                ${dto.originalFilename}
		                                | <a href="javascript:deleteFile('${dto.docuNum}');">삭제</a>
		                            </c:if>	        
		                        </td>
		                    </tr>
		                   </c:if>			
								


							</tbody>
							<tfoot>
								<tr>
									<td colspan="4" style="text-align: center; padding-top: 15px;">
										<button type="submit" class="btn btn-info btn-sm btn-search">
											확인 <span class="glyphicon glyphicon-ok"></span>
										</button>
										<button type="button" class="btn btn-default btn-sm wbtn"
											onclick="javascript:location.href='<%=cp%>/docu/list.sst';"> 취소</button>
											 <c:if test="${mode=='update'}">
											      <input type="hidden" name="docuNum" value="${dto.docuNum}">
											      <input type="hidden" name="memId" value="${dto.memId}">
											      <input type="hidden" name="fileSize" value="${dto.fileSize}">
	                                              <input type="hidden" name="docuFile" value="${dto.docuFile}">
	                                              <input type="hidden" name="originalFilename" value="${dto.originalFilename}">
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