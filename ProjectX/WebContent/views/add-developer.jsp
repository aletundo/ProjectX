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
				<c:choose>
					<c:when test="${requestScope.outsourcing == 'True' }">
						<div class="alert alert-warning" role="alert">
							<i class="fa fa-exclamation-triangle"></i>&nbsp;<em>Ops,
								sorry :( There are no available developers during this period. Please, fill the
								outsourcing form below or talk with your superior.</em>
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon">Company name</span> <input
								class="form-control" type="text"
								placeholder="Insert company name" name="company-name" />
						</div>
						<br>
						<div class="input-group">
							<span class="input-group-addon">Mail</span> <input
								class="form-control" type="text"
								placeholder="Insert mail (company@domain.example)"
								name="company-mail" />
						</div>
					</c:when>
					<c:otherwise>
						<div class="input-group">
							<jsp:include page="/views/sharable/developers-list.jsp" />
						</div>
					</c:otherwise>
				</c:choose>
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