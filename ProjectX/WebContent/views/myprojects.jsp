<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - My Projects</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<%
		//Check session exists
		String user = (String) session.getAttribute("username");
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Your projects</h3>
				</div>

				<div class="panel-body">
					<p>
						Hi <strong><%=user%></strong>, there are projects which you are
						involved in
					</p>
				</div>

				<table class="table">
					<tr>
						<th><i class="fa fa-tag"></i>&nbsp;Project Name</th>
						<th><i class="fa fa-user"></i>&nbsp;Client</th>
						<th><i class="fa fa-magic"></i>&nbsp;Project Manager</th>
					</tr>

					<c:forEach items="${requestScope.projects}" var="project">
						<tr>
							<td><a href="#"><strong><c:out
											value="${project.name}"></c:out></strong></a></td>
							<td><span class="label label-success"><c:out
										value="${project.clientName}"></c:out></span></td>
							<td><span class="label label-warning"><c:out
										value="${project.pmName}"></c:out></span></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sidebar-project-manager.jsp" />
			<p>
				Your Session ID=<%=sessionID%></p>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>