<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/css/themes/default/easyui.css">
	<%--<link rel="stylesheet" type="text/css" href="../demo.css">--%>

	<script type="text/javascript" src="${ctx}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/js/jquery.easyui.min.js"></script>

	<title>文件管理</title>

</head>

<body style="width: 80%;float: left">

<table id="grid" class="easyui-datagrid" title="文件管理Grid" style="width:750px;height:450px"
	   data-options="rownumbers:true,singleSelect:true,collapsible:true,url:'listFile.html',method:'get',toolbar:'#toolbar'">
	<thead>
	<tr>
		<th data-options="field:'id',width:120,hidden:true">ID</th>
		<th data-options="field:'filename',width:120">文件名</th>
		<th data-options="field:'filepath',width:180">文件路径</th>
		<th data-options="field:'createtime',width:150,align:'center'">创建时间</th>
		<th data-options="field:'createuser',width:80,align:'center'">创建人</th>
		<th data-options="field:'remarks',width:150,align:'center'">备注</th>
	</tr>
	</thead>
</table>
<div id="toolbar" style="padding:5px;height:auto">
	<div style="margin-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="$('#upload').window('open')"></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-cut" plain="true" onclick="downloadFile()"></a>
		<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteFile()"></a>
	</div>
</div>

<div id="upload" class="easyui-window" title="Modal Window" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:300px;height:250px;padding:10px;">
	<form action="<c:url value="/file/upload.html"/>" method="post" enctype="multipart/form-data">
		<div style="margin-bottom:20px">
			<div>file:</div>
			<input class="easyui-filebox" name="file" data-options="prompt:'Choose a file...'" style="width:100%">
		</div>
		<div style="margin-bottom:20px">
			<div>Remarks:</div>
			<input class="easyui-textbox" name="remarks" style="width:100%">
		</div>
		<div style="display: flex;justify-content: center">
			<input type="submit" class="easyui-linkbutton" style="width:60%;" value="Upload">
		</div>
	</form>
</div>

<script>
	function downloadFile() {
        var row = $('#grid').datagrid('getSelected');
        if (!row) {
            alert("请选择一个文件");
            return;
        }
        console.log("fileDownload");
        var url = "<c:url value="/file/fileDownload.html"/>";
        var filename = row.filename;
        var filepath = row.filepath;
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "filepath").attr("value", filepath));
        form.append($("<input></input>").attr("type", "hidden").attr("name", "filename").attr("value", filename));
        form.appendTo('body').submit().remove();
    }

    function deleteFile() {
        var row = $('#grid').datagrid('getSelected');
        if (!row) {
            alert("请选择一个文件");
            return;
        }
//        $.post("/lajim/file/fileDownload.html",{"filepath":row.filepath,"filename":row.filename});
        $.messager.confirm('Delete', '确定删除该文件？', function(r){
            if (r){
                $.ajax({
                    type: "POST",
                    data : {"id":row.id},
                    url: "deleteFile.html",
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