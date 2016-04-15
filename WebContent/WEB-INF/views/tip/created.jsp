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
<link rel="stylesheet" href="<%=cp%>/res/css/style.css" type="text/css">
<link rel="stylesheet" href="<%=cp%>/res/css/layout/layout.css" type="text/css">

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
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

	
	<div class="layoutBody">

		<div style="min-height: 450px;">
				<div style="width:100%;	height: 40px; line-height:40px;clear: both; border-top: 1px solid #DAD9FF;border-bottom: 1px solid #DAD9FF;">
				    <div style="width:600px; height:30px; line-height:30px; margin:5px auto;">
				        <img src="<%=cp%>/res/image/arrow.gif" alt="" style="padding-left: 5px; padding-right: 5px;">
				        <span style="font-weight: bold;font-size:13pt;font-family: 나눔고딕, 맑은 고딕, 굴림;">게시판</span>
				    </div>
				</div>
			
				<div style="margin: 10px auto; margin-top: 20px; width:600px; min-height: 400px;">
		
					<form name="boardForm" method="post" onsubmit="return check();">
					  <table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					  <tr><td colspan="2" height="3" bgcolor="#507CD1"></td></tr>
					
					<tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">말 머 리</td>
					      <td width="500" style="padding-left:10px;"> 
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
					  
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					
					  <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipSubject" size="75" maxlength="100" class="boxTF" value="${dto.tipSubject}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					  <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">작 성 자</td>
					      <td width="500" style="padding-left:10px;"> 
					        ${sessionScope.member.memId}
					      </td>
					  </tr>
				      <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				    
					  <tr align="left"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
					      <td width="500" valign="top" style="padding:5px 0px 5px 10px;"> 
					        <textarea name="tipContent" cols="75" rows="12" class="boxTA">${dto.tipContent}</textarea>
					      </td>
					  </tr>
					  <tr><td colspan="2" height="3" bgcolor="#507CD1"></td></tr>
					  
					  <tr align="left"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center; padding-top:5px;" valign="top">소스코드</td>
					      <td width="500" valign="top" style="padding:5px 0px 5px 10px;"> 
					        <textarea name="tipSource" cols="75" rows="12" class="boxTA">${dto.tipSource}</textarea>
					      </td>
					  </tr>
					  <tr><td colspan="2" height="3" bgcolor="#507CD1"></td></tr>
					  </table>
					
					  <table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					     <tr height="45"> 
					      <td align="center" >
						    <input type="image" src="<%=cp%>/res/image/btn_submit.gif" >
		        		    <a href="javascript:location.href='<%=cp%>/tip/list.sst';"><img src="<%=cp%>/res/image/btn_cancel.gif" border="0"></a>
		
							<c:if test="${mode=='update'}">
								<input type="hidden" name="tipNum" value="${dto.tipNum}">
								<input type="hidden" name="page" value="${page}">
							</c:if>
		
					      </td>
					    </tr>
					  </table>
					</form>
				</div>
		</div>

    </div>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>
</html>