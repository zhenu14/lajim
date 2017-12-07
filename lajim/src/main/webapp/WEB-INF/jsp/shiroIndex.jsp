<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <title>Shiro综合案例</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/layout-default-latest.css">
    <link href="${ctx}/css/default.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/metro/easyui.css" />
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css" />
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src='${ctx}/js/outlook2.js'> </script>
    <script type="text/javascript">
        var _menus = {"menus":[
            {"menuid":"10","menuname":"一个辣鸡系统的导航菜单",
                "menus":[
                    <c:forEach items="${menus}" var="m">
                    {"menuid":"${m.id}","menuname":"${m.name}","icon":"icon-mini-refresh","url":"${m.url}"},
                    </c:forEach>

                ]
            }
        ]};

        //设置登录窗口
        function openPwd() {
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: true,
                closed: true,
                height: 160,
                resizable:false
            });
        }
        //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }



        //修改密码
        function serverLogin() {
            var $newpass = $('#txtNewPass');
            var $rePass = $('#txtRePass');

            if ($newpass.val() == '') {
                msgShow('系统提示', '请输入密码！', 'warning');
                return false;
            }
            if ($rePass.val() == '') {
                msgShow('系统提示', '请在一次输入密码！', 'warning');
                return false;
            }

            if ($newpass.val() != $rePass.val()) {
                msgShow('系统提示', '两次密码不一至！请重新输入', 'warning');
                return false;
            }

            $.post('/editPassword.html?newpass=' + $newpass.val(), function(msg) {
                msgShow('系统提示', '恭喜，密码修改成功！<br>您的新密码为：' + msg, 'info');
                $newpass.val('');
                $rePass.val('');
                close();
            })

        }

        $(function() {

            openPwd();

            $('#editpass').click(function() {
                $('#w').window('open');
            });

            $('#btnEp').click(function() {
                serverLogin();
            })

            $('#btnCancel').click(function(){closePwd();})

            $('#loginOut').click(function() {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
                    if (r) {
                        location.href = 'logout.html';
                    }
                });
            })
        });
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
<div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
    Hello~[<shiro:principal/>]<span style="float:right; padding-right:20px;" class="head"><a href="#" id="editpass">修改密码</a> <a href="#" id="loginOut">安全退出</a></span>
</div>

<div region="west" hide="true" split="true" title="oooooooooo" style="width:180px;" id="west">
    <div id="nav" class="easyui-accordion" fit="true" border="false">
        <!--  导航内容 -->

    </div>

</div>
<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
        <div title="主页" style="padding:20px;overflow:hidden; color:red; " ></div>

    </div>
</div>

<div id="mm" class="easyui-menu" style="width:150px;">
    <div id="mm-tabupdate">刷新</div>
    <div class="menu-sep"></div>
    <div id="mm-tabclose">关闭</div>
    <div id="mm-tabcloseall">全部关闭</div>
    <div id="mm-tabcloseother">除此之外全部关闭</div>
</div>

<!--修改密码窗口-->
<div id="w" class="easyui-window" title="修改密码" collapsible="false" minimizable="false"
     maximizable="false" icon="icon-save"  style="width: 300px; height: 150px; padding: 5px;
        background: #fafafa;">
    <div class="easyui-layout" fit="true">
        <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
            <table cellpadding=3>
                <tr>
                    <td>新密码：</td>
                    <td><input id="txtNewPass" type="Password" class="txt01" /></td>
                </tr>
                <tr>
                    <td>确认密码：</td>
                    <td><input id="txtRePass" type="Password" class="txt01" /></td>
                </tr>
            </table>
        </div>
        <div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
            <a id="btnEp" class="easyui-linkbutton" icon="icon-ok" href="javascript:void(0)" >
                确定</a> <a id="btnCancel" class="easyui-linkbutton" icon="icon-cancel" href="javascript:void(0)">取消</a>
        </div>
    </div>
</div>

<%--<iframe name="content" class="ui-layout-center"--%>
        <%--src="${pageContext.request.contextPath}/welcome" frameborder="0" scrolling="auto"></iframe>--%>
<%--<div class="ui-layout-north">欢迎[<shiro:principal/>]学习Shiro综合案例，<a href="${pageContext.request.contextPath}/logout">退出</a></div>--%>

<%--<div class="ui-layout-west">--%>
    <%--功能菜单<br/>--%>
    <%--<c:forEach items="${menus}" var="m">--%>
        <%--<a href="${pageContext.request.contextPath}/${m.url}" target="content">${m.name}</a><br/>--%>
    <%--</c:forEach>--%>
<%--</div>--%>


<%--<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>--%>
<%--<script src="${pageContext.request.contextPath}/js/js/jquery.layout-latest.min.js"></script>--%>
<%--<script>--%>
    <%--$(function () {--%>
        <%--$(document).ready(function () {--%>
            <%--$('body').layout({ applyDemoStyles: true });--%>
        <%--});--%>
    <%--});--%>
<%--</script>--%>
</body>
</html>