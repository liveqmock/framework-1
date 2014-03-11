/**
 * 新闻控制器，新闻业务处理，
 */
/////这里放全局对象
//实例化角色工具类
roleUtils= null;

Ext.define("Wms.controller.framework.RoleController",{
    extend:"Ext.app.Controller",

    //要用到的视图，模型，数据
    views:  ["framework.role.View","framework.role.RoleAdd","framework.role.RoleEdit","framework.util.CheckboxTree"],
    models: ["framework.role.RoleListModel","framework.TreeModel","framework.group.GroupModel"],
    stores: ["framework.role.RoleListStore","framework.group.GroupStore","framework.TreeStore"],

    //引用静态工具类
    requires:[
        "Wms.utils.framework.RoleUtils"
    ],


    id:'roleControllerId',


    //当前list面板的父角色ID
    nowParentRoleId:0,

    //根据ref属性创建getXXXX方法。用于获取到对应的视图组件
    refs:[
        //角色主视图
        {
            ref:"roleview",
            selector:"roleview"
        },

        //角色列表
        {
            ref:"rolelist",
            selector:"rolelist"
        },

        //角色tab panel
        {
            ref:"roleTabPanel",
            selector:"roletabpanel"
        },
        //添加面板
        {
            ref:"roleaddview",
            selector:"roleaddview"
        },

        //树面板
        {
            ref:"checkboxtree",
            selector:"checkboxtree"
        }

    ],



    //自定义属义------------------------------------------
    //创建一个已打开的Panels集合，所以已打开的面板都会以key---value的方法存入此集合中。
    openedPanels:Ext.create("Ext.util.MixedCollection"),



    //初始化控制，注册各类事件
    init:function(){
        var me=this;
        //实例化角色工具
        roleUtils=Ext.create('Wms.utils.framework.RoleUtils');
      //  roleUtils.setAddRoleButton(me.getRolelist());
        me.control({
            'rolelist':{
                //视图准备好之后，才去请求GridPanel的数据。
                viewready:function(){
                	var store =Ext.StoreManager.lookup("framework.role.RoleListStore");
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
		            };
                    //查看子角色按钮
                    var backButton =Ext.create('Ext.Button', {
                        text: '查看子角色',
                        id:'querySubRoleButton',
                        action:'querySubRole',
                        disabled:true

                    });
                    panel.down("toolbar").add(backButton);

                    //查看父角色按钮
                    var backButton2 =Ext.create('Ext.Button', {
                        text: '查看上一级角色',
                        id:'queryParentRoleButton',
                        action:'queryParentRole',
                        disabled:true
                    });
                    panel.down("toolbar").add(backButton2);

                    roleUtils.setAddRoleButton(me.getRolelist().down("toolbar button[action=addRole]"));
                    //刷新按钮
                    roleUtils.refresh();

		        }

            },

            //添加角色
            'roleview [action=addRole]':{
                click:me.onShowAddPanel
            },

            //编辑角色
            'roleview [action=editRole]':{
                click:me.onShowEditPanel
            },

            //删除角色
            'roleview [action=delRole]':{
                click:me.onDelRole
            },
            //查询子角色
            'roleview [action=querySubRole]':{
                click:me.onQuerySubRoleList
            },
            //查询上一级列表
            'roleview [action=queryParentRole]':{
                click:me.onQueryParentRoleList
            }

        });

    },

    onLaunch:function(){
    },

    //添加功能
    onShowAddPanel:function(btn){
        var me=this;
        var panelName,panel;
        var parentRoleId =0;

        var rec = me.getRolelist().getSelectionModel().getSelection();
        panelName ="添加角色";
        if(rec.length<=0){
            //如果没有选择角色，默认添加当前面板父角色的子角色
            parentRoleId = me.nowParentRoleId;
            //如果是admin，则添加顶级角色
            /*if(Ext.util.Cookies.get("userName")=='YWRtaW4='){
                parentRoleId=0;
                panelName='添加角色';
            }*/
        }else{
            parentRoleId =rec[0].data["roleId"];
        }

        if (btn.hasOwnProperty("action")){
            panelName=btn.action;

            //尝试在集合中获取面板
            panel=me.openedPanels.get(panelName);

            if(panel){
                //面板存在，切换
                panel.isHidden()?panel.show():null;
            }else{
                //面板不存在，创建
                panel=Ext.widget("roleaddview",{
                    title:"添加角色",
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY:"auto",//自动添加滚动条
                    parentRoleId:parentRoleId
                });

                //显示面板，将面板添加入打开的面板集合中.
                me.getRoleTabPanel().add(panel).show();
                me.openedPanels.add(panelName,panel);


                //获取新闻模型类            获取到表单对象
            //    var roleListModel=me.get_RoleListModelModel();
                var roleListModel=Ext.create("Wms.model.framework.role.RoleListModel");
                var form=panel.getForm();
                form.loadRecord(roleListModel);


                panel.isShowView();

                //提交按钮事件
                panel.down("button[action=roleAddSubmit]").on("click",function(){
                    if(form.isValid() &&form.isDirty()){
                        //创建一条记录，
                        var newRec=form.getRecord();
                        //更新记录内容
                        form.updateRecord(newRec);
                        form.submit({
                            method:"POST",
                            params:{"listCheckbox":me.getCheckboxtree().getChecklist(),"parentRoleId":parentRoleId},
                            url:Ext.StoreManager.lookup("framework.role.RoleListStore").getProxy().api.create,
                            success: function (f, a) {
                                Ext.Msg.alert("添加角色成功", "添加角色成功");
                                Ext.getCmp('roleAddForm').close();
                                Ext.StoreManager.lookup("framework.role.RoleListStore").load();
                                //Wms.refresh.fn();//刷新桌面
                            },
                            //失败
                            failure: function (e, a) {
                                Ext.Msg.alert("错误", a.result.message);
                            }

                        });

                    }else{
                        Ext.Msg.alert("提示","表单不能为空！");
                    }

                });

                //重置按钮
                panel.down("button[action=roleReset]").on("click",function(){
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
            var rec = me.getRolelist().getSelectionModel().getSelection();
            if(rec.length<=0){return;}
            if(rec.length>1){
                Ext.Msg.alert("错误","不可编辑多个角色！");
                return;
            }
            panelName=panelName+  rec[0].data["roleId"];


            //尝试在集合中获取面板
            panel=me.openedPanels.get(panelName);

            if(panel){
                //面板存在，切换
                panel.isHidden()?panel.show():null;
            }else{

                //面板不存在，创建
                //添加角色面板
                panel=Ext.widget("roleeditview",{
                    title:"编辑"+rec[0].data["roleName"],
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY:"auto", //自动添加滚动条
                    roleId:rec[0].data["roleId"],
                    parentRoleId:rec[0].data["parentRoleId"]
                });
                //显示面板，将面板添加入打开的面板集合中.
                me.getRoleTabPanel().add(panel).show();
                me.openedPanels.add(panelName,panel);




                // 获取到表单对象
                var form=panel.getForm();
                form.loadRecord(rec[0]);


                //提交按钮事件
                panel.down("button[action=roleEditSubmit]").on("click",function(){
                    if((form.isValid() && form.isDirty()) || me.getCheckboxtree().isChanged){
                        //创建一条记录，
                        var newRec=form.getRecord();
                        //更新记录内容
                        form.updateRecord(newRec);
                        form.submit({
                            method:"POST",
                            params:{"listCheckbox":me.getCheckboxtree().getChecklist(),"delMenuOperateIds":me.getCheckboxtree().getUnChecklist(),"roleId":rec[0].data["roleId"]},
                            url:Ext.StoreManager.lookup("framework.role.RoleListStore").getProxy().api.update,
                            success: function (f, a) {
                                Ext.Msg.alert("成功", "编辑角色成功");
                                Ext.getCmp('roleEditForm').close();
                                Ext.StoreManager.lookup("framework.role.RoleListStore").load();
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
                panel.down("button[action=roleReset]").on("click",function(){
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
    onDelRole:function(btn){
        var me=this;
        var rec = me.getRolelist().getSelectionModel().getSelection();
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
        var msg=["确定删除以下角色吗？"];
        var ids=[];
        for(var i= 0,len=rec.length;i<len;i++){
            ids.push(rec[i].data["roleId"]);
            msg.push(rec[i].data["roleName"]);
        }

        Ext.Msg.confirm("删除角色",msg.join("<br />"),function(btn){
            if(btn==="yes"){

                //这里为直接启用了一个ajax请求来删除(演示效果使用)。
                // 开发时应该调用store.sync同步服务器的方法，来实现删除功能
                //var store=me.get_NewsListStoreStore();
                //store.sync({})
                Ext.Ajax.request({
                    url:Ext.StoreManager.lookup("framework.role.RoleListStore").getProxy().api.destroy,
                    params:{"listCheckbox":ids},
                    success:function(response,opts){
                        Ext.StoreManager.lookup("framework.role.RoleListStore").loadPage(1);
                    },

                    failure:function(response,opts){
                        Ext.Msg.alert("错误",'状态：'+response.status+":"+response.responseText);
                    }
                });

            }
        });


    },
    //查询子角色功能
    onQuerySubRoleList:function(btn){
        var me=this;
        var rec = me.getRolelist().getSelectionModel().getSelection();
        if(rec.length<=0){return;}
        if(rec.length>1){
            Ext.Msg.alert("错误","不可查看多个角色的子角色！");
            return;
        }

        var store = Ext.StoreManager.lookup("framework.role.RoleListStore");
        store.getProxy().extraParams={"parentRoleId":rec[0].data["roleId"],"page":1};
        store.method='GET';
        store.loadPage(1,{
            callback:function(){
                //显示查看上一级按钮
                //将本组的父角色ID加入队列中
                roleUtils.push(rec[0].data["parentRoleId"]);
                me.nowParentRoleId=rec[0].data["roleId"];
                if(me.getRolelist().down("toolbar button[action=addRole]") != undefined)
                	me.getRolelist().down("toolbar button[action=addRole]").setText("添加子角色");
            }
        });

    },

    //查看上一级角色功能
    onQueryParentRoleList:function(btn){
        var me=this;
        var store = Ext.StoreManager.lookup("framework.role.RoleListStore");
        var parentRoleId =roleUtils.pop(me.getRolelist().down("toolbar button[action=addRole]"));
        me.nowParentRoleId=parentRoleId;
        store.getProxy().extraParams={"parentRoleId":parentRoleId};
        store.method='GET';
        store.loadPage(1);
    }




});
