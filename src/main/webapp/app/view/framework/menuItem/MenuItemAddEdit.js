//新闻添加或编辑视图
Ext.define(
    "Wms.view.framework.menuItem.MenuItemAddEdit",
    {
        extend: "Ext.form.Panel",
        alias: "widget.menuitemaddedit",

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
        //面板需要的组件
        requires: [
            "Wms.view.framework.util.CheckboxTree",
            'Wms.view.framework.util.TreeComboBox'
        ],
        // items内表单项，默认配置
        fieldDefaults: {
            msgTarget: 'side',
            labelWidth: 80,
            anchor: "0",
            labelSeparator: "："
        },
        //动态加载角色树使用
        menuId: "0",
        mainMenuId:0,
        // 表单数据提交处理
        /*
         * api:{ submit:"menuItem/edit/inMenuItemView" },
         */

        // 当为编辑内容，点击重置，还原为最初的编辑内容，而非空值。
        trackResetOnLoad: true,
        // 提交表单时提示语。
        waitTitle: "提交中，请稍候....",

        // 自定义属性
        required: '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',

        // 组件初始化
        initComponent: function () {

            /*            var store=Ext.create(Ext.data.TreeStore,{
             storeId:"Store1",
             fields:["id","text"],
             root:{
             text:"根节点",id:"tree1",
             expanded:true,
             children:[
             {id:"1",text:"节点1",expanded:true,
             children:[
             {id:"4",text:"节点1-1",leaf:true},
             {id:"5",text:"节点1-2",leaf:true},
             {id:"6",text:"节点1-3",leaf:true}
             ]
             },
             {id:"2",text:"节点2",expanded:true,
             children:[
             {id:"7",text:"节点2-1",leaf:true},
             {id:"8",text:"节点2-2",leaf:true},
             {id:"9",text:"节点2-3",leaf:true}
             ]
             },
             {id:"3",text:"节点3",expanded:false,
             children:[
             {id:"10",text:"节点3-1",leaf:true},
             {id:"11",text:"节点3-2",leaf:true},
             {id:"12",text:"节点3-3",leaf:true}
             ]
             }
             ]
             }
             });*/
            var me = this;
                me.items = [
                {
                    title: '菜单信息',
                    // border:true,
                    layout: "anchor",
                    defaultType: "textfield",
                    bodyPadding: '5 10',
                    items: [
                        {
                            anchor: '20%',
                            xtype: "hiddenfield",
                            fieldLabel: '菜单ID',
                            name: 'menuId'
                        },
                        {
                            anchor: '80%',
                            fieldLabel: '菜单名称',
                            tooltip: '请输入菜单名称',
                            name: 'menuName',
                            allowBlank: false,
                            afterLabelTextTpl: me.required,
                            maxLength: 60
                        },
                        {
                            anchor: '80%',
                            fieldLabel: '视图别名',
                            tooltip: '请输入菜单名称',
                            allowBlank : false,
                            afterLabelTextTpl: me.required,
                            maxLength: 60,
                            name: 'menuWName'
                        },
                        {
                            anchor: '80%',
                            fieldLabel: '视图请求',
                            tooltip: '请输入请求地址',
                            maxLength: 255,
                            name: 'menuAction',
                            allowBlank: false,
                            afterLabelTextTpl: me.required
                        },
                        {
                            anchor: '80%',
                            fieldLabel: '桌面菜单',
                            tooltip: '请选择桌面菜单',
                            xtype: "combo",
                            name: 'menuStartMenu',
                            store:  Wms.Dictionary.fn("FRAMEWORK_MENUISSHOW").load(),
                            valueField: 'id',
                            editable: false,
                            forceSelection: true,//必须选择一个选项
                            displayField: 'text',
                            allowBlank: false,
                            afterLabelTextTpl: me.required
                        },
                        {
                            allowBlank: false,
                            afterLabelTextTpl: me.required,
                            anchor: '80%',
                            fieldLabel: '开始菜单',
                            tooltip: '请选择开始菜单',
                            xtype: "combo",
                            name: 'menuQuickMenu',
                            store: Wms.Dictionary.fn("FRAMEWORK_MENUISSHOW").load(),
                            valueField: 'id',
                            editable: false,
                            forceSelection: true,//必须选择一个选项
                            displayField: 'text'
                        },
                        {
                            allowBlank: false,
                            afterLabelTextTpl: me.required,
                            anchor: '80%',
                            fieldLabel: '菜单状态',
                            tooltip: '请选择菜单状态',
                            xtype: "combo",
                            name: 'menuStatus',
                            store: Wms.Dictionary.fn("FRAMEWORK_MENUSTATE").load(),
                            valueField: 'id',
                            editable: false,
                            forceSelection: true,//必须选择一个选项
                            displayField: 'text'
                        },
                        {
                            anchor: '80%',
                            allowBlank: false,
                            afterLabelTextTpl: me.required,
                            fieldLabel: '优先级',
                            tooltip: '请输入菜单级别',
                            name: 'menuPriority',
                            maxLength: 5,
                            xtype : 'numberfield',
                            minValue: 1
                        },
                        {
                            allowBlank: false,
                            afterLabelTextTpl: me.required,
                            anchor:'80%',
                            fieldLabel: '父级',
                            tooltip: '请选择父级',
                            name: 'mainMenuId',
                            xtype:'treecombobox',
                            valueField: 'id',
                            displayField: 'text',
                            store:Ext.create('Wms.store.framework.TreeStore', {
                                model: "Wms.model.framework.TreeModel",
                                proxy: {
                                    type: 'ajax',
                                    url: 'role/getMenuTree.json?loadsubMenu=0&&showChecked=0'
                                }
                            }).load({
                                scope: this,
                                callback: function(records) {
                                    if(this.mainMenuId!=0){
                                        me.getForm().findField('mainMenuId').setValue(me.mainMenuId);
                                    }

                                }
                            }),
                            listeners:{
                                "select":function(combo, record,index){
                                    //this.setValue(me.mainMenuId)
                                }
                            }
                        },
                        /*{
                            afterLabelTextTpl: me.required,
                            anchor: '80%',
                            fieldLabel: '菜单状态',
                            xtype: "combo",
                            name: 'menuStatus',
                            store: Wms.Dictionary.fn("FRAMEWORK_MENUSTATE").load(),
                            valueField: 'id',
                            editable: false,
                            displayField: 'text',
                            allowBlank: false,
                            forceSelection: true,//必须选择一个选项
                            listeners:{
                                "afterrender":function(combo, record,index){
                                	if(me.menuId=="0")
                                    this.setValue("1")
                                }
                            }
                        },*/
                        {
                            anchor: '80%',
                            fieldLabel: '菜单Url',
                            maxLength: 255,
                            name: 'menuUrl',
                            tooltip: '请输入菜单Url'
                        },
                        {
                            anchor: '80%',
                            fieldLabel: '桌面菜单图标',
                            xtype: "combo",
                            name: 'menuIconcls',
                            store: Wms.Dictionary.fn("FRAMEWORK_DESKTOPICON").load(),
                            editable: false,
                            valueField: 'id',
                            displayField: 'text'
                        },
                        {
                            anchor: '80%',
                            fieldLabel: '开始菜单图标',
                            xtype: "combo",
                            name: 'menuSmallcls',
                            editable: false,
                            store:Wms.Dictionary.fn("FRAMEWORK_STARTICON").load(),//menuSmallcls
                            valueField: 'id',
                            displayField: 'text'
                        }
                    ]
                }
            ];

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
                            action: "menuItemSubmit",
                            // 验证表单中没有无效值时才会自动启动控钮
                            formBind:true,
                            margin: '0 15 0 0'
                            // handler:me.onMenuItemSubmit
                        },
                        {
                            xtype: "button",
                            text: '重  置',
                            action: "menuItemReset",
                            // 验证表单中没有无效值时才会自动启动控钮
                            // formBind:true,
                            margin: '0 15 0 0'
                            // handler:me.onMenuItemSubmit
                        }
                    ]
                }
            ];

            me.callParent();
        }

    });