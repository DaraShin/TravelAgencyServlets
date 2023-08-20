<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="Model.Entity.Trip"%>

<%
List<Trip> tripsList = (List<Trip>) request.getAttribute("trips-list");
if (tripsList == null)
	tripsList = new ArrayList();
String role = (String)request.getSession().getAttribute("role");
if(role==null){
	request.getRequestDispatcher("./TripsAppServlet?action=show-login").forward(request,response);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Доступные туры</title>
<link rel="stylesheet" href="./styles/common-styles.css" type="text/css" />
</head>
<body>
	<div class="navbar">
		<a href="./TripsAppServlet?action=show-all-trips" class="selected-menu-item">Туры</a> 
		<a <% if( !role.equals("client")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <%} %> href="./TripsAppServlet?action=show-user-orders">Мои заказы</a> 
		<a <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %> href="./TripsAppServlet?action=show-all-orders">Заказы</a> 
		<a href="./TripsAppServlet?action=show-discount" <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %>>Скидки</a> 
		<a href="./TripsAppServlet?action=logout"><% if( role.equals("guest")) {%> Войти <% } else {  %> Выйти <%} %></a>
		<p><%= request.getSession().getAttribute("login") %></p>
	</div>
	<div class="central-section">
		<h1>Список доступных туров</h1>
		<form action="TripsAppServlet" method="POST">
			<label class="tour-type-label">Выберите тип тура:</label> <select name="tourType">
				<option>отдых</option>
				<option>шоппинг</option>
				<option>экскурсия</option>
			</select> <input type="hidden" name="action" value="get-hot-tours-of-type">
			<input type="submit" value="показать горящие туры" />
		</form>
		<form action="TripsAppServlet" method="POST">
			<input type="hidden" name="action" value="show-all-trips"> <input
				type="submit" value="показать все" />
		</form>
		<h2 class="table-title">Все доступные туры</h2>
		<div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Куда</th>
						<th>Тип тура</th>
						<th>Цена</th>
						<th <%if (role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>горящий тур</th>
						<th <%if (!role.equals("client")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>заказ</th>
						<th <%if (!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>горящий тур</th>
						<!--<th <%if (!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>сохранить</th>-->
						<th width="1px"></th>
					</tr>
				</thead>
				<%
				for (Trip trip : tripsList) {
				%>
				<tr>
					<td><%=trip.getTripId()%></td>
					<td><%=trip.getDestination()%></td>
					<td><%=trip.getTripType()%></td>
					<td><%=trip.getPrice()%></td>
					<td <%if (role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>><%=trip.isHotTour() ? "горящий тур !!!" : ""%></td>
					<td <%if (!role.equals("client")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>
					<a href="./TripsAppServlet?action=new-order&trip-id=<%=trip.getTripId()%>"><button>заказать</button></a></td>
					<td <%if (!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>
						<form action="TripsAppServlet" method="POST">
							<input type="checkbox" name="isHotTour" <%=trip.isHotTour() ? "checked" : ""%>/>
							<input type="hidden" name="action" value="set-hot-tour"/>
							<input type="hidden" name="trip-id" value="<%=trip.getTripId()%>"/>
							<input type="submit" value="сохранить"/>
						</form>
					</td>
					<!-- <td <%if (!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:table-cell;" <%}%>>
						<a href="./TripsAppServlet?action=set-hot-tour&trip-id=<%=trip.getTripId()%>"><button>сохранить</button></a>
					</td>-->
					<td ></td>
				</tr>
				<%
				}
				%>

			</table>
		</div>
	</div>
</body>
</html>