<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Create tasks</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			<strong>Perfect!</strong> Now you can divide your stages into tasks.
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/addtask" method="POST" role="form"
				autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Name</span> <input type="text"
						class="form-control" placeholder="Insert the name" name="name" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Start day</span> <input
						class="form-control" type="text"
						placeholder="Insert the start day (yyyy-MM-dd)" name="start-day" />
				</div>
				<br>
				<div class="input-group">
					<span class="input-group-addon">Finish day</span> <input
						class="form-control" type="text"
						placeholder="Insert the finish day (yyyy-MM-dd)" name="finish-day" />
				</div>
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