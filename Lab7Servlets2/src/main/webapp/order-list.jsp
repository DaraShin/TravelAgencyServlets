<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="Model.Entity.Order"%>

<%
List<Order> ordersList = (List<Order>) request.getAttribute("orders-list");
String role = (String)request.getSession().getAttribute("role");
if(role==null){
	request.getRequestDispatcher("./TripsAppServlet?action=show-login").forward(request,response);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Мои заказы</title>
<link rel="stylesheet" href="./styles/common-styles.css" type="text/css" />
</head>
<body>
	<div class="navbar">
		<a href="./TripsAppServlet?action=show-all-trips" >Туры</a> 
		<a <% if( !role.equals("client")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <%} %> href="./TripsAppServlet?action=show-user-orders" class="selected-menu-item">Мои заказы</a> 
		<a <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %> href="./TripsAppServlet?action=show-all-orders" class="selected-menu-item">Заказы</a> 
		<a <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %> href="./TripsAppServlet?action=show-discount">Скидки</a> 
		<a href="./TripsAppServlet?action=logout"><% if( role.equals("guest")) {%> Войти <% } else {  %> Выйти <%} %></a>
		<p><%= request.getSession().getAttribute("login") %></p>
	</div>

	<div class="central-section">
		<h1>Заказы</h1>

		<div>
			<table border="1">
				<!--<caption>Таблица размеров обуви</caption>-->
				<thead>
					<tr>
						<th>ID</th>
						<th>Оплата</th>
						<th>Покупатель</th>
						<th>Куда</th>
						<th>Тип поездки</th>
						<th>Цена</th>
						<th width="1px"></th>
					</tr>
				</thead>
				<%
				for (Order order : ordersList) {
				%>
				<tr>
					<td><%=order.getOrderId()%></td>
					<td>
					<% if(order.isPaid()) {%>
						оплачен
					<%} else if(role.equals("client")) {%>
					
						<form action="TripsAppServlet" method="POST">
							<input type="hidden" name="action" value="pay-order"/>
							<input type="hidden" name="order-id" value="<%=order.getOrderId()%>"/>
							<input type="submit" value="оплатить"/>
						</form>
					<%} %>
					</td>
					<td><%=order.getClient().getSurname() + " " +  order.getClient().getFirstname() + " " + order.getClient().getMiddlename()%></td>
					<td><%=order.getTrip().getDestination()%></td>
					<td><%=order.getTrip().getTripType()%></td>
					<td><%=order.getTrip().getPrice()%></td>
					<td></td>
				</tr>
				<%}%>

				<!--<tr>
					<td>2</td>
					<td>оплачен</td>
					<td>Шинкевич Дарья Олеговна</td>
					<td>экскурсия</td>
					<td>200</td>
					<td>
						<button disabled>Оплатить</button>
					</td>
				</tr>  -->
			
			</table>
		</div>
	</div>

</body>
</html>