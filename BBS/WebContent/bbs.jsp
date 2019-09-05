<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter"%>
<%@ page import="bbs.BbsDAO"%>
<%@ page import="bbs.Bbs"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta http-eqiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="vieport" content="width=device-width" , initial-scale="1">
<link rel="stylesheet" href="css/custom.css">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP 게시판 웹사이트</title>
<style type="text/css">

a, a:hover {
	color : #000000
	text-decoration:none;
}


</style>
</head>
<body>
	<%
		String userID = null; // Session이 존재하는 사람이면 그 ID값을 받아와서 그대로 관리할수 있게 만듦
		if (session.getAttribute("userID") != null) {
			userID = (String) session.getAttribute("userID");
		}
		int pageNumber = 1;
		if (request.getParameter("pageNumber") != null) {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
	%>
	<nav class="navbar navbar-default">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="main.jsp">JSP 게시판 웹사이트</a>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="main.jsp">메인</a>
				<li class="active"><a href="bbs.jsp">게시판</a></li>
			</ul>
			<%
				if (userID == null) {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expended="false">접속하기<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="login.jsp">로그인</a></li>
						<li><a href="join.jsp">회원가입</a></li>
					</ul></li>
			</ul>
			<%
				} else {
			%>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown" role="button" aria-haspopup="true"
					aria-expended="false">회원관리<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="logoutAction.jsp">로그아웃</a></li>
					</ul></li>
			</ul>
			<%
				}
			%>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<table class="table table-striped"
				style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align: center;">번호</th>
						<th style="background-color: #eeeeee; text-align: center;">제목</th>
						<th style="background-color: #eeeeee; text-align: center;">작성자</th>
						<th style="background-color: #eeeeee; text-align: center;">작성일</th>

					</tr>
				</thead>
				<tbody>
					<%
						BbsDAO bbsDAO = new BbsDAO();
						ArrayList<Bbs> list = bbsDAO.getList(pageNumber);
						for (int i = 0; i < list.size(); i++) {
					%>
					<tr>
						<td><%=list.get(i).getBbsID()%></td>
						<td><a href="view.jsp?bbsID=<%= list.get(i).getBbsID() %>"><%= list.get(i).getBbsTitle().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></a></td>
						<td><%=list.get(i).getUserID()%></td>
						<td><%=list.get(i).getBbsDate()%></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
	<%
		if(pageNumber != 1) {
			
		
	%>
		<a href="bbs.jsp?pageNumber=<%=pageNumber - 1 %> " class="btn btn-success btn-arrow-left">이전</a>
	<%
		} if(bbsDAO.nextPage(pageNumber + 1)) {
	%>
			<a href="bbs.jsp?pageNumber=<%=pageNumber + 1 %>" class="btn btn-success btn-arrow-left">다음</a>
	<% 
			}
		%>
	
	
<%-- 			<c:forEach var="pageNum" begin="1" end="<%bbsDAO.page();%>" step="1">
				<a href="#"><c:out value="pageNum"></c:out></a>
			</c:forEach> --%>
			<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>

		</div>
	</div>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="js/bootstrap.js"></script>
</body>
</html>