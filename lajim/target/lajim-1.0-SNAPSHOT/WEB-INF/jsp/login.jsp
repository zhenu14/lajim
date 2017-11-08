<%--
  Created by IntelliJ IDEA.
  User: prayer
  Date: 2017/10/25
  Time: 23:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/default/panel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body>
<c:if test="${!empty error}">
    <font color="red"><c:out value="${error}" /></font>
</c:if>
<div class="easyui-panel" title="New Topic" style="width:400px">
    <div style="padding:10px 60px 20px 60px">
        <form id="loginForm" action = "<c:url value="login.html"/>" method="post">
            <table cellpadding="5">
                <tr>
                    <td>
                        <input class="easyui-textbox" name="userName" data-options="prompt:'username',validType:'name'" iconCls="icon-man" iconAlign=left style="width:100%;height:32px"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input class="easyui-textbox" name="password" data-options="prompt:'password',validType:'password'" iconCls="icon-lock" iconAlign=left style="width:100%;height:32px"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" class="easyui-linkbutton" style="width:45%;height:32px" value="登录" />
                        <input type="reset" class="easyui-linkbutton" style="width:45%;height:32px" value="重置" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script>
    function submitForm(){
        $('#loginForm').form('submit');
    }
    function clearForm(){
        $('#loginForm').form('clear');
    }

    //	function lajim() {
    //        if ($("input[name='username']").val() == "" || $("input[name='password']").val() == "") {
    //            $("#showMsg").html("用户名或密码为空，请输入");
    //            $("input[name='username']").focus();
    //        } else {
    //            //ajax异步提交
    //            $.ajax({
    //                type: "POST",   //post提交方式默认是get
    //                url: "lajim.html",
    //                data: $("#loginForm").serialize(),   //序列化
    //                error: function (request) {      // 设置表单提交出错
    //                    $("#showMsg").html(request);  //登录错误提示信息
    //                },
    //                success: function (data) {
    //                    document.location = "/toMain.html";
    //                }
    //            });
    //        }
    //    }
</script>
</body>
</html>
