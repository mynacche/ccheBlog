<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<%@ include file="../top.jsp"%>
<link href="${path }/css/blog.css" rel="stylesheet" type="text/css">
<style type="text/css">
.bloglist {
	border-bottom: 1px solid;
	height: 600px;
}

ul {
	margin: 20px;
}

li {
	margin: 10px;
}

#title {
	width: 400px;
}

#content {
	width: 860px;
	height: 400px;
	resize: none;
	font-size: 12px;
}
</style>
<title>发布博客</title>
</head>
<body>
	<div class="bloglist">
		<ul>
			<li>标题</li>
			<li><input type="text" id="title" name="title"></li>
		</ul>
		<ul style="height: 420px">
			<li>内容</li>
			<li><textarea id="content" name="content" class="ckeditor"></textarea></li>
		</ul>
		<ul>
			<li><button id="commitB">提交</button></li>
			<li><button id="resetB">重置</button></li>
			<li><span id="msg"></span></li>
		</ul>
	</div>
</body>
<script type="text/javascript" src="../js/ajax.js"></script>
<script type="text/javascript" src="../kindeditor/kindeditor.js"></script>
<script type="text/javascript">
	var editor;
	KindEditor.ready(function(K) {
		/* var options = {
			cssPath : '../kindeditor/plugins/code/prettify.css',
			uploadJson : 'kindeditor/jsp/upload_json.jsp',
			fileManagerJson : 'kindeditor/jsp/file_manager_json.jsp',
			allowFileManager : true,
			resizeType : 0,
			items : [ 'source', '|', 'undo', 'redo', '|', 'preview',
					'wordpaste', '|', 'justifyleft', 'justifycenter',
					'justifyright', 'justifyfull', 'insertorderedlist',
					'insertunorderedlist', 'selectall', '|', 'fullscreen',
					'formatblock', 'fontname', 'fontsize', '|', 'forecolor',
					'hilitecolor', 'bold', 'italic', 'lineheight',
					'removeformat', '|', 'table', 'hr' ],
			afterCreate : function() {
				this.sync();
			},
			afterBlur : function() {
				this.sync();
			}
		};
		editor = K.create('#content', options); */
		editor = K.create('#content');
	});
	var titledom = document.getElementById("title");
	var contentdom = document.getElementById("content");

	document.getElementById("resetB").onclick = function() {
		var b = confirm("重置将丢失所填写的信息，是否继续？");
		if (b == true) {
			titledom.value = "";
			contentdom.value = "";
			titledom.focus();
		}

	};

	document.getElementById("commitB").onclick = function() {

		editor.sync();

		var url = "../blog/add";
		callServer(
				url,
				"post",
				"title=" + encodeURIComponent(titledom.value) + "&content="
						+ encodeURIComponent(contentdom.value),
				true,
				function() {
					if (xmlHttp.readyState == 4) {
						var jsonStr = eval("(" + xmlHttp.responseText + ")");
						if (jsonStr.flag == "-1") {
							document.getElementById("msg").innerHTML = "<font color='red'>"
									+ jsonStr.respMsg + "</font>";
						} else {
							redirect = jsonStr.mapping;
							document.getElementById("msg").innerHTML = jsonStr.respMsg;
							window.location = redirect;

						}
					}
				});

	};
</script>
</html>