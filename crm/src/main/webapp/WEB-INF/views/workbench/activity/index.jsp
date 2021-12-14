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

		// "创建" 市场活动按钮
		$("#createButton").click(function (){
			//模态窗口下拉框数据
			modData("#create-owner");

			//模态窗口日期选择框,为class为createTime的日期框添加样式
			dateClass(".createTime");

			//show：打开模态窗口，hide：关闭模态窗口
			$("#createActivityModal").modal("show");
		});

		//保存市场活动
		$("#saveActivity").click(function (){
			//获取表单中的内容
			var owner = $("#create-owner").val();
			var name = $.trim($("#create-name").val());
			var startDate = $("#create-startDate").val();
			var endDate = $("#create-endDate").val();
			var cost = $.trim($("#create-cost").val());
			var description = $.trim($("#create-description").val());
			$.ajax({
				url:"activity",
				data:{owner:owner,name:name,startDate:startDate,endDate:endDate,cost:cost,description:description},
				type:"POST",
				dataType:"json",
				success:function (data) {
					if (data.flag){
						alert("保存成功");
						//保存完成后清空模态窗口数据
						$("#activityAddForm")[0].reset();
						//关闭模态窗口
						$("#createActivityModal").modal("hide");

						//刷新页面，应该回到第一页，维持每页展现的记录数
						pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

					}else{
						alert("保存失败");
					}
				}
			})
		});

		//修改市场活动按钮
		$("#editButton").click(function (){
			var $checkbox = $("input[class=checkbox]:checked");

			if ($checkbox.length == 0){
				alert("请选择需要修改的记录");
			}else if ($checkbox.length > 1){
				alert("只能选择一条记录进行修改");
			}else{

				//获取模态窗口下拉框数据
				modData("#edit-owner");

				//模态窗口日期选择框,为class为selectTime的日期框添加样式
				dateClass(".updateTime");

				//回显要修改的数据 到 模态窗口
				$.ajax({
					url:"selectOneActivity",
					data:{id:$checkbox.val()},
					type:"get",
					dataType:"json",
					success:function (data) {
						//处理单条activity，设置回显数据
						$("#edit-id").val(data.id);
						$("#edit-name").val(data.name);
						$("#edit-owner").val(data.owner);
						$("#edit-startDate").val(data.startDate);
						$("#edit-endDate").val(data.endDate);
						$("#edit-cost").val(data.cost);
						$("#edit-description").val(data.description);

						//show：打开模态窗口，hide：关闭模态窗口
						$("#editActivityModal").modal("show");
					}
				});
			}

		});

		//更新市场活动按钮
		$("#update-btn").click(function (){
			$.ajax({
				url:"activity",
				data:{
					"_method":"PUT",
					"id" : $.trim($("#edit-id").val()),
					"owner" : $.trim($("#edit-owner").val()),
					"name" : $.trim($("#edit-name").val()),
					"startDate" : $.trim($("#edit-startDate").val()),
					"endDate" : $.trim($("#edit-endDate").val()),
					"cost" : $.trim($("#edit-cost").val()),
					"description" : $.trim($("#edit-description").val())

				},
				type:"POST",
				dataType:"json",
				success:function (data) {
					if (data.flag){

						//修改操作后，刷新数据，应该维持在当前页，维持每页展现的记录数
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));

						alert("修改成功！");
					}else{
						alert("修改失败！")
					}
				}
			});
		})

		//删除市场活动按钮
		$("#deleteButton").click(function (){
			//获取所有选中元素
			var $checkbox = $("input[class=checkbox]:checked");

			if ($checkbox.length == 0){
				alert("请选择需要删除的记录");
			}else{
				if (confirm("确定要删除活动记录吗？")){

					//拼接参数
					var param = "";
					for (var i = 0; i < $checkbox.length; i++) {

						param += "id=" + $($checkbox[i]).val();

						//如果不是最后一个元素，需要在后面追加一个&符
						if(i<$checkbox.length-1){
							param += "&";
						}
					}

					$.ajax({
						url:"activity?"+param,
						type:"DELETE",
						dataType:"json",
						success:function (data) {

							if (data.flag){
								//删除操作后，刷新页面，维持当前页码和每页展现的记录数
								pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
										,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
								alert("删除成功！");
							}else{
								alert("删除失败！")
							}
						}
					});
				}
			}
		});


		//按条件查询市场活动信息
		$("#select-btn").click(function (){
			//刷新页面，应该回到第一页，维持每页展现的记录数
			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		});
		//清空查询信息
		$("#empty_btn").click(function (){
			$("#select-name").val("");
			$("#select-owner").val("");
			$("#select-startDate").val("");
			$("#select-endDate").val("");
			//刷新页面，应该回到第一页，维持每页展现的记录数
			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		});

		//全选、全不选操作
		$("#input_checkbox").click(function (){
			$(".checkbox").prop("checked",this.checked);
		});
		// 动态生成的元素，我们要以on方法的形式来触发事件
		// 语法：
		// 		$(需要绑定元素的有效的外层元素).on(绑定事件的方式,需要绑定的元素的jquery对象,回调函数)
		$("#activityBody").on("click",$("input[class=checkbox]"),function () {
			$("#input_checkbox").prop("checked",$("input[class=checkbox]").length==$("input[class=checkbox]:checked").length);
		})

		//打开页面时，加载页面分页数据
		pageList(1,2);
	});

	//加载页面信息
	function pageList(pageNo,pageSize){
		//将全选的复选框的√干掉
		$(":checkbox").prop("checked",false);

		//模态窗口日期选择框,为class为selectTime的日期框添加样式
		dateClass(".selectTime");

		//查询数据，页面加载完成执行
		$.ajax({
			url:"activity",
			data:{
				"pageNo" : pageNo,
				"pageSize" : pageSize,
				"name" : $.trim($("#select-name").val()),
				"owner" : $.trim($("#select-owner").val()),
				"startDate" : $.trim($("#select-startDate").val()),
				"endDate" : $.trim($("#select-endDate").val())
			},
			type:"GET",
			dataType:"json",
			success:function (data) {
				//表格主体数据
				tabBodyData(data);
				//分页条
				pageInfo(data,pageNo,pageSize);
			}
		});
	}
	//表格主体数据
	function tabBodyData(data){
		//每次刷新时，清空之前的数据
		$("#activityBody").empty();

		$.each(data.list,function (i,map) {
			var td1 = $("<td></td>").append($("<input type='checkbox' class='checkbox'></input>").attr({value:map.id}));
			var td2 = $("<td></td>").append($("<a></a>").attr({
				style: "text-decoration: none; cursor: pointer;",
				onclick: "window.location.href='selectActivityDetail?id="+ map.id+"';"
			}).text(map.name));

			var td3 = $("<td></td>").text(map.userName);
			var td4 = $("<td></td>").text(map.startDate);
			var td5 = $("<td></td>").text(map.endDate);

			$("<tr class='active'></tr>").append(td1).append(td2).append(td3).append(td4).append(td5).appendTo("#activityBody");
		})
	};

	//分页条
	function pageInfo(data,pageNo,pageSize){
		//数据处理完毕后，结合分页查询，对前端展现分页信息
		$("#activityPage").bs_pagination({
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
	};

	//获取模态窗口中指定id的下拉框 数据
	function modData($ownerData){
		$($ownerData).empty();
		$.ajax({
			url:"user",
			data:"",
			type:"GET",
			dataType:"json",
			success:function (data) {
				var $html = "";
				$.each(data,function (i,user){
					$html += "<option value='" + user.id + "'>"+ user.name+ "</option>";
				});

				$($ownerData).html($html);
				//下拉框默认选中登录者数据
				var id = "${sessionScope.user.id}";
				$("#create-owner").val(id);
			}
		})
	}
	//为指定class日期框添加  日期样式
	function dateClass($dateclass) {
		//模态窗口日期选择框
		$($dateclass).datetimepicker({
			minView: "month",
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			autoclose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});
	}

</script>
</head>
<body>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="activityAddForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control createTime" id="create-startDate" readonly>
							</div>
							<label for="create-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control createTime" id="create-endDate" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveActivity" type="button" class="btn btn-primary" data-dismiss="modal">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">
						<input type="hidden" id="edit-id"/>
						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">

								</select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control updateTime" id="edit-startDate" >
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control updateTime" id="edit-endDate" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="update-btn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >名称</div>
				      <input class="form-control" type="text" id="select-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >所有者</div>
				      <input class="form-control" type="text" id="select-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon" >开始日期</div>
					  <input class="form-control selectTime" type="text" id="select-startDate" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control selectTime" type="text" id="select-endDate">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" id="select-btn">查询</button>
				  <button type="button" class="btn btn-default" id="empty_btn">清空</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="createButton"> <span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editButton"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteButton"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="input_checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
					<%--
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='workbench/activity/detail.jsp';">发传单</a></td>
							<td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
						<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>
							<td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
					--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共 <b id="page_total"></b> 条记录</button>--%>
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

<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>