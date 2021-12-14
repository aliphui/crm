<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<%@ include file="common/head.jsp" %>

	<script type="text/javascript">
	$(function (){

		//如果当前窗口不是顶层，就将当前窗口设置为顶层窗口
		if(window.top!=window){
			window.top.location=window.location;
		}
		//为当前登录也窗口绑定敲键盘事件
		//event:这个参数可以取得我们敲的是哪个键
		$(window).keydown(function (event) {
			// alert(event.keyCode);
			//如果取得的键位的码值为13，表示敲的是回车键
			if(event.keyCode==13){
				login();
			}
		})


		$("#but_login").click(function(){
			login();
		})

	})

	function login(){
		//获取用户名和密码（忽略前后空格）
		var loginAct = $.trim($("#login_act").val());
		var loginPwd = $.trim($("#login_pwd").val());
		if(loginAct == "" || loginPwd == ""){
			$("#msg").text("用户名或密码不能为空!!!");
			return false;
		};

		$.post("login","loginAct="+loginAct+ "&loginPwd=" +loginPwd,function (data){
			if(data.msg == "success"){
				document.location.href = "workbench";
			}else{
				$("#msg").text(data.msg);
			}
		},"json");
	}
</script>

</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="static/image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.jsp" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="login_act">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="login_pwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						
							<span id="msg" style="color:red"></span>
						
					</div>
					<!--默认类型为submit,会提交表单，-->
					<button type="button" id="but_login" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>