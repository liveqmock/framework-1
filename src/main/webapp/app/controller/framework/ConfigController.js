//用户控制器
Ext.define('Wms.controller.framework.ConfigController', {
    extend:'Ext.app.Controller',

    //添加模型与数据与视图
    models: [  'framework.config.ConfigListModel'  ],
    stores: [ 'framework.config.ConfigListStore'],
    views: [ 'framework.config.View','framework.config.ConfigList','framework.config.ConfigAdd' ],

//选择器配置项
    refs: [
        { ref: "View", selector: "configview"},
        
        { ref: "ConfigList", selector: "configlist"},

        { ref:"ConfigTabPanel", selector:"configtabpanel"},

        { ref: "addConfig",selector: "#configaddbutton" },
        
        { ref: "editConfig",selector: "#configeditbutton" },

        { ref: "delConfig",selector: "#cofigdelbutton" }
    ],

    //自定义属义------------------------------------------
    //创建一个已打开的Panels集合，所以已打开的面板都会以key---value的方法存入此集合中。
    openedPanels:Ext.create("Ext.util.MixedCollection"),

    init: function () {

        //console.log("ConfigController.init()")
        var me=this;

        me.control({
            'configlist':{
                //视图准备好之后，才去请求GridPanel的数据。
                viewready:function(){
                	//me.get_ConfigListStoreStore().load();
                	var store = Ext.StoreManager.lookup("framework.config.ConfigListStore");
                    store.getProxy().extraParams = {};
                    store.load();
                },
                beforerender:function(panel){
                    var btns=Wms.getOperates.fn(Wms.menuInfo.operates);
                    if(btns!=null){
                        for(var i=0;i<btns.length;i++){

                            panel.down("toolbar").add(btns[i]);
                            if(i==0){
                                panel.down("toolbar").add("->");
                            }
                        }
                    }
                }
            },
			'navmenu':{
                //在面板加载前动态加载他的子菜单
                beforerender:function(panel){

                    //添加子菜单Wms.menuShow.fn(menuInfo)方法封装了动态全局参数和生成菜单的方法
                    if(Wms.menuInfo.submenu!=null){
                        panel.add(Wms.menuShow.fn(Wms.menuInfo.submenu));
                    }

                }

            },
            //添加
            'configlist [action=addConfig]':{
               click:me.onShowAddPanel
            },

            //编辑
            'configlist [action=editConfig]':{
              	click:me.onShowEditPanel
            },

            //删除
            'configview [action=delConfig]':{
               click : me.onDelConfig
            }
        });

    },
    onLaunch:function(){
        //console.log("ConfigController.onLaunch()------------------------------------------------------------------------------------")
    },

  //Event handler=============================================
    //事件处理函数

  //添加功能
    onShowAddPanel:function(btn){
        var me=this;
        var panelName,panel;
       
        if (btn.hasOwnProperty("id")){
             panelName=btn.id;
             
            //尝试在集合中获取面板
            panel=me.openedPanels.get(panelName);
            if(panel){
                //面板存在，切换
                panel.isHidden()?panel.show():null;
            }else{
                //面板不存在，创建
                panel=Ext.widget("configadd",{
                    title:"添加配置",
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY:"auto" //自动添加滚动条
                });
                //显示面板，将面板添加入打开的面板集合中.
                me.getConfigTabPanel().add(panel).show();
                me.openedPanels.add(panelName,panel);
                //console.log(me.openedPanels);



                //获取新闻模型类            获取到表单对象
                //var configListModel=me.get_ConfigListModelModel();
                var configListModel= Ext.create("Wms.model.framework.config.ConfigListModel");
                var form=panel.getForm();
                //form.loadRecord(configListModel);



                //提交按钮事件
                panel.down("button[action=configSubmit]").on("click",function(){
                    if(form.isValid() &&form.isDirty()){
                        //创建一条记录，
                        var configRec=form.getRecord();
                        //更新记录内容
                        //form.updateRecord(configRec);
                        //console.log(configRec);


                        //在_NewsListStore中添加一条新记录
                       // me.get_ConfigListStoreStore().insert(0, configRec);

                      
                       
                      form.submit({
                           url:Ext.StoreManager.lookup("framework.config.ConfigListStore").getProxy().api.create,
                           success: function (f, action) {
                               //console.log(action);
                               Ext.Msg.alert("", "添加成功");
                               Ext.StoreManager.lookup("framework.config.ConfigListStore").load();
                               Ext.getCmp('configaddForm').close();
                               //Wms.refresh.fn()();//刷新桌面
                           },

                           //失败
                           failure: function (e, action) {
                               //console.log(action);
                               Ext.Msg.alert("错误", action.result.message);
                           }

                       });
                       
                    }

                });

                //关闭面板时，在集合中除名
                panel.on({
                    destroy:function(p){
                        me.openedPanels.remove(p);
                        //console.log(me.openedPanels);
                    }
                });



            }
        }
    },
    
    // 编辑功能
	onShowEditPanel : function(btn) {
		var me = this;
		var panelName, panel;
		if (btn.hasOwnProperty("action")) {

			panelName = btn.action;
			var rec = this.getConfigList().getSelectionModel().getSelection();
			if (rec.length <= 0) {
				return;
			}
			panelName = panelName + rec[0].data["id"];

			// 尝试在集合中获取面板
			panel = me.openedPanels.get(panelName);

			if (panel) {
				// 面板存在，切换
				panel.isHidden() ? panel.show() : null;
			} else {

				// 面板不存在，创建
				// 添加系统配制面板
				panel = Ext.widget("configadd", {
							title : "编辑" + rec[0].data["value"],
							closable : true, // 可关闭。
							iconCls : 'tabs',
							overflowY : "auto", // 自动添加滚动条
							flag:true
						});

				// 显示面板，将面板添加入打开的面板集合中.
				me.getConfigTabPanel().add(panel).show();
				me.openedPanels.add(panelName, panel);

				// 获取到表单对象
				var form = panel.getForm();
				form.loadRecord(rec[0])

				// 提交按钮事件
				panel.down("button[action=configSubmit]").on("click",
						function() {

							 if(form.isValid &&form.isDirty()){
                        //创建一条记录，
                        var configRec=form.getRecord();
                        //更新记录内容
                          form.updateRecord(configRec);
//                        console.log(configRec);


                        //在_NewsListStore中添加一条新记录
                       // Ext.StoreManager.lookup("framework.config.ConfigListStore").update(0, configRec);

                      
                       
                      form.submit({
                           url:Ext.StoreManager.lookup("framework.config.ConfigListStore").getProxy().api.update,
                           success: function (f, action) {
                               //console.log(action);
                               Ext.StoreManager.lookup("framework.config.ConfigListStore").load();
                               Ext.Msg.alert("", "更新成功");
                           },

                           //失败
                           failure: function (e, action) {
                               //console.log(action);
                               Ext.Msg.alert("错误", action.result.message);
                           }

                       });
                       
                    }

						});

				// 重置按钮
				panel.down("button[action=configReset]").on("click",
						function() {
							form.reset();
						});

				// 关闭面板时，在集合中除名
				panel.on({
							destroy : function(p) {
								me.openedPanels.remove(p);
								
							}
						});

			}
		}
	},
    
    
    // 删除功能
	onDelConfig : function() {
		//console.log("onDelConfig");
		var me = this;
		var rec = this.getConfigList().getSelectionModel().getSelection();
		if (rec.length <= 0) {
			Ext.MessageBox.alert({
						title : '错误提示',
						msg : '请选择要删除的项',
						buttons : Ext.MessageBox.OK,
						icon : Ext.MessageBox.INFO
					});
			return;
		}

		// 提示是否删除
		var msg = ["确定删除以下配置吗？"];
		var ids = [];
		for (var i = 0, len = rec.length; i < len; i++) {
			ids.push(rec[i].data["key"]);
			msg.push(rec[i].data["key"]);
		}

		Ext.Msg.confirm("删除配置", msg.join("<br />"), function(btn) {
					if (btn === "yes") {

						Ext.Ajax.request({
									method:'get',
									url : "config/remove.json",
									params:{
										ids:ids
									},
									success : function(response, opts) {
										var obj = Ext
												.decode(response.responseText);
										if (obj.success) {

											Ext.Msg.alert("删除成功", "删除成功",
													function() {
														Ext.StoreManager.lookup("framework.config.ConfigListStore").load();
													})
										} else {
											Ext.Msg.alert("错误", obj.msg)
										}
									},

									failure : function(response, opts) {
										Ext.Msg
												.alert(
														"错误",
														'状态：'
																+ response.status
																+ ":"
																+ response.responseText);

									}
								});

					}
				});

	}

});
