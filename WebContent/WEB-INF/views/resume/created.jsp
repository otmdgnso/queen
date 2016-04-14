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
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeSubject" size="75" maxlength="100" class="boxTF" value="${dto.resumeSubject}">
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
				      <!-- 내용 넣기 -->
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">지원회사</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeCompany" size="75" maxlength="100" class="boxTF" value="${dto.resumeCompany}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">지원시기</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeDate" size="75" maxlength="100" class="boxTF" value="${dto.resumeDate}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">지원직무</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeJob" size="75" maxlength="100" class="boxTF" value="${dto.resumeJob}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">출신학교</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeSchool" size="75" maxlength="100" class="boxTF" value="${dto.resumeSchool}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					  
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">전&nbsp;&nbsp;&nbsp;&nbsp;공</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeMajor" size="75" maxlength="100" class="boxTF" value="${dto.resumeMajor}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  				               
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">학&nbsp;&nbsp;&nbsp;&nbsp;점</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeScore" size="75" maxlength="100" class="boxTF" value="${dto.resumeScore}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">어학성적</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeLanguage" size="75" maxlength="100" class="boxTF" value="${dto.resumeLanguage}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">대외활동</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeEx" size="75" maxlength="100" class="boxTF" value="${dto.resumeEx}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">강조역량</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="resumeAbility" size="75" maxlength="100" class="boxTF" value="${dto.resumeAbility}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      	      
				      
				      
					  <tr align="left"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center; padding-top:5px;" valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
					      <td width="500" valign="top" style="padding:5px 0px 5px 10px;"> 
					        <textarea name="resumeContent" cols="75" rows="12" class="boxTA">${dto.resumeContent}</textarea>
					      </td>
					  </tr>
					  <tr><td colspan="2" height="3" bgcolor="#507CD1"></td></tr>
					  </table>
					
					  <table style="width: 600px; margin: 0px auto; border-spacing: 0px;">
					     <tr height="45"> 
					      <td align="center" >
						    <input type="image" src="<%=cp%>/res/image/btn_submit.gif" >
		        		    <a href="javascript:location.href='<%=cp%>/resume/list.sst';"><img src="<%=cp%>/res/image/btn_cancel.gif" border="0"></a>
		
							<c:if test="${mode=='update'}">
								<input type="hidden" name="resumeNum" value="${dto.resumeNum}">
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