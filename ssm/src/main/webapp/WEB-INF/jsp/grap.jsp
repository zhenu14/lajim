<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>参数</title>
        <!-- 加载Query文件-->
        <script type="text/javascript" src="${ctx}/static/js/jquery-3.2.1.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function () {
            	  //模拟35000个异步请求，进行并发
                var max = 35000;
                for (var i = 1; i <= max; i++) {
                    $.post({
                        url: "grapRedPacketByRedis?redPacketId=5&userId=" + i,
                        success: function (result) {
                        }
                    });
                }
            });
        </script>
    </head>
    <body>
    </body>
</html>