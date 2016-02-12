<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form class="form center-block" action="j_security_check"
	method="POST" autocomplete="off">
	<div class="form-group input-group">
            <span class="input-group-addon"><i class="fa fa-user"></i></span>
            <input class="form-control input-lg" type="text" name='username' placeholder="Username" required autofocus/>          
          </div>
	<div class="form-group input-group">
            <span class="input-group-addon"><i class="fa fa-lock"></i></span>
		<input class="form-control input-lg" name="j_password"
			placeholder="Password" type="password" autofocus required>
	</div>
	<div class="form-group">
			<button class="btn btn-success btn-lg btn-block" type="submit">
				<i class="fa fa-sign-in"></i> Log-in
			</button>
	</div>
	<a href="#" data-toggle="tooltip" data-placement="bottom" title="Please contact your SysAdmin">Forgot your password!?!</a>
</form>