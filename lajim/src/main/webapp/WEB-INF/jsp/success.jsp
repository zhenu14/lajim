<%--
  Created by IntelliJ IDEA.
  User: prayer
  Date: 2017/11/12
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title></title>
</head>
<body>
<shiro:hasAnyRoles name="admin">
    <shiro:principal/>拥有角色admin
</shiro:hasAnyRoles>
</body>
</html>