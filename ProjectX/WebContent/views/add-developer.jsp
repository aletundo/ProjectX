<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Assign a developer</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			Select a developer for the task.<br>
			<br> Hours to be assigned:&nbsp;&nbsp;<span class="badge">${requestScope.taskHoursRequired }</span>
			<hr>
			<form class="form col-md-12 center-block"
				action="./add-developer?idStage=${param.idStage}&idTask=${param.idTask}&startDay=${param.startDay}&finishDay=${param.finishDay}"
				method="POST" role="form" autocomplete="off">
				<div class="input-group">
					<jsp:include page="/views/sharable/developers-list.jsp" />
				</div>
				<input type="hidden" name="task-hours-required"
					value="${requestScope.taskHoursRequired }">
				<div class="input-group">
					<div class="checkbox">
						<label><input type="checkbox" name="completed">Check
							me if it is the last task.</label>
					</div>
				</div>
				<br>
				<div class="input-group pull-right">
					<button type="submit" class="btn btn-success">
						<i class="fa fa-arrow-right"></i> Continue
					</button>
					&nbsp;&nbsp;
					<button class="btn btn-danger" type="reset">
						<i class="fa fa-undo"></i> Reset
					</button>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>