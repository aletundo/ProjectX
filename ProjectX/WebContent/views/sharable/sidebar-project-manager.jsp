<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="panel panel-info">
	<div class="panel-heading">
		<h3 class="panel-title">
			<i class="fa fa-cogs"></i> Management
		</h3>
	</div>
	<!-- List group -->
	<div class="list-group">
		<a class="list-group-item" href="${pageContext.request.contextPath}/addproject"><i class="fa fa-plus"></i>&nbsp;Create
			a project</a> 
		<a class="list-group-item" href="${pageContext.request.contextPath}/organizemeeting"><i class="fa fa-users"></i>&nbsp;Organize a meeting</a>
		<div class="dropdown">
			<div class="list-group-item dropdown-toggle" data-toggle="dropdown"
				aria-haspopup="true" aria-expanded="true">
				<i class="fa fa-search"></i>&nbsp;Search <span class="caret"></span>
			</div>
			<ul class="dropdown-menu">
				<li><a href="${pageContext.request.contextPath}/searchprojects">Related projects</a></li>
				<li><a href="${pageContext.request.contextPath}/searchclients">Related clients</a></li>
			</ul>
		</div>
	</div>
</div>
