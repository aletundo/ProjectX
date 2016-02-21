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
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Your projects</h3>
				</div>

				<div class="panel-body">
					<p>
						Hi <strong><c:out value="${sessionScope.username }"></c:out></strong>, there are projects which you are
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
							<td><a href="${pageContext.request.contextPath}/project?idProject=${project.idProject}"><strong><c:out
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
		<c:if test="${sessionScope.userType == 'ProjectManager'}">
			<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
		</c:if>
		<c:if test="${sessionScope.userType == 'Senior' }">
			<jsp:include page="/views/sharable/sidebar-senior.jsp" />
		</c:if>
		<c:if test="${sessionScope.userType == 'Junior' }">
		</c:if>
			<p>
				Your Session ID=<c:out value="${cookie['JSESSIONID'].value}"></c:out></p>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>