<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Organize Meeting</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			Organize a meeting is very <i><b>easy</b></i>!
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/addproject" method="POST"
				role="form" autocomplete="off">

				<div class="input-group">
					<span class="input-group-addon">Project Manager</span> <input
						type="text" class="form-control" placeholder="PM Name" name="pm"
						disabled />
				</div>

				<br>

				<div class="input-group">
					<span class="input-group-addon">Client</span> <input type="text"
						class="form-control" placeholder="Client E-Mail" name="client"
						disabled />
				</div>

				<br>

				<div class="input-group">
					<span class="input-group-addon">Object Meeting</span> <input
						type="text" class="form-control" placeholder="Meeting Object"
						name="object" />
				</div>

				<br> Do you want also supervisors? &nbsp;&nbsp;<input type="radio"
					name="radios" value="radio1" checked> Yes &nbsp;&nbsp;<input
					type="radio" name="radios" value="radio2"> No <br>
				<br>

				<div class="input-group">
					<span class="input-group-addon">Meeting Reasons</span> <input
						type="text" class="form-control"
						placeholder="Insert the Meeting Reasons" name="reasons" />
				</div>

				<br>
				<br>

				<div class="input-group pull-right">
					<button type="submit" class="btn btn-success">
						<i class="fa fa-arrow-right"></i> Continue
					</button>
					&nbsp;&nbsp;
				</div>

			</form>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>