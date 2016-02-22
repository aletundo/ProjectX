<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Access denied</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
			<div class="alert alert-danger" role="alert">
				<h3><i class="fa fa-frown-o"></i>
&nbsp;<em><strong>Um, I do
						not think you have permission!</strong></em></h3>
			</div>
		</div>
		<div class="col-md-3 col-xs-12">
			<c:if test="${sessionScope.userType == 'ProjectManager'}">
				<jsp:include page="/views/sharable/sidebar-project-manager.jsp" />
			</c:if>
			<c:if test="${sessionScope.userType == 'Senior' }">
				<jsp:include page="/views/sharable/sidebar-senior.jsp" />
			</c:if>
			<c:if test="${sessionScope.userType == 'Junior' }">
			</c:if>
			<p>
				Your Session ID=
				<c:out value="${cookie['JSESSIONID'].value}"></c:out>
			</p>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>