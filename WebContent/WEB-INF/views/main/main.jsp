<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	request.setCharacterEncoding("utf-8");
	String cp=request.getContextPath();
	
	
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
    <link href="<%=cp %>/res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="<%=cp %>/res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<%-- <script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script>  --%>

<script type="text/javascript">

</script>
</head>

<body>
 <!-- Navigation -->
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
 	
 <!-- Header Carousel -->
    <header id="myCarousel" class="carousel slide">
        <!-- Indicators -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>

        <!-- Wrapper for slides -->
        <div class="carousel-inner">
            <div class="item active">
            	<div class="fill" style="background-image:url('<%=cp%>/res/image/mainslide/fill1_2.jpg');"></div>
                <div class="carousel-caption">
                    <h2></h2>
                </div>
            </div>
            <div class="item">	
                <div class="fill" style="background-image:url('<%=cp%>/res/image/mainslide/fill2.jpg');"></div>
                <div class="carousel-caption">
                    <h2></h2>
                </div>
            </div>
            <div class="item">
                <div class="fill" style="background-image:url('<%=cp%>/res/image/mainslide/fill3.jpg');"></div>
                <div class="carousel-caption">
                    <h2></h2>
                </div>
            </div>
          
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
            <span class="icon-prev"></span>
        </a>
        <a class="right carousel-control" href="#myCarousel" data-slide="next">
            <span class="icon-next"></span>
        </a>
        
        <br><br>
		<br><br>
        
        <img style="margin: 0px auto;" src ="<%=cp%>/res/image/newbar1.png">
    </header>
	

    <!-- Page Content -->
    <div class="container">

        <!-- Marketing Icons Section -->
        <div class="row">
            <div class="col-lg-12">
				<br><br>
				<br><br>
                <br><br>
                <br><br>
                <br>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-paperclip"></i> 수업자료</h5>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                          <c:forEach var="dtoDocu" items="${listDocu}">
                        	<li><a href="<%=cp%>/docu/article.sst?page=1&docuNum=${dtoDocu.docuNum}" >${dtoDocu.docuSubject}</a></li>
                          </c:forEach>
                        </ul>
                        
                        <a href="<%=cp%>/docu/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-film"></i> 포트폴리오</h5>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
		                  <c:forEach var="dtoPfo" items="${listPfo}">
                        	<li><a href="<%=cp%>/portfolio/article.sst?page=1&num=${dtoPfo.pfoNum}">${dtoPfo.pfoSubject}</a></li>
                          </c:forEach>
                        </ul>
            
                        <a href="<%=cp%>/portfolio/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-file-text"></i> 자소서 공유</h5>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                          <c:forEach var="dtoResume" items="${listResume}">
                        	<li><a href="<%=cp %>/resume/list.sst">${dtoResume.resumeSubject}</a></li>
                          </c:forEach>
                        </ul>
                        
                        <a href="<%=cp %>/resume/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-info"></i> 회사정보  </h5>
                    </div>
                    <div class="panel-body">
                          
                          <ul type="disc">
                        	<c:forEach var="dtoCompany" items="${listCompany}">
                        		<li><a href="<%=cp %>/company/article.sst?page=1&companyNum=${dtoCompany.companyNum}">${dtoCompany.companySubject}</a></li>
                          </c:forEach>
                        </ul>
                        
                        <a href="<%=cp %>/company/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                        	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-user"></i> 취업정보나눔 </h5>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
	                    	 <c:forEach var="dtoShare" items="${listShare}">
	                        	<li><a href="<%=cp%>/bbs/article.sst?page=1&shareNum=${dtoShare.shareNum}">${dtoShare.shareSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        <a href="<%=cp%>/bbs/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-user"></i> 팀원구하기</h5>
                    </div>
                    <div class="panel-body">
                      <ul type="disc">
                        	<c:forEach var="dtoWanted" items="${listWanted}">
	                        	<li><a href="<%=cp%>/wanted/article.sst?page=1&wantedNum=${dtoWanted.wantedNum}"><b style="color:#687ead;">[${dtoWanted.wantedHead}]</b>&nbsp;${dtoWanted.wantedSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="<%=cp%>/wanted/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-info"></i> IT동향</h5>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                        	<c:forEach var="dtoTrend" items="${listTrend}">
	                        	<li><a href="<%=cp%>/trend/article.sst?page=1&trendNum=${dtoTrend.trendNum}"><b style="color:#687ead;">[${dtoTrend.trendHead}]</b>&nbsp;${dtoTrend.trendSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="<%=cp%>/trend/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-gittip"></i> 개발Tip</h5>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                        	<c:forEach var="dtoTip" items="${listTip}">
                        	
	                        	<li><a href="<%=cp%>/tip/article.sst?page=1&tipNum=${dtoTip.tipNum}"><b style="color:#687ead;">[${dtoTip.tipHead}]</b>&nbsp;${dtoTip.tipSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="<%=cp%>/tip/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h5 style="color:#687ead; font-weight: bold;"><i class="fa fa-fw fa-question"></i>개발Q&nbsp;&#38;&nbsp;A</h5>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                        	<c:forEach var="dtoQuest" items="${listQuest}">
                        	
	                        	<li><a href="<%=cp%>/qna/article.sst?page=1&qnaNum=${dtoQuest.questNum}"><b style="color:#687ead;">[${dtoQuest.questHead}]</b>&nbsp;${dtoQuest.questSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="<%=cp%>/qna/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->



        <!-- Call to Action Section -->
        <c:if test="${empty sessionScope.member}"> 
        <div class="well">
            <div class="row">
                <div class="col-md-8">
                    <p style="color:#687ead; font-size: 14pt"><b>Join NOW !!</b></p>
                    <p>지금 회원가입 하시면, 쌍용교육센터의 수업자료, 포트폴리오 등 많은 유용한 정보를 만나실 수 있습니다.</p>
                </div>
                <div class="col-md-4">
                    <a class="btn btn-lg btn-default btn-block" href="<%=cp%>/member/member.sst" style="color:white; margin-top: 10px">Join Member</a>
                </div>
            </div>
        </div>
        </c:if>

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12"  style="margin-left: 50px;">
                    <p>Copyright &copy; SIST Comm 2016</p>
                </div>
            </div>
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="res/js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="res/js/bootstrap.min.js"></script>

    <!-- Script to Activate the Carousel -->
    <script>
    $('.carousel').carousel({
        interval: 5000 //changes the speed
    })
    </script>
   

</body>

</html>
