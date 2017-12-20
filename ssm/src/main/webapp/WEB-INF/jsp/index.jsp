<%--
  Created by IntelliJ IDEA.
  User: prayer
  Date: 2017/12/20
  Time: 22:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctx}/static/js/jquery-3.2.1.min.js"></script>
    <script type="text/javascript" language="JavaScript">
        var timerId = null;//定时器ID
        $(document).ready(function () {
            /*
             定时轮询执行进度
             */
            timerId = setInterval(function () {
                getStatus();
            }, 1000);
            getStatus();
        });
        /**
         获取执行进度
         */
        function getStatus() {
            var statusUrl = window.location.href + "/status";
            $.get(statusUrl, function (data) {
                var data = JSON.parse(data);
                if (data == null || data.status == null || data.status == "null") {
                    updateStatus("准备中");
                    return;
                }
                var status = data.status;
                updateStatus(status);
                var temp = status.split("/");
                if (temp[0] == temp[1]) {
                    updateStatus("完成");
                    clearInterval(timerId);//停止定时器
                    clearStatus();//清理redis缓存
                }
            })
        }

        /**
         * 执行完成后，清理缓存
         */
        function clearStatus() {
            var clearStatusUrl = window.location.href + "/clear";
            $.get(clearStatusUrl, function (data) {
                var data = JSON.parse(data);
                alert(data.status);
            })
        }

        /**
         更新进度显示
         */
        function updateStatus(msg) {
            $("#status").html(msg);
        }
    </script>
</head>
<body>
<div id="msgBox">
    <span>请稍候，服务器正在处理中...</span>
    <h1>当前处理进度：<span style="color:red" id="status">准备中</span></h1>
</div>

</body>
</html>
