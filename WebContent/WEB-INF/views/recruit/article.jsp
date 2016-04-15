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

    <title>사자의 심장을 가져라 </title>

    <!-- Bootstrap Core CSS -->
    <link href="<%=cp %>/res/css/bootstrap.css" rel="stylesheet">

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

	 <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript">

function deleteRecruit(){
	var recruitNum=${dto.recruitNum};
	var params="recruitNum="+recruitNum;
	var url="<%=cp%>/recruit/delete.sst?"+params;
	
	if(confirm("위 자료를 삭제 하시 겠습니까 ? "))
	    	location.href=url;
}

//-- 댓글 ------------------------------------
//댓글 리스트
$(function(){
	listPage(1);
});

function listPage(page) {
	var url="<%=cp%>/recruit/listReply.sst";
	var recruitNum="${dto.recruitNum}";
	$.post(url, {recruitNum:recruitNum, pageNo:page}, function(data){
		$("#listReply").html(data);
	});
}
//리플 저장

function sendReply(){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	var recruitNum="${dto.recruitNum}"; //해당 게시물 번호
	var recruitR_content=$.trim($("#recruitR_content").val());
	if(!recruitR_content){
		alert("댓글내용을  입력하세요!");
		$("#recruitR_content").focus();
		return false;
	}
	
	var params="recruitNum="+recruitNum;
	params+="&recruitR_content="+recruitR_content;
	
	$.ajax({
		type:"POST"
		,url:"<%=cp%>/recruit/insertReply.sst"
		,data:params
		,dataType:"json"
		,success:function(data) {
			$("#recruitR_content").val("");
			
			var state=data.state;
			if(state=="true") {
				listPage(1);
			} else if(state=="false") {
				alert("댓글을 등록하지 못했습니다. !!!");
			} else if(state=="loginFail") {
				login();
			}
		}
		,error:function(e) {
			alert(e.responseText);
		}
	});
}
//댓글 삭제
function deleteReply(recruitR_num, pageNo, memId){
	var mId="${sessionScope.member.memId}";
	if(! mId){
		login();
		return false;
	}
	
	if(confirm("게시물을 삭제하시겠습니까?")){
		var url="<%=cp%>/recruit/deleteReply.sst";
		$.post(url,{recruitR_num:recruitR_num, memId:memId}, function(data){
			var state=data.state;
			if(state=="loginFail"){
				login();
			}else{
				listPage(pageNo);
			}
			
		},"json");
	}
}




</script>

<style type="text/css">

.bbs-reply{
  font-family: NanumGothic, 나눔고딕, "Malgun Gothic", "맑은 고딕", 돋움, sans-serif;
    border-top: #687ead solid 2px; 
    border-bottom: #687ead solid 2px; padding:15px;
    margin-bottom:70px;
}

.bbs-reply-write {
    border-bottom: #ddd solid 2px; 
    padding: 10px;
    min-height: 50px;
}
</style>

</head>

<body>

   <!-- Navigation -->
   <div>
     <jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
   </div>

    <div class="container" role="main">
    <div class="col-sm-10_2"  style="float:none; margin-left: auto; margin-right: auto;">

       <div class="body-title">
             <h3  style="font-size:30px;"><span class="glyphicon glyphicon-book"></span> &nbsp; 공채일정  </h3>
       </div>
       
       <div class="alert alert-info">
           <i class="glyphicon glyphicon-info-sign"></i>&nbsp;&nbsp;&nbsp; 기업별 상세 채용 내용을 확인하는 공간입니다
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
								<a href="${companyUrl}">${dto.recruitCompany} 에 대한 상세 정보를 알고 싶다면 클릭!</a>                        	
                        	</td>
                        </tr>
                        
                   </tbody>
                   <tfoot>
                      <tr>
                         <td>           
                         <c:if test="${sessionScope.member.memId=='admin'}">	               
                                 <input type="button" value="수정" class="btn btn-default btn-sm wbtn" onclick="javascript:location.href='<%=cp%>/recruit/update.sst?recruitNum=${dto.recruitNum}';">
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
                      <textarea id="recruitR_content" class="form-control" rows="3" required="required"></textarea>
                  </div>
                  <div style="text-align: right; padding-top: 10px;">
                      <button type="button" class="btn btn-primary btn-sm" onclick="sendReply();"> 댓글등록 <span class="glyphicon glyphicon-ok"></span></button>
                  </div>           
              </div>
          
              <div id="listReply"></div>
          </div>
          
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