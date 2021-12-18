<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
	<%@ include file="../../common/head.jsp" %>
<%--<link href="../../jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />--%>

<%--<script type="text/javascript" src="../../jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>--%>
<%--<script type="text/javascript" src="../../jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>--%>
	<link rel="stylesheet" type="text/css" href="static/jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="static/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="static/jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){

		//查询按钮
		$("#selectButton").click(function (){
			pageList(1,$("#pageBodys").bs_pagination('getOption', 'rowsPerPage'));
		})

		//清空按钮
		$("#emptyButton").click(function (){
			$("#select-owner").val("");
			$("#select-name").val("");
			$("#select-customerId").val("");
			$("#select-stage").val("");
			$("#select-type").val("");
			$("#select-source").val("");
			$("#select-contactsId").val("");
			pageList(1,$("#pageBodys").bs_pagination('getOption', 'rowsPerPage'));
		})

		//加载数据字典下拉框数据
		$.ajax({
			url:"setting/dic",
			data:{
				"stage":"stage",
				"transactionType":"transactionType",
				"source":"source"
			},
			type:"get",
			dataType:"json",
			success:function (data){
				var html = "<option></option>";
				$.each(data.stage,function (i,n){
					html += "<option value='"+ n.value +"'>"+ n.text +"</option>";
				})
				$("#select-stage").html(html);

				var html = "<option></option>";
				$.each(data.transactionType,function (i,n){
					html += "<option value='"+ n.value +"'>"+ n.text +"</option>";
				})
				$("#select-type").html(html);

				var html = "<option></option>";
				$.each(data.source,function (i,n){
					html += "<option value='"+ n.value +"'>"+ n.text +"</option>";
				})
				$("#select-source").html(html);
			}
		})
		
		pageList(1,2);
	});

	//分页查询交易(包括条件查询)
	function pageList(pageNo,pageSize){
		$.ajax({
			url:"workbench/tran",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"owner":$.trim($("#select-owner").val()),
				"name":$.trim($("#select-name").val()),
				"customerId":$.trim($("#select-customerId").val()),
				"stage":$.trim($("#select-stage").val()),
				"type":$.trim($("#select-type").val()),
				"source":$.trim($("#select-source").val()),
				"contactsId":$.trim($("#select-contactsId").val())
			},
			type:"get",
			dataType:"json",
			success:function (data){

				alert("fefffff");
				//交易主体数据
				// $("#tranBodys").empty();
				var html = "";
				$.each(data.list,function (i,n){
					//data:  list<Tran>.使用pageInfo封装
					html += '<tr class="active">';
					html += '<td><input type="checkbox" /></td>';
					html += '<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'transactionDetail\';">' + n.name + '</a></td>';
					html += '<td>' + n.customerId + '</td> ';
					html += '<td>' + n.stage + '</td>';
					html += '<td>' + n.type + '</td>';
					html += '<td>' + n.owner+ '</td> ';
					html += '<td>' + n.source + '</td>';
					html += '<td>' + n.contactsId + '</td>';
					html += '</tr> ';
				})
				$("#tranBodys").html(html);


				//分页条
				$("#pageBodys").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: data.pages, // 总页数
					totalRows: data.total, // 总记录条数

					visiblePageLinks: data.navigatePages, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					//该回调函数时在，点击分页组件的时候触发的
					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});
			}
		})
	}
	
</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="select-owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="select-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="select-customerId">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="select-stage">

					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="select-type">

					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="select-source">

						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="select-contactsId">
				    </div>
				  </div>
				  
				  <button type="button" id="selectButton" class="btn btn-default">查询</button>
				  <button type="button" id="emptyButton" class="btn btn-default">清空</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='transactionSave';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="window.location.href='transactionEdit';"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tranBodys">
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='transactionDetail';">动力节点-交易01</a></td>--%>
<%--                            <td>动力节点</td>--%>
<%--                            <td>谈判/复审</td>--%>
<%--                            <td>新业务</td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>广告</td>--%>
<%--                            <td>李四</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 20px;">
				<div id="pageBodys" ></div>
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>--%>
<%--				</div>--%>
<%--				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--					<div class="btn-group">--%>
<%--						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--							10--%>
<%--							<span class="caret"></span>--%>
<%--						</button>--%>
<%--						<ul class="dropdown-menu" role="menu">--%>
<%--							<li><a href="#">20</a></li>--%>
<%--							<li><a href="#">30</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--				</div>--%>
<%--				<div style="position: relative;top: -88px; left: 285px;">--%>
<%--					<nav>--%>
<%--						<ul class="pagination">--%>
<%--							<li class="disabled"><a href="#">首页</a></li>--%>
<%--							<li class="disabled"><a href="#">上一页</a></li>--%>
<%--							<li class="active"><a href="#">1</a></li>--%>
<%--							<li><a href="#">2</a></li>--%>
<%--							<li><a href="#">3</a></li>--%>
<%--							<li><a href="#">4</a></li>--%>
<%--							<li><a href="#">5</a></li>--%>
<%--							<li><a href="#">下一页</a></li>--%>
<%--							<li class="disabled"><a href="#">末页</a></li>--%>
<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
			</div>
			
		</div>
		
	</div>
</body>
</html>