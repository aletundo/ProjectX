<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/head-imports.jsp" />
<title>ProjectX - Related clients</title>
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
				<div class="panel-heading">
					<h3 class="panel-title">Clients</h3>
				</div>

				<div class="panel-body">
					You search related projects to: <strong><c:out
							value="${requestScope.subjectArea}"></c:out></strong>
				</div>

				<table class="table">
					<tr>
						<th><i class="fa fa-key"></i>&nbsp;Id</th>
						<th><i class="fa fa-user"></i>&nbsp;Name</th>
						<th><i class="fa fa-at"></i>&nbsp;Mail</th>
					</tr>

					<c:forEach items="${requestScope.clients}" var="client">
						<tr>
							<td><a href="#"><strong><c:out
											value="${client.idClient}"></c:out></strong></a></td>
							<td><span class="label label-success"><c:out
										value="${client.name}"></c:out></span></td>
							<td><span class="label label-warning"><c:out
										value="${client.mail}"></c:out></span></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/footer.jsp" />
</body>
</html>
</html>