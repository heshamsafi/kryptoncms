<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-type" content="text/html; charset=utf-8">
<title>Twitter login</title>
<script
	src="http://platform.twitter.com/anywhere.js?id=ECadGjfOnTlBTwz0Xnaxhg&v=1"
	type="text/javascript"></script>
</head>
<body>

	<span id="twitter-connect-placeholder"></span>
	<script type="text/javascript">
		twttr.anywhere(function(T) {

			var currentUser, screenName, profileImage, profileImageTag;

			if (T.isConnected()) {
				// if connected , show user's data
				// T.currentUser holds information about the visiting user
				currentUser = T.currentUser;
				screenName = currentUser.data('screen_name');
				profileImage = currentUser.data('profile_image_url');
				profileImageTag = "<img src='" + profileImage + "'/>";
				$('#twitter-connect-placeholder').append(
						"Logged in as " + profileImageTag + " " + screenName);
			} else {
				// if not , show a connect button
				T("#twitter-connect-placeholder").connectButton({
					size : "large",
					authComplete : function(user) {
						// triggered when auth completed successfully
						// user holds the data of the authenticated user
					},
					signOut : function() {
						// triggered when user logs out
					}
				});
			}
			;

		});
	</script>
</body>
</html>