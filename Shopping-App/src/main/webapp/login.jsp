<%@page import="model.*"%>
<%@page import="java.util.*"%>

<%
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
		<title>LoginPage</title>
		<%@include file="includes/head.jsp" %>
	</head>
	<body>
		<%@include file="includes/navbar.jsp" %>
	
		<div class="container">
			<div class="card w-50 mx-auto my-5">
				<div class="card-header text-center">User Login</div>
				<div class="card-body">
					<form action="user-login" method="post">
						<div class="form-group">
							<label>Email adress</label>
							<input type="email" name="login-email" class="form-control" placeholder="Enter email">
						</div>
						
						<div class="form-group">
							<label>Password</label>
							<input type="password" name="login-password" class="form-control" placeholder="Password">
						</div>
						
						<div class="text-center">
							<button type="submit" class="btn btn-primary">Login</button>
						</div>
					
					</form>
				
				</div>
			</div>
		</div>
		
		<%@include file="includes/foot.jsp" %>
	</body>
</html>