<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="panel panel-primary">
	<!-- Default panel contents -->
	<div class="panel-heading">
		<span class="fa fa-group"></span><b>&nbsp;Available supervisors</b>
	</div>

	<table class="table table-responsive table-striped">
		<thead>
			<tr>
				<th><i class="fa fa-user"></i>&nbsp;Fullname</th>
				<th><i class="fa fa-sitemap"></i>&nbsp;Type</th>
				<th><i class="fa fa-diamond"></i>&nbsp;Skills</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.candidates}" var="candidate">
				<tr>
					<td><div class="radio">
							<label><input type="radio" name="id-supervisor"
								value="${candidate.idUser}"><strong> <c:out
										value="${candidate.fullname}"></c:out></strong></label>
						</div></td>
					<td><div class="radiotext">
							<span class="label label-warning"><c:out
									value="${candidate.type}"></c:out></span>
						</div></td>
					<td><div class="radiotext">
							<em><c:out value="${candidate.skills}"></c:out></em>
						</div></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>