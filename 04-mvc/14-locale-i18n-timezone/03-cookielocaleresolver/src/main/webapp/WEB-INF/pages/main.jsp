<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="fm"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
  <body style="margin:20px;">
    <h3> <spring:message code="label.mainPage"/></h3>
    <h4><spring:message code="label.lang"/>:
    <spring:message code="text.lang"/></h4>

    <fm:form method="post" modelAttribute="formData">
     <fm:select path="localeCode">
        <fm:options items="${localeChoices}"/>
      </fm:select>
      <input type="submit" value="Change Language" />
    </fm:form>
    <br/>
    <P><spring:message code="text.mainPage"/></p>
    <a href="<spring:url value="/page1" />"><spring:message code="label.page1"/></a>
    <br/>
    <a href="<spring:url value="/page2" />"><spring:message code="label.page2"/></a>
  </body>
</html>