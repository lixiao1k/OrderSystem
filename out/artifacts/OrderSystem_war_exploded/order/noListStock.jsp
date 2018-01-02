<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/1/2
  Time: 下午8:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Watch List</title>
</head>
<body>
<H1>Online Stock.</H1>

<H4><BR>You currently do not have any items checked out from the stock.</H4>

<form method="GET" action="<%=response.encodeURL(request.getContextPath() + "/LoginServlet")%>">
    </p>
    <input type="submit" name="Logout" value="Logout">
</form>
</body></html>
</body>
</html>
