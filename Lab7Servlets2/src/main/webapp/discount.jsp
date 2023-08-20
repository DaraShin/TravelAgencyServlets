<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String role = (String)request.getSession().getAttribute("role");
if(role==null){
	request.getRequestDispatcher("./TripsAppServlet?action=show-login").forward(request,response);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Скидки</title>
<link rel="stylesheet" href="./styles/common-styles.css" type="text/css" />
</head>
<body>
<div class="navbar">
		<a href="./TripsAppServlet?action=show-all-trips" >Туры</a> 
		<a <% if( !role.equals("client")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <%} %> href="./TripsAppServlet?action=show-user-orders">Мои заказы</a> 
		<a <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %> href="./TripsAppServlet?action=show-all-orders">Заказы</a> 
		<a <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %> href="./TripsAppServlet?action=show-discount" class="selected-menu-item">Скидки</a> 
		<a href="./TripsAppServlet?action=logout"><% if( role.equals("guest")) {%> Войти <% } else {  %> Выйти <%} %></a>
		<p><%= request.getSession().getAttribute("login") %></p>
	</div>
	<div class="central-section">
		<h1>Задать скидку постоянным клиентам</h1>
		<form action="TripsAppServlet" method="POST">
			<div class="input-row">
				<label>Скидка</label>
				<div>
					<input name="discount" type="number" value="<%=request.getAttribute("discount")%>"/>
				</div>
			</div>
			
			<input type="hidden" name="action" value="set-discount"/>
			<input type="submit" value="Сохранить">
		</form>
	</div>
</body>
</html>