<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title></title>
    <link rel="stylesheet" type="text/css" href="${ctx}/css/common.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${ctx}/css/themes/metro/easyui.css">
    <script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/jquery-migrate-1.2.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/js/moment.min.js"></script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
<div data-options="region:'north',border:false" title="" style="width:750px;height: 85px; overflow: hidden; padding: 5px;">
    <div class="well well-small">
        <span class="badge" iconCls="icon-save" plain="true" >提示</span>
        <p>
            在此你可以对<span class="label-info"><strong>菜单功能</strong></span>进行编辑!  &nbsp;<span class="label-info"><strong>注意</strong></span>操作功能是对菜单功能的操作权限！
            请谨慎填写程序编码，权限区分标志，请勿重复!
        </p>
    </div>
</div>
<div data-options="region:'center',border:true">
    <div id="tb">
        <div style="margin:5px 5px 5px 5px;">
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="showResource();">添加</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="openResource();">编辑</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delRows();">删除</a>|
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="expandAll();">展开</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="collapseAll();">收缩</a>
            <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="refresh();">刷新</a>
        </div>
    </div>

    <%-- 列表右键 --%>
    <div id="resource_treegrid_menu" class="easyui-menu" style="width:120px;display: none;">
        <div onclick="showResource();" data-options="iconCls:'icon-add'">新增</div>
        <div onclick="openResource();" data-options="iconCls:'icon-edit'">编辑</div>
        <div onclick="del();" data-options="iconCls:'icon-remove'">删除</div>
        <%--<div onclick="lock(false);" data-options="iconCls:'icon-ok'">启用</div>--%>
        <%--<div onclick="lock(true);" data-options="iconCls:'icon-lock'">停用</div>--%>
    </div>

    <table id="resource" title="权限"></table>
</div>
</div>

<script>
    var resource_treegrid;

    $(function() {
        resource_treegrid = $("#resource").treegrid({
            width : '750px',
            height : '400px',
//            url : "listResource.html",
            method:'post',          //请求方式
            url : "${ctx}/json/data.json",
            rownumbers:true,
            animate: true,
            collapsible: true,
            fitColumns: true,
            border:true,
            striped:true,
            cascadeCheck:true,
            deepCascadeCheck:true,
            idField: 'id',
            treeField: 'name',
//            parentField : 'parentId',
            columns : [
                [
                    {field : 'id',title : 'ID',width : 50},
                    {field : 'name',title : '资源名称',width : 200},
//                    {field:'parentId',title:'所属父级',width:100},
                    {field : 'type',title : '资源类型',width : 100,align : 'center',
                        formatter:function(value,row){
                            if("menu"==row.type){
                                return "<font color=green>菜单<font>";
                            }else{
                                return "<font color=red>操作<font>";
                            }
                        }
                    },
                    {field : 'url',title : '资源路径',width : 200,align : 'center'},
                    {field : 'permission',title : '权限字符串',width : 200,align : 'left'},
                    {field : 'available',title : '是否启用',width : 100,align : 'center',
                        formatter:function(value,row){
                            if("1"==row.available){
                                return "<font color=green>是<font>";
                            }else{
                                return "<font color=red>否<font>";
                            }
                        }
                    }
                ]
            ],
            toolbar:'#tb',
            onContextMenu: function (e, row) {
                e.preventDefault();
                $('#resource_treegrid_menu').menu('show', {
                    left: e.pageX,
                    top: e.pageY
                });
            }
        });
    });

    //收缩
    function collapseAll(){
        var node = resource_treegrid.treegrid('getSelected');
        if (node) {
            resource_treegrid.treegrid('collapseAll', node.id);
        } else {
            resource_treegrid.treegrid('collapseAll');
        }
    }

    //展开
    function expandAll(){
        var node = resource_treegrid.treegrid('getSelected');
        if (node) {
            resource_treegrid.treegrid('expandAll', node.id);
        } else {
            resource_treegrid.treegrid('expandAll');
        }
    }

    //刷新
    function refresh(){
        resource_treegrid.treegrid('reload');
    }

    //----------------Resource-------------------

    function resourceFormInit(row) {
        var _url = ctx+"/resourceAction/doAdd";
        if (row != undefined && row.id) {
            _url = ctx+"/resourceAction/doUpdate";
        }
        resource_form = $('#resource_form').form({
            url: _url,
            onSubmit: function (param) {
                $.messager.progress({
                    title: '提示信息！',
                    text: '数据处理中，请稍后....'
                });
                var isValid = $(this).form('validate');
                if (!isValid) {
                    $.messager.progress('close');
                }
                return isValid;
            },
            success: function (data) {
                $.messager.progress('close');
                var json = $.parseJSON(data);
                if (json.status) {
                    resource_dialog.dialog('destroy');//销毁对话框
                    resource_treegrid.treegrid('reload');//重新加载列表数据
                    $.messager.show({
                        title : json.title,
                        msg : json.message,
                        timeout : 1000 * 2
                    });
                } else {
                    $.messager.show({
                        title :  json.title,
                        msg : json.message,
                        timeout : 1000 * 2
                    });
                }
            }
        });
    }

    function showResource(row){
        var _url = ctx+"/resourceAction/toAdd";
        if (row != undefined && row.id) {
            _url = ctx+"/resourceAction/toUpdate/"+row.id;
        }
        //弹出对话窗口
        resource_dialog = $('<div/>').dialog({
            title : "资源信息",
            top: 20,
            width : 600,
            height : 400,
            modal: true,
            minimizable: true,
            maximizable: true,
            href: _url,
            onLoad: function () {
                resourceFormInit(row);
                if (row) {
                    resource_form.form('load', row);
                }

            },
            buttons: [
                {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: function () {
                        resource_form.submit();
                    }
                },
                {
                    text: '关闭',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        resource_dialog.dialog('destroy');
                    }
                }
            ],
            onClose: function () {
                resource_dialog.dialog('destroy');
            }
        });
    }

    //添加修改操作
    function openResource() {
        var row = resource_treegrid.treegrid('getSelected');
        if (row) {
            showResource(row);
        } else {
            $.messager.alert("提示", "您未选择任何操作对象，请选择一行数据！");
        }
    }

    //删除组
    function delRows() {
        var row = resource_treegrid.treegrid('getSelected');
        if (row) {
            $.messager.confirm('确认提示！', '您确定要删除选中数据? 同时也会删除此用户组所对应的权限信息！', function (result) {
                if (result) {
                    var id = row.id;
                    $.ajax({
                        url: ctx + '/resourceAction/doDelete/'+id,
                        type: 'post',
                        dataType: 'json',
                        data: {},
                        success: function (data) {
                            if (data.status) {
                                resource_treegrid.treegrid('reload'); //reload the resource data
                                $.messager.show({
                                    title : data.title,
                                    msg : data.message,
                                    timeout : 1000 * 2
                                });
                            } else {
                                $.messager.show({
                                    title : data.title,
                                    msg : data.message,
                                    timeout : 1000 * 2
                                });
                            }
                        }
                    });
                }
            });
        } else {
            $.messager.alert("提示", "您未选择任何操作对象，请选择一行数据！");
        }
    }
</script>

</body>
</html>