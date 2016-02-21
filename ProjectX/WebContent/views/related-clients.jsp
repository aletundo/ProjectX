<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Related clients</title>
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
		Here's your client research by key
			<hr>
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Clients</h3>
				</div>
				<c:choose>
					<c:when test="${requestScope.noMatchFound!=null}">
						<div class="panel-body">
							Results for: <strong><c:out
									value="${requestScope.subjectArea}"></c:out></strong> <br>
							<br>
							<div class="alert alert-warning" role="alert">
								<i class="fa fa-exclamation-triangle"></i>&nbsp;<em><c:out
										value="${requestScope.noMatchFound}"></c:out></em>
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="panel-body">
							Results for: <strong><c:out
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
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>
</html>