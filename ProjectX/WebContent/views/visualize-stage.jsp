<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Stage: <c:out
		value="${requestScope.stage.name }"></c:out></title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<c:out value="${requestScope.stage.name }"></c:out>
					</h3>
					<a class="btn btn-danger btn-sm pull-right" href="./editstage?idStage=${requestScope.stage.idStage }"><i
						class="fa fa-pencil"></i>&nbsp;Edit</a>
				</div>

				<div class="panel-body">
					<p>
						Start-Day:&nbsp;
						<c:out value="${requestScope.stage.startDay }"></c:out>
						<br> Finish-Day:&nbsp;
						<c:out value="${requestScope.stage.finishDay }"></c:out>
						<br> Supervisor:&nbsp;
						<c:out value="${requestScope.stage.supervisorFullname }"></c:out>
					</p>
					<div class="progress">
						<div
							class="progress-bar progress-bar-success progress-bar-striped"
							role="progressbar" aria-valuenow="${requestScope.stage.rateWorkCompleted }" aria-valuemin="0"
							aria-valuemax="100" style="width: ${requestScope.stage.rateWorkCompleted }%">${requestScope.stage.rateWorkCompleted }% Complete</div>
					</div>
				</div>

				<table class="table">
					<tr>
						<th><i class="fa fa-tag"></i>&nbsp;Task</th>
						<th><i class="fa fa-hourglass-start"></i>&nbsp;Start</th>
						<th><i class="fa fa-hourglass-end"></i>&nbsp;Finish</th>
						<th><i class="fa fa-check-circle-o"></i>&nbsp;Completed</th>
					</tr>

					<c:forEach items="${requestScope.tasks}" var="task">
						<tr>
							<td><a
								href="./task?idTask=${task.idTask}"><strong><c:out
											value="${task.name}"></c:out></strong></a></td>
							<td><c:out value="${task.startDay}"></c:out></td>
							<td><c:out value="${task.finishDay}"></c:out></td>
							<td><c:choose>
							<c:when test="${task.completed == 'True' }">
							<i class="fa fa-check"></i>
							</c:when>
							<c:otherwise><i class="fa fa-times"></i></c:otherwise>
							</c:choose></td>
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
			<p>
				Your Session ID=
				<c:out value="${cookie['JSESSIONID'].value}"></c:out>
			</p>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>