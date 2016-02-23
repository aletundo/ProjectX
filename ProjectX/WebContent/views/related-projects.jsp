<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Related projects</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			Here's your project research by key
			<hr>
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">Projects</h3>
				</div>
				<c:choose>
					<c:when test="${requestScope.noMatchFound!=null}">
						<div class="panel-body">
							Results for: <strong><c:out
									value="${requestScope.subjectArea}"></c:out></strong> <br> <br>
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
								<th><i class="fa fa-tag"></i>&nbsp;Name</th>
								<th><i class="fa fa-magic"></i>&nbsp;Project Manager</th>
							</tr>

							<c:forEach items="${requestScope.projects}" var="project">
								<tr>
									<td><a href="#"><strong><c:out
													value="${project.idProject}"></c:out></strong></a></td>
									<td><span class="label label-success"><c:out
												value="${project.name}"></c:out></span></td>
									<td><span class="label label-warning"><c:out
												value="${project.pmName}"></c:out></span></td>
								</tr>
							</c:forEach>
						</table>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>
</html>