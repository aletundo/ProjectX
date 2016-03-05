<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Task: <c:out
		value="${requestScope.task.name }"></c:out></title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			<div id="projects-panel" class="panel panel-primary">
				<div class="panel-heading">
					<h3 class="panel-title">
						<a href="./stage?idStage=${requestScope.task.idStage }">
							<i class="fa fa-arrow-left"></i>
						</a>&nbsp;&nbsp;&nbsp;
						<c:out value="${requestScope.task.name }"></c:out>
					</h3>
					<a class="btn btn-danger btn-sm pull-right"
						href="./edittask?idStage=${requestScope.task.idStage }&idTask=${requestScope.task.idTask }"><i
						class="fa fa-pencil"></i>&nbsp;Edit</a>
					<form action="./setworkcompleted?idTask=${requestScope.task.idTask }" method="POST">
					<button class="btn btn-success btn-sm pull-right" type="submit"><i
						class="fa fa-check"></i>&nbsp;Set my work completed</button>
					</form>
				</div>

				<div class="panel-body">
					<p>
						<i class="fa fa-hourglass-start"></i>&nbsp;Start-Day:&nbsp;
						<c:out value="${requestScope.task.startDay }"></c:out>
						<br> <i class="fa fa-hourglass-end"></i>&nbsp;Finish-Day:&nbsp;
						<c:out value="${requestScope.task.finishDay }"></c:out>
						<br> <i class="fa fa-check-circle-o"></i>&nbsp;Completed:&nbsp;
						<c:choose>
							<c:when test="${requestScope.task.completed == 'True'}">
								<span class="label label-success"><strong><c:out
											value="${requestScope.task.completed }"></c:out></strong></span>
							</c:when>
							<c:otherwise>
								<span class="label label-warning"><strong><c:out
											value="${requestScope.task.completed }"></c:out></strong></span>
							</c:otherwise>
						</c:choose>

					</p>
					<hr>
					<strong><i class="fa fa-group"></i>&nbsp;Developers</strong>
					<ul class="list-group">
						<c:forEach items="${requestScope.developers }" var="developer">
							<li class="list-group-item"><c:out
									value="${developer.fullname }"></c:out> &nbsp;|&nbsp;<span class="label label-warning"><c:out
									value="${developer.type }"></c:out></span></li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>