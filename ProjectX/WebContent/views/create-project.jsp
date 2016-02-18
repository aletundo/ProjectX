<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Create a project</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<%
		//Check session exists
		String user = (String) session.getAttribute("username");
		String sessionID = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID"))
					sessionID = cookie.getValue();
			}
		}
	%>
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			Create a project is very <i><b>easy</b></i>! A step-by-step procedure is going to
			help you.
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/addproject" method="POST" role="form"
				autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Name</span> <input type="text"
						class="form-control" placeholder="Insert the name" name="name" />
				</div>

				<br>

				<div class="input-group">
					<span class="input-group-addon">Goals</span>
					<textarea class="form-control" placeholder="Insert the goals"
						name="goals" rows="1"></textarea>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Requirements</span>
					<textarea class="form-control"
						placeholder="Insert the requirements" name="requirements" rows="1"></textarea>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Budget</span> <input type="text"
						class="form-control" name="budget" placeholder="Insert the budget" />
					<span class="input-group-addon">&euro;</span>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Estimated costs</span> <input
						type="text" class="form-control" name="estimated-costs"
						placeholder="Insert the estimated costs" /> <span
						class="input-group-addon">&euro;</span>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Client</span> <input
						class="form-control" type="text"
						placeholder="Insert the name of the client" name="client-name" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Client mail</span> <input
						class="form-control" type="text"
						placeholder="client@domain.example" name="client-mail" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Start</span> <input
						class="form-control" type="text"
						placeholder="Insert the start (YYYY-mm-DD)" name="start" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Deadline</span> <input
						class="form-control" type="text"
						placeholder="Insert the deadline (YYYY-mm-DD)" name="deadline" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Estimated duration</span> <input
						type="text" class="form-control"
						placeholder="Insert the estimated duration (weeks)"
						name="estimated-duration" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Subject areas</span> <input
						type="text" class="form-control"
						placeholder="Insert the subjet areas (preferably splitted by a comma)"
						name="subject-areas" />
				</div>
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
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>