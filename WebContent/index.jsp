<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<%@ taglib uri="myJstl" prefix="myJstl"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>博客列表</title>
<%@ include file="top.jsp"%>
<link href="${path }/css/blog.css" rel="stylesheet" type="text/css">

</head>
<body>

	<div class="bloglist">
		<c:forEach items="${dataMap }" var="List">
			<c:if test="${List.key== 'blogList'}">
				<c:forEach items="${List.value }" var="blog" varStatus="i">
					<div class="blogtitle">
						<a href="${path }/blog/show/${blog.blogId }">${blog.title }</a>
					</div>
					<div class="blogcontent">
						<c:out value="${fn:substring(myJstl:byteToString(blog.content), 0, 150)}"
							escapeXml="false" />
						... <a href="${path }/blog/show/${blog.blogId }">阅读全部</a>
					</div>
					<div class="blogauthor">
						作者:${blog.author.username } 发布时间:
						<fmt:formatDate value="${blog.createtime}" type="date"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</div>
				</c:forEach>
			</c:if>
		</c:forEach>
	</div>

</body>
</html>