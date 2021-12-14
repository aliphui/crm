<%--
  Created by IntelliJ IDEA.
  User: 86155
  Date: 2021/12/9
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- 我的资料 -->
<div class="modal fade" id="myInformation" role="dialog">
    <div class="modal-dialog" role="document" style="width: 30%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title">我的资料</h4>
            </div>
            <div class="modal-body">
                <div style="position: relative; left: 40px;">
                    姓名：<b>${sessionScope.user.name}</b><br><br>
                    登录帐号：<b>${sessionScope.user.loginAct}</b><br><br>
                    组织机构：<b>${sessionScope.user.deptno}，市场部，二级部门</b><br><br>
                    邮箱：<b>${sessionScope.user.email}</b><br><br>
                    失效时间：<b>${sessionScope.user.expireTime}</b><br><br>
                    允许访问IP：<b>${sessionScope.user.allowIps}</b>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
