<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Set precedences</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			Set the precedences for the stage <c:out value="${sessionScope.stagesQueue[0].name}"></c:out>
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/addprecedences"
				method="POST" role="form" autocomplete="off">

				<div class="panel panel-default">
					<!-- Default panel contents -->
					<div class="panel-heading">
						<span class="fa fa-list"></span>&nbsp;<b>Stages</b>
					</div>
					<!-- List group -->
					<ul class="list-group list-results">
						<c:forEach items="${sessionScope.stages}" var="stage">
							<li class="checkbox"><label><input type="checkbox"
									name="id-precedences" value="${stage.idStage}"> <c:out
										value="${stage.name}"></c:out></label></li>
						</c:forEach>
					</ul>
				</div>
				
				<input type="hidden" name="id-project" value="${requestScope.idProject}" />
				<!-- <input type="hidden" name="id-stage" value="${ sessionScope.stagesQueue[0].idStage}" /> -->
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
			<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>