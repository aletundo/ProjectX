<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Project X - Where project management becomes a breeze</title>
<jsp:include page="/views/sharable/head-imports.jsp" />
</head>
<body>
	<div class="container">
		<div class="row center-block">
			<div class="col-md-7 col-xs-12">
				<h1>Welcome to ProjectX,</h1>
				<p class="lead">Where project management becomes a breeze.</p>
			</div>
			<div class="col-md-5 col-xs-12">
				<form class="form center-block" action="./login" method="POST"
					autocomplete="off">
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-user"></i></span>
						<input class="form-control input-lg" type="text" name='user'
							placeholder="Username" required />
					</div>
					<div class="form-group input-group">
						<span class="input-group-addon"><i class="fa fa-lock"></i></span>
						<input class="form-control input-lg" name="pw"
							placeholder="Password" type="password" required>
					</div>
					<c:if test="${requestScope.errorLogin == 'True' }">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
							<strong><i class="fa fa-ban"></i>
							Oops!</strong> Either username or password are wrong.
						</div>
					</c:if>
					<div class="form-group">
						<button class="btn btn-success btn-lg btn-block" type="submit">
							<i class="fa fa-sign-in"></i> Log-in
						</button>
					</div>
					<a href="#" data-toggle="tooltip" data-placement="bottom"
						title="Please contact your SysAdmin">Forgot your password!?!</a>
				</form>
			</div>
		</div>
	</div>
	<!-- /.container -->
</body>
</html>