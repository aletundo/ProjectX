<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/head-imports.jsp" />
<title>ProjectX - Related clients</title>
</head>
<body>
	<jsp:include page="/views/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-9 col-xs-12">
		<jsp:include page="/views/search-form.jsp" />
		</div>
		<div class="col-md-3 col-xs-12">
			<jsp:include page="/views/sidebar-project-manager.jsp" />
		</div>
	</div>
	<jsp:include page="/views/footer.jsp" />
</body>
</html>
</html>