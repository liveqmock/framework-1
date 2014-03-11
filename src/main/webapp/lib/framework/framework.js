var flag = true;
		/* document.onkeydown=function(e){
			  e=e||window.event;
			  if(e.keyCode==116){
			       flag = false;
			  }
		} */
		
		document.onkeydown=function(e){
			 e=e||window.event;
			 if (e.keyCode == 8) {//backspace
				    var t = document.activeElement.type;
		            if (t == "password" || t == "text" || t == "textarea") {
		                if (document.activeElement.readOnly == false)
		                    return true;
		     }
		     return false;
		    }
			if(e.keyCode==116){//刷新
				 flag = false;
			}
		}
 		
		function delCookie() 
		{ 	
			if(Ext.isIE)
			{
				var n = window.event.screenX - window.screenLeft;   
	            var b = n > document.documentElement.scrollWidth-20;   
	            if(b && window.event.clientY < 0 || window.event.altKey)   
	            {   
	    			Ext.util.Cookies.set('uid',"");
	            }   
			}else {
				if(flag){
				    window.event.returnValue="";
				    Ext.util.Cookies.set('uid',"");
				}
			}
		} 
         Ext.Loader.setConfig({
             //开启动态加载js文件功能，
             enabled: true,

             //动态载入js文件不附加随机数，即启用js文件缓存。调试时设置为false，否则firebug中打断点会无效。
             disableCaching: true,

             //载入类路径设置。
             paths:{
                 //应用名称对应的路径
                 'Wms': 'app',

                 //针对Ext本身的一些功能扩展类存放路径 ，
                 // 扩展类应定义为  Ext.define('Ext.ux.ClassName'{.......})，并创建ClassName.js文件放在lib/ux/目录内
                 'Ext.ux':'lib/ux'
		//'Wms.Application':'/app/Application.js'

		}
	});

	//初始化tips 提示框，
	Ext.QuickTips.init();

	//设置表单错误消息提示方式。
	Ext.form.Field.prototype.msgTarget = 'side';

	//创建命名空间
	Ext.ns("Wms");

	//预先载入Wms.Application，如果有其它自定义的类要使用，也应该以此方式   Ext.require('myClass');，在这里先载入
	Ext.require('Wms.Application');
	
	Ext.require('Ext.ux.Button');

    //桌面数据源，可以用来刷新桌面
    Wms.menuStores = {};

    Ext.define('_Dictionary', {
    extend: 'Ext.data.Model',
    fields: [
    {name: 'id', mapping:'codeId' ,type: 'string'},
    {name: 'text', mapping:'codeName', type: 'string'}
    ]
    });

    //获取字典数据
    Wms.Dictionary={
    	fn:function(codeType){


        var myStore = Ext.create('Ext.data.Store',{
        model: '_Dictionary',
        proxy: new Ext.data.HttpProxy({
        type: 'ajax',
        url:'dictionary/read.json',
        reader: {
        type: 'json',
        root: 'resultList'
        }
        }),
    initComponent: function(){
    myStore.superclass.initComponent.call(this);
    }
        });
        myStore.getProxy().extraParams={codeType:codeType};
        return myStore;
    	}
    };

    //刷新桌面
    Wms.refresh = {
        fn:function(){
            Wms.menuStores.load({
                callback:function(){
                	var btnHeight =64,
                    btnWidth =104,
                    cbtnPadding =16,
                    rbtnPadding=26,
                    col ={index :1,x : cbtnPadding},
                    row ={index :1,y : rbtnPadding},
                    bottom,
                    numberOfItems = 0,
                    taskBarHeight = Ext.query(".ux-taskbar")[0].clientHeight,
                    bodyHeight = Ext.getBody().getHeight()- taskBarHeight,
                    items = Ext.query(".ux-desktop-shortcut");


                for(var i =0, len = items.length; i < len; i++){
                    numberOfItems +=1;
                    bottom = row.y + btnHeight;
                    if(((bodyHeight < bottom)?true:false)&& bottom >(btnHeight + cbtnPadding)){
                        numberOfItems =0;
                        col ={index : col.index++,x : col.x + btnWidth + cbtnPadding};
                        row ={index :1,y : rbtnPadding};
                    }

                    Ext.fly(items[i]).setXY([col.x, row.y]);
                    row.index++;
                    row.y = row.y + btnHeight+rbtnPadding;

                }
                }
            });
        }
    };

	//全局动态变量，保存菜单信息
	Wms.menuInfo = {};

	//全局的方法，用来配置菜单项
	Wms.menuShow = {
		fn:function(menuInfo) {
			if (menuInfo == null || menuInfo == "") {
				return;
			}
			var menu = [];
			for ( var i = 0; i < menuInfo.length; i++) {
				//如果有子菜单添加子菜单
				if (menuInfo[i].menuItems!=null&&menuInfo[i].menuItems.length > 0) {
					menu.push({
						text : menuInfo[i].menuName,//菜单名
						action : menuInfo[i].menuAction,//菜单请求
						menu : Wms.menuShow.fn(menuInfo[i].menuItems)//子菜单
					});
				} else {
					menu.push({
						text : menuInfo[i].menuName,//菜单名
						action : menuInfo[i].menuAction,//菜单请求
						menuInfo:menuInfo[i],
						listeners :{
                            'mouseover':function(){
                                Wms.menuInfo = this.menuInfo;
                            },
                            'click':function(){
                                Wms.menuInfo = this.menuInfo;
                            }
						}
					});
				}
			}
			return menu;
		}
	};
	
    Wms.getOperates = {
        fn:function(object) {
            if(object==null||object.length==0){
                return null;
            }
            var b=[];
            for(var i=0;i<object.length;i++){
            	var btn;
            
           		btn={
                           text:object[i].opName,
                           disabled:object[i].opIsBtn==0?true:false,
                           action:object[i].opAction
                       };
           	
                b.push(btn);
            }
            return b;
        }
    };
	function init() {
		var base64 = new Base64();
		var userName;
		if(Ext.util.Cookies.get('userName')!=null){
			userName = base64.decode(Ext.util.Cookies.get('userName'));
		}
		return userName;
	}
	
	function getTree(menuInfo){
	    if (menuInfo == null || menuInfo == "") {
	        return;
	    }
	    var tree = [];
	    for ( var i = 0; i < menuInfo.length; i++) {
	        //如果有子菜单添加子菜单
	        if (menuInfo[i].menuItems!=null&&menuInfo[i].menuItems.length > 0) {
	            tree.push({
	                text : menuInfo[i].menuName,//菜单名
	                expanded:true,
	                children :getTree(menuInfo[i].menuItems)//子菜单
	            });
	        } else {
	            tree.push({
	                text : menuInfo[i].menuName,//菜单名
	                id : menuInfo[i].menuAction,//菜单请求
	                expanded:true,
	                children : getTree(menuInfo[i].menuItems)//子菜单
	            });
	        }
	    }
	    return tree;
	}

	function setMenuInfo(menuInfo,action){
	    if (menuInfo == null || menuInfo == "") {
	        return;
	    }
	    for ( var i = 0; i < menuInfo.length; i++) {
	        if (menuInfo[i].menuItems!=null&&menuInfo[i].menuItems.length > 0) {
	            setMenuInfo(menuInfo[i].menuItems,action)
	        } else {
	            if(menuInfo[i].menuAction==action){
	                Wms.menuInfo=menuInfo[i];
	            }
	        }
	    }
	}
	Wms.showWelcomePanel=function(panel,me,method){
		var tree = Wms.treeShow.fn(Wms.menuInfo.submenu,me,method);
        var mypanel = new Ext.panel.Panel({
            title: "欢迎页面",
            resizeTabs: true,
            enableTabScroll: true,
            activeTab: 0,
            iconCls: "demoSmall",
            items: [
                {
                    baseCls:'maintree',
                    xtype: 'fieldset',
                    title: Wms.menuInfo.name,
                    anchor: '100%',
                    style: {
                    background:"#fff",
                    border: "0px solid #e8e8e8"
                    },
                    items:tree
                }
            ]

        });
        panel.add(mypanel);
	}
	Wms.treeShow = {
	    fn:function(menuInfo,me,method) {
	        var tree=getTree(menuInfo);
	        var length=tree.length;
	        var rowitem=[];
	        var colitem=[];
	        var col=4;//列数
	        var row=length/4;//行数
	        for(var i=0;i<row;i++){
	            for(var j=0;j<col;j++){
	                rowitem.push(
	                    {
	                        xtype: 'container',
	                        flex: 1,
	                        layout: 'anchor',
	                        bodyPadding:5,
	                        defaults:{
	                            margin:"0 10 10 0"
	                        },
	                        items: [
	                            new Ext.tree.Panel({
	                                rootVisible: false,
	                                border:false,
	                                store:Ext.create('Ext.data.TreeStore',{
	                                    fields:["id","text"],
	                                    root:{
	                                        text:"根节点",
	                                        expanded:true,
	                                        children:tree[i*4+j]
	                                    }
	                                }),
	                                listeners:{
	                                    itemclick:function(item,re){
	                                        setMenuInfo(Wms.menuInfo.submenu,re.data.id);
	                                        var btn={action:re.data.id,text:re.data.text,menuInfo:Wms.menuInfo};
	                                        if(method){
	                                        	me[method](btn)
	                                        	//me.onShowMenuTabPanel(btn);
	                                        }else{
	                                        	me.onShowMenuTabPanel(btn);
	                                        }
	                                    }
	                                }
	                            })
	                        ]
	                    });
	                if(length<=(i*4+j+1)){
	                    break;
	                }
	            }
	            //填充列
	            colitem.push({
	                xtype: 'container',
	                anchor: '100%',
	                layout: 'hbox',
	                items:rowitem
	            });
	            rowitem=[];
	        }
	        return colitem;
	    }
	};
	//创建一个用户对象，用于存储已登录的用户信息，此对象可以在服务端写入，
	// 此处为了方便Demo开发，直接写入一个User对象
	Wms.curUser = {
		userName :init(),
		signInTime : new Date().getDate(),
		roles : 0,
		online : true,
		locked : false,
		authority : [
		//新闻中心菜单项
		{
			type : "newsCenter",
			menu : [ {
				menuName : "newsMgr",
				add : true,
				edit : true,
				del : true
			}, {
				menuName : "newsTypeMgr",
				add : true,
				edit : true,
				del : false
			} ]
		},

		//Demo菜单项
		{
			type : "demo",
			menu : [ {
				menuName : "file",
				create : true,
				open : true,
				exit : true
			}, {
				menuName : "edit",
				copy : true,
				cut : true,
				paste : true,
				search : true
			} ]
		}

		]
	};

	//dom ready 后启动应用，即显示应用界面。实例化 Wms.Application类
	Ext.onReady(function() {
		init();
		Ext.create("Wms.Application");
	});
	
	Ext.Ajax.on('requestcomplete',checkUserSessionStatus, this);    
	function checkUserSessionStatus(conn,response,options){  
	var obj = Ext.decode(response.responseText);
		if(obj.message == '您没有权限访问该功能'||obj.message=='系统异常，操作失败！'){   
			//alert(obj.message);
			if (Ext.isIE){
	            window.location="loginOut.html";
	        }else {
	            window.location="system/loginOut.html";
	        } 
	        delCookie();
		} 
	}
	
	 //编辑器全局配置对象
    Wms.tinyCfg1 = {
        // 基本选项---语言，皮肤，行高等
        theme:"advanced",  language:"zh",   skin:"extjs",    inlinepopups_skin: "extjs", inline_styles: true,  theme_advanced_row_height: 27,
        convert_urls : false,  relative_urls :true,   media_use_script:true,

        //要添加那些插件
        plugins:"autolink,lists,pagebreak,style,layer,table,advhr,advimage,advlink,inlinepopups,media,searchreplace,contextmenu,paste,directionality,noneditable,visualchars,nonbreaking,advlist,imagemanager",

        // 样式选项，用来设置显示那些操作按钮
        theme_advanced_buttons1: "bold,italic,underline,strikethrough,|,justifyleft,justifycenter,justifyright,justifyfull,formatselect,fontselect,fontsizeselect",
        theme_advanced_buttons2:"cut,copy,paste,pastetext,pasteword,|,search,replace,|,bullist,numlist,|,outdent,indent,blockquote,|,undo,redo,|,link,unlink,anchor,image,cleanup,code,|,forecolor,backcolor",
        theme_advanced_buttons3:"tablecontrols,|,hr,removeformat,visualaid,|,sub,sup,|,charmap,media,advhr,|,ltr,rtl,|,imagemanager",
        theme_advanced_toolbar_location: "top", theme_advanced_toolbar_align:"left", theme_advanced_statusbar_location: "bottom",

        // Drop lists for link/image/media/template dialogs
        template_external_list_url : "js/template_list.js",
        external_link_list_url : "js/link_list.js",
        external_image_list_url : "js/image_list.js",
        media_external_list_url : "js/media_list.js",

        // Replace values for the template plugin
        template_replace_values : {  username : "Some User",  staffid : "991234" }
    };