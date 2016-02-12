<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Project X - Home</title>
<script
	src="./static/js/jquery-2.1.4.min.js"></script>
<script
	src="./static/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="./static/css/bootstrap.min.css">
<link rel="stylesheet"
	href="./static/css/bootstrap-theme.min.css">
<link rel="stylesheet"
	href="./static/css/font-awesome.min.css">
	<link rel="stylesheet"
	href="./static/css/custom.css">
	<script>$(document).ready(function() {
	    $("body").tooltip({ selector: '[data-toggle=tooltip]' });
	});</script>
</head>
<body>
	<div class="container">
		<div class="row center-block">
			<div class="col-md-7 col-xs-12">
				<h1>Welcome to ProjectX,</h1>
				<p class="lead">where project management becomes a breeze.</p>
			</div>
			<div class="col-md-5 col-xs-12">
				<jsp:include page="/views/login-form.jsp" />
			</div>
		</div>
	</div>
	<!-- /.container -->
</body>
</html>