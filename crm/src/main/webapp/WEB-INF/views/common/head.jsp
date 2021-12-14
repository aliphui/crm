<%--
  Created by IntelliJ IDEA.
  User: 86155
  Date: 2021/12/8
  Time: 22:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%
        //动态获取base标签使用的地址
        String  basePath = request.getScheme()
                +  "://"
                +  request.getServerName()
                +  ":"
                +  request.getServerPort()
                +  request.getContextPath()
                +  "/";
        request.setAttribute("basePath",basePath);
    %>
    <base  href ="<%=basePath%>">

    <link href="static/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="static/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="static/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
    <link href="static/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="static/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
    <script type="text/javascript" src="static/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
</head>
<body>

</body>