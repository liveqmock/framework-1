Ext.define("Wms.controller.framework.MenuItemController", {
    extend: "Ext.app.Controller",


    //要用到的视图，模型，数据
    views: ["framework.menuItem.View"],
    models: ["framework.menuItem.MainMenuItemModel", 
             "framework.menuItem.MenuItemListModel", 
             "framework.operate.OperateListModel", 
             'framework.TreeModel'],
    stores: ["framework.menuItem.MenuItemListStore",
             "framework.menuItem.MenuItemOpStore",
             "framework.TreeStore"],

    //根据ref属性创建getXXXX方法。用于获取到对应的视图组件
    refs: [
        //主视图
        {
            ref: "menuItemList",
            selector: "menuItemlist"

        },

        {
            ref: "mytriggerfield",
            selector: "mytriggerfield"

        },


        //列表
        {
            ref: "menuitemView",
            selector: "menuitemview"
        },

        //tab panel
        {
            ref: "menuItemTabPanel",
            selector: "menuItemtabpanel"
        },

        {
            ref: "checkboxtree",
            selector: "checkboxtree"
        }

    ],


    //自定义属义------------------------------------------
    //创建一个已打开的PMenuItemListStoreanels集合，所以已打开的面板都会以key---value的方法存入此集合中。
    openedPanels: Ext.create("Ext.util.MixedCollection"),


    //初始化控制，注册各类事件
    init: function () {

        //console.log("MenuItemController.init()");
        var me = this;

        me.control({

            'menuitemview': {
                beforerender: function (panel) {

                    /**
                     * Wms.menuInfo为全局变量的属性
                     * 添加子菜单Wms.menuShow.fn(menuInfo)方法封装了动态全局参数和生成菜单的方法
                     */
                    if (Wms.menuInfo.submenu != null) {
                        panel.down("toolbar").add(Wms.menuShow.fn(Wms.menuInfo.submenu));
                    }
                }

            },


            //编辑面板加载之前
            'menuItemAddEdit': {

                beforerender: function () {

                }
            },
            'menuItemlist': {
                //视图准备好之后，才去请求GridPanel的数据。
                viewready: function (panel) {
                    //me.get_MenuItemListStoreStore().load();
                	var store =Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore");
                     
                    store.getProxy().extraParams = {};
                    store.load();

                },
                //视图加载前 添加操作
                beforerender: function (panel) {
                    var btns = Wms.getOperates.fn(Wms.menuInfo.operates);
                    if (btns != null) {
                        for (var i = 0; i < btns.length; i++) {

                            panel.down("toolbar").add(btns[i]);
                            if (i == 0) {
                                panel.down("toolbar").add("->");
                            }
                        }
                    }
                }
            },

            //添加菜单
            'menuitemview [action=addMenuItem]': {
                click: me.onShowAddPanel
            },

            //编辑菜单
            'menuitemview [action=editMenuItem]': {
                click: me.onShowEditPanel
            },

            //删除菜单
            'menuitemview [action=delMenuItem]': {
                click: me.onDelMenuItem
            }



        });

    },

    onLaunch: function () {


        //console.log("MenuItemController.onLaunch()----------------------------------")
    },


    //Event handler=============================================
    //事件处理函数

    //添加功能
    onShowAddPanel: function (btn) {
        var me = this;
        var panelName, panel;
        if (btn.hasOwnProperty("action")) {
            panelName = btn.action;

            //尝试在集合中获取面板
            panel = me.openedPanels.get(panelName);

            if (panel) {
                //面板存在，切换
                panel.isHidden() ? panel.show() : null;
            } else {
                //面板不存在，创建
                panel = Ext.widget("menuitemaddedit", {
                    title: "添加菜单",
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY: "auto" //自动添加滚动条
//                    operates:[]
                });

                //显示面板，将面板添加入打开的面板集合中.
                me.getMenuItemTabPanel().add(panel).show();
                me.openedPanels.add(panelName, panel);

                var form = panel.getForm();

                //提交按钮事件
                panel.down("button[action=menuItemSubmit]").on("click", function () {
                    if (form.isValid() && form.isDirty()) {
                        form.submit({
                            params: {enuId: 0},

                            //url
                            url: Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore").proxy.api.create,

                            //成功
                            success: function (f, action) {
                                Ext.Msg.alert("操作提示", action.result.message);
                                Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore").loadPage(1);
                                //Wms.refresh.fn();//刷新桌面
                            },


                            //失败
                            failure: function (f, action) {
                                Ext.Msg.alert("操作提示", action.result.message);
                            }
                        });
                    }

                });

                //重置按钮
                panel.down("button[action=menuItemReset]").on("click", function () {
                    form.reset();
                });

                //关闭面板时，在集合中除名
                panel.on({
                    destroy: function (p) {
                        me.openedPanels.remove(p);
                    }
                });
            }
        }

    },

    //编辑功能
    onShowEditPanel: function (btn) {
        var me = this;
        var panelName, panel;
        if (btn.hasOwnProperty("action")) {
            var rec = this.getMenuItemList().getSelectionModel().getSelection();
            panelName = btn.action;

            if (rec.length <= 0) {
                return;
            }

            //尝试在集合中获取面板
            panel = me.openedPanels.get(panelName);

            if (panel) {
                //面板存在，切换
                panel.isHidden() ? panel.show() : null;
            } else {

                //面板不存在，创建
                //添加新闻面板
                panel = Ext.widget("menuitemaddedit", {
                    title: "编辑" + rec[0].data["menuName"],
                    closable: true, //可关闭。
                    iconCls: 'tabs',
                    overflowY: "auto", //自动添加滚动条
                    menuId: rec[0].data["menuId"],
                    mainMenuId: rec[0].data["mainMenuId"]
                });

                //显示面板，将面板添加入打开的面板集合中.
                me.getMenuItemTabPanel().add(panel).show();
                me.openedPanels.add(panelName, panel);

                // 获取到表单对象
                var form = panel.getForm();
                form.loadRecord(rec[0]);

                //提交按钮事件
                panel.down("button[action=menuItemSubmit]").on("click", function () {

                    if ((form.isValid() && form.isDirty())) {
                        form.submit({
                            //url
                            url: Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore").proxy.api.create,

                            method: 'POST',

                            //成功
                            success: function (f, action) {
                                Ext.Msg.alert("操作提示", action.result.message);
                                Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore").loadPage(1);
                                //Wms.refresh.fn();//刷新桌面
                            },


                            //失败
                            failure: function (f, action) {
                                Ext.Msg.alert("操作提示", action.result.message);
                            }
                        });
                    }
                });

                //重置按钮
                panel.down("button[action=menuItemReset]").on("click", function () {
                    form.reset();
                });


                //关闭面板时，在集合中除名
                panel.on({
                    destroy: function (p) {
                        me.openedPanels.remove(p);
                    }
                });
            }
        }
    },


    //删除功能
    onDelMenuItem: function () {
        var me = this;
        var rec = this.getMenuItemList().getSelectionModel().getSelection();
        if (rec.length <= 0) {
            Ext.MessageBox.alert({
                title: '错误提示',
                msg: '请选择要删除的项',
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.INFO
            });
            return;
        }

        //提示是否删除
        var msg = ["确定删除以下菜单吗？"];
        var ids = [];
        for (var i = 0, len = rec.length; i < len; i++) {
            ids.push(rec[i].data["menuId"]);
            msg.push(rec[i].data["menuName"]);
        }
//        var menuId=ids[0];
        Ext.Msg.confirm("删除菜单", msg.join("<br />"), function (btn) {
            if (btn === "yes") {



                //这里为直接启用了一个ajax请求来删除(演示效果使用)。
                // 开发时应该调用store.sync同步服务器的方法，来实现删除功能
                //var store=me.get_NewsListStoreStore();
                //store.sync({})

                Ext.Ajax.request({
                    url: Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore").proxy.api.destroy,
                    method: 'POST',//请求方式
                    params: {menuIds: ids},
                    success: function (response) {
                        var obj = Ext.decode(response.responseText);
                        if (obj.success) {
                            var msg = [].concat([obj.message]);
                            msg.push("列表将自动刷新");
                            Ext.Msg.alert("删除成功", msg.join("<br />"), function () {
                                Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore").loadPage(1);
                            });
                            //Wms.refresh.fn();//刷新桌面
                        } else {
                            Ext.Msg.alert("错误", obj.message)
                        }
                    },

                    failure: function (response) {
                        var obj = Ext.decode(response.responseText);
                        Ext.Msg.alert("错误", '状态：' + response.status + ":" + response.responseText);

                    }
                });

            }
        });

    }
});
