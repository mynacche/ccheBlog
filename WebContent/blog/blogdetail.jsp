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

<%@ include file="../top.jsp"%>
<c:forEach items="${dataList }" var="blog" begin="0" end="0">
	<title>${blog.title }</title>
</c:forEach>
<link href="${path }/css/blog.css" rel="stylesheet" type="text/css">
<style type="text/css">
.bloglist {
	border-style: solid solid solid solid;
}

#blogedit {
	float: right;
	text-align: right;
	line-height: 30px;
	padding-right: 10px;
}

#blogedit a {
	text-decoration: none;
	color: blue;
}
</style>
</head>
<body>
	<c:forEach items="${dataList }" var="blog" begin="0" end="0">

		<div class="bloglist">

			<c:if test="${loginUser.id == blog.authorId}">
				<div id="blogedit">
					<a href="${path }/blog/edit/${blog.blogId }">编辑</a>
				</div>
			</c:if>

			<div class="blogtitle">${blog.title }</div>


			<div class="blogauthor">
				作者:${blog.author.username } 发布时间:
				<fmt:formatDate value="${blog.createtime}" type="date"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</div>
			<div class="blogcontent">
				<%-- <c:set property="content" value="<c:out value='${blog.content}' escapeXml='false' />"></c:set>
				<c:out value="${fn.replace(content,vEnter,'<br/>'}"></c:out> --%>
				<c:out value="${myJstl:byteToString(blog.content) }"
					escapeXml="false"></c:out>

			</div>
		</div>

	</c:forEach>
</body>
</html>