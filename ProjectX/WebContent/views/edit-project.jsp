<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Edit <c:out value="${requestScope.project.name }"></c:out></title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			Enter your changes and update the project or continue to add stages to it.
			<hr>
			<form class="form col-md-12 center-block"
				action="${pageContext.request.contextPath}/editproject?idProject=${requestScope.project.idProject}" method="POST"
				role="form" autocomplete="off">
				<div class="input-group">
					<span class="input-group-addon">Name</span> <input type="text"
						class="form-control" placeholder="${requestScope.project.name}" name="name"
						value="${fn:escapeXml(param.name)}"/>
				</div>
				<span class="help-block">${messages.name}</span> <br>

				<div class="input-group">
					<span class="input-group-addon">Goals</span>
					<textarea class="form-control" placeholder="${requestScope.project.goals}"
						name="goals" rows="1" ></textarea>
				</div>
				<span class="help-block">${messages.goals}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Requirements</span>
					<textarea class="form-control"
						placeholder="${requestScope.project.requirements}" name="requirements" rows="1"
						></textarea>
				</div>
				<span class="help-block">${messages.requirements}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Budget</span> <input type="text"
						class="form-control" name="budget" placeholder="${requestScope.project.budget}"
						value="${fn:escapeXml(param.budget)}"  /> <span
						class="input-group-addon">&euro;</span>
				</div>
				<span class="help-block">${messages.budget}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Estimated costs</span> <input
						type="text" class="form-control" name="estimatedcosts"
						placeholder="${requestScope.project.estimatedCosts}"
						value="${fn:escapeXml(param.estimatedcosts)}"  /> <span
						class="input-group-addon">&euro;</span>
				</div>
				<span class="help-block">${messages.estimatedcosts}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Client</span> <input
						class="form-control" type="text"
						placeholder="Insert the name of the client" name="clientname"
						value="${fn:escapeXml(param.clientname)}"  />
				</div>
				<span class="help-block">${messages.clientname}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Client mail</span> <input
						class="form-control" type="text"
						placeholder="client@domain.example" name="clientmail"
						value="${fn:escapeXml(param.clientmail)}"  />
				</div>
				<span class="help-block">${messages.clientmail}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Start</span> <input
						class="form-control" type="text"
						placeholder="${requestScope.project.start}" name="start"
						value="${fn:escapeXml(param.start)}" />
				</div>
				<span class="help-block">${messages.start}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Deadline</span> <input
						class="form-control" type="text"
						placeholder="${requestScope.project.deadline}" name="deadline"
						value="${fn:escapeXml(param.deadline)}"/>
				</div>
				<span class="help-block">${messages.deadline}</span> <br>
				<div class="input-group">
					<span class="input-group-addon">Subject areas</span> <input
						type="text" class="form-control"
						placeholder="${requestScope.project.subjectAreas}"
						name="subjectareas" value="${fn:escapeXml(param.subjectareas)}"
						/>
				</div>
				<span class="help-block">${messages.subjectareas}</span> <br> <br>
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
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>