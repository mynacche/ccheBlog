<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	request.setAttribute("path", path);
%>
<style type="text/css">
.topdiv {
	width: 1024px;
	margin: 0 auto 10px auto;
	text-align: right;
	padding-right: 20px;
	font-family: Verdana, Arial;
	font-size:12px;
}
.topdiv a {
	text-decoration: none;
	color: black;
}
</style>
</head>
<body>

	<c:if test="${loginUser==null }">
		<div class="topdiv">
			<a href="${path }/user/login">登录</a> 
			<a href="${path }/user/reg">注册</a>
			<a href="${path }/blog/add">写博客</a>
			<a href="${path }/blog/list">博客列表</a>
		</div>
	</c:if>
	<c:if test="${loginUser!=null }">
		<div class="topdiv">
			欢迎您 <a href="${path }/user/show/${loginUser.userid }">${loginUser.username}</a> 
				  <a href="${path }/user/logout/${loginUser.userid }">退出</a>
				  <a href="${path }/blog/add">写博客</a>
				  <a href="${path }/blog/list">博客列表</a>
		</div>
	</c:if>
</body>
</html>