<%--
  Created by IntelliJ IDEA.
  User: shelton
  Date: 2018/1/2
  Time: 下午7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib prefix="order" uri="/WEB-INF/tlds/orderInfo.tld" %>
<%@taglib prefix="count" uri="/WEB-INF/tlds/userCount.tld" %>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Watch List</title>
</head>
<body>
<table width="650" border="0">
    <tr>
        <td width="650" height="80"
            background="<%=request.getContextPath() + "/image/top.jpg"%>">&nbsp;</td>
    </tr>
</table>
<H1>Online Order.</H1>
<H2>
    <BR>
    <jsp:useBean id="listOrder"
                 type="action.bussiness.OrderListBean"
                 scope="session"></jsp:useBean>
    <jsp:useBean id="item" class="model.Order"
                 scope="page"></jsp:useBean>
    There are all Orders.
</H2>
<H4>
    <BR>
    <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
        <TBODY>
        <TR>
            <TH width="10%">ordid</TH>
            <TH width="10%">userid</TH>
            <TH width="10%">ordname</TH>
            <TH width="10%">amount</TH>
            <TH width="10%">price</TH>
            <TH width="30%">time</TH>
            <TH width="20%">oos</TH>
        </TR>
        <order:orderInfo/>
        </TBODY>
    </TABLE>
    <jsp:useBean id="userCount"
                 type="action.bussiness.UserCountBean"
                 scope="session"></jsp:useBean>
    <count:userCount/>
</H4>

<form method="GET" action="<%=response.encodeURL(request.getContextPath() + "/LoginServlet")%>">
    </p>
    <input type="submit" name="Logout" value="Logout">
</form>

</body>
</html>
