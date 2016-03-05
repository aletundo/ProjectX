<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Edit <c:out
		value="${requestScope.stage.name }"></c:out></title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			Enter your changes and update the stage.
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/editstage?idStage=${requestScope.stage.idStage}" method="POST"
				role="form" autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Name</span> <input type="text"
						class="form-control" placeholder="${requestScope.stage.name}" name="name"
						value="${fn:escapeXml(param.name)}"/>
				</div>
				<span class="help-block">${messages.name}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Goals</span>
					<textarea class="form-control" placeholder="${requestScope.stage.goals}"
						name="goals" rows="1"></textarea>
				</div>
				<span class="help-block">${messages.goals}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Requirements</span>
					<textarea class="form-control"
						placeholder="${requestScope.stage.requirements}" name="requirements" rows="1"></textarea>
				</div>
				<span class="help-block">${messages.requirements}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Start day</span> <input
						class="form-control" type="text"
						placeholder="${requestScope.stage.startDay}" name="startday"
						value="${fn:escapeXml(param.startday)}"/>
				</div>
				<span class="help-block">${messages.startday}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Finish day</span> <input
						class="form-control" type="text"
						placeholder="${requestScope.stage.finishDay}" name="finishday"
						value="${fn:escapeXml(param.finishday)}"/>
				</div>
				<span class="help-block">${messages.finishday}</span> <br> <br>
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
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>