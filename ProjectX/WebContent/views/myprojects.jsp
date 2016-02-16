<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/views/head-imports.jsp" />
<title>ProjectX - My Projects</title>
</head>
<body>
	<jsp:include page="/views/navbar.jsp" />
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
				<div class="panel-heading">Your projects</div>

				<div class="panel-body">
					<p>
						Hi <strong><%=user%></strong>, there are projects which you are
						involved in
					</p>
				</div>

				<table class="table">
					<tr>
						<th>Project Name</th>
						<th>Client</th>
						<th>Project Manager</th>
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
			Your Session ID=<%=sessionID%>
		</div>
	</div>
	<jsp:include page="/views/footer.jsp" />
</body>
</html>