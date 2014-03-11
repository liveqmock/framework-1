
Ext.define("Wms.view.framework.user.MemberAddEdit", {
    extend: "Ext.form.Panel",
    alias: "widget.memberaddedit",
    id: "memberAddEditForm",
    // 布局 ，垂直排列
    layout: {
        type: "vbox",
        align: 'stretch'
    },
    bodyPadding: 5,
    // items内的子项默认样式。
    defaults: {
        margin: "0 0 10 0"
    },
    // items内表单项，默认配置
    fieldDefaults: {
        msgTarget: 'side',
        labelWidth: 80,
        anchor: "0",
        labelSeparator: "："
    },
    // 表单数据提交处理
    /*
     * api:{ submit:"_news/edit/inNewsView" },
     */
    // 当为编辑内容，点击重置，还原为最初的编辑内容，而非空值。
    trackResetOnLoad: true,
    // 提交表单时提示语。
    waitTitle: "提交中，请稍候....",
    // 自定义属性
    required: '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',
    // 组件初始化
    initComponent: function () {
        var me = this;
        var flag = false;
        if(me.flag==true){
        	flag =  true;
        }
     // 提交按钮
        me.dockedItems = [
            // 第五部分------按钮区域
            {
                xtype: "toolbar",
                dock: "top",
                ui: "footer",
                layout: {
                    pack: "center"
                },
                // margin:'20 0 10 95',
                items: [
                    // 提交按钮
                    {
                        xtype: "button",
                        text: '提  交',
                        action: "memberSubmit",
                        // 验证表单中没有无效值时才会自动启动控钮
                        formBind:true,
                        margin: '0 15 0 0'
                        // handler:me.onNewsSubmit
                    },
                    // 重置按钮
                    {
                        xtype: "button",
                        action: "memberReset",
                        text: "重  置"
                    }
                ]
            }
        ];
        me.items = [
            // 表单区域 ，垂直布局，分成多个块
            // 第一部分----纯表单
            {
                // title:"第一部分",
                // border:true,
                layout: "anchor",
                defaultType: "textfield",
                bodyPadding: '5 10',
                items: [
                    {
                        anchor: '30%',
                        xtype: "hiddenfield",
                        name: 'uid',
                        id: 'uid'
                    },
                    // 用户名
                    {
                        anchor: '30%',
                        // xtype:"hiddenfield",
                        afterLabelTextTpl: me.required,
                        fieldLabel: '用户名  ',
                        name: 'userName',
                        id: 'userName',
                        maxLength:'20',
                        allowBlank: false,
                        tooltip: '请输入用户名',
                        readOnly:me.flag
                    },
                    // 用户密码
                    {
                        anchor: '30%',
                        fieldLabel: '密         码   ',
                        //vtype:'password',
                        inputType: 'password',
                        afterLabelTextTpl: me.required,
                        name: 'password',
                        id: 'password',
                        maxLength:'20',
                        minLength: 6,
                        allowBlank: flag,
                        tooltip: '请输入密码',
                        hideLabel:flag,
                        hidden:flag
                    },
                    // 邮箱
                    {
                        anchor: '30%',
                        fieldLabel: '邮           箱',
                        vtype: 'email',
                        id: 'email',
                        afterLabelTextTpl: me.required,
                        name: 'email',
                        maxLength:'30',
                        tooltip: '请输入邮箱',
                        allowBlank: false
                    },
                    //分组
                    {
//                        anchor: '30%',
//                        xtype: "combo",
//                        fieldLabel: '分组',
//                        id: 'group',
//                        name: 'group.groupId',
//                        afterLabelTextTpl: me.required,
//                        store: 'framework.user.UserGroupStore',
//                        emptyText: '请选择分组',
//                        blankText: '请选择分组',
//                        valueField: 'groupId',
//                        editable: false,
//                        displayField: 'groupName',
//                        allowBlank: false,
//                        forceSelection: true,//必须选择一个选项
                    	afterLabelTextTpl: me.required,
                    	anchor:'30%',
                        fieldLabel: '分组',
                        name:'group.groupId',
                        xtype:'treecombobox',
                        valueField: 'id',
                        id:'group',
                        displayField: 'text',
                        store:Ext.create('Wms.store.framework.TreeStore', {
                            model: "Wms.model.framework.TreeModel",
                            proxy: {
                                type: 'ajax',
                                url: 'group/getGroupTree.json'
                            }
                        }).load({
                            scope: this,
                            callback: function(records) {
                            	if(me.flag!=null && me.flag == true){
                                	var combox = Ext.getCmp('group');
                                	combox.setValue(me.groupId);
                                	var combox = Ext.getCmp('group');
                                    var store = Ext.StoreManager.lookup("framework.user.UserRolesStore");
                                    store.getProxy().extraParams = {'groupId': me.groupId};
                                    var searchUrl = store.proxy.api.read;
                                    store.getProxy().api.read = searchUrl;
                                    store.load();
                                    Ext.getCmp('role').setValue(me.roleId);
                            	}
                            }
                        }),
                       listeners: {
                            beforequery: function (obj) {
                                //obj.combo.store.load();
                            },
                            select: function (combo, record, index) {
                            	var combox = Ext.getCmp('group');
                                var store = Ext.StoreManager.lookup("framework.user.UserRolesStore");
                                store.getProxy().extraParams = {'groupId': combo.getValue()};
                                var searchUrl = store.proxy.api.read;
                                store.getProxy().api.read = searchUrl;
                                store.load();
                            }
                        }
                    },
                    //角色
                    {
                        anchor: '30%',
                        xtype: "combo",
                        fieldLabel: '角色',
                        id: 'role',
                        name: 'role.roleId',
                        valueField: 'roleId',
                        afterLabelTextTpl: me.required,
                        displayField: 'roleName',
                        editable: false,
                        store: "framework.user.UserRolesStore",
                        emptyText: '请选择角色',
                        blankText: '请选择角色',
                        allowBlank: false,
                        forceSelection: true,//必须选择一个选项
                        listeners: {
                            beforequery: function (obj) {
                                //obj.combo.store.load();
                            },
                            focus: function (combo, record, index) {
/*                              var combox = Ext.getCmp('group');
                                var store = Ext.StoreManager.lookup("framework.user.UserRolesStore");
                                store.getProxy().extraParams = {'groupId': combox.getValue()};
                                var searchUrl = store.proxy.api.read;
                                store.getProxy().api.read = searchUrl;
                                store.load();*/
                            }
                        }
                    }

                ]
            }
        ];
/*        Ext.StoreManager.lookup("framework.user.UserRolesStore").on("load",function(){   //对 ComboBox 的数据源加上 load 事件就好  
        	Ext.getCmp('role').setValue(0);  
        }); */
        //Ext.StoreManager.lookup("framework.user.UserRolesStore").load();
        
        me.callParent();
        if(me.flag!=null && me.flag == true){
   		   // if(me.roleName!="")Ext.getCmp('role').emptyText=me.roleName;
        	//Ext.getCmp('role').setValue(me.roleId);
    	}
    }
});
