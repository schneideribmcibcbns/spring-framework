<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h2>Trades </h2>
   <h3> Message : ${msg} <h3>
<br/>
<h4>Current visitor history</h4>
<p>ip : ${visitor.ip}:</p>
<c:forEach items="${visitor.pagesVisited}" var="page">
    <p>${page}</p>
</c:forEach>

<a href="<%=request.getContextPath()%>/trades/clear">Clear History</a>
</body>
</html>