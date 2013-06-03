<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%
	String path = request.getContextPath();
%>
<style type="text/css">
ul {
	border-bottom: 1px solid #DADCDD;
	border-top-width: 0;
	clear: left;
	height: 40px;
	list-style: none outside none;
	margin: 0;
	overflow: visible;
	padding: 0;
	white-space: nowrap;
}
ul:hover{
	background-color: RGB(204,232,207);
	cursor: pointer;
}

li {
	border-right: 1px solid #DADCDD;
	float: left;
	height: 40px;
	line-height: 40px;
	list-style: none outside none;
	margin: 0;
	overflow: visible;
	padding: 0;
	position: relative;
	white-space: nowrap;
	width: 100px;
	text-align: center;
}
</style>

</head>
<body>

	<ul>
		<li style="width: 50px;">序号</li>
		<li>用户名</li>
		<li>密码</li>
		<li>详细</li>
	</ul>
	<c:forEach items="${dataMap }" var="List" >
		<c:if test="${List.key== 'userList'}">
			<c:forEach items="${List.value }" var="user" varStatus="i">
				<ul>
					<li style="width: 50px;">${i.index + 1 }</li>
					<li>${user.username }</li>
					<li>${user.password }</li>
					<li><a href="<%=path %>/user/show/${user.userid }">详细</a></li>
				</ul>
			</c:forEach>
		</c:if>
	</c:forEach>
	
	<a href="<%=path %>/user/list">查看更多</a>
	<a href="<%=path %>/login.html">登录</a>
	<a href="<%=path %>/reg.html">注册</a>
</body>
</html>