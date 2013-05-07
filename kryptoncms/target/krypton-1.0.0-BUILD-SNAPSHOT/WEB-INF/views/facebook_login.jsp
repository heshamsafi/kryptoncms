<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Facebook login</title>
</head>
<body>
	<div id="fb-root"></div>
	<script type="text/javascript">
		window.fbAsyncInit = function() {
			FB.init({
				appId : '456580091064303',
				status : true,
				cookie : true,
				xfbml : true
			});
			FB.Event.subscribe('auth.login', function(response) {
				//triggered after signing in
				login();
			});
			FB.Event.subscribe('auth.logout', function(response) {
				//triggered after signing out
				logout();
			});
			FB.getLoginStatus(function(response) {
				if (response.session) {
					greet();
				}
			});
		};

		(function() {
			var e = document.createElement('script');
			e.type = 'text/javascript';
			e.src = document.location.protocol
					+ '//connect.facebook.net/en_US/all.js';
			e.async = true;
			document.getElementById('fb-root').appendChild(e);
		}());

		function login() {
			FB.api('/me',
					function(response) {
						alert('You have successfully logged in, '
								+ response.name + "!");
					});
		}
		function logout() {
			alert('You have successfully logged out!');
		}
		function greet() {
			FB.api('/me', function(response) {
				alert('Welcome, ' + response.name + "!");
			});
		}
	</script>
	<fb:login-button autologoutlink='true'
		&nbsp;perms='email,user_birthday,status_update,publish_stream'></fb:login-button>

</body>
</html>