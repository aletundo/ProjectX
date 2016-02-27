<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Create a project</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			Create a project is very <i><b>easy</b></i>! A step-by-step procedure is going to
			help you.
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/addproject" method="POST" role="form"
				autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Name</span> <input type="text"
						class="form-control" placeholder="Insert the name" name="name" required />
				</div>

				<br>

				<div class="input-group">
					<span class="input-group-addon">Goals</span>
					<textarea class="form-control" placeholder="Insert the goals"
						name="goals" rows="1" required></textarea>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Requirements</span>
					<textarea class="form-control"
						placeholder="Insert the requirements" name="requirements" rows="1" required></textarea>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Budget</span> <input type="text"
						class="form-control" name="budget" placeholder="Insert the budget" required/>
					<span class="input-group-addon">&euro;</span>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Estimated costs</span> <input
						type="text" class="form-control" name="estimated-costs"
						placeholder="Insert the estimated costs" required/> <span
						class="input-group-addon">&euro;</span>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Client</span> <input
						class="form-control" type="text"
						placeholder="Insert the name of the client" name="client-name" required/>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Client mail</span> <input
						class="form-control" type="text"
						placeholder="client@domain.example" name="client-mail" required/>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Start</span> <input
						class="form-control" type="text"
						placeholder="Insert the start (YYYY-mm-DD)" name="start" required/>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Deadline</span> <input
						class="form-control" type="text"
						placeholder="Insert the deadline (YYYY-mm-DD)" name="deadline" required/>
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Subject areas</span> <input
						type="text" class="form-control"
						placeholder="Insert the subjet areas (preferably splitted by a comma)"
						name="subject-areas" required/>
				</div>
				<br> <br>
				<div class="input-group pull-right">
					<button type="submit" class="btn btn-success">
						<i class="fa fa-arrow-right"></i>&nbsp;Continue
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