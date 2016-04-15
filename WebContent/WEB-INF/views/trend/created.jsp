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
<title>study</title>
<!-- Bootstrap Core CSS -->
<link href="<%=cp%>/res/css/bootstrap.css" rel="stylesheet">

<!-- Custom CSS -->
<link href="<%=cp%>/res/css/modern-business.css" rel="stylesheet">

<!-- Custom Fonts -->
<link href="<%=cp%>/res/font-awesome/css/font-awesome.min.css"
   rel="stylesheet" type="text/css">

<!-- jQuery -->
<script src="<%=cp%>/res/js/jquery.js"></script>

<script type="text/javascript" src="<%=cp%>/res/js/util.js"></script>
<script type="text/javascript">
// enctype="multipart/form-data" 파일첨부 안할건데 이거쓰면 에러남 1시간30분동안 못잡음
    function check() {
        var f = document.trendForm;

       var str = f.trendSubject.value;
        if(!str) {
            f.trendSubject.focus();
            return false;
        }
        
       str = f.trendContent.value;
        if(!str) {
            f.trendContent.focus();
            return false;
        }

        var mode="${mode}";
       if(mode=="created"||mode=="update" && f.upload.value!="") {
        if(! /(\.gif|\.jpg|\.png|\.jpeg)$/i.test(f.upload.value)) {
           alert('이미지 파일만 가능합니다. !!!');
           f.upload.focus();
           return false;
        }
       }
        
       if(mode=="created")
          f.action="<%=cp%>/trend/created_ok.sst";
       else if(mode=="update")
          f.action="<%=cp%>/trend/update_ok.sst";

      // image 버튼, submit은 submit() 메소드 호출하면 두번전송
      return true;
   }
    <c:if test="${mode=='update'}">
    function deleteFile(trendNum) {
       var url="<%=cp%>/trend/deleteFile.sst?trendNum="+trendNum+"&page=${page}";
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
	          <h3><span class="glyphicon glyphicon-book"></span> IT동향 글쓰기 </h3>
	    </div>

	    <div class="alert alert-info">
	        <i class="glyphicon glyphicon-info-sign"></i> IT동향에 관한 글을 써주세요.
	    </div>

         <div>
            <form name="trendForm" method="post" onsubmit="return check();" enctype="multipart/form-data">
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
                              name="trendSubject" class="form-control input-sm"
                              value="${dto.trendSubject}" required="required"></td>
                        </tr>
                        <tr>
                           <td class="td1">말머리</td>
                           <td colspan="3" class="td3"><select name="trendHead">
                                 <option value="자바"
                                    ${dto.trendHead=="자바"?"selected='selected'" : ""}>자바</option>
                                 <option value="스프링"
                                    ${dto.trendHead=="스프링"?"selected='selected'" : ""}>스프링</option>
                                 <option value="JSP"
                                    ${dto.trendHead=="JSP"?"selected='selected'" : ""}>JSP</option>
                                 <option value="오라클"
                                    ${dto.trendHead=="오라클"?"selected='selected'" : ""}>오라클</option>
                           </select></td>
                        </tr>
                        <tr>
                           <td class="td1" colspan="4" style="padding-bottom: 0px;">내용</td>
                        </tr>
                        <tr>
                           <td colspan="4" class="td4"><textarea name="trendContent"
                                 class="form-control" rows="7" required="required">${dto.trendContent}</textarea>
                           </td>
                        </tr>
                        
                        <tr>
      <td colspan="4">
       <label class="col-sm-2 control-label" for="imageFilename">이미지</label>
       <input type="file" name="upload" class="form-control input-sm">
        <p class="help-block">jpg, png, gif의 확장자를 가진 이미지 파일만 가능합니다.</p>
        </td>

                        </tr>
                      <c:if test="${mode=='update'}">
                               <tr> 
                              <td class="td1">첨부된파일</td>
                              <td colspan="3" class="td3"> 
                                  <c:if test="${not empty dto.imageFilename}">
                                      ${dto.imageFilename}
                                      | <a href="javascript:deleteFile('${dto.trendNum}');">삭제</a>
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
                                 onclick="javascript:location.href='<%=cp%>/trend/list.sst';">
                                 취소</button> <c:if test="${mode=='update'}">
                                 <input type="hidden" name="trendNum" value="${dto.trendNum}">
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



   <!-- Bootstrap Core JavaScript -->
   <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>
</html>