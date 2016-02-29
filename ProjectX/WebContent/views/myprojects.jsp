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
		<div class="col-md-12 col-xs-12">
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h2 class="panel-title">
						<strong>Your projects</strong>
					</h2>
				</div>

				<div class="panel-body">
					<h5>
						Hi <strong><c:out value="${sessionScope.username }"></c:out></strong>,
						welcome back! There are projects you are involved in.
					</h5>

				</div>

				<table class="table table-responsive table-striped">
					<thead>
						<tr>
							<th><i class="fa fa-tag"></i>&nbsp;Project Name</th>
							<th><i class="fa fa-user"></i>&nbsp;Client</th>
							<th><i class="fa fa-magic"></i>&nbsp;Project Manager</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.projects}" var="project">
							<tr>
								<td><a href="./project?idProject=${project.idProject}"><strong><c:out
												value="${project.name}"></c:out></strong></a></td>
								<td><span class="label label-success"><c:out
											value="${project.clientName}"></c:out></span></td>
								<td><span class="label label-warning"><c:out
											value="${project.pmName}"></c:out></span></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>