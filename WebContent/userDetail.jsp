<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户详细</title>
<style type="text/css">
	ul{
		border-style: none;
	    clear: left;
	    height: 40px;
	    list-style: none outside none;
	    margin: 0;
	    overflow: visible;
	    padding: 0;
	    white-space: nowrap;
	}
	li{
	    border-color: #FFFFFF #000000 #000000 #FFFFFF;
	    border-style: solid;
	    border-width: 0;
	    float: left;
	    height: 33px;
	    line-height: 31px;
	    list-style: none outside none;
	    margin: 0;
	    overflow: visible;
	    padding: 0;
	    position: relative;
	    white-space: nowrap;
	}
	
</style>

</head>
<body>
	<c:forEach items="${dataList }" var="user" begin="0" end="0">
		<ul>
			<li>用户名：</li>
			<li>${user.username }</li>
		</ul>
		<ul>
			<li>密码：</li>
			<li>${user.password}</li>
		</ul>
	</c:forEach>
</body>
</html>