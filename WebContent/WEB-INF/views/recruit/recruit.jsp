<%@page import="java.util.List"%>
<%@page import="com.recruit.RecruitDAO"%>
<%@page import="com.recruit.RecruitDTO"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    	request.setCharacterEncoding("utf-8");
		String cp=request.getContextPath();
		String articleUrl=(String)request.getAttribute("articleUrl");

    
		//시스템 상 오늘의 날짜를 가져온다.
   		Calendar tocal=Calendar.getInstance();
   		int w=tocal.get(Calendar.DAY_OF_WEEK);
   		int year=tocal.get(Calendar.YEAR);
   		int month=tocal.get(Calendar.MONTH)+1;
   		int day=tocal.get(Calendar.DATE);
   		
   		//'주'의 첫날을 가져온다.
   		if(w!=1)
   			tocal.add(Calendar.DATE, -(w-1));
   		int toyear=tocal.get(Calendar.YEAR);
   		int tomonth=tocal.get(Calendar.MONTH)+1;
   		int today=tocal.get(Calendar.DATE);
  		
   		//현재로부터 전주의 첫날을 가져온다.
   		Calendar beforecal=(Calendar)tocal.clone();
   		beforecal.add(Calendar.DATE, -7);
   		int beforeyear=beforecal.get(Calendar.YEAR);
   		int beforemonth=beforecal.get(Calendar.MONTH)+1;
   		int beforeday=beforecal.get(Calendar.DATE);
   		
   		//현재로부터 다음주의 마지막날을 가져온다.
   		Calendar endcal=(Calendar)tocal.clone();
   		endcal.add(Calendar.DATE, +13);
   		int endyear=endcal.get(Calendar.YEAR);
   		int endmonth=endcal.get(Calendar.MONTH)+1;
   		int endday=endcal.get(Calendar.DATE);
   		
   		//공채달력 시작날짜와 마지막날짜를 저장한다.
   		String start=String.format("%04d-%02d-%02d", beforeyear,beforemonth,beforeday);
   		String end=String.format("%04d-%02d-%02d", endyear,endmonth,endday);

   		RecruitDAO dao=new RecruitDAO();
   		
   		List<RecruitDTO> startList=dao.listStartRecruit(start, end);
   		List<RecruitDTO> endList=dao.listEndRecruit(start, end);
   		
   		
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

	 <!-- jQuery -->
    <script src="<%=cp%>/res/js/jquery.js"></script>

	
</head>

<body>

    <!-- Navigation -->
	<div>
		<jsp:include page="/WEB-INF/views/layout/navigation.jsp"></jsp:include>
	</div>


    <!-- Page Content -->
    <div class="container">

        <!-- Page Heading/Breadcrumbs -->
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"><%=year%>년 공채 달력
                    <small>쌍용강북교육센터</small>
                </h1>
                <ol class="breadcrumb">
                    <li><a href="index.html">Home</a>
                    </li>
                    <li class="active">Full Width Page 여기는 헤더 입니다.</li>
                </ol>
            </div>
        </div>
        <!-- /.row -->

        <!-- Content Row -->
        <div class="row">
            <div class="col-lg-12">
        	
        <!-- 달력 출력 시작  -->
        	<table style="width: 1050px; border: solid #BDBDBD  1px; border-spacing: 0; margin: 5px auto 0; background: #F6F6F6;">
	<tr height="30" align="center">
		<td width="150px" bgcolor="#EAEAEA" style="color: red; border: solid #BDBDBD 1px;">일(SUN)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">월(MON)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">화(TUE)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">수(WED)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">목(THU)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">금(FRI)</td>
		<td width="150px" bgcolor="#EAEAEA" style="color: blue; border: solid #BDBDBD 1px;">토(SAT)</td>
	</tr>
<%
	out.print("<tr height='30' align='center'>");

	//첫번째 줄의 날짜를 출력한다.
	for(int i=1; i<=7; i++){
		if(i==1){
			out.print("<td style='border: solid #BDBDBD  1px; color: red;'>"+beforemonth+"/"+beforeday+"</td>");
		}else if(i==7){
			out.print("<td style='border: solid #BDBDBD 1px; color: blue'>"+beforemonth+"/"+beforeday+"</td>");
		}else{
			out.print("<td style='border: solid #BDBDBD 1px;'>"+beforemonth+"/"+beforeday+"</td>");
		}
		
		//날짜를 하루씩 늘려간다.
   		beforecal.add(Calendar.DATE, 1);
   		beforeyear=beforecal.get(Calendar.YEAR);
   		beforemonth=beforecal.get(Calendar.MONTH)+1;
   		beforeday=beforecal.get(Calendar.DATE);
	}
	out.print("</tr>");
	
	out.print("<tr height='200' align='center'>");
	
	//공채정보를 출력하기 위해 날짜정보를 초기화한다.
	beforecal.add(Calendar.DATE, -7);
	beforeyear=beforecal.get(Calendar.YEAR);
	beforemonth=beforecal.get(Calendar.MONTH)+1;
	beforeday=beforecal.get(Calendar.DATE);
	
	//공채정보를 출력한다.
	for(int i=1; i<=7; i++){
		out.print("<td style='border: solid #BDBDBD 1px;'>");
		out.print("<table><tr>");
		
		//해당 날짜에 맞는 공채 시작 자료를 출력한다.
		for(RecruitDTO dto:startList){
			String s=dto.getRecruitStart();
			Integer y=Integer.parseInt(s.split("-")[0]);
			Integer m=Integer.parseInt(s.split("-")[1]);
			Integer d=Integer.parseInt(s.split("-")[2]);
			
			int recruitNum=dto.getRecruitNum();
			
			if(y==beforeyear && m==beforemonth && d==beforeday){
				String subject=dto.getRecruitSubject();
				out.print("<tr>");
				out.print("<td style='font-size: 3pt; background-color: #B2CCFF'>");
				out.print("시작"+"</td>");
				out.print("<td>"+"&nbsp&nbsp"+"<a href='"+articleUrl+"?recruitNum="+recruitNum+"' style='font-size: 5pt; color: black;'>"+subject+"</a></td><br>");
				out.print("</tr>");
			}
		}
		
		//해당 날짜에 맞는 공채 종료 자료를 출력한다.
		for(RecruitDTO dto:endList){
			String s=dto.getRecruitEnd();
			Integer y=Integer.parseInt(s.split("-")[0]);
			Integer m=Integer.parseInt(s.split("-")[1]);
			Integer d=Integer.parseInt(s.split("-")[2]);
			
			int recruitNum=dto.getRecruitNum();
					
			if(y==beforeyear && m==beforemonth && d==beforeday){
				String subject=dto.getRecruitSubject();
				out.print("<tr>");
				out.print("<td style='font-size: 3pt; background-color: #FFC19E'>");
				out.print("마감"+"</td>");
				out.print("<td>"+"&nbsp&nbsp"+"<a href='"+articleUrl+"?recruitNum="+recruitNum+"' style='font-size: 5pt; color: black;'>"+subject+"</a></td><br>");
				out.print("</tr>");
			}
		}
		
		
		//다음 날짜로 넘어간다.
		beforecal.add(Calendar.DATE, 1);
		beforeyear=beforecal.get(Calendar.YEAR);
   		beforemonth=beforecal.get(Calendar.MONTH)+1;
   		beforeday=beforecal.get(Calendar.DATE);
		
   		
   		out.print("</tr></table>");
		out.print("</td>");
	}
	out.print("</tr>");
	

%>
</table>

<table style="width: 1050px; border: solid #BDBDBD 1px; border-spacing: 0; margin: 5px auto 0; background: #F6F6F6;">
	<tr height="30" align="center">
		<td width="150px" bgcolor="#EAEAEA" style="color: red; border: solid #BDBDBD 1px;">일(SUN)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">월(MON)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">화(TUE)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">수(WED)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">목(THU)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">금(FRI)</td>
		<td width="150px" bgcolor="#EAEAEA" style="color: blue; border: solid #BDBDBD 1px;">토(SAT)</td>
	</tr>
<%
	out.print("<tr height='30' align='center'>");

	//두번째 줄의 날짜를 출력한다.
	for(int i=1; i<=7; i++){
		if(i==1){
			out.print("<td style='border: solid #BDBDBD  1px; color: red;'>"+beforemonth+"/"+beforeday+"</td>");
		}else if(i==7){
			out.print("<td style='border: solid #BDBDBD 1px; color: blue'>"+beforemonth+"/"+beforeday+"</td>");
		}else{
			out.print("<td style='border: solid #BDBDBD 1px;'>"+beforemonth+"/"+beforeday+"</td>");
		}
		
		//날짜를 하루씩 늘려간다.
 		beforecal.add(Calendar.DATE, 1);
 		beforeyear=beforecal.get(Calendar.YEAR);
 		beforemonth=beforecal.get(Calendar.MONTH)+1;
 		beforeday=beforecal.get(Calendar.DATE);
 		
 		
	}
		
	
	out.print("</tr>");
	
	out.print("<tr height='200' align='center'>");
	
	
	
	//공채정보를 출력하기 위해 날짜정보를 초기화한다.
	beforecal.add(Calendar.DATE, -7);
	beforeyear=beforecal.get(Calendar.YEAR);
	beforemonth=beforecal.get(Calendar.MONTH)+1;
	beforeday=beforecal.get(Calendar.DATE);
		
	//공채정보를 출력한다.
	for(int i=1; i<=7; i++){
		out.print("<td style='border: solid #BDBDBD 1px;'>");
		out.print("<table><tr>");
			
		//해당 날짜에 맞는 공채 시작 자료를 출력한다.
		for(RecruitDTO dto:startList){
			String s=dto.getRecruitStart();
			Integer y=Integer.parseInt(s.split("-")[0]);
			Integer m=Integer.parseInt(s.split("-")[1]);
			Integer d=Integer.parseInt(s.split("-")[2]);
			
			int recruitNum=dto.getRecruitNum();
			
			if(y==beforeyear && m==beforemonth && d==beforeday){
				String subject=dto.getRecruitSubject();
				out.print("<tr>");
				out.print("<td style='font-size: 3pt; background-color: #B2CCFF'>");
				out.print("시작"+"</td>");
				out.print("<td>"+"&nbsp&nbsp"+"<a href='"+articleUrl+"?recruitNum="+recruitNum+"' style='font-size: 5pt; color: black;'>"+subject+"</a></td><br>");
				out.print("</tr>");
			}
		}
			
			//해당 날짜에 맞는 공채 종료 자료를 출력한다.
			for(RecruitDTO dto:endList){
				String s=dto.getRecruitEnd();
				Integer y=Integer.parseInt(s.split("-")[0]);
				Integer m=Integer.parseInt(s.split("-")[1]);
				Integer d=Integer.parseInt(s.split("-")[2]);
						
				int recruitNum=dto.getRecruitNum();
				
				if(y==beforeyear && m==beforemonth && d==beforeday){
					String subject=dto.getRecruitSubject();
					out.print("<tr>");
					out.print("<td style='font-size: 3pt; background-color: #FFC19E'>");
					out.print("마감"+"</td>");
					out.print("<td>"+"&nbsp&nbsp"+"<a href='"+articleUrl+"?recruitNum="+recruitNum+"' style='font-size: 5pt; color: black;'>"+subject+"</a></td><br>");
					out.print("</tr>");
				}
			}
			
			//다음 날짜로 넘어간다.
			beforecal.add(Calendar.DATE, 1);
			beforeyear=beforecal.get(Calendar.YEAR);
	   		beforemonth=beforecal.get(Calendar.MONTH)+1;
	   		beforeday=beforecal.get(Calendar.DATE);
			
	   		
	   		out.print("</tr></table>");
			out.print("</td>");
		}
	out.print("</tr>");
	

%>
</table>

<table style="width: 1050px; border: solid #BDBDBD 1px; border-spacing: 0; margin: 5px auto 0; background: #F6F6F6;">
<tr height="30" align="center">
		<td width="150px" bgcolor="#EAEAEA" style="color: red; border: solid #BDBDBD 1px;">일(SUN)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">월(MON)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">화(TUE)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">수(WED)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">목(THU)</td>
		<td width="150px" bgcolor="#EAEAEA" style="border: solid #BDBDBD 1px;">금(FRI)</td>
		<td width="150px" bgcolor="#EAEAEA" style="color: blue; border: solid #BDBDBD 1px;">토(SAT)</td>
</tr>

<%
	out.print("<tr height='30' align='center'>");

	//세번째 줄의 날짜를 출력한다.
   	for(int i=1; i<=7; i++){
      if(i==1){
         out.print("<td style='border: solid #BDBDBD  1px; color: red;'>"+beforemonth+"/"+beforeday+"</td>");
      }else if(i==7){
         out.print("<td style='border: solid #BDBDBD 1px; color: blue'>"+beforemonth+"/"+beforeday+"</td>");
      }else{
         out.print("<td style='border: solid #BDBDBD 1px;'>"+beforemonth+"/"+beforeday+"</td>");
      }
      
      //날짜를 하루씩 늘려간다.
      beforecal.add(Calendar.DATE, 1);
      beforeyear=beforecal.get(Calendar.YEAR);
      beforemonth=beforecal.get(Calendar.MONTH)+1;
      beforeday=beforecal.get(Calendar.DATE);
      
   }
   
   out.print("</tr>");
   
   out.print("<tr height='200' align='center'>");
   
   
   //공채정보를 출력하기 위해 날짜정보를 초기화한다.
   beforecal.add(Calendar.DATE, -7);
   beforeyear=beforecal.get(Calendar.YEAR);
   beforemonth=beforecal.get(Calendar.MONTH)+1;
   beforeday=beforecal.get(Calendar.DATE);
      
   //공채정보를 출력한다.
   for(int i=1; i<=7; i++){
      out.print("<td style='border: solid #BDBDBD 1px;'>");
      out.print("<table><tr>");
         
      //해당 날짜에 맞는 공채 시작 자료를 출력한다.
      for(RecruitDTO dto:startList){
         String s=dto.getRecruitStart();
         Integer y=Integer.parseInt(s.split("-")[0]);
         Integer m=Integer.parseInt(s.split("-")[1]);
         Integer d=Integer.parseInt(s.split("-")[2]);
         
         int recruitNum=dto.getRecruitNum();
            
         if(y==beforeyear && m==beforemonth && d==beforeday){
            String subject=dto.getRecruitSubject();
            out.print("<tr>");
            out.print("<td style='font-size: 3pt; background-color: #B2CCFF'>");
            out.print("시작"+"</td>");
            out.print("<td>"+"&nbsp&nbsp"+"<a href='"+articleUrl+"?recruitNum="+recruitNum+"' style='font-size: 5pt; color: black;'>"+subject+"</a></td><br>");
          	out.print("</tr>");
         }
      }
         
      //해당 날짜에 맞는 공채 종료 자료를 출력한다.
      for(RecruitDTO dto:endList){
         String s=dto.getRecruitEnd();
         Integer y=Integer.parseInt(s.split("-")[0]);
         Integer m=Integer.parseInt(s.split("-")[1]);
         Integer d=Integer.parseInt(s.split("-")[2]);
         
         int recruitNum=dto.getRecruitNum();
                  
         if(y==beforeyear && m==beforemonth && d==beforeday){
            String subject=dto.getRecruitSubject();
            out.print("<tr>");
            out.print("<td style='font-size: 3pt; background-color: #FFC19E'>");
            out.print("마감"+"</td>");
            out.print("<td>"+"&nbsp&nbsp"+"<a href='"+articleUrl+"?recruitNum="+recruitNum+"' style='font-size: 5pt; color: black;'>"+subject+"</a></td><br>");
            out.print("</tr>");
         }
      }
         
    //다음 날짜로 넘어간다.
    beforecal.add(Calendar.DATE, 1);
	beforeyear=beforecal.get(Calendar.YEAR);
	beforemonth=beforecal.get(Calendar.MONTH)+1;
	beforeday=beforecal.get(Calendar.DATE);
         
            
	out.print("</tr></table>");
	out.print("</td>");
   }
   out.print("</tr>");
	
%>

</table>
<hr>
<!-- 달력 출력 끝 -->

<!-- 글쓰기 버튼 출력 -->
<!-- 관리자인 경우만 일정 등록 가능 -->
<c:if test="${sessionScope.member.memId=='admin'}">
<!-- 글쓰기 폼으로 가는 주소 입력 -->
<input type="button" value="일정 등록" onclick="javascript:location.href='<%=cp%>/recruit/created.sst';">
</c:if>
<!-- 글쓰기 버튼 출력 끝 -->
        	
        	
            </div>
        </div>
        <!-- /.row -->

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

   
    <!-- Bootstrap Core JavaScript -->
    <script src="<%=cp%>/res/js/bootstrap.min.js"></script>

</body>

</html>
