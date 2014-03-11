/**
 * 分组控制器，分组业务处理，
 */


Ext.define("Wms.controller.framework.GroupController",{
    extend:"Ext.app.Controller",


    //要用到的视图，模型，数据,
    views:  ["framework.group.View","framework.group.GroupTabPanel","framework.group.GroupSearch","framework.group.GroupAdd","framework.group.GroupEdit"
        ,"framework.util.CheckboxTree","framework.util.TreeComboBox"],
    models: ["framework.group.GroupModel","framework.TreeModel"],
    stores: ["framework.group.GroupStore","framework.TreeStore"],


    //根据ref属性创建getXXXX方法。用于获取到对应的视图组件
    refs:[
        //分组主视图
        {
            ref:"groupview",
            selector:"groupview"
        },

        //分组列表
        {
            ref:"grouplist",
            selector:"grouplist"
        },

        //分组tab panel
        {
            ref:"groupTabPanel",
            selector:"grouptabpanel"
        },
        //添加面板
        {
            ref:"groupaddview",
            selector:"groupaddview"
        },
        //编辑面板
        {
            ref:"groupeditview",
            selector:"groupeditview"
        },
        //树面板
        {
            ref:"checkboxtree",
            selector:"checkboxtree"
        },
        //搜索面板
        {
            ref:"groupserach",
            selector:"groupserach"
        }
    ],



    //自定义属义------------------------------------------
    //创建一个已打开的Panels集合，所以已打开的面板都会以key---value的方法存入此集合中。
    openedPanels:Ext.create("Ext.util.MixedCollection"),



    //初始化控制，注册各类事件
    init:function(){
        var me=this;


        me.control({
            'grouplist':{
                //视图准备好之后，才去请求GridPanel的数据。
                viewready:function(){
                	var store = Ext.StoreManager.lookup("framework.group.GroupStore");
                    store.getProxy().extraParams = {};
                    store.load();
                },
  		      //视图加载前 添加操作
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

            //添加分组
            'groupview [action=addGroup]':{
                click:me.onShowAddPanel
            },

            //编辑分组
            'groupview [action=editGroup]':{
                click:me.onShowEditPanel
            },

            //删除分组
            'groupview [action=delGroup]':{
                click:me.onDelGroup
            }

/*            //搜索分组
            'groupview [action=groupSearch]':{
                click:me.onSerachGroup
            }*/

        });

    },

    onLaunch:function(){
    },

    //添加功能
    onShowAddPanel:function(btn){
        var me=this;
        var panelName,panel;
        if (btn.hasOwnProperty("action")){
            panelName=btn.action;
            //尝试在集合中获取面板
            panel=me.openedPanels.get(panelName);

            if(panel){
                //面板存在，切换
                panel.isHidden()?panel.show():null;
            }else{
                //面板不存在，创建
                panel=Ext.widget("groupaddview",{
                    title:"添加分组",
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY:"auto" //自动添加滚动条
                });

                //显示面板，将面板添加入打开的面板集合中.
                me.getGroupTabPanel().add(panel).show();
                me.openedPanels.add(panelName,panel);
                //console.log(me.openedPanels);



                //获取新闻模型类            获取到表单对象
            //    var groupModel=me.getGroupModelModel();
                var groupModel=Ext.create("Wms.model.framework.group.GroupModel");
                var form=panel.getForm();
                form.loadRecord(groupModel);



                //提交按钮事件
                panel.down("button[action=groupAddSubmit]").on("click",function(){
                    if(form.isValid() &&form.isDirty()){
                        //创建一条记录，
                        var newRec=form.getRecord();
                        //更新记录内容
                        form.updateRecord(newRec);
                        form.submit({
                            method:"POST",
                            params:{"listCheckbox":me.getCheckboxtree().getChecklist()},
                            url:Ext.StoreManager.lookup("framework.group.GroupStore").getProxy().api.create,
                            success: function (f, a) {
                                Ext.Msg.alert("成功", "添加分组成功");
                                Ext.getCmp('groupAddView').close();
                                Ext.StoreManager.lookup("framework.group.GroupStore").load();
                                //Wms.refresh.fn();//刷新桌面
                            },
                            failure: function (e, a) {
                                Ext.Msg.alert("错误", a.result.message);
                            }

                        });

                    }else{
                        Ext.Msg.alert("提示","表单不能为空！");
                    }

                });

                //重置按钮
                panel.down("button[action=groupReset]").on("click",function(){
                    form.reset();
                });

                //关闭面板时，在集合中除名
                panel.on({
                    destroy:function(p){
                        me.openedPanels.remove(p);
                    }
                });



            }
        }
    },

    //编辑功能
    onShowEditPanel:function(btn){
        var me=this;
        var panelName,panel;
        if (btn.hasOwnProperty("action")){

            panelName=btn.action;
            var rec = me.getGrouplist().getSelectionModel().getSelection();
            if(rec.length<=0){return;}
            if(rec.length>1){
                Ext.Msg.alert("错误","不可编辑多个分组！");
                return;
            }
            panelName=panelName+  rec[0].data["groupId"];


            //尝试在集合中获取面板
            panel=me.openedPanels.get(panelName);

            if(panel){
                //面板存在，切换
                panel.isHidden()?panel.show():null;
            }else{
                //面板不存在，创建
                //添加分组面板
                panel=Ext.widget("groupeditview",{
                    title:"编辑"+rec[0].data["groupName"],
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY:"auto", //自动添加滚动条
                    groupId:rec[0].data["groupId"],
                    defaultGroupType:rec[0].data["groupType"],
                    parentGroupId:rec[0].data["parentGroupId"]
                });
                //显示面板，将面板添加入打开的面板集合中.
                me.getGroupTabPanel().add(panel).show();
                me.openedPanels.add(panelName,panel);




                // 获取到表单对象
                var form=panel.getForm();
                form.loadRecord(rec[0]);

                //提交按钮事件
                panel.down("button[action=groupEditSubmit]").on("click",function(){
                	if((form.isValid() && form.isDirty()) || me.getCheckboxtree().isChanged){
                        //创建一条记录，
                        var newRec=form.getRecord();

                        //更新记录内容
                        form.updateRecord(newRec);
                        //在_NewsListStore中添加一条新记录
                 //       me.getGroupStoreStore().insert(0, newRec);
                        form.submit({
                            method:"POST",
                            params:{"listCheckbox":me.getCheckboxtree().getChecklist(),"delRoleIds":me.getCheckboxtree().getUnChecklist(),"groupId":rec[0].data["groupId"]},
                            url:Ext.StoreManager.lookup("framework.group.GroupStore").getProxy().api.update,
                            success: function (f, a) {
                                Ext.Msg.alert("成功", "编辑分组成功");
                                Ext.getCmp('groupEditView').close();
                                Ext.StoreManager.lookup("framework.group.GroupStore").load();
                                //Wms.refresh.fn();//刷新桌面
                            },
                            failure: function (e, a) {
                                Ext.Msg.alert("错误", a.result.message);
                            }

                        });
                    }else{
                        Ext.Msg.alert("提示","没有修改动作！");
                    }



                });

                //重置按钮
                panel.down("button[action=groupReset]").on("click",function(){
                    form.reset();
                });



                //关闭面板时，在集合中除名
                panel.on({
                    destroy:function(p){
                        me.openedPanels.remove(p);
                    }
                });



            }
        }
    },

    //删除功能
    onDelGroup:function(btn){
        var me=this;
        var rec = me.getGrouplist().getSelectionModel().getSelection();
        if(rec.length<=0){
            Ext.MessageBox.alert({
                title: '错误提示',
                msg: '请选择要删除的项',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });
            return;
        }

        //提示是否删除
        var msg=["确定删除以下分组吗？"];
        var ids=[];
        for(var i= 0,len=rec.length;i<len;i++){
            ids.push(rec[i].data["groupId"]);
            msg.push(rec[i].data["groupName"]);
        }

        Ext.Msg.confirm("删除分组",msg.join("<br />"),function(btn){
            if(btn==="yes"){

                //这里为直接启用了一个ajax请求来删除(演示效果使用)。
                // 开发时应该调用store.sync同步服务器的方法，来实现删除功能
                //var store=me.get_NewsListStoreStore();
                //store.sync({})
                Ext.Ajax.request({
                    method:"POST",
                    url:Ext.StoreManager.lookup("framework.group.GroupStore").getProxy().api.destroy,
                    params:{"listCheckbox":ids},
                    success:function(response,action){
                    	var result  = eval('('+response.responseText+')');
                    	Ext.Msg.alert("操作提示", result.message);
                        Ext.StoreManager.lookup("framework.group.GroupStore").loadPage(1);
                    },
                    failure:function(response,action){
                    	var result  = eval('('+response.responseText+')');
                    	Ext.Msg.alert("操作提示", result.message);
                    }
                });

            }
        });


    }

    //搜索功能
/*    onSerachGroup:function(btn){
        panelName=btn.action;
        //尝试在集合中获取面板
        panel=this.openedPanels.get(panelName);
        //搜索按钮事件
        panel.down("button[action=groupSearch]").on("click",function(){
            var form =panel.getForm();
            if(form.isValid &&form.isDirty()){
                form.submit({
                    method:"GET",
                  //  params:{"listCheckbox":me.getCheckboxtree().checklist},
                 //   url:me.getGroupStoreStore().getProxy().api.query+"/1/10.json",
                    url:me.getGroupStoreStore().getProxy().api.read,
                    success: function (f, a) {
                    },
                    //失败
                    failure: function (e, opt) {
                    }

                });

            }

        });
    }*/

    //提




});
