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
						class="btn btn-info btn-sm pull-right"
						href="./organizemeeting?idProject=${requestScope.project.idProject}"><i
						class="fa fa-users"></i>&nbsp;Organize client meeting</a>
				</div>

				<div class="panel-body">
					<p>
						<strong>Start:</strong>&nbsp;
						<c:out value="${requestScope.project.start }"></c:out>
						<br> <strong>Deadline:</strong>&nbsp;
						<c:out value="${requestScope.project.deadline }"></c:out>
						<br> <strong>Client:</strong>&nbsp;
						<c:out value="${requestScope.project.clientName }"></c:out>
						<br> <strong>Goals:</strong>&nbsp;
						<c:out value="${requestScope.project.goals }"></c:out>
						<br> <strong>Requirements:</strong>&nbsp;
						<c:out value="${requestScope.project.requirements }"></c:out>
						<br> <strong>Budget:</strong>&nbsp;<span class="label label-danger">
						<c:out value="${requestScope.project.budget }"></c:out></span>
						<br> <strong>Estimated costs:</strong>&nbsp;<span class="label label-warning">
						<c:out value="${requestScope.project.estimatedCosts }"></c:out></span>
					</p>
					<div class="progress">
						<div
							class="progress-bar progress-bar-success progress-bar-striped"
							role="progressbar"
							aria-valuenow="${requestScope.project.rateWorkCompleted }"
							aria-valuemin="0" aria-valuemax="100"
							style="width: ${requestScope.project.rateWorkCompleted }%">${requestScope.project.rateWorkCompleted }%
							Complete</div>
					</div>
				</div>

				<table class="table table-responsive table-striped">
					<thead>
						<tr>
							<th><i class="fa fa-tag"></i>&nbsp;Stage</th>
							<th><i class="fa fa-hourglass-start"></i>&nbsp;Start</th>
							<th><i class="fa fa-hourglass-end"></i>&nbsp;Finish</th>
							<th><i class="fa fa-percent"></i>&nbsp;Progress</th>
							<th><i class="fa fa-user"></i>&nbsp;Supervisor</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${requestScope.stages}" var="stage">
							<tr>
								<td><a href="./stage?idStage=${stage.idStage}"><strong><c:out
												value="${stage.name}"></c:out></strong></a></td>
								<td><c:out value="${stage.startDay}"></c:out></td>
								<td><c:out value="${stage.finishDay}"></c:out></td>
								<td>
									<div class="progress">
										<div
											class="progress-bar progress-bar-info progress-bar-striped"
											role="progressbar"
											aria-valuenow="${stage.rateWorkCompleted }" aria-valuemin="0"
											aria-valuemax="100"
											style="width: ${stage.rateWorkCompleted }%">${stage.rateWorkCompleted }%
											Complete</div>
									</div>
								</td>
								<td><c:choose>
										<c:when test="${stage.outsourcing == 'True' }">
											<span class="label label-danger">Outsourced</span>
										</c:when>
										<c:otherwise>
											<span class="label label-warning"><c:out
													value="${stage.supervisorFullname}"></c:out></span>
										</c:otherwise>
									</c:choose></td>
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