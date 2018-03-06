<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<body>
<h3>A JSP page</h3>
  <spring:message code="app.name" arguments="${by}"/>
</body>
</html>


