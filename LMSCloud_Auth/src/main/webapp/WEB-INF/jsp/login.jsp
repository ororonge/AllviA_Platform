<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
response.setHeader("Pragma","no-cache"); 
response.setDateHeader("Expires",0); 
response.setHeader("Cache-Control", "no-cache");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value="/js/jquery-3.5.1.min.js"/>"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#btnLogin").click(function() {
// 		$.ajax({
// 			type:"POST",
// 			url:"/oauth/token",
// 			data: {
// 				"client_id": "head-admin",
// 				"secret": "allvia-seckey-v0.0.1-head-admin",
// 				"grant_type": "password",
// 				"scope": "webclient",
// 				"username": "testadmin",
// 				"password": "q12345"
// 			},
// 			dataType:"JSON",
// 			success : function(data) {
// 				console.log(data);
// 			},
// 			complete : function(data) {
			
// 			},
// 			error : function(xhr, status, error) {
// 			     alert("에러발생");
// 			}
// 		});

		var myClientId = "testadmin";
		var mySecret = "q12345";
	    $.ajax({
	        url: "/jwt/token",
	        data: {
				"client_id": "head-admin",
				"secret": "allvia-seckey-v0.0.1-head-admin",
				"grant_type": "password",
				"scope": "webclient",
				"username": "testadmin",
				"password": "q12345"
			},
	        type: 'POST', 
	        dataType: "json", 
	        contentType: "application/x-www-form-urlencoded",
// 	        beforeSend: function (xhr) {
// 	            xhr.setRequestHeader("Authorization", "Basic " + btoa(myClientId+":"+ mySecret));
// 	        },
	        success: function(data) {
	            console.log(data);
	        }
	    }); 
	});
});
</script>
</head>
<body>
<form method="post">
userid: <input type="text" name="username" /><br>
pwd :   <input type="text" name="password" />
<input type="button" id="btnLogin" value="로그인" />
</form>
</body>
</html>