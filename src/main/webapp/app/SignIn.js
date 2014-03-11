/**
 * 登录窗口
 */
Ext.define("Wms.view.signin.SignIn",{
    extend : 'Ext.window.Window',
    id:'signInView',

    //窗口配置
    title : '后台管理系统',
    hideMode: 'offsets',
    closeAction: 'hide',
    width : 400,
    height : 318,
    resizable : false,
    draggable : false,
    layout : 'fit',
    bodyStyle : 'background:#ecf0f1;',
    plain : true,
    closable : false,
    singleton:true,
    modal:false,
    currentTabIndex:0, 


    initComponent : function() {
        //console.log("初始化登录")
        var me = this;
        me.form = me.createForm();
        me.items=[
            me.form
        ];

        //添加show事件
        me.on("show",function(){
            me.onReset();
        },me);

        //初始化登录框。
        me.callParent();
    },

    //创建一个表单
    createForm : function() {
        var me = this;
        var fp=Ext.create("Ext.form.FormPanel",{
            id:'signInForm',
            layout:"absolute",
            border:false,
            bodyStyle:"padding:5px; backgroudn:#ffc",
            defaultType: 'textfield',
            defaults:{
                height:36,
                width:318
            },
           
             listeners: {  
        		afterRender: function(thisForm, options){  
            		this.keyNav = Ext.create('Ext.util.KeyNav', this.el, {  
                	enter: function(){  
                   		onLogin();
                	},  
                	scope: this  
            	});  
        		}  
    		},  
            items:[
                //用户名
                {
                    id:"userName",
                    name:"userName",
                    //value:"admin",
                    allowBlank: false,
                    x: 40,
                    y: 30,
                    anchor: '-40',
                    fieldStyle:"height:30px;line-height:30px;"
                },

                //密码
                {
                    id:"userPwd",
                    name:"password",
                    inputType:"password",
                    //emptyText:"密码任意",
                    allowBlank : false ,
                    x:40,
                    y: 105,
                    anchor: '-40',
                    fieldStyle:"height:30px;line-height:30px;"
                },
                //登录按钮
                {
                    id : 'btnOnSignIn',
                    xtype:"button",
                    text : '登 录',
                    handler : me.onLogin,
                    x:40,
                    y: 185,
                    anchor: '-40'
                }
            ]

        });
        return fp;
    },


    //组件渲染后开始初始化事件，这里的作用是。Enter键监控
    initEvents:function(){
        var me=this;
        me.KeyNav=Ext.create("Ext.util.KeyNav",me.form.getEl(),{
            enter:me.onFocus,
            scope:me
        });
    },
    



    //登录按钮事件
    onLogin : function() {
    	 
        var f=Ext.getCmp("signInForm");
        //验证通过，提交表单
        if(f.isValid()){
            var mask = new Ext.LoadMask(Ext.getBody(), {msg:"登录中..."});
            var login=function(options,success,response){
                if(Ext.JSON.decode(response.responseText).success==true){
                    mask.hide();
                    Wms.view.signin.SignIn.close();
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
                    Ext.MessageBox.alert('失败',  Ext.JSON.decode(response.responseText).message);
                    mask.hide();
                }
            };
            
            Ext.Ajax.request({
                url: 'system/login.json',
                params: {  
                           userName:Ext.getCmp('userName').getValue(),  
                           password:Ext.getCmp('userPwd').getValue()  
                       	},
                method: 'POST',
                callback : function(options,success,response) {
                    if(success)
                        login(options,success,response);
                    else{
                        Ext.MessageBox.alert('失败',"登录失败，服务器异常");
                        mask.hide();
                    }
				}
            });

           


/*            setTimeout(function(){
                mask.hide();
                Wms.view.signin.SignIn.close();
                window.location="demo";
                Ext.state.Manager.set("hasLogin","true");
            },1000)*/

        }else{

           var userName = Ext.getCmp('userName').getValue();
			var password = Ext.getCmp('userPwd').getValue();
			if(userName == ''){
				Ext.MessageBox.show({
                title: '提示',
                msg: '用户名不能为空',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            	});
            	return ;
			}else if(password == '') {
				 Ext.MessageBox.show({
                title: '提示',
                msg: '密码不能为空',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            	});
            	return;
			}

        }
    },

    
    //重置按钮事件
    onReset : function() {
        var me = this;
        me.form.form.reset();
        //alert("reset");
        // 默认焦点
        var f = Ext.getCmp('userName');
        f.focus(true, 100);
    }

});

    //登录按钮事件
    var onLogin=function() {
    	 
        var f=Ext.getCmp("signInForm");
        //验证通过，提交表单
        if(f.isValid()){
            var mask = new Ext.LoadMask(Ext.getBody(), {msg:"登录中..."});
            var login=function(options,success,response){
                if(Ext.JSON.decode(response.responseText).success==true){
                    mask.hide();
                    Wms.view.signin.SignIn.close();
                    if (Ext.isIE){
                        window.location="center.html";
                    }else {
                        window.location="system/center.html";
                    }
                }else{
                    Ext.MessageBox.alert('失败',  Ext.JSON.decode(response.responseText).message);
                    mask.hide();
                }
            };
            
            Ext.Ajax.request({
                url: 'system/login.json',
                params: {  
                           userName:Ext.getCmp('userName').getValue(),  
                           password:Ext.getCmp('userPwd').getValue()  
                       	},
                method: 'POST',
                callback : function(options,success,response) {
                    if(success)
                        login(options,success,response);
                    else{
                        Ext.MessageBox.alert('失败',"登录失败，服务器异常");
                        mask.hide();
                    }
				}
            });

           


/*            setTimeout(function(){
                mask.hide();
                Wms.view.signin.SignIn.close();
                window.location="demo";
                Ext.state.Manager.set("hasLogin","true");
            },1000)*/

        }else{

           var userName = Ext.getCmp('userName').getValue();
			var password = Ext.getCmp('userPwd').getValue();
			if(userName == ''){
				Ext.MessageBox.show({
                title: '提示',
                msg: '用户名不能为空',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            	});
            	return ;
			}else if(password == '') {
				 Ext.MessageBox.show({
                title: '提示',
                msg: '密码不能为空',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            	});
            	return;
			}

        }
    }

