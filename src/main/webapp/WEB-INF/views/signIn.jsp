<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
<link href="<%=basePath%>resources/css/reset-min.css" rel="stylesheet" type="text/css">
<link href="<%=basePath%>resources/css/login.css" rel="stylesheet" type="text/css">
<!--动态加载js，引导文件-->
<script type="text/javascript" src="<%=basePath%>lib/ext/bootstrap.js"></script>
<!--语言本地化-->
<script type="text/javascript" src="<%=basePath%>lib/ext/ext-lang-zh_CN.js"></script>
<script type="text/javascript">

   document.onkeydown = function(evt){  
		    evt = window.event || evt;  
		    if(evt.keyCode==13){//如果取到的键值是回车  
		    	onLogin();
		    }            
	}

	 //window.document.onkeydown=submitOrHidden;//当有键按下时执行函数 

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
	
	function onLogin(){
   		 var usrName = document.getElementById('userName').value;
		 var userPwd = document.getElementById('password').value;
		//检查用户是否是第一次登陆
		Ext.Ajax.request({
             url: 'system/validateLoginFirst.json',
             params: {  
                        userName: usrName,
                        password:userPwd
                    	},
             method: 'POST',
             callback : function(options,success,response) {
                 if(Ext.JSON.decode(response.responseText).success==true)//是第一次登陆
                 {
                	 //Ext.util.Cookies.get("userName")
                	 Ext.util.Cookies.set("fUserName",usrName);
                	 window.location="system/changePwdFirstLogin.html";
                 }else if(Ext.JSON.decode(response.responseText).success == false){
            		 Ext.Ajax.request({
                         url: 'system/login.json',
                         params: {  
                                    userName: usrName,
                                    password:userPwd
                                	},
                         method: 'POST',
                         callback : function(options,success,response) {
                             if(success)
                                 login(options,success,response);
                             else{
                                alert("登录失败，服务器异常");
                             }
            				}
                     });
                 }else{
                    alert("登录失败，服务器异常");
                 }
			}
         });
		
	}
	
</script>
</head>

<body>

<div class="l_box">
<div class="l_contain">
<h1><span class="logo"></span><span class="sub_logo">FRAMEWORK</span></h1>
<div class="login_tit">登录</div><!--login_tit-->
<div class="login_box">
<form action="" method="get">
<div class="l_context">
   <div class="input input_1"><span>用户名：</span><input id="userName" name="userName" type="text" class="text"></div>
   <div class="input input_2"><span>密　码：</span><input id="password" name="password" type="password" class="text"></div>
 </div>  
   <input type="button" value="" onclick="onLogin()" class="login_btn">
   
</form>
</div>
</div><!--l_contain-->

</div><!--login_box-->