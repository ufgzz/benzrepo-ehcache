<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>

</head>

<body>
<h1>欢迎你……</h1>
查询接口访问：<a href="./getUserPermission" target="_blank">/getUserPermission</a>，看后台打印以及浏览器返回数据<br/>
<hr/>
<b>注意查看控制台打印，第1次访问控制台会打印出："查数据库了==========="和sql语句<br/>
下一次访问的时候，控制台不会打印，但依然查出数据，说明是从缓存中取的数据。</b><br/>
<br/>
清除缓存接口：<a href="./deleteUserPermissionCache" target="_blank">/deleteUserPermissionCache</a>，看后台打印以及浏览器返回数据
<hr/>
<b>调用清除缓存接口后，再去调用查询接口，注意看控制台打印，发现又会去查数据库了。</b>
</body>

</html>
