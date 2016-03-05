<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Related clients</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			<form class="from col-md-12 center-block" role="search"
				autocomplete="off" method="POST"
				action="./searchclients">
				<jsp:include page="/views/sharable/search-form-input.jsp" />
			</form>
		</div>
	</div>
	<jsp:include page="/views/sharable/footer.jsp" />
</body>
</html>
</html>