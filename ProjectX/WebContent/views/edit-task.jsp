<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Edit tasks</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			Enter your changes and update the task.
			<hr>
			<form class="form col-md-12 center-block"
				action="./edittask?idTask=${requestScope.task.idTask}" method="POST" role="form"
				autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Name</span> <input type="text"
						class="form-control" placeholder="${requestScope.task.name}" name="name"  value="${fn:escapeXml(param.name)}" />
				</div>
				<span class="help-block">${messages.name}</span> <br>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Start day</span> <input
						class="form-control" type="text"
						placeholder="${requestScope.task.startDay}" name="startday"  value="${fn:escapeXml(param.startday)}" />
				</div>
				<span class="help-block">${messages.startday}</span> <br>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Finish day</span> <input
						class="form-control" type="text"
						placeholder="${requestScope.task.finishDay}" name="finishday"  value="${fn:escapeXml(param.finishday)}" />
				</div>
				<span class="help-block">${messages.finishday}</span> <br>
				<br>
				<input type="hidden" value="${param.idTask}" name="id-task"/>
				
				<br> <br>
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