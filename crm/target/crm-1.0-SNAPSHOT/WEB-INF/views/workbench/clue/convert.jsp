<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<%@ include file="../../common/head.jsp" %>

<%--<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />--%>
<%--<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>--%>
<%--<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>--%>

<script type="text/javascript">
	$(function(){
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});

		//为客户创建交易选择框
		$("#create-transaction1").click(function (){
			//模态窗口日期选择框
			$(".time").datetimepicker({
				minView: "month",
				language: 'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});

			//从数据字典中加载stage类型下拉框数据
			$.ajax({
				url:"setting/dic",
				data:{
					"stage":"stage",
				},
				Type:"get",
				dataType:"json",
				success:function (data){
					var html = "<option></option>";
					$.each(data.stage,function (i,n){
						html += "<option value='"+ n.value +"'>"+ n.text +"</option>";
					})
					$("#stage").html(html);
				}
			})

			searchActivity();

		});

		//点击放大镜，打开 市场活动源 模态窗口
		$("#openActivityModal").click(function (){
			//打开模态窗口
			$("#searchActivityModal").modal("show");
		})


		//给市场活动搜索框绑定回车触发事件（支持模糊查询）；
		$("#searchActivity").keydown(function (enevt){
			//13代表回车键；
			if (enevt.keyCode == 13){
				searchActivity();
				return false;
			}
		})


		//确认选中的市场活动源
		$("#comfirmBtn").click(function (){
			//获取选中的市场活动的 id和name
			var $radio = $("input[name=activity]:checked");

			if ($radio.length == 0){
				alert("请选择市场活动源");
				return false;
			}else{
				var id = $radio.val();
				var name = $("#"+id).html();

				$("#activityName").val(name);
				$("#activityId").val(id);

				//关闭模态窗口
				$("#searchActivityModal").modal("hide");
			}
		})


		//转换线索按钮
		$("#converClueBtn").click(function (){
			if ($("#isCreateTransaction").prop("checked")){
				//创建交易，转换客户、联系人
				$("#tranForm").submit();
			}else{
				//转换客户、联系人
				window.location.href="workbench/converClue?clueId=${param.id}";
			}
		})


	});

	//按条件查询此线索关联的市场活动信息,(支持模糊查询）
	function searchActivity(){
		var aname = $.trim($("#searchActivity").val());

		$.ajax({
			url:"workbench/ClueAndActivity/${param.id}/"+aname,
			Type:"get",
			dataType:"json",
			success:function (data){
				var html = "";
				$.each(data,function (i,n){
					html += '<tr>';
					html += '<td><input type="radio" name="activity" value="'+ n.id +'"/></td>';
					html += '<td id="'+ n.id +'">'+ n.name+'</td>';
					html += '<td>'+ n.startDate+'</td>';
					html += '<td>'+ n.endDate+'</td>';
					html += '<td>'+ n.owner+'</td>';
					html += '</tr>';
				})

				$("#activityTableBodys").html(html);
			}
		})
	}


</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input id="searchActivity" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="activityTableBodys">

						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					<button type="button" class="btn btn-primary" id="comfirmBtn" >确定</button>
				</div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small>${param.fullname}${param.appellation}-${param.company}</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：${param.company}
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：${param.fullname}${param.appellation}
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form id="tranForm" action="workbench/converClue" method="post">
			<input type="hidden" name="clueId" value="${param.id}">
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney" name="money">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="动力节点-" name="name">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control time" id="expectedClosingDate"  name="expectedDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;" >
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control"  name="stage">
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="activityName">市场活动源&nbsp;&nbsp;<a id="openActivityModal" href="javascript:void(0);"  style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activityName" placeholder="点击上面搜索" readonly>
			  <input type="hidden" id="activityId"  name="activityId">
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b>${param.owner}</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" id="converClueBtn" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
</body>
</html>