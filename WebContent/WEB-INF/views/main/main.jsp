<%@ page contentType="text/html; charset=UTF-8" %>
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
    <link href="<%=cp %>/res/css/bootstrap.min.css" rel="stylesheet">

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
                        <h4><i class="fa fa-fw fa-check"></i>Class&nbsp;materials</h4>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                        	<li><a href="#">수업자료 제목 1</a></li>
                        	<li><a href="#">수업자료 제목 2</a></li>
                        	<li><a href="#">수업자료 제목 3</a></li>
                        	<li><a href="#">수업자료 제목 4</a></li>
                        	<li><a href="#">수업자료 제목 5</a></li>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Portfolio</h4>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                        	<li><a href="#">포트폴리오 1</a></li>
                        	<li><a href="#">포트폴리오 2</a></li>
                        	<li><a href="#">포트폴리오 3</a></li>
                        	<li><a href="#">포트폴리오 4</a></li>
                        	<li><a href="#">포트폴리오 5</a></li>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-compass"></i>Self&nbsp;Introduction</h4>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                        	<li><a href="#">자기소개서 공유 제목 1</a></li>
                        	<li><a href="#">자기소개서 공유 제목 2</a></li>
                        	<li><a href="#">자기소개서 공유 제목 3</a></li>
                        	<li><a href="#">자기소개서 공유 제목 4</a></li>
                        	<li><a href="#">자기소개서 공유 제목 5</a></li>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-check"></i> Company&nbsp;Info</h4>
                    </div>
                    <div class="panel-body">
                          
                          <ul type="disc">
                        	<li><a href="#">회사정보 공유 제목 1</a></li>
                        	<li><a href="#">회사정보 공유 제목 2</a></li>
                        	<li><a href="#">회사정보 공유 제목 3</a></li>
                        	<li><a href="#">회사정보 공유 제목 4</a></li>
                        	<li><a href="#">회사정보 공유 제목 5</a></li>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>
                        	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Employment&nbsp;Information</h4>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                        	<li><a href="#">취업정보 공유 제목 1</a></li>
                        	<li><a href="#">취업정보 공유 제목 2</a></li>
                        	<li><a href="#">취업정보 공유 제목 3</a></li>
                        	<li><a href="#">취업정보 공유 제목 4</a></li>
                        	<li><a href="#">취업정보 공유 제목 5</a></li>
                        </ul>
                        <a href="<%=cp%>/bbs/list.sst" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-compass"></i> Team&nbsp;Member</h4>
                    </div>
                    <div class="panel-body">
                      <ul type="disc">
                        	<li><a href="#">팀원 구하기 제목 1</a></li>
                        	<li><a href="#">팀원 구하기 제목 2</a></li>
                        	<li><a href="#">팀원 구하기 제목 3</a></li>
                        	<li><a href="#">팀원 구하기 제목 4</a></li>
                        	<li><a href="#">팀원 구하기 제목 5</a></li>
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
                        	<li><a href="#">IT 팀원 구하기 1</a></li>
                        	<li><a href="#">IT 팀원 구하기 2</a></li>
                        	<li><a href="#">IT 팀원 구하기 3</a></li>
                        	<li><a href="#">IT 팀원 구하기 4</a></li>
                        	<li><a href="#">IT 팀원 구하기 5</a></li>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-gift"></i> Development&nbsp;Tip</h4>
                    </div>
                    <div class="panel-body">
                        <ul type="disc">
                        	<li><a href="#">개발 팀 공유 1</a></li>
                        	<li><a href="#">개발 팀 공유 2</a></li>
                        	<li><a href="#">개발 팀 공유 3</a></li>
                        	<li><a href="#">개발 팀 공유 4</a></li>
                        	<li><a href="#">개발 팀 공유 5</a></li>
                        </ul>
                        
                        <a href="#" class="btn btn-default" style="text-align: right">View&nbsp;More</a>	
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h4><i class="fa fa-fw fa-compass"></i> Q&nbsp;&#38;&nbsp;A</h4>
                    </div>
                    <div class="panel-body">
                         <ul type="disc">
                        	<li><a href="#">개발 질문 답변 게시판 1</a></li>
                        	<li><a href="#">개발 질문 답변 게시판 2</a></li>
                        	<li><a href="#">개발 질문 답변 게시판 3</a></li>
                        	<li><a href="#">개발 질문 답변 게시판 4</a></li>
                        	<li><a href="#">개발 질문 답변 게시판 5</a></li>
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
