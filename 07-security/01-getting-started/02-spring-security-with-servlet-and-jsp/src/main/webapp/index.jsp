<html>
<body>
<h2>Welcome to App</h2>
 <p>
  <%=request.getUserPrincipal().getName().toString()%>
 </p>
 <a href="/example">Go to Example Servlet</a>
</body>
</html>