<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String role = (String)request.getSession().getAttribute("role");
if(role==null){
	request.getRequestDispatcher("./TripsAppServlet?action=show-login").forward(request,response);
}
%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="./styles/common-styles.css" type="text/css" />
</head>
<body>
	<div class="navbar">
		<a href="./TripsAppServlet?action=show-all-trips">Туры</a> 
		<a <% if( !role.equals("client")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <%} %> href="./TripsAppServlet?action=show-user-orders">Мои заказы</a> 
		<a <% if(!role.equals("admin")) {%> style="display:none;" <% } else {  %> style="display:inline-block;" <% } %> href="./TripsAppServlet?action=show-discount">Скидки</a> 
		<a href="./TripsAppServlet?action=logout"><% if( role.equals("guest")) {%> Войти <% } else {  %> Выйти <%} %></a>
		<p><%= request.getSession().getAttribute("login") %></p>
	</div>
	<div class="central-section">
		<h1>Добавление нового заказа</h1>

		<h3>Заполните данные о заказе:</h3>
		<form id="order-form" novalidate action="TripsAppServlet" method="POST">
			<div class="input-row"> 
				<label>Заказ оплачен:</label> 
				<input name="is-paid" type="checkbox" />
			</div>

			<div class="input-row">
				<label>ID клиента
					<div class="star">*</div>
				</label>
				<div>
					<input id="client-id-input_order-form" name="client-id" type="number" value="<%=request.getSession().getAttribute("client-id")%>" readonly/>
					<p id="client-id-invalid_order-form" class="invalid-input-message">Значение - положительное число</p>
				</div>
			</div>

			<div class="input-row">
				<label>ID тура
					<div class="star">*</div>
				</label>
				<div>
					<input id="trip-id-input_order-form" name="trip-id"
						value="<%=request.getParameter("trip-id")%>" type="number" readonly
						 />
					<p id="trip-id-invalid_order-form" class="invalid-input-message">Значение - положительное число</p>
				</div>
			</div>
			
			<input type="hidden" name="action" value="create-order"/>
			<input id="submit-input_order-form" type="submit"
				value="Оформить заказ">
		</form>
	</div>

</body>
</html>