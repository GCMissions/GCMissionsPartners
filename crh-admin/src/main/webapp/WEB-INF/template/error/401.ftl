<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>unauthorized</title>
        <style>
        	html {
				background: repeat scroll 0 0 #F5F5F5;
			}
			body{
				color: #FF6623;
				text-align: center;
				vertical-align: middle;
				font-size: 55px;
				font-weight: 900;
			}
        </style>
    </head>
    <body>
		No access to this page!
    </body>
    <script>
    	setTimeout(function(){
    		location.href = "${rc.contextPath}/web/login/login";
    	}, 5000);
    </script>
</html>
