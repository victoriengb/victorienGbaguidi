<%@page import="model.*"%>
<%@page import="java.util.*"%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="dao.OrderDao"%>
<%@page import="connection.DatabaseConnector"%>
<%@page import="dao.ProductDao"%>

<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);

List<Order> orders = null;
OrderDao orderDao  = new OrderDao(DatabaseConnector.getConnection());
orders = orderDao.userOrders(1);

List<Cart> cartListSession = (ArrayList<Cart>) session.getAttribute("cart-list");

if (cartListSession != null) {
	request.setAttribute("cartListSession", cartListSession);
}
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Orders Page</title>
		<%@include file="includes/head.jsp" %>
	</head>
	<body>
		<%@include file="includes/navbar.jsp" %>
		
		<div class="container">
			<div class="card-header my-3">All Orders</div>
			<table class="table table-light">
				<thead>
					<tr>
						<th scope="col">Date</th>
						<th scope="col">Name</th>
						<th scope="col">Category</th>
						<th scope="col">Quantity</th>
						<th scope="col">Price</th>
						<th scope="col">Cancel</th>
					</tr>
				</thead>
				<tbody>
				
				<%
				if(orders != null){
					for(Order o:orders){%>
						<tr>
							<td><%=o.getDate() %></td>
							<td><%=o.getName() %></td>
							<td><%=o.getCategory() %></td>
							<td><%=o.getQuantity() %></td>
							<td><%=dcf.format(o.getPrice()) %></td>
							<td><a class="btn btn-sm btn-danger" href="cancel-order?id=<%=o.getOrderId()%>">Cancel Order</a></td>
						</tr>
					<%}
				}
				%>
				
				</tbody>
			</table>
		</div>
		
		
		<%@include file="includes/foot.jsp" %>
	</body>
</html>