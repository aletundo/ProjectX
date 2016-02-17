<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="from col-md-12 center-block" role="search"
	autocomplete="off" method="POST"
	action="${pageContext.request.contextPath}/searchclients">
	<div class="input-group">
		<span class="input-group-addon">Subject area</span> <input type="text"
			class="form-control" placeholder="Insert keyword" name="subject-area" />
	</div>
<br>
	<div class="input-group pull-right">
		<button type="submit" class="btn btn-success">
			<i class="fa fa-search"></i>&nbsp;Search
		</button>
		&nbsp;&nbsp;
		<button class="btn btn-danger" type="reset">
			<i class="fa fa-undo"></i>&nbsp;Reset
		</button>
	</div>
</form>