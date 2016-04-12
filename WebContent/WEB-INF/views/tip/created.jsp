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
				      <!-- 내용 넣기 -->
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">회 사 명</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipName" size="75" maxlength="100" class="boxTF" value="${dto.tipName}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">웹 사 이 트</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipWeb" size="75" maxlength="100" class="boxTF" value="${dto.tipWeb}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">기 업 형 태</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipForm" size="75" maxlength="100" class="boxTF" value="${dto.tipForm}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					  
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">설 립 일</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipDate" size="75" maxlength="100" class="boxTF" value="${dto.tipDate}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">산 업 군</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipIndustry" size="75" maxlength="100" class="boxTF" value="${dto.tipIndustry}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">매 출 액</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipSales" size="75" maxlength="100" class="boxTF" value="${dto.tipSales}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">초 봉 평 균</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipSalary" size="75" maxlength="100" class="boxTF" value="${dto.tipSalary}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
				      
				       <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">평&nbsp;&nbsp;&nbsp;점</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipScore" size="75" maxlength="100" class="boxTF" value="${dto.tipScore}">
					      </td>
					  </tr>
					  <tr><td colspan="2" height="1" bgcolor="#DBDBDB"></td></tr>
					  
					   <tr align="left" height="40"> 
					      <td width="100" bgcolor="#EEEEEE" style="text-align: center;">잡 플 래 닛</td>
					      <td width="500" style="padding-left:10px;"> 
					        <input type="text" name="tipPlanet" size="75" maxlength="100" class="boxTF" value="${dto.tipPlanet}">
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