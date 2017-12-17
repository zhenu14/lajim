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
       data-options="rownumbers:true,singleSelect:true,collapsible:true,url:'listRole.html',method:'get',toolbar:'#toolbar'">
    <thead>
    <tr>
        <th data-options="field:'id',width:120,hidden:true">ID</th>
        <th data-options="field:'role',width:120">角色标识</th>
        <th data-options="field:'description',width:180">角色描述</th>
        <th data-options="field:'resourceIds',width:150,align:'center'">资源</th>
        <th data-options="field:'available',width:80,align:'center'">是否可用</th>
    </tr>
    </thead>
</table>

<div id="toolbar" style="padding:5px;height:auto">
    <div style="margin-bottom:5px">
        <shiro:hasPermission name="role:create">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#createRole').window('open')">新增</a>
        </shiro:hasPermission>

        <shiro:hasPermission name="role:update">
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        </shiro:hasPermission>

        <shiro:hasPermission name="role:delete">
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteRole()">删除</a>
        </shiro:hasPermission>
    </div>
</div>

<div id="createRole" class="easyui-window" title="Modal Window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:300px;height:300px;padding:10px;">
    <form id="roleForm" method="post">
        <div style="margin-bottom:20px">
            <div>角色标识:</div>
            <input class="easyui-textbox"  name="role" style="width:100%" data-options="required:true">
        </div>
        <div style="margin-bottom:20px">
            <div>角色描述:</div>
            <input class="easyui-textbox"  name="description" style="width:100%" data-options="required:true">
        </div>
        <div class="easyui-panel" style="padding:5px">
            <div>资源权限:</div>
            <input type="text" id = "resourceIds" name = "resourceIds" value="" hidden>
            <ul id="tt" class="easyui-tree" data-options="url:'listResources.html',method:'get',animate:true,cascadeCheck:false,checkbox:true"></ul>
        </div>
        <br>
        <div style="display: flex;justify-content: center">
            <input type="button" class="easyui-linkbutton" style="width:60%;" value="Confirm" onclick="submitForm()">
        </div>
    </form>
</div>

<script>
    function submitForm() {
        var nodes = $('#tt').tree('getChecked');
        var s = '';
        for(var i=0; i<nodes.length; i++){
            if (s != '') s += ',';
            s += nodes[i].id;
        }
        document.getElementById("resourceIds").value = s;
        $.messager.confirm('Create', '确定创建该角色-+？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    data: $('#roleForm').serialize(),
                    url: "createRole.html",
                    success: function(data) {
                        var dataObj = JSON.parse(data);
                        if(dataObj.success === "true"){
                            $.messager.alert("添加成功了吗？",dataObj.msg,'info');
                        }else{
                            $.messager.alert("添加成功了吗？",dataObj.msg,'error');
                        }
                        $("#roleForm")[0].reset();
                        $("#grid").datagrid("reload");
                        $('#tt').tree('reload');
                    }
                });
            }
        });
    }

    function deleteRole() {
        var row = $('#grid').datagrid('getSelected');
        if (!row) {
            alert("请选择一条记录");
            return;
        }
        $.messager.confirm('Delete', '确定删除该角色？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    data : {"id":row.id},
                    url: "deleteRole.html",
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
</script>

</body>
</html>