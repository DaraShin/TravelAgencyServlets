<%@ page language="java" contentType="text/html; UTF-8"
	pageEncoding="UTF-8"%>
	
<%
Boolean showInvalidMessage = request.getAttribute("invalid-params") != null && (Boolean)request.getAttribute("invalid-params");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Вход в систему</title>
<link rel="stylesheet" href="./styles/common-styles.css" type="text/css" />
</head>
<body>
	<div class="container">
		<form action="TripsAppServlet" method="POST">
		<p class="invalid-pswd-msg " <% if(showInvalidMessage) {%> style="display:block;" <% } else {  %> style="display:none;" <%} %>>Неверный логин или пароль</p>
			<div class="input-row">
				<label class="login-label">Логин:</label> <input name="login" type="text" />
			</div>
			<div class="input-row">
				<label class="login-label ">Пароль:</label> <input name="password" type="password" />
			</div>
			<div class="center-btn-block">
				<input type="hidden" name="action" value="login">
				<input type="submit" value="войти" />
				<a href="./TripsAppServlet?action=login-guest"><button type="button" class="btn-in-link">войти как гость</button></a>
			</div>

		</form>
	</div>

</body>
</html>