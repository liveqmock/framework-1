<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--动态加载js，引导文件-->
<script type="text/javascript" src="<%=basePath%>lib/ext/bootstrap.js"></script>
<!--语言本地化-->
<script type="text/javascript"
	src="<%=basePath%>lib/ext/ext-lang-zh_CN.js"></script>
<title>修改密码</title>
<style type="text/css">
body,h1,h2,h3,h4,h5,h6,hr,p,blockquote,dl,dt,dd,ul,ol,li,pre,form,fieldset,legend,button,input,textarea,th,td
	{
	margin: 0;
	padding: 0;
}

body,button,input,select,textarea {
	font: 12px/1.5 arial, tahoma, \5b8b\4f53, sans-serif;
}

h1,h2,h3,h4,h5,h6 {
	font-size: 100%;
}

address,cite,dfn,em,var {
	font-style: normal;
}

code,kbd,pre,samp {
	font-family: courier new, courier, monospace;
}

small {
	font-size: 12px;
}

ul,ol {
	list-style: none;
}

a {
	text-decoration: none;
}

a:hover {
	text-decoration: underline;
}

sup {
	vertical-align: text-top;
}

sub {
	vertical-align: text-bottom;
}

legend {
	color: #000;
}

fieldset,img {
	border: 0;
}

button,input,select,textarea {
	font-size: 100%;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}

article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section,summary,time,mark,audio,video
	{
	display: block;
	margin: 0;
	padding: 0;
}

mark {
	background: #ff0;
}

.cf:before,.cf:after {
	content: "";
	display: table;
}

.cf:after {
	clear: both;
}

.cf {
	zoom: 1;
}

.clr {
	clear: both;
	font-size: 0;
	height: 0;
	line-height: 0;
	overflow: hidden;
}

body table,body table tr,body table tr td {
	font-size: 12px;
}

.tit_p {
	background: url(<%=basePath%>resources/images/framework/Keychain2.png) left center no-repeat;
	height: 55px;
	line-height: 55px;
	margin-top: 35px;
}

.tit_p em {
	display: inline-block;
	margin-left: 6px;
	color: #F00;
}

.tit_p span {
	font-family: 微软雅黑;
	font-size: 24px;
	padding-left: 50px;
}

.pass_c ul li {
	margin: 16px 0;
}

.pass_c input.tex {
	padding: 4px 6px 3px;
	border: 1px solid #d9d9d9;
	border-top: 1px solid silver;
	width: 220px;
}

.pass_c span {
	display: inline-block;
	margin: 0 10px 0 0;
	width: 100px;
	text-align: right;
}

.op_area {
	text-align: center;
}

.op_area input {
	margin: 0 6px;
	background: url(<%=basePath%>resources/images/framework/btn.jpg) center
		center;
	width: 44px;
	height: 24px;
	line-height: 24px;
	border: none;
	cursor: pointer;
	text-align: center;
	color: #FFF;
}
</style>
<script type="text/javascript">
	function login(options, success, response) {
		if (Ext.JSON.decode(response.responseText).success == true) {
			alert(Ext.JSON.decode(response.responseText).message);
			window.close();
		} else {
			alert(Ext.JSON.decode(response.responseText).message);
		}
	}

	function onLogin() {
		var userPwd = document.getElementById('newPwd').value;
		var oldPwd = document.getElementById('oldPwd').value;
		var confirmPwd = document.getElementById('confirmPwd').value;
		if (userPwd == "" || oldPwd == "" || confirmPwd == "") {
			alert("密码不能为空！");
			return;
		}
		if (userPwd != confirmPwd) {
			alert("新密码和确认密码不相同！");
			return;
		}

		Ext.Ajax.request({
			url : 'system/changePwd.json',
			params : {
				password : userPwd,
				oldPwd:oldPwd
			},
			method : 'POST',
			callback : function(options, success, response) {
				if (Ext.JSON.decode(response.responseText).success==true)
					login(options, success, response);
				else {
					alert(Ext.JSON.decode(response.responseText).message);
				}
			}
		});

	}
</script>
</head>
<body>
	<div class="pass_icon" style="width: 460px; margin: 0 auto;">
		<div class="tit_p">
			<span>修改密码</span>
		</div>
		<div class="pass_c">
			<ul>
				<li><span>旧密码：</span><input id="oldPwd" name="oldPwd"
					type="password" class="tex" /></li>
				<li><span>新密码：</span><input id="newPwd" name="newPwd"
					type="password" class="tex" /></li>
				<li><span>确认新密码：</span><input id="confirmPwd" name="confirmPwd"
					type="password" class="tex" /></li>


			</ul>
			<div class="op_area">
				<input type="button" value="确定" onclick="return onLogin()" /><input
					type="button" value="关闭" onclick="window.close()" />
			</div>
		</div>

	</div>
</body>
</html>