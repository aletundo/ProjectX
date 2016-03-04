<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>Sign Up</title>
</head>
<body>
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			<form class="form col-md-12 center-block" action="./signup"
				method="POST" role="form" autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Fullname</span> <input type="text"
						class="form-control" placeholder="Insert the fullname"
						name="fullname" value="${fn:escapeXml(param.fullname)}" required />
				</div>
				<span class="help-block">${messages.fullname}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Username</span> <input type="text"
						class="form-control" value="${fn:escapeXml(param.username)}" placeholder="Insert the username"
						name="username" required>
				</div>
				<span class="help-block">${messages.username}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Password</span> <input
						type="password" class="form-control"
						placeholder="Insert the password"
						value="${fn:escapeXml(param.pw)}" name="pw" required>
				</div>
				<span class="help-block">${messages.pw}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">E-mail</span> <input
						class="form-control" type="text" placeholder="foo@bar.baz"
						name="mail" value="${fn:escapeXml(param.mail)}" required />
				</div>
				<span class="help-block">${messages.mail}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Skills</span>
					<textarea class="form-control" placeholder="Insert the skills"
						name="skills" rows="1" required></textarea>
				</div>
				<span class="help-block">${messages.skills}</span><br>
				<div class="input-group">
					<span class="input-group-addon">Type</span> <select class="form-control" name="type" required>
						<option>ProjectManager</option>
						<option>Senior</option>
						<option>Junior</option>
					</select>
				</div>
				<span class="help-block">${messages.error}</span>
				<br> <br>
				<div class="input-group pull-right">
					<button type="submit" class="btn btn-success">
						<i class="fa fa-user-plus"></i> Sign-up!
					</button>
					&nbsp;&nbsp;
					<button class="btn btn-danger" type="reset">
						<i class="fa fa-undo"></i> Reset
					</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>