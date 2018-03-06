<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<html>
<body>
  <h3><spring:message code="label.page2"/></h3>
  <h4><spring:message code="label.lang"/>:
  <spring:message code="text.lang"/></h4>
  <P><spring:message code="text.page2"/></p>

  <a href="<spring:url value="/page2"/>"><spring:message code="label.page1"/></a>
  <br/>
  <a href="<spring:url value="/"/>"><spring:message code="label.mainPage"/></a>
</body>
</html>