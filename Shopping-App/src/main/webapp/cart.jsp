<%@page import="connection.DatabaseConnector"%>
<%@page import="dao.ProductDao"%>
<%@page import="model.*"%>
<%@page import="java.util.*"%>

<%@page import="java.text.DecimalFormat"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);

List<Cart> cartListSession = (ArrayList<Cart>) session.getAttribute("cart-list");
List<Cart> cartProduct = null;
if (cartListSession != null) {
	ProductDao productDao = new ProductDao(DatabaseConnector.getConnection());
	cartProduct = productDao.getCartProducts(cartListSession);
	double total = productDao.getTotalCartPrice(cartListSession);
	request.setAttribute("total", total);
	request.setAttribute("cartListSession", cartListSession);
}

%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Cart Page</title>
		<%@include file="includes/head.jsp" %>
	</head>
	<body>
		<%@include file="includes/navbar.jsp" %>
		
		<div class="container my-3">
		<div class="d-flex py-3"><h3>Total Price: € ${ (total>0)? dcf.format(total):0 } </h3> <a class="mx-3 btn btn-primary" href="buy-all">Buy All</a></div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (cartListSession != null) {
					for (Cart c : cartProduct) {
				%>
				<tr>
					<td><%=c.getName()%></td>
					<td><%=c.getCategory()%></td>
					<td><%= dcf.format(c.getPrice())+"€"%></td>
					<td>
						<form action="order" method="post" class="form-inline">
						<input type="hidden" name="id" value="<%= c.getId()%>" class="form-input">
							<div class="form-group d-flex justify-content-between">
								<a class="btn btn-sm btn-decre" href="modify-quantity?action=dec&id=<%=c.getId()%>"><i class="fas fa-minus-square"></i></a>
								<input type="text" name="quantity" class="form-control"  value="<%=c.getQuantity()%>" readonly> 
								<a class="btn bnt-sm btn-incre" href="modify-quantity?action=inc&id=<%=c.getId()%>"><i class="fas fa-plus-square"></i></a>  
							</div>
							<button type="submit" class="btn btn-primary btn-sm">Buy</button>
						</form>
					</td>
					<td><a href="remove-from-cart?id=<%=c.getId() %>" class="btn btn-sm btn-danger">Remove</a></td>
				</tr>

				<%
				}}%>
			</tbody>
		</table>
	</div>
		
		<%@include file="includes/foot.jsp" %>
	</body>
</html>