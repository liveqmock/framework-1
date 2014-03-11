//新闻添加或编辑视图
Ext.define("Wms.view.framework.role.RoleEdit",{
    extend:"Ext.form.Panel",
    alias:"widget.roleeditview",

     id:"roleEditForm",

    //布局 ，垂直排列
    layout:{
        type:"vbox",
        align: 'stretch'
    },
    bodyPadding:5,
    //items内的子项默认样式。
    defaults:{
        margin:"0 0 10 0"
    },

    //items内表单项，默认配置
    fieldDefaults: {
        msgTarget: 'side',
        labelWidth:80,
        anchor:"0",
        labelSeparator:"："
    },

    roleId:0,
    parentRoleId:0,

    //当为编辑内容，点击重置，还原为最初的编辑内容，而非空值。
    trackResetOnLoad:true,
    //提交表单时提示语。
    waitTitle:"提交中，请稍候....",

    //自定义属性
    required :'<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',


    //组件初始化
    initComponent:function(){
        var me=this;

        me.items=[
            //表单区域 ，垂直布局，分成多个块
            //第一部分----纯表单
            {
                // title:"第一部分",
                //border:true,
                layout:"anchor",
                defaultType:"textfield",
                bodyPadding: '5 10',
                items:[
                    //角色ID
                    {
                        anchor:'20%',
                        fieldLabel: '角色ID  ',
                        name: 'roleId',
                        //使用disabled，roleId的值将无法传递到服务端，去掉则可以
                        disabled:true
                    //    xtype:"hiddenfield"
                    },
                    //角色名称
                    {
                        anchor:'80%',
                        fieldLabel: '角色名称',
                        afterLabelTextTpl:me.required,
                        name: 'roleName',
                        allowBlank: false,
                        tooltip: '请输入角色名称',
                        maxLength:30,
                        maxLengthtext:'名称不能超30字符'
                    },
                    //角色描述
                    {
                        anchor:'80%',
                        fieldLabel: '角色描述',
                        name: 'roleDsc',
                        tooltip: '请输入角色描述',
                        xtype:'textareafield',
                        maxLength:200,
                        maxLengthtext:'描述不能超过200字符'
                    },
                    // 角色菜单
                    {
                        id:'roleEditTree',
                        anchor:'80%',
                        fieldLabel: '拥有菜单',
                        name: 'roleMenu',
                        xtype:'checkboxtree',
                        store: Ext.create('Wms.store.framework.TreeStore', {
                            model: "Wms.model.framework.TreeModel",
                            proxy: {
                                type: 'ajax',
                                url: 'role/getMenuTree.json?roleId='+me.roleId+"&parentRoleId="+me.parentRoleId
                            }
                        })
                    }
                ]
            }


        ]

        //提交按钮
        me.dockedItems=[
            //第五部分------按钮区域
            {
                xtype:"toolbar",
                dock:"top",
                ui:"footer",
                layout:{pack:"center"},
                //margin:'20 0 10 95',
                items:[
                    //提交按钮
                    {
                        xtype:"button",
                        text: '提  交',
                        action:"roleEditSubmit",
                        //验证表单中没有无效值时才会自动启动控钮
                        formBind:true,
                        margin:'0 15 0 0'
                        // handler:me.onNewsSubmit
                    },
                    //重置按钮
                    {
                        xtype:"button",
                        action:"roleReset",
                        text:"重  置",
                        handler:function(){
                            Ext.getCmp("roleEditTree").store.load();
                        }
                    }
                ]
            }
        ] ;


        me.callParent() ;
    }


});
