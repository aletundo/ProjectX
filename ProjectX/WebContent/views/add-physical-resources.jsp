<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<html>
<head>
<jsp:include page="/views/sharable/head-imports.jsp" />
<title>ProjectX - Choose physical resources</title>
</head>
<body>
	<jsp:include page="/views/sharable/navbar.jsp" />
	<div class="row center-block">
		<div class="col-md-12 col-xs-12">
			Choose and add physical resources to your project
			<hr>
			<form class="form col-md-12 center-block"
				action="./addphysicalresources?idProject=${param.idProject }"
				method="POST" role="form" autocomplete="off">
				<c:forEach items="${requestScope.resources}" var="resource" varStatus="k">
				<div class="input-group">
					<span class="input-group-addon"><c:out value="${resource.type }"></c:out></span> <input type="text"
						class="form-control" name="${k.count }" placeholder="Insert the quantity" />
					<span class="input-group-addon">Available:&nbsp;&nbsp;<span class="badge">${resource.quantity }</span></span>
				</div> <br>
				</c:forEach>
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