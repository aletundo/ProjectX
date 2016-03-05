<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Organize a meeting</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-8 col-xs-12">
			Organize a meeting is very <i><b>easy</b></i>!
			<hr>
			<form class="form col-md-12 center-block"
				action="./organizemeeting?idProject=${param.idProject }"
				method="POST" role="form" autocomplete="off">

				<div class="input-group">
					<span class="input-group-addon">Project Manager</span> <input
						type="text" class="form-control" value="${requestScope.pmMail }"
						placeholder="${requestScope.pmMail }" name="pm-mail" disabled />
				</div>

				<br>

				<div class="input-group">
					<span class="input-group-addon">Client</span> <input type="text"
						class="form-control" value="${requestScope.clientMail }"
						placeholder="${requestScope.clientMail }" name="client-mail"
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

				<br> Do you want to invite supervisors too? &nbsp;&nbsp;<input
					type="radio" name="invite-supervisors" value="Yes" checked> Yes
				&nbsp;&nbsp;<input type="radio" name="invite-supervisors" value="No">
				No <br> <br>
				
				<span class="help-block">${messages.status }</span>

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