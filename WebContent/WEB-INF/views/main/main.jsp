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

    <title>Modern Business - Start Bootstrap Template</title>

    <!-- Bootstrap Core CSS -->
    <link href="res/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="res/css/modern-business.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="res/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<%-- <script type="text/javascript" src="<%=cp%>/res/js/jquery-1.12.0.min.js"></script>  --%>

</head>

<body>
 <!-- Navigation -->
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>
 	<br>
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
            	<div class="fill" style="background-image:url('<%=cp%>/res/image/mainslide/fill1.jpg');"></div>
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
    </header>
	
	
	
	
	
	
	
	
	
	
	
	
	
	

    <!-- Page Content -->
    <div class="container">

        <!-- Marketing Icons Section -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">
                    Welcome to SIST Community
                </h1>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-check"></i>Class&nbsp;materials(리스트불러)</h4>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                          <c:forEach var="dtoDocu" items="${listDocu}">
                        	<li><a href="#">${dtoDocu.docuSubject}</a></li>
                          </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Portfolio (리스트불러)</h4>
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
                        <h4><i class="fa fa-fw fa-compass"></i>Self&nbsp;Introduction(리스트불러)</h4>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                          <c:forEach var="dtoResume" items="${listResume}">
                        	<li><a href="#">${dtoResume.resumeSubject}</a></li>
                          </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-check"></i> Company&nbsp;Info (리스트불러)</h4>
                    </div>
                    <div class="panel-body">
                          
                          <ul type="disc">
                        	<c:forEach var="dtoCompany" items="${listCompany}">
                        		<li><a href="#">${dtoCompany.companySubject}</a></li>
                          </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                        	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Recruits&nbsp;Information (리스트불러)</h4>
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
                        <h4><i class="fa fa-fw fa-compass"></i> Team&nbsp;Member(리스트불러)</h4>
                    </div>
                    <div class="panel-body">
                      <ul type="disc">
                        	<c:forEach var="dtoWanted" items="${listWanted}">
	                        	<li><a href="#"><b style="color:#FF7012;">[${dtoWanted.wantedHead}]</b>&nbsp;${dtoWanted.wantedSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-check"></i> IT&nbsp;Trend&nbsp;Info</h4>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                        	<c:forEach var="dtoTrend" items="${listTrend}">
	                        	<li><a href="#"><b style="color:#FF7012;">[${dtoTrend.trendHead}]</b>&nbsp;${dtoTrend.trendSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Development&nbsp;Tip(리스트불러)</h4>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                        	<c:forEach var="dtoTip" items="${listTip}">
	                        	<li><a href="#"><b style="color:#FF7012;">[${dtoTip.tipHead}]</b>&nbsp;${dtoTip.tipSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-compass"></i> Q&nbsp;&#38;&nbsp;A(리스트불러)</h4>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                        	<c:forEach var="dtoQuest" items="${listQuest}">
	                        	<li><a href="#"><b style="color:#FF7012;">[${dtoQuest.questHead}]</b>&nbsp;${dtoQuest.questSubject}</a></li>
	                         </c:forEach>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
        </div>
        <!-- /.row -->



        <!-- Call to Action Section -->
        <div class="well">
            <div class="row">
                <div class="col-md-8">
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Molestias, expedita, saepe, vero rerum deleniti beatae veniam harum neque nemo praesentium cum alias asperiores commodi.</p>
                </div>
                <div class="col-md-4">
                    <a class="btn btn-lg btn-default btn-block" href="#">Call to Action</a>
                </div>
            </div>
        </div>

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; Your Website 2014</p>
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
