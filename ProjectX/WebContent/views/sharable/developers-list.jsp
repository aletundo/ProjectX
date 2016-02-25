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
		<c:forEach items="${sessionScope.candidates}" var="candidate" varStatus="k">
			<li class="radio"><label><input type="radio"
					name="index" value="${k.count }"> <c:out
						value="${candidate.fullname}"></c:out>&nbsp;|&nbsp;<c:out
						value="${candidate.type}"></c:out>&nbsp;|&nbsp;<c:out
						value="${candidate.temporaryHoursAvailable}"></c:out></label></li>
		</c:forEach>
	</ul>
</div>