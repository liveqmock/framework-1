<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>修改密码</title>
<!--动态加载js，引导文件-->
<script type="text/javascript" src="<%=basePath%>lib/ext/bootstrap.js"></script>
<!--语言本地化-->
<script type="text/javascript" src="<%=basePath%>lib/ext/ext-lang-zh_CN.js"></script>
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
	background: url(<%=basePath%>resources/images/framework/btn.jpg) center center;
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

function formReset(){
	document.getElementById('oldPwd').value="";
	document.getElementById('newPwd').value="";
	document.getElementById('confirmPwd').value="";
}

function login(options,success,response){
    if(Ext.JSON.decode(response.responseText).success==true){
        if (Ext.isIE){
            window.location="center.html";
        	//window.open("center.html","","fullscreen=1,menubar=0,toolbar=0,directories=0,location=0,status=0,scrollbars=0");
        	//window.open ('center.html','FullWnd','fullscreen,scrollbars=no')
        }else {
        	 //window.open("system/center.html","","fullscreen=1,menubar=0,toolbar=0,directories=0,location=0,status=0,scrollbars=0");
        	 window.location="system/center.html";
        	 //window.open ('system/center.html','FullWnd','fullscreen,scrollbars=no')
        }
    }else{
       alert(Ext.JSON.decode(response.responseText).message);
    }
}

function changePwd(){
	var oldPwd = document.getElementById('oldPwd').value;
	 var newPwd = document.getElementById('newPwd').value;
	 var confirmPwd = document.getElementById('confirmPwd').value;
	
	 if (oldPwd=="") {
			alert('请输入密码!');
			document.getElementById('newPwd').focus();
			return;
	}
	 if (newPwd=="") {
			alert('请输入新密码!');
			document.getElementById('newPwd').focus();
			return;
	}
	if (confirmPwd=="") {
			alert('请输入确认密码!');
			document.getElementById('confirmPwd').focus();
			return;
	}
	if (confirmPwd != newPwd) {
			alert('两次密码输入不一样,请重新输入!');
			return;
	}
	if (oldPwd == newPwd) {
		alert('修改前密码和修改后密码不能相同,请重新输入!');
		return;
	}
	
	//修改密码
	Ext.Ajax.request({
       url: 'system/doLoginFirst.json',
       params: {  
       		      userName:Ext.util.Cookies.get("fUserName"),
       		      oldPwd:oldPwd,
                  newPwd:newPwd
              	},
       method: 'POST',
       callback : function(options,success,response) {
    	   if(Ext.JSON.decode(response.responseText).success==true)//修改密码成功
           {
           	Ext.Ajax.request({
                   url: 'system/login.json',
                   params: {  
                              userName:Ext.util.Cookies.get("fUserName"),
                              password:newPwd
                          	},
                   method: 'POST',
                   callback : function(options,success,response) {
                       if(Ext.JSON.decode(response.responseText).success==true){
                           login(options,success,response);
                       }else{
                          alert(Ext.JSON.decode(response.responseText).message);
                       }
      				}
               });
           }else{
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
			<span>修改密码</span><em>为了保证您的账户安全，第一次登录用户需修改密码。</em>
		</div>

		<div class="pass_c">
			<ul>
				<li><span>原密码：</span><input id="oldPwd" name="oldPwd" type="password" class="tex" /></li>
				<li><span>新密码：</span><input id="newPwd" name="newPwd" type="password" class="tex" /></li>
				<li><span>确认新密码：</span><input id="confirmPwd" name="confirmPwd" type="password" class="tex" /></li>


			</ul>
			<div class="op_area">
				<input type="button" value="确定" onclick="changePwd()"/><input type="button" value="重置" onclick="formReset()"/>
			</div>
		</div>

	</div>
</body>
</html>
<script>
</script>
