<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>mopon</title>
<!--皮肤-->
<link rel="stylesheet"
	href="<%=basePath%>resources/themes/ext-theme-neptune/ext-theme-neptune-all.css" />
<!--自定义桌面样式-->
<link rel="stylesheet" href="<%=basePath%>resources/css/desktop.css" />




<!--动态加载js，引导文件-->
<script src="<%=basePath%>lib/ext/bootstrap.js"></script>

<!--语言本地化-->
<script src="<%=basePath%>lib/ext/ext-lang-zh_CN.js"></script>

<!--样式修正-->
<script src="<%=basePath%>lib/ext/ext-theme-neptune.js"></script>

<script type="text/javascript" src="<%=basePath%>lib/tools/base64.js"></script>


<!--添加上传组件swf文件-->
<script src="lib/ux/multiupload/swfobject.js"></script>
<!--引入TinyMCE  htmleditor编辑器-->
<script src="<%=basePath%>lib/tinymce/tiny_mce.js"></script>

<!--frameworkjs-->
<script src="<%=basePath%>lib/framework/framework.js"></script>

<!--自定义的工具类-->
<script src="<%=basePath%>lib/tools/WmsTools.js"></script>
</head>
<body onbeforeunload="delCookie()">

</body>
</html>