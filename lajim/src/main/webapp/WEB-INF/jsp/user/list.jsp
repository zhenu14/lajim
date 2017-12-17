<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/metro/easyui.css">
    <%--<link rel="stylesheet" type="text/css" href="../demo.css">--%>
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
</head>
<body>

<table id="grid" class="easyui-datagrid" title="用户管理Grid" style="width:750px;height:450px"
       data-options="rownumbers:true,singleSelect:true,collapsible:true,url:'listUser.html',method:'get',toolbar:'#toolbar'">
    <thead>
    <tr>
        <th data-options="field:'id',width:120,hidden:true">ID</th>
        <th data-options="field:'username',width:120">用户名</th>
        <th data-options="field:'salt',width:180">盐</th>
        <th data-options="field:'roleIds',width:150,align:'center'">角色列表</th>
        <th data-options="field:'locked',width:80,align:'center'">是否冻结</th>
    </tr>
    </thead>
</table>

<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <shiro:hasPermission name="user:create">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#createUser').window('open')">新增</a>
        </shiro:hasPermission>

        <shiro:hasPermission name="user:update">
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        </shiro:hasPermission>

        <shiro:hasPermission name="user:delete">
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
        </shiro:hasPermission>
    </div>
</div>

<div id="createUser" class="easyui-window" title="Modal Window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:300px;height:300px;padding:10px;">
    <form id="userForm" action="<c:url value="/user/createUser.html"/>" method="post">
        <div style="margin-bottom:20px">
            <div>用户名:</div>
            <input class="easyui-textbox"  name="username" style="width:100%" data-options="required:true">
        </div>
        <div style="margin-bottom:20px">
            <div>密码:</div>
            <input class="easyui-textbox"  name="password" style="width:100%" data-options="required:true">
        </div>
        <div class="easyui-panel" style="padding:5px">
            <div>选择角色:</div>
            <input type="text" id = "roleIds" name = "roleIds" value="" hidden>
            <ul id="tt" class="easyui-tree" data-options="url:'listRoles.html',method:'get',animate:true,checkbox:true"></ul>
        </div>
        <br>
        <div style="display: flex;justify-content: center">
            <input type="button" class="easyui-linkbutton" style="width:60%;" value="Confirm" onclick="submitForm()">
        </div>
    </form>
</div>

<script>
    function deleteUser() {
        var row = $('#grid').datagrid('getSelected');
        if (!row) {
            alert("请选择一个用户");
            return;
        }
        $.messager.confirm('Delete', '确定删除该用户？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    data : {"id":row.id},
                    url: "deleteUser.html",
                    success: function(data) {
                        var dataObj = JSON.parse(data);
                        console.log(dataObj);
                        console.log(dataObj.success);
                        if(dataObj.success === "true"){
                            $.messager.alert("删除成功了吗？",dataObj.msg,'info');
                        }else{
                            $.messager.alert("删除成功了吗？",dataObj.msg,'error');
                        }
                        $("#grid").datagrid("load");
                    }
                });
            }
        });
    }

    function submitForm() {
        var nodes = $('#tt').tree('getChecked');
        var s = '';
        for(var i=0; i<nodes.length; i++){
            if (s != '') s += ',';
            s += nodes[i].id;
        }
        document.getElementById("roleIds").value = s;
        $.messager.confirm('Create', '确定确定该用户？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    data: $('#userForm').serialize(),
                    url: "createUser.html",
                    success: function(data) {
                        var dataObj = JSON.parse(data);
                        if(dataObj.success === "true"){
                            $.messager.alert("添加成功了吗？",dataObj.msg,'info');
                        }else{
                            $.messager.alert("添加成功了吗？",dataObj.msg,'error');
                        }
                        $("#userForm")[0].reset();
                        $("#grid").datagrid("reload");
                        $('#tt').tree('reload');
                    }
                });
            }
        });
    }

    function editUser() {
        var row = $('#grid').datagrid('getSelected');
        if (!row) {
            $.messager.alert("修改用户","请选择一条记录",'info');
            return;
        }
//        document.getElementById("username").value = row.username;
//        document.getElementById("password").value = row.password;
        $('#createUser').window('open')
    }

</script>

</body>
</html>