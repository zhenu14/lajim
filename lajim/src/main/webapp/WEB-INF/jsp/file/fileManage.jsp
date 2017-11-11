<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url value="/file/listFile.html" var="listUrl"/>
<c:url value="/file/addFile.html" var="addUrl"/>
<c:url value="/file/uploadFile.html" var="uploadUrl"/>
<%--<c:url value="/users/update" var="editUrl"/>--%>
<%--<c:url value="/users/delete" var="deleteUrl"/>--%>

<html>
<head>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/css/jquery-ui/pepper-grinder/jquery-ui-1.8.16.custom.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/css/ui.jqgrid-4.3.1.css"/>'/>
	<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/css/style.css"/>'/>
	
	<script type='text/javascript' src='<c:url value="/js/jqgrid/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/js/jqgrid/jquery-ui-1.8.16.custom.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/js/jqgrid/grid.locale-en-4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/js/jqgrid/jquery.jqGrid.min.4.3.1.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/js/jqgrid/custom.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/js/ajaxfileupload.js"/>'></script>
	
	<title>文件管理</title>
	
	<script type='text/javascript'>
	$(function() {
		$("#grid").jqGrid({
		   	url:'${listUrl}',
			datatype: 'json',
			mtype: 'GET',
		   	colNames:['Id', 'FileName', 'FilePath', 'CreateTime', 'CreateUser'],
		   	colModel:[
		   		{name:'id',index:'id', width:55, editable:false, editoptions:{readonly:true, size:10}, hidden:true},
		   		{name:'filename',index:'filename', width:100, editable:true, edittype:"file", editrules:{required:true}, editoptions:{enctype:"multipart/form-data"}},
		   		{name:'filepath',index:'filepath', width:100, editable:false, editoptions:{size:10}},
		   		{name:'createtime',index:'createtime', width:100, editable:false,  editoptions:{readonly:true,size:10}},
		   		{name:'createuser',index:'createuser', width:100, editable:false, editoptions:{readonly:true,size:10}}
		   	],
		   	postData: {},
			rowNum:10,
		   	rowList:[10,20,40,60],
            height : "auto",
            autowidth: true,
			rownumbers: true,
		   	pager: '#pager',
		   	sortname: 'id',
		    viewrecords: true,
		    sortorder: "asc",
		    caption:"Records",
		    emptyrecords: "Empty records",
		    loadonce: false,
		    loadComplete: function() {},
		    jsonReader : {
		        root: "rows",
		        page: "page",
		        total: "total",
		        records: "records",
		        repeatitems: false,
		        cell: "cell",
		        id: "id"
		    }
		});

		$("#grid").jqGrid('navGrid','#pager',
				{edit:false, add:false, del:false, search:true},
				{}, {}, {}, 
				{ 	// search
					sopt:['cn', 'eq', 'ne', 'lt', 'gt', 'bw', 'ew'],
					closeOnEscape: true, 
					multipleSearch: true, 
					closeAfterSearch: true
				}
		);
		
		$("#grid").navButtonAdd('#pager',
				{ 	caption:"Add", 
					buttonicon:"ui-icon-plus", 
					onClickButton: addRow,
					position: "last", 
					title:"", 
					cursor: "pointer"
				} 
		);
		
//		$("#grid").navButtonAdd('#pager',
//				{ 	caption:"Edit",
//					buttonicon:"ui-icon-pencil",
//					onClickButton: editRow,
//					position: "last",
//					title:"",
//					cursor: "pointer"
//				}
//		);
		
		$("#grid").navButtonAdd('#pager',
			{ 	caption:"Delete", 
				buttonicon:"ui-icon-trash", 
				onClickButton: deleteRow,
				position: "last", 
				title:"", 
				cursor: "pointer"
			} 
		);

		// Toolbar Search
//		$("#grid").jqGrid('filterToolbar',{stringResult: true,searchOnEnter : true, defaultSearch:"cn"});
	});

	function addRow() {
//   		$("#grid").jqGrid('setColProp', 'username', {editoptions:{readonly:false, size:10}});
//   		$("#grid").jqGrid('setColProp', 'password', {editrules:{required:true}});
		// Get the currently selected row
		$('#grid').jqGrid('editGridRow','new',
	    		{
	    		    url: '${addUrl}',
					editData: {},
                    jqModal:true,
                    closeAfterEdit: true,
                    recreateForm:true,
	                serializeEditData: function(data){
	                    return $.param(data);
	                },
                    onInitializeForm : function(formid){
                        $(formid).attr('method','POST');
                        $(formid).attr('action','');
                        $(formid).attr('enctype','multipart/form-data');
                    },
				    beforeShowForm: function(form) {
	                    console.log(form)
//			            $('#pData').hide();
//			            $('#nData').hide();
//			            $('#password',form).addClass('ui-widget-content').addClass('ui-corner-all');
				    },
					beforeInitData: function(form) {},
					closeAfterAdd: true,
					reloadAfterSubmit:true,
                    afterSubmit : function(response, postdata){
                        console.log("##afterSubmit")
                        $.ajaxFileUpload({
                                url: '${uploadUrl}',
                                secureuri: false,
                                fileElementId: 'filename',
                                dataType: 'json',
//                                data: { id: id},
                                success: function (data,status) {
                                    var msg = data['msg'];
                                    alert(msg);
                                },
								error : function(data, status, e) {
									alert("上传发生异常");
								}
						})
                    }
//					afterSubmit : function(response, postdata)
//					{
//				        var result = eval('(' + response.responseText + ')');
//						var errors = "";
//
//				        if (result.success == false) {
//							for (var i = 0; i < result.message.length; i++) {
//								errors +=  result.message[i] + "<br/>";
//							}
//				        }  else {
//				        	$('#msgbox').text('Entry has been added successfully');
//							$('#msgbox').dialog(
//									{	title: 'Success',
//										modal: true,
//										buttons: {"Ok": function()  {
//											$(this).dialog("close");}
//										}
//									});
//		                }
//				    	// only used for adding new records
//				    	var newId = null;
//
//						return [result.success, errors, newId];
//					}

	    		});
	} // end of addRow


	<%--function editRow() {--%>
   		<%--$("#grid").jqGrid('setColProp', 'username', {editoptions:{readonly:true, size:10}});--%>
   		<%--$("#grid").jqGrid('setColProp', 'password', {hidden: true});--%>
   		<%--$("#grid").jqGrid('setColProp', 'password', {editrules:{required:false}});--%>

		<%--// Get the currently selected row--%>
		<%--var row = $('#grid').jqGrid('getGridParam','selrow');--%>

		<%--if( row != null ) {--%>

			<%--$('#grid').jqGrid('editGridRow', row,--%>
				<%--{	url: '${editUrl}',--%>
					<%--editData: {},--%>
			        <%--recreateForm: true,--%>
			        <%--beforeShowForm: function(form) {--%>
			            <%--$('#pData').hide();--%>
			            <%--$('#nData').hide();--%>
			        <%--},--%>
					<%--beforeInitData: function(form) {},--%>
					<%--closeAfterEdit: true,--%>
					<%--reloadAfterSubmit:true,--%>
					<%--afterSubmit : function(response, postdata)--%>
					<%--{--%>
			            <%--var result = eval('(' + response.responseText + ')');--%>
						<%--var errors = "";--%>

			            <%--if (result.success == false) {--%>
							<%--for (var i = 0; i < result.message.length; i++) {--%>
								<%--errors +=  result.message[i] + "<br/>";--%>
							<%--}--%>
			            <%--}  else {--%>
			            	<%--$('#msgbox').text('Entry has been edited successfully');--%>
							<%--$('#msgbox').dialog(--%>
									<%--{	title: 'Success',--%>
										<%--modal: true,--%>
										<%--buttons: {"Ok": function()  {--%>
											<%--$(this).dialog("close");}--%>
										<%--}--%>
									<%--});--%>
		                <%--}--%>
				    	<%--// only used for adding new records--%>
				    	<%--var newId = null;--%>

						<%--return [result.success, errors, newId];--%>
					<%--}--%>
				<%--});--%>
		<%--} else {--%>
			<%--$('#msgbox').text('You must select a record first!');--%>
			<%--$('#msgbox').dialog(--%>
					<%--{	title: 'Error',--%>
						<%--modal: true,--%>
						<%--buttons: {"Ok": function()  {--%>
							<%--$(this).dialog("close");}--%>
						<%--}--%>
					<%--});--%>
		<%--}--%>
	<%--}--%>

	function deleteRow() {
		// Get the currently selected row
	    var row = $('#grid').jqGrid('getGridParam','selrow');

	    // A pop-up dialog will appear to confirm the selected action
		if( row != null )
			$('#grid').jqGrid( 'delGridRow', row,
	          	{	url:'${deleteUrl}',
					recreateForm: true,
				    beforeShowForm: function(form) {
				    	//Change title
				        $(".delmsg").replaceWith('<span style="white-space: pre;">' +
				        		'Delete selected record?' + '</span>');
		            	//hide arrows
				        $('#pData').hide();
				        $('#nData').hide();
				    },
	          		reloadAfterSubmit:true,
	          		closeAfterDelete: true,
	          		serializeDelData: function (postdata) {
		          	      var rowdata = $('#grid').getRowData(postdata.id);
		          	      // append postdata with any information
		          	      return {id: postdata.id, oper: postdata.oper, username: rowdata.username};
		          	},
	          		afterSubmit : function(response, postdata)
					{
			            var result = eval('(' + response.responseText + ')');
						var errors = "";

			            if (result.success == false) {
							for (var i = 0; i < result.message.length; i++) {
								errors +=  result.message[i] + "<br/>";
							}
			            }  else {
			            	$('#msgbox').text('Entry has been deleted successfully');
							$('#msgbox').dialog(
									{	title: 'Success',
										modal: true,
										buttons: {"Ok": function()  {
											$(this).dialog("close");}
										}
									});
		                }
				    	// only used for adding new records
				    	var newId = null;

						return [result.success, errors, newId];
					}
	          	});
		else {
			$('#msgbox').text('You must select a record first!');
			$('#msgbox').dialog(
					{	title: 'Error',
						modal: true,
						buttons: {"Ok": function()  {
							$(this).dialog("close");}
						}
					});
		}
	}
	</script>
</head>

<body style="width: 80%;float: left">

	<div id='jqgrid'>
		<table id='grid'></table>
		<div id='pager'></div>
	</div>
	
	<div id='msgbox' title='' style='display:none'></div>
</body>
</html>