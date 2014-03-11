//新闻添加或编辑视图
Ext.define(
    "Wms.view.framework.operate.OperateAddEdit",
    {
        extend : "Ext.form.Panel",
        alias : "widget.operateaddedit",

        // 布局 ，垂直排列
        layout : {
            type : "vbox",
            align : 'stretch'
        },
        bodyPadding : 5,
        // items内的子项默认样式。
        defaults : {
            margin : "0 0 10 0"
        },
        // items内表单项，默认配置
        fieldDefaults : {
            msgTarget : 'side',
            labelWidth : 80,
            anchor : "0",
            labelSeparator : "："
        },
        //面板需要的组件
        requires: [
            "Wms.view.framework.util.CheckboxTree",
            'Wms.view.framework.util.TreeComboBox'
        ],
        // 表单数据提交处理
        /*
         * api:{ submit:"operate/edit/inOperateView" },
         */

        // 当为编辑内容，点击重置，还原为最初的编辑内容，而非空值。
        trackResetOnLoad : true,
        // 提交表单时提示语。
        waitTitle : "提交中，请稍候....",

        // 自定义属性
        required : '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',


        // 组件初始化
        initComponent : function() {
            var me = this;
            var btnState=new Ext.data.SimpleStore({
                fields : ['id', 'text'],
                data : [['1', '正常'], ['0', '灰显']]
            });
            me.items = [
                // 表单区域 ，垂直布局，分成多个块
                // 第一部分----纯表单
                {
                    // title:"第一部分",
                    // border:true,
                    layout : "anchor",
                    defaultType : "textfield",
                    bodyPadding : '5 10',
                    items : [
                        {
                            anchor: '20%',
                            xtype: "hiddenfield",
                            fieldLabel: '操作ID',
                            name: 'opId'

                        },
                        {
                            anchor : '80%',
                            fieldLabel : '操作名称',
                            afterLabelTextTpl : me.required,
                            name : 'opName',
                            allowBlank : false,
                            maxLength: 30,
                            tooltip : '请输入操作名称'
                        },

                        {
                            anchor : '80%',
                            fieldLabel : '操作请求',
                            afterLabelTextTpl : me.required,
                            name : 'opAction',
                            maxLength: 30,
                            allowBlank : false,
                            tooltip : '请输入请求地址'
                        },

                        {
                            anchor : '80%',
                            fieldLabel : '请求Url',
                            afterLabelTextTpl : me.required,
                            name : 'opUrl',
                            maxLength: 250,
                            allowBlank : false,
                            tooltip : '请输入请求Url'
                        },

                        {
                            anchor : '80%',
                            fieldLabel : '优先级',
                            afterLabelTextTpl : me.required,
                            name : 'opPriority',
                            xtype : 'numberfield',
                            maxLength: 5,
                            allowBlank : false,
                            minValue: 1,
                            tooltip : '请输入请求Url'
                        },

/*                        {
                            anchor : '80%',
                            fieldLabel : '按钮Id',
                            afterLabelTextTpl : me.required,
                            name : 'btnId',
                            maxLength: 30,
                            allowBlank : false,
                            tooltip : '请输入按钮Id'
                        },*/
                        {
                            afterLabelTextTpl : me.required,
                            anchor : '80%',
                            fieldLabel: '按钮状态',
                            xtype:"combo",
                            name: 'opIsBtn',
                            store:btnState,
                            valueField : 'id',
                            editable : false,
                            displayField: 'text',
                            allowBlank : false,
                            forceSelection:true//必须选择一个选项
                        },
                        /*                                {
                         afterLabelTextTpl : me.required,
                         anchor : '80%',
                         fieldLabel: '菜单',
                         xtype:"combo",
                         name: 'opMenuId',
                         store:'framework.operate.SubMenuStore',
                         valueField : 'menuId',
                         editable : false,
                         displayField: 'menuName',
                         allowBlank : false,
                         enabled:true,
                         forceSelection:true//必须选择一个选项
                         },*/
                        {
                            afterLabelTextTpl: me.required,
                            allowBlank : false,
                            anchor:'80%',
                            fieldLabel: '菜单',
                            name: 'opMenuId',
                            valueField : 'id',
                            cls:"cls",
                            displayField: 'text',
                            xtype:'treecombobox',
                            store:Ext.create('Wms.store.framework.TreeStore', {
                                model: "Wms.model.framework.TreeModel",
                                proxy: {
                                    type: 'ajax',
                                    url: 'role/getMenuTree.json?loadOperate=0&showChecked=0'
                                }
                            }).load({
                                    scope: this,
                                    callback: function() {
                                        if(this.opMenuId!="0"){
                                            me.getForm().findField('opMenuId').setValue(me.opMenuId);
                                        }

                                    }
                                }),
                            listeners:{
                                "renderer":function(value){
                                    alert(value);
                                },
                                "select":function(combo, record){
                                    if(record[0].data.leaf==""||!record[0].data.leaf){
                                        Ext.Msg.alert("操作提示","该菜单上不能添加操作");
                                        this.setValue("");
                                    }
                                }
                            }
                        },
                        {
                            anchor : '80%',
                            fieldLabel : '操作描述',
                            xtype: 'htmleditor',
                            height:300,
                            name : 'opDesc',
                            tooltip : '请输入菜单Id'
                        }
                    ]
                }];



            // 提交按钮
            me.dockedItems = [
                // 第五部分------按钮区域
                {
                    xtype : "toolbar",
                    dock : "top",
                    ui : "footer",
                    layout : {
                        pack : "center"
                    },
                    // margin:'20 0 10 95',
                    items : [
                        // 提交按钮
                        {
                            xtype : "button",
                            text : '提  交',
                            action : "operateSubmit",
                            // 验证表单中没有无效值时才会自动启动控钮
                            formBind:true,
                            margin : '0 15 0 0'
                            // handler:me.onOperateSubmit
                        },
                        // 重置按钮
                        {
                            xtype : "button",
                            action : "operateReset",
                            text : "重  置"
                            /*
                             * handler:function(){ me.up("form").form.reset(); }
                             */
                        } ]
                } ];

            me.callParent();
        }

    });
