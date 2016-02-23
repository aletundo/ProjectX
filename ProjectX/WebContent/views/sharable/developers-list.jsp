<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-default">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<span class="fa fa-group"></span><b>&nbsp;Available developers</b>
	</div>
	<!-- List group -->
	<ul class="list-group list-results">
		<c:forEach items="${requestScope.candidates}"
			var="candidate">
			<li class="radio"><label><input type="radio"
					name="id-developer" value="${candidate.idUser}">
				<c:out value="${candidate.name}"></c:out></label></li>
		</c:forEach>
	</ul>
</div>