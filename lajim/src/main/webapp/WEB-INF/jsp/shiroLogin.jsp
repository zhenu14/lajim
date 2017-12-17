<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/metro/easyui.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/metro/panel.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body bgcolor="#a9a9a9" onload="showError()">
<div class="easyui-panel" title="New Topic" style="width:400px">
    <form id="loginForm" action="" method="post">
        <table cellpadding="5">
            <tr>
                <td>
                    <input class="easyui-textbox" name="username" data-options="prompt:'username',validType:'name'" iconCls="icon-man" iconAlign=left style="width:100%;height:32px"/>
                </td>
            </tr>
            <tr>
                <td>
                    <input type="password" class="easyui-textbox" name="password" data-options="prompt:'password',validType:'password'" iconCls="icon-lock" iconAlign=left style="width:100%;height:32px"/>
                </td>
            </tr>
            <tr>
                <td>
                    自动登录:<input class="easyui-checkbox" name="rememberMe" type="checkbox" value="true"/>
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

<script type="text/javascript">
    function showError(){
        var error = "${error}";
        if(error != ""){
            $.messager.show({
                title:'登录失败',
                msg:"${error}",
                timeout:1500,
                style:{
                    right:'',
                    bottom:''
                },
                showType:'fade'
            });
        }
    }

</script>
</body>
</html>