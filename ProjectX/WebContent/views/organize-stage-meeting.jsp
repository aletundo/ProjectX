<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Organize a stage meeting</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-8 col-xs-12">
			Organize a meeting is very <i><b>easy</b></i>!
			<hr>
			<form class="form col-md-12 center-block"
				action="./organizemeeting?idStage=${param.idStage }" method="POST"
				role="form" autocomplete="off">

				<div class="input-group">
					<span class="input-group-addon">Supervisor</span> <input
						type="text" class="form-control" value="${requestScope.supervisorMail }" placeholder="${requestScope.supervisorMail }" name="supervisor-mail"
						disabled />
				</div>

				<br>

				<div class="input-group">
					<span class="input-group-addon">Object</span> <input
						type="text" class="form-control" placeholder="Insert the object"
						name="object" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Message</span>
					<textarea class="form-control" placeholder="Insert the message"
						name="message" rows="2"></textarea>
				</div>
				<br> Do you want to invite the project manager too? &nbsp;&nbsp;<input
					type="radio" name="invite-project-manager" value="Yes" checked> Yes
				&nbsp;&nbsp;<input type="radio" name="invite-project-manager" value="No">
				No

				<br>
				<br>

				<div class="input-group pull-right">
					<button type="submit" class="btn btn-success">
						<i class="fa fa-envelope-o"></i>&nbsp;Send
					</button>
					&nbsp;&nbsp;
					<button class="btn btn-danger" type="reset">
						<i class="fa fa-undo"></i>&nbsp;Reset
					</button>
				</div>

			</form>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>