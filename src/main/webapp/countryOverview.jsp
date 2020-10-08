<%@page import="domain.model.Country"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="css/sample.css">
<meta charset="UTF-8">
<title>Countries</title>
</head>
<body>
	<jsp:include page="header.jsp"/>

	<main id="container">
	 <jsp:include page="title.jsp">
		<jsp:param name="title" value="Countries" />
	</jsp:include>

	<article>
		<%
			Country mostPopularCountry = (Country) request
					.getAttribute("popular");
			if (mostPopularCountry != null) {
		%>
		<p>
			The most popular country is
			<%=mostPopularCountry.getName()%>
		</p>
		<%
			}
		%>
		<%
			Collection<Country> countries = (Collection<Country>) request
					.getAttribute("countries");
			if (countries != null) {
		%>
		<table id="overview">
			<tr>
				<th>Name</th>
				<th>Capital</th>
				<th class="number">Inhabitants</th>
				<th class="number">Votes</th>
			</tr>
			<%
				for (Country country : countries) {
			%>
			<tr>
				<td><%=country.getName()%></td>
				<td><%=country.getCapital()%></td>
				<td class="number"><%=country.getNumberInhabitants()%></td>
				<td class="number"><%=country.getVotes()%></td>
			</tr>
			<%
				}
			%>
		</table>
		<%
			}
		%>
		<p>
			<a href="countryForm.jsp">Add new country</a>
		</p>
	</article>
	</main>
</body>
</html>