<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Project: <c:out
		value="${requestScope.project.name }"></c:out></title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<c:out value="${requestScope.project.name }"></c:out>
					</h3>
					<a class="btn btn-danger btn-sm pull-right clearfix"
						href="./editproject?idProject=${requestScope.project.idProject}"><i
						class="fa fa-pencil"></i>&nbsp;Edit</a> <a
						class="btn btn-success btn-sm pull-right"
						href="./organizemeeting?idProject=${requestScope.project.idProject}"><i
						class="fa fa-users"></i>&nbsp;Organize meeting</a>
				</div>

				<div class="panel-body">
					<p>
						Start:&nbsp;
						<c:out value="${requestScope.project.start }"></c:out>
						<br> Deadline:&nbsp;
						<c:out value="${requestScope.project.deadline }"></c:out>
						<br> Client:&nbsp;
						<c:out value="${requestScope.project.clientName }"></c:out>
					</p>
					<div class="progress">
						<c:choose>
							<c:when test="${requestScope.project.rateWorkCompleted } >= 99">
								<div
									class="progress-bar progress-bar-success progress-bar-striped"
									role="progressbar" aria-valuenow="100" aria-valuemin="0"
									aria-valuemax="100" style="width: 100%">100% Complete</div>
							</c:when>
							<c:otherwise>
								<div
									class="progress-bar progress-bar-success progress-bar-striped"
									role="progressbar"
									aria-valuenow="${requestScope.project.rateWorkCompleted }"
									aria-valuemin="0" aria-valuemax="100"
									style="width: ${requestScope.project.rateWorkCompleted }%">${requestScope.project.rateWorkCompleted }%
									Complete</div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>

				<table class="table">
					<tr>
						<th><i class="fa fa-tag"></i>&nbsp;Stage</th>
						<th><i class="fa fa-hourglass-start"></i>&nbsp;Start</th>
						<th><i class="fa fa-hourglass-end"></i>&nbsp;Finish</th>
						<th><i class="fa fa-percent"></i>&nbsp;Progress</th>
						<th><i class="fa fa-user"></i>&nbsp;Supervisor/Outsourcer</th>
					</tr>

					<c:forEach items="${requestScope.stages}" var="stage">
						<tr>
							<td><a href="./stage?idStage=${stage.idStage}"><strong><c:out
											value="${stage.name}"></c:out></strong></a></td>
							<td><c:out value="${stage.startDay}"></c:out></td>
							<td><c:out value="${stage.finishDay}"></c:out></td>
							<td>
								<div class="progress">
									<c:choose>
										<c:when test="${stage.rateWorkCompleted } >= 99">
											<div
												class="progress-bar progress-bar-success progress-bar-striped"
												role="progressbar" aria-valuenow="100" aria-valuemin="0"
												aria-valuemax="100" style="width: 100%">100% Complete</div>
										</c:when>
										<c:otherwise>
											<div
												class="progress-bar progress-bar-success progress-bar-striped"
												role="progressbar"
												aria-valuenow="${stage.rateWorkCompleted }"
												aria-valuemin="0" aria-valuemax="100"
												style="width: ${stage.rateWorkCompleted }%">${stage.rateWorkCompleted }%
												Complete</div>
										</c:otherwise>
									</c:choose>
								</div>
							</td>
							<td><span class="label label-warning"><c:out
										value="${stage.supervisorFullname}"></c:out></span></td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>