<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

	<%@ include file="../../common/head.jsp" %>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){

		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});

		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});

		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});

		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});

		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});

		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});

		$("#remarkBody").on("mouseover",".remarkDiv",function(){
			$(this).children("div").children("div").show();
		});
		$("#remarkBody").on("mouseout",".remarkDiv",function(){
			$(this).children("div").children("div").hide();
		});




		//编辑按钮，打开模态窗口
		$("#editButton").click(function (){

			//模态窗口日期选择框
			$(".editDate").datetimepicker({
				minView: "month",
				language: 'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});

			//打开模态窗口
			$("#editActivityModal").modal("show");
		})

		//市场活动更新按钮
		$("#updateButton").click(function (){

			//serialize()表单序列化，
			var $form = $("#editActivityForm").serialize();
			$.ajax({
				url:"activity?" + $form,
				data:{
					"_method":"PUT"
				},
				type:"POST",
				dataType:"json",
				success:function (data) {
					if (data.flag){
						window.location.href = "selectActivityDetail?id=${map.id}";
						alert("修改成功！");
					}else{
						alert("修改失败！")
					}
				}
			});
		})


		//市场活动删除按钮
		$("#deleteButton").click(function (){
			if (confirm("确定要删除活动记录吗？")){
				$.ajax({
					url:"activity?id=${map.id}",
					type:"DELETE",
					dataType:"json",
					success:function (data) {

						if (data.flag){
							//删除操作后，跳到市场活动页面
							window.location.href = "activityIndex";
							alert("删除成功！");
						}else{
							alert("删除失败！")
						}
					}
				});
			}
		});

		//备注保存按钮
		$("#saveButton").click(function (){
			$.ajax({
				url:"activityRemark",
				data:{
					"noteContent":$("#remark").val(),
					"activityId":"${map.id}",
				},
				type:"POST",
				dataType:"json",
				success:function (data) {
					if (data.flag){
						//保存完成后清空备注输入框数据
						$("#remark").val("");


						//局部刷新页面，
						showActivityRemark();
					}else{
						alert("保存失败");
					}
				}
			})

		});

		//市场活动备注更新按钮
		$("#updateRemarkBtn").click(function (){
			var note = $("#noteContent").val();
			var id = $("#remarkId").val();
			$.ajax({
				url:"activityRemark",
				data:{
					"_method":"PUT",
					"id":id,
					"noteContent":note
				},
				type:"POST",
				dataType:"json",
				success:function (data) {
					if (data.flag){
						//修改成功后，刷新备注信息
						showActivityRemark()

						//打开修改备注模态窗口
						$("#editRemarkModal").modal("hide");
					}else{
						alert("修改失败！")
					}
				}
			});
		})

		showActivityRemark();

	});

	//市场活动备注信息
	function showActivityRemark(){
		$(".remarkDiv").remove();

		$.ajax({
			url:"activityRemark",
			data:{
				"activityId":"${map.id}"
			},
			type:"GET",
			dataType:"json",
			success:function (data) {
				var html = "";
				$.each(data,function (i,remark){
				html +=	'<div class="remarkDiv" style="height: 60px;">';
				html +=	'<img title="'+remark.createBy+'" src="static/image/user-thumbnail.png" style="width: 30px; height:30px;">';
				html +=	'<div style="position: relative; top: -40px; left: 40px;" >';
				html +=	'<h5>'+remark.noteContent+'</h5>';
				html +=	'<font color="gray">市场活动</font> <font color="gray">-</font> <b>${map.name}</b> <small style="color: gray;"> '+(remark.editFlag==0?remark.createTime:remark.editTime) +' 由 ' + (remark.editFlag==0?remark.createBy:remark.editBy) + '</small>';
				html +=	'<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">';
				html +=	'<a class="myHref" href="javascript:void(0); onclick=updateRemark(\''+remark.id +'\',\''+ remark.noteContent+'\')"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #FF0000;"></span></a>';
				html +=	'&nbsp;&nbsp;&nbsp;&nbsp;';
				html +=	'<a class="myHref" href="javascript:void(0); onclick=deleteRemark(\''+remark.id+'\')"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #FF0000;"></span></a>';
				html +=	'</div>';
				html +=	'</div>';
				html +=	'</div>';

				})

				$("#pageHeaderDiv").after(html);

			}
		})

	}

	//修改市场活动备注
	function updateRemark(id,noteContent){
		//回显备注信息
		$("#noteContent").val(noteContent);
		$("#remarkId").val(id);

		//打开修改备注模态窗口
		$("#editRemarkModal").modal("show");
	}

	//删除市场活动备注
	function deleteRemark(id){
		if (confirm("确定要删除备注吗？")){
			$.ajax({
				url:"activityRemark?id=" + id,
				type:"DELETE",
				dataType:"json",
				success:function (data) {

					if (data.flag){
						//删除操作后，刷新备注信息
						showActivityRemark()
					}else{
						alert("删除失败！")
					}
				}
			});
		}
	}

</script>

</head>
<body>
	
	<!-- 修改市场活动备注的模态窗口 -->
	<div class="modal fade" id="editRemarkModal" role="dialog">
		<%-- 备注的id --%>
		<input type="hidden" id="remarkId">
        <div class="modal-dialog" role="document" style="width: 40%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">修改备注</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" role="form">

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="noteContent"></textarea>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="updateRemarkBtn">更新</button>
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
                    <h4 class="modal-title" id="myModalLabel">修改市场活动</h4>
                </div>
                <div class="modal-body">

                    <form class="form-horizontal" role="form" id="editActivityForm">
						<input type="hidden" name="id" value="${map.id}">
						<input type="hidden" name="owner" value="${map.owner}">
                        <div class="form-group">
                            <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="user">
										<c:if test="${user.id == map.owner}">
											<option selected="selected">${user.name}</option>
										</c:if>
										<c:if test="${user.id != map.owner}">
											<option>${user.name}</option>
										</c:if>
									</c:forEach>
                                </select>
                            </div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" name="name" value="${map.name}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-startTime" class="col-sm-2 control-label ">开始日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control editDate" readonly id="edit-startTime" name="startDate" value="${map.startDate}">
                            </div>
                            <label for="edit-endTime" class="col-sm-2 control-label ">结束日期</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control editDate" readonly id="edit-endTime" name="endDate" value="${map.endDate}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-cost" name="cost" value="${map.cost}">
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                            <div class="col-sm-10" style="width: 81%;">
                                <textarea class="form-control" rows="3" id="edit-describe" name="description">${map.description}</textarea>
                            </div>
                        </div>

                    </form>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal" id="updateButton">更新</button>
                </div>
            </div>
        </div>
    </div>

	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>市场活动-${map.name} <small>${map.startDate} ~ ${map.endDate}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" id="editButton"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger" id="deleteButton"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: -70px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${map.userName}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${map.name}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>

		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">开始日期</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${map.startDate}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">结束日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${map.endDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">成本</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${map.cost}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${map.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${map.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${map.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${map.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${map.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 30px; left: 40px;" id="remarkBody">
		<div class="page-header" id="pageHeaderDiv">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
<%--		<div class="remarkDiv" style="height: 60px;">--%>
<%--			<img title="zhangsan" src="static/image/user-thumbnail.png" style="width: 30px; height:30px;">--%>
<%--			<div style="position: relative; top: -40px; left: 40px;" >--%>
<%--				<h5>哎呦！</h5>--%>
<%--				<font color="gray">市场活动</font> <font color="gray">-</font> <b>发传单</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>--%>
<%--				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">--%>
<%--					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>--%>
<%--					&nbsp;&nbsp;&nbsp;&nbsp;--%>
<%--					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--		</div>--%>

		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary" id="saveButton">保存</button>
				</p>
			</form>
		</div>
	</div>
	<div style="height: 200px;"></div>
</body>
</html>