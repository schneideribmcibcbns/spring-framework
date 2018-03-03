<%@ page language="java"
    contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<style>
span.error {
   color: red;
}
</style>
</head>
<body>

<h3> Registration Form <h3>
<br/>
 <form action="register" method="post" >
  <pre>
                  Name <input type="text" name="name" value="${user.name}" />
                       <span class="error">${nameError}</span>

         Email address <input type="text" name="emailAddress" value="${user.emailAddress}" />
                       <span class="error">${emailAddressError}</span>

              Password <input type="password" name="password" value="${user.password}" />
                       <span class="error">${passwordError}</span>
                                        <input type="submit" value="Submit" />
  </pre>
 </form>
</body>
</html>