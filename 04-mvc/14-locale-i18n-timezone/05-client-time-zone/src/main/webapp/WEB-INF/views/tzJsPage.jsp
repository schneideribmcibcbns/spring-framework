<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<html>
 <body>
    <form method="post" id="tzForm" action="/tzValueHandler">
      <input id="tzInput" type="hidden" name="timeZoneOffset"><br>
      <input type="hidden" name="requestedUrl" value="${requestedUrl}">
    </form>

    <script>
        var date = new Date();
        var offSet = date.getTimezoneOffset();
        document.getElementById("tzInput").value = offSet;
        document.getElementById("tzForm").submit();
    </script>

 </body>
</html>