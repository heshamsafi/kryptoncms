<%@ include file="/WEB-INF/views/includes/taglibs.jsp"%>

<html lang="en">
<head>
	<%@ include file="/WEB-INF/views/includes/common_head.jsp"%>
</head>
<body>
	<div class="container-fluid">
		<%@ include file="/WEB-INF/views/includes/header.jsp"%>
		<!--Body-->
		<div class="row-fluid">
			<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
			<div class="span9 well well-large">
				<form action="<c:url value='/membership/register/2' />" method="post" class="membership_register">
					<div class="control-group input-prepend input-append">
						<label for="username" class="add-on pull-left"><span class="icon-asterisk"></span>Username</label>
					    <input type="text" id="username" name="username" class="pull-left" 
					    	   placeholder="Username" 
					    	   data-required
					    	   data-describedby="username-description"
					    	   data-description="username" data-pattern=".{3,}" />
						<label id="username-description" for="username" class="add-on"></label>
					</div>
	    			<div class="control-group input-prepend input-append">
	    				<label for="password" class="add-on pull-left">
	    					<span class="icon-asterisk"></span>Password
	    				</label>
					    <input type="password" id="password" name="password" class="pull-left"
					    	   placeholder="Password" 
					    	   data-required
					    	   data-describedby="password-description"
					    	   data-description="password"
					    	   data-pattern=".{5,}"  />
					    <label for="password" class="add-on" id="password-description"></label>
					</div>
					
	    			<div class="control-group input-prepend input-append">
	    				<label for="password_confirm" class="add-on pull-left">
	    					<span class="icon-asterisk"></span>Confirm Password
	    				</label>
					    <input type="password" id="password_confirm" placeholder="Confirm Password" 
					    	   class="pull-left"
					    	   data-required 
					    	   data-conditional="confirm"
					    	   data-conditional-confirm-with="password"
					    	   data-describedby="password-confirm-description"
					    	   data-description="password-confirm" />
					    <label for="password" class="add-on" id="password-confirm-description"></label>
					</div>
					<div class="btn-group">
						<div class="input-prepend pull-left">
							<button type="button" class="btn" data-toggle="button" value="false">Remember Me</button>
						</div>
						<div class="input-append pull-left">
							<button class="btn btn-primary" type="submit" >Register</button>
						</div>
					</div>
				</form>
			</div>
		</div>
		<!--End Body-->
		<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
	</div>
</body>
</html>