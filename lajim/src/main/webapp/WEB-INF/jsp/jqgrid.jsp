<%--
  Created by IntelliJ IDEA.
  User: prayer
  Date: 2018/1/3
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/jqgrid/ui.jqgrid.css" />
    <script type="text/javascript" src="${ctx}/js/jqgrid/js/jquery.jqGrid.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body>

<table id="jqGridId"></table>
<div id="pager"></div>

</body>
</html>
