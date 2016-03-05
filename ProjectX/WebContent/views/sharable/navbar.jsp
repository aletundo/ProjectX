<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">ProjectX</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li><a href="./myprojects"><i class="fa fa-folder"></i>&nbsp;My
						Projects</a></li>
				<c:choose>
				<c:when test="${sessionScope.userType == 'ProjectManager'}">
					<li><a href="./addproject"><i class="fa fa-plus"></i>&nbsp;Create
							a project</a></li>
					<li class="dropdown">
						<a class="dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
							<i class="fa fa-search"></i>&nbsp;Search <span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="./searchprojects">Related projects</a></li>
							<li><a href="./searchclients">Related clients</a></li>
						</ul>
					</li>
				</c:when>
				<c:otherwise>
					<li><a href="./searchprojects"><i class="fa fa-search"></i>&nbsp;Search related projects</a></li>
				</c:otherwise>
				</c:choose>
				<li><a href="./logout"><i class="fa fa-sign-out"></i>&nbsp;Logout</a></li>
			</ul>
		</div>
	</div>
</nav>