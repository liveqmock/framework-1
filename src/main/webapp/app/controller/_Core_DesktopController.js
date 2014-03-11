/**
 * 桌面图标控制器
 */
Ext.define("Wms.controller._Core_DesktopController",{
    extend:"Ext.app.Controller",



    //创建选择器 ，主要是在事件函数中使用，用于直接获取要操作视图组件对象。
    refs:[
        // 根据ref属性自动生成一个 getXxxxx()方法，即自动附加get并使首字母大写。值为自定义值
        //  如下ref属性值为 desktop，则自动生一个getDesktop()方法，此方法使用selector定义的值作为选择器，来获取相应的对象(组件，dom，Ext.Element)。
        // selector的值可以是，ID名，类名，视图别名。如  "#wmsDesktop'  ,  '.ux-desktop-shortcut'  ,   'desktopview'

        //桌面视图选择器，selector为桌面视图组件的别名
        {
            ref:"desktop",
            selector:"desktopview"
        },

        //桌面图标视图组件选择器
        {
            ref:"shortcutImg",
            selector:"shortcutimg"
        },

        //开始菜单视图组件
        {
           ref:"startMenu",
            selector:"startmenu"
        }

        //快速启动栏 ，selector为ID选择器 ，(快速启动栏没有独立出来，做为一个单独视图组件来定义，而是直接定义在了taskbar组件内。定义时添加了一个id属性，方便获取)
//        {
//            ref:"quickStart",
//            selector:"#quickStart"
//        }
    ],



    //控制器内要使用的数据
    //数据会自动去加载对象的模型，桌面控制器在onLaunch()方法中需要加载桌面图标数据，用于桌面图标的显示，开始菜单，与快速启动栏的处理。
    stores: ["_Core_ShortcutStore"],



    //控制器要使用的视图组件，一般来说，要使用某个视图组件，那么必须先加载这个视图组件，
    // 但这里，我为了防止，加载桌面时间过长，视图组件的加载放在了，对应的控制器内才加载，而对应的控制器会在触发了相应的事件后才会去加载与初始化。
  // views:["_news.NewsAddEdit","_user.View"],


   //controllers:["_NewsController","_DemoController","_UserController"],


    //自定义属性 ，用于关联的业务处理==========================================
    shortcutDataCache:null,
    shortDataView:null,
    curSignInUser:null,




   //初始化控制器，事件注册通常在这里

    init:function(){
        //console.log("_Core_DesktopController.init()   控制器初始化+++++++++++++++++++++++++++++");

        //this.control()方法，所有事件注册都应写此方法内，
        this.control({

            //桌面图标----注册itemclick点击事件
            'shortcutimg':{
                itemclick: this.onShortcutItemClick ,
                resize:this.initShortcut
            },


            //开始菜单面板，注册activate激活桌面事件-----显示开始菜单时，才添加菜单项 single:true，指此方法只会调用一次。
            'startmenu':{
                activate:{
                    single: true,
                    fn:this.onStartMenuActivate
                }
            },


            //开始菜单中退出按钮注册click事件
            'startmenu button[action=logout]':{
                click:this.onLogout
            },
            'startmenu button[action=changePwd]':{
                click:this.changePwd
            }

        });

    },

    //在主视图渲染之后，马上要处理的内容。
    onLaunch: function() {
        var me=this;
        //console.log("_Core_DesktopController.onLaunch()  在主视图渲染之后要做的事++++++++++++++++++++++++++=");

        //检测用户是否已登录 ，未登录跳转回登录页
        me.curSignInUser=me.getSignInUserInfo();
         if(me.curSignInUser && me.curSignInUser.hasOwnProperty("online")){
             if(!me.curSignInUser.online){
                 window.location="system/loginOut.html";
             }
         }





        //console.log("2、加载桌面图标数据 _Core_ShortcutStore----在视图渲染之后");
         var shortcutStore=me.get_Core_ShortcutStoreStore().load({
             callback:  function(records){

                 //快速启动栏，添加内容
                 //console.log("3、加载快速启动栏内容项");
                 //me.addQuickStartButton();

                  //桌面图标位置计算。

                 //给全局变量赋值
                 Wms.menuStores=shortcutStore;

                 //将图标数据与图标视图缓存起来，方便在其它地方使用
                 me.shortcutDataCache=shortcutStore.data;
                 me.shortDataView=me.getShortcutImg();

                 //var tubiao=me.initShortcut();
                 //解决IE8图标不正确计算的问题
                 setTimeout(function(){
                     me.initShortcut();
                 },50);

             },
             scope: this
         });
    },



    //获取登录用户信息，检测用户登录状态
    //在signIn.html页面登录成功后，假设服务器端在跳转到index.html页面时，将用户信息直接写入了 index.html文件中定义的Wms.curUser对象中，
    getSignInUserInfo:function(){
        return   Wms.curUser || null;
    },



    /*//在快速启动栏中添加内容，
    addQuickStartButton:function(){
        var me=this;
        var list=this.shortcutDataCache.items;

        if(list){
            var len=list.length;
            var quickStart=this.getQuickStart();

            //遍历数据，决定是否添加为开始菜单项
            for(var i=0;i<len;i++){
                var tmp=list[i]
                var dataObj=tmp.data;


                if(dataObj["isAddToQuickStar"]=="yes"){
                    //  console.log(dataObj);
                    var x=quickStart.add({
                        tooltip: { text: dataObj["name"], align: 'bl-tl' },
                        overflowText: dataObj["name"],
                        iconCls: dataObj["smallIconCls"]
                    });

                    //注册click事件
                    (function(record){
                        x.on("click",function(){
                            me.onShortcutItemClick(this.shortDataView,record)
                        });
                    })(tmp);

                }
            }
        }

    },*/




    //点击快捷图标时，首先根据控制器名称，(如果有)，来初始化其控制器。创建一个弹窗，然后在弹窗内加载这个视图。
    onShortcutItemClick: function (dataView, record) {
        //console.log("\r\nSTART : onShortcutItemClick()开始   点击了桌面上的图标触发========================================");


        //record为图标数据，通过record.widgetName来加载对应的视图。
        var data= record.data;
        //console.log(data);

        Wms.menuInfo=data;
       //如果在Application中已加载了控制器，则此处代码不需要
       // 如果在Application中只加载了桌面控制器，则启用下的代码，手动加载控制器  ，控制器并初始化。注意控制器内的onLaunch()需要手动调用
        if(data.controller){
            //确保只让控制器init()一次
            if(!data.hasOwnProperty("controllerIsInit")){
                this.application.getController(data.controller).onLaunch();
                data.controllerIsInit=true;
            }
        }


        //获取到桌面视图组件对象----此对象内包含了所有针窗口操作的方法，如右键菜单功能
        var desktop=this.getDesktop();

        //开始处理弹出窗口
        var win=null;

        //一个视图可以多个弹窗中使用。但要求一个弹窗只的某个视图只能出现一次。
        //先判断有没有这个窗体，有在到这个窗体里去找这个视图。
        //console.log("先尝试在desktop.windows集合中获取弹窗对象 ，如果没有则创建新的，有则不需要创建");
        win= desktop.windows.get(data.widgetName);



        //窗口不存在，创建新窗口，创建任务栏窗口。
        if(!win){
            //console.log("1、-------开始用  Ext.window.Window来创建窗口组件，设置窗口的默认参数。")
            var isViewLoad=true;
           var view=null;
            try{
               view= Ext.widget(data.widgetName);
            }catch(err){
                if(err){
                    isViewLoad=false;
                    Ext.MessageBox.alert({
                        title: '错误提示',
                        msg: '一、别名为：<b>'+data.widgetName+'</b> 视图组件未加载或该组件内的子组件未加载。<br />' +
                            '<span style="color: #f00"> 解决方法：</span><br />1、如有控制器使用此视图，先初始化控制器，<br /> <i>初始化控制器：this.application.getController("name").onLaunch()</i><br />' +
                            '2、如没有控制器，应在DesktopContorller控制器的views属性内添加此视图。'
                            +'<br /><br />二、视图组件中需要使用数据的Store类名不正确。请检查视图组件中的数据名称是store文件下的store类名对应<br/>'+
                            '异常信息：'+err,
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.ERROR
                    });
                }
                //console.log(err);
            }
           if(!isViewLoad){return;}

           view.height="500px";
           view.width="1000px";
           
            //创建窗体，合并窗体配置参数
            var cfg=Ext.apply(this.getWindowDefaultConfig()||{},{
                title:data.name,
                iconCls:data.smallIconCls,
                //id:"win-"+data.widgetName,
                items:[
                   view
                ]
            }) ;
            //新创建的窗口为桌面视图的一个组件，防止窗口最大化，隐藏任务栏
            win=desktop.add(Ext.create("Ext.window.Window",cfg));

            //console.log("2、------将新创建的窗口添加入me.windows集合中与所使用的视图添加进视图集合中")
            desktop.windows.add(data.widgetName,win);



            //console.log("3、-----在任务栏上添加一个按钮")
            win.taskButton = desktop.taskbar.addTaskButton(win);
            win.animateTarget = win.taskButton.el;



            //console.log("4、----给新窗口注册各类事件")
            win.on({
                activate: desktop.updateActiveWindow,
                beforeshow: desktop.updateActiveWindow,
                deactivate: desktop.updateActiveWindow,
                minimize: desktop.minimizeWindow,
                destroy: desktop.onWindowClose,
                scope: desktop
            });

            //容器准备好了之后的处理
            win.on({
                boxready: function () {
                    win.dd.xTickSize = desktop.xTickSize;
                    win.dd.yTickSize = desktop.yTickSize;

                    if (win.resizer) {
                        win.resizer.widthIncrement = desktop.xTickSize;
                        win.resizer.heightIncrement = desktop.yTickSize;
                    }
                },
                single: true
            });

            // 以渐隐方式关闭窗口。替换直接隐藏窗口的方式
            win.doClose = function ()  {
                win.doClose = Ext.emptyFn; // dblclick can call again...
                win.el.disableShadow();
                win.el.fadeOut({
                    listeners: {
                        afteranimate: function () {
                            win.destroy();
                        }
                    }
                });
            };
        }


        //显示窗口
        desktop.restoreWindow(win);
        //console.log("END : onShortcutItemClick() ========================================\r\n");
    },

    //返回弹出窗口默认参数对象
    getWindowDefaultConfig:function(){
        return{
            layout:"fit",
            width:"80%",
            height:"90%",
            stateful: false,
            isWindow: true,
            constrainHeader: true,
            minimizable: true,
            maximizable: true,
            items:[]
        }
    },






    //在开始菜单内添加内容 ，根据图标数据中的项isAddStarMenu-----确定是否要添加入开始菜单中
    onStartMenuActivate:  function(){
        //console.log("添加开始菜单内的菜单项");

        var me=this,
            list=null,
            tmp=null,
            dataObj=null,
            startLeftMenu=null,
            startRightMenuList=null,
            len=0;

        this.getStartMenu().setTitle(this.curSignInUser.userName);
        //开始菜单右边
         startRightMenuList=this.getStartMenu().getComponent("startRightMenuList");

        startRightMenuList.add({
       	 text:'修改密码',
         iconCls:'logout',
         action:"changePwd"
    },{
            text:'退出',
            iconCls:'logout',
            action:"logout"
        });


        //开始菜单左边
        if(this.shortcutDataCache && this.shortcutDataCache.hasOwnProperty("items")){
            list=this.shortcutDataCache.items;
          //console.log(list)
        }

        if(list){
             len=list.length;
             startLeftMenu=this.getStartMenu().getComponent("startLeftMenuList");
            //遍历数据，决定是否添加为开始菜单项
            for(var i=0;i<len;i++){
                tmp=list[i];
                dataObj=tmp.data;
               if(dataObj["isAddToStarMenu"]=="yes"){
                   //console.log(dataObj);
                   var x=startLeftMenu.add({
                     text: dataObj["name"],
                     iconCls:dataObj["smallIconCls"],
                     cls:"start_menu",
                     defaults: {
                       color:"#fff"
                     }
                 });

                    //给开始菜单注册click事件
                   (function(record){
                       x.on("click",function(){
                           me.onShortcutItemClick(this.shortDataView,record)
                       });
                   })(tmp);

               }
            }
        }

    },

    //退出后台
    onLogout:function(){
        Ext.MessageBox.confirm('提示', '确定退出后台吗?', function(btn){
        	if(btn=="yes"){
	        	if (Ext.isIE){
	               window.location="loginOut.html";
	        	}else{
	        		window.location="system/loginOut.html";
	        	}
        	 }
        });
    },
    
    changePwd:function(){
    	var src ="system/doChangePwd.html";
    	if (Ext.isIE){
            window.location="doChangePwd.html";
     	}
    	showModalDialog(src, "修改密码", "status:no;resizable=no"); 
    },

    //桌面图标换行处理
    initShortcut :function(){
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


