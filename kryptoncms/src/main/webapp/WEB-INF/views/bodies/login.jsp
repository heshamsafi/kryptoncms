<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>
<form action="<c:url value='/j_spring_security_check' />" method="post"
	class="membership_login" style="margin-bottom: 0px"
	data-validation-enable>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">x</button>
		<h1>Login</h1>
	</div>

	<div class="modal-body">
		<div>
			<div class="control-group input-prepend input-append">
				<label for="username" class="add-on pull-left"> <span
					class="icon-asterisk"></span>Username
				</label> <input type="text" class="pull-left" id="username"
					name="j_username" placeholder="Username" data-required
					data-describedby="username-description" data-description="username"
					data-pattern=".{3,}" /> <label id="username-description"
					for="username" class="add-on"></label>
			</div>
		</div>
		<div>
			<div class="control-group input-prepend input-append">
				<label for="password" class="add-on pull-left"> <span
					class="icon-asterisk"></span>Password
				</label> <input type="password" id="password" class="pull-left"
					name="j_password" placeholder="Password" data-required
					data-describedby="password-description" data-description="password"
					data-pattern=".{5,}" /> <label for="password" class="add-on"
					id="password-description"></label>
			</div>
		</div>
	</div>
		<ul class="thumbnails" style="margin-left:0px;display:none">
		<li class="span1.5">
		<a id="facebook" href="<c:url value='/signin/facebook' />" data-method="POST"  class="thumbnail"> 
				<img
				src='<c:url value="/resources/img/facebook.png" />' alt="facebook">
		</a>
		</li>

		<li class="span1.5"><a href="#" class="thumbnail"> <img
				src='<c:url value="/resources/img/twitter.png" />' alt="twitter">
		</a></li>

		<li class="span1.5"><a href="#" class="thumbnail"> <img
				src='<c:url value="/resources/img/google.png" />' alt="google">
		</a></li>
	</ul>
	<div class="modal-footer">
		<div class="btn-group">
			<div class="input-prepend pull-left">
				<button type="button" class="btn" data-toggle="button" value="false">
					Remember Me</button>
			</div>
			<div class="input-append pull-left">
				<button class="btn btn-primary" type="submit">Login</button>
			</div>
		</div>
	</div>
</form>