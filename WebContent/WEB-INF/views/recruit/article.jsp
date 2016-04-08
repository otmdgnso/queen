<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
   String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>글 보기</title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=cp%>/res/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript">

function deleteRecruit(){
	var recruitNum=${dto.recruitNum};
	var params="recruitNum="+recruitNum;
	var url="<%=cp%>/recruit/delete.sst?"+params;
	
	if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
	    	location.href=url;
}






</script>



</head>

<body>

   <!-- Navigation -->
   <div>
     <jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   </div>

    <div class="container" role="main">
    <div class="bodyFrame col-sm-10"  style="float:none; margin-left: auto; margin-right: auto;">

       <div class="body-title">
             <h3><span class="glyphicon glyphicon-book"></span> 게시판 </h3>
       </div>
       
       <div class="alert alert-info">
           <i class="glyphicon glyphicon-info-sign"></i> 기업별 상세 채용 내용을 확인하는 공간입니다.
       </div>
       
       <div class="table-responsive" style="clear: both;">
           <div class="bbs-article">
               <table class="table">
                    <thead>
                        <tr>
                            <th colspan="2" style="text-align: center;">
                                   [${dto.recruitCompany}] ${dto.recruitHead} 채용
                            </th>
                        </tr>
                   <thead>
                    <tbody>
                        <tr>
                            <td style="text-align: left;">
                                	작성자 : ${dto.memId}
                            </td>
                            <td style="text-align: right;">
                             ${dto.recruitCreated}
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2" style="height: 230px;">
                             	  접수 기간 : ${dto.recruitStart} ~ ${dto.recruitEnd}<br>
                             	  지원 자격 : ${dto.recruitQual}<br>
                             	  전형 절차 : ${dto.recruitStep}<br><br>
                                 <img src="<%=cp%>/uploads/recruit/${dto.recruitImg}" style="max-width:100%; height:auto; resize:both;">
                             </td>
                        </tr>
                        <tr>
                        	<td style="text-align: center;">
								<a href='#'>${dto.recruitCompany} 에 대한 상세 정보를 알고 싶다면 클릭!</a>                        	
                        	</td>
                        </tr>
                        
                   </tbody>
                   <tfoot>
                      <tr>
                         <td>           
                         <c:if test="${sessionScope.member.memId=='admin'}">	               
                                 <input type="button" value="수정" onclick="javascript:location.href='<%=cp%>/recruit/update.sst?recruitNum=${dto.recruitNum}';">
                                 <input type="button" value="삭제" class="btn btn-default btn-sm wbtn" onclick="deleteRecruit();">
                         </c:if>
                         </td>
                         <td align="right">
					           <input type="image" src="<%=cp%>/res/image/btn_list.gif" onclick="javascript:location.href='<%=cp%>/recruit/recruit.sst';">
					    </td>
                      </tr>
                   </tfoot>
               </table>
          </div>
          
          <div class="bbs-reply">
              <div class="bbs-reply-write">
                  <div style="clear: both;">
                        <div style="float: left;"><span style="font-weight: bold;">댓글쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span></div>
                        <div style="float: right; text-align: right;"></div>
                  </div>
                  <div style="clear: both; padding-top: 10px;">
                      <textarea id="content" class="form-control" rows="3" required="required"></textarea>
                  </div>
                  <div style="text-align: right; padding-top: 10px;">
                      <button type="button" class="btn btn-primary btn-sm" onclick="sendReply();"> 댓글등록 <span class="glyphicon glyphicon-ok"></span></button>
                  </div>           
              </div>
          
              <div id="recruitReply"></div>
          </div>
          
      </div>

    </div>
</div>
 <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>
</body>

</html>