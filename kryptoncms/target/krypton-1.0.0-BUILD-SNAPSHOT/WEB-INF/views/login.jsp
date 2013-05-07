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
				<form action="<c:url value='/j_spring_security_check' />" method="post" class="membership_login">
					<div class="control-group input-prepend input-append">
						<label for="username" class="add-on pull-left">
							<span class="icon-asterisk"></span>Username
						</label>
					    <input type="text" class="pull-left" id="username" name="j_username" 
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
					    <input type="password" id="password" class="pull-left" name="j_password" 
					    	   placeholder="Password"
					     	   data-required
					    	   data-describedby="password-description"
					    	   data-description="password" data-pattern=".{5,}"  />
					    <label for="password" class="add-on" id="password-description"></label>
					</div>
					<div class="btn-group">
						<div class="input-prepend pull-left">
							<button type="button" class="btn" data-toggle="button" value="false">
								Remember Me
							</button>
						</div>
						<div class="input-append pull-left">
							<button class="btn btn-primary" type="submit" >Login</button>
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