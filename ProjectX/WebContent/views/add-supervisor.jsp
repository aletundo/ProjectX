<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Assign a supervisor</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			Select a supervisor for the stage.
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/addsupervisor"
				method="POST" role="form" autocomplete="off">

				<c:choose>
					<c:when test="${requestScope.outsourcing == true }">
						<div class="alert alert-warning" role="alert">
							<i class="fa fa-exclamation-triangle"></i>&nbsp;<em>Ops,
								sorry :( There are no available supervisors. Please, fill the
								outsourcing form below.</em>
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
							<jsp:include page="/views/sharable/users-list.jsp" />
						</div>
					</c:otherwise>
				</c:choose>

				<div class="input-group">
					<div class="checkbox">
						<label><input type="checkbox" name="completed" value="">Check
							me if you have inserted all the stages and you are ready to set
							the precedences between them.</label>
					</div>
				</div>
				<input type="hidden" name="id-project" value="${param.idProject}" />
				<input type="hidden" name="id-stage" value="${param.idStage}" /> <br>
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
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>