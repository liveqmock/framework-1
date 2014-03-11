//分组添加
Ext.define("Wms.view.framework.group.GroupAdd",{
    extend:"Ext.form.Panel",
    alias:"widget.groupaddview",

    id:"groupAddView",


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
    //角色树store
    store:Ext.create('Wms.store.framework.TreeStore', {
        model: "Wms.model.framework.TreeModel",
        proxy: {
            type: 'ajax',
            url: 'role/getRoleTree.json'
        },
        autoLoad: true
    }),
    //items内表单项，默认配置
    fieldDefaults: {
        msgTarget: 'side',
        labelWidth:80,
        anchor:"0",
        labelSeparator:"："
    },

   //表单数据提交处理
/*
    api:{
        submit:"_news/edit/inNewsView"
    },
*/

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
                     {
                         anchor:'30%',
                         fieldLabel: '父分组',
                         name: 'parentGroupId',
                         xtype:'treecombobox',
                         afterLabelTextTpl:me.required,
                         allowBlank: false,
                         editable:false,
                         valueField: 'id',
                         displayField: 'text',
                       
                         store:Ext.create('Wms.store.framework.TreeStore', {
                             model: "Wms.model.framework.TreeModel",
                             proxy: {
                                 type: 'ajax',
                                 url: 'group/getGroupTree.json'
                             }
                         }).load()
                     },
                  //角色名称
                    {
                        anchor:'80%',
                        fieldLabel: '分组名称',
                        afterLabelTextTpl:me.required,
                        name: 'groupName',
                        allowBlank: false,
                        tooltip: '请输入分组名称',
                        maxLength:30,
                        maxLengthtext:'名称不能超过30字符'
                    },
                     //分组类型
                     {
                         anchor:'30%',
                         fieldLabel: '分组类型',
                         xtype:"combo",
                         afterLabelTextTpl:me.required,
                         allowBlank:false,
                         name: 'groupType',
                         editable:false,
                         store:Wms.Dictionary.fn("FRAMEWORK_GROUPTYPE"),
                         displayField: 'text',
                         valueField:'id',
                         emptyText:"--请选择--"

                     },

                    //角色描述
                    {
                        anchor:'80%',
                        fieldLabel: '分组描述',
                        name: 'description',
                        tooltip: '请输入分组描述',
                        xtype:'textareafield',
                        maxLength:200,
                        maxLengthtext:'描述不能超过200字符'
                    },
                     // 角色树
                     {
                         anchor:'80%',
                         name:'checkboxtree',
                         fieldLabel: '拥有角色',
                         xtype:'checkboxtree',
                         parentNodeStatus:false,
                         subNodeStatus:false,
                         // store:'TreeStore'
                         store:me.store
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
                        action:"groupAddSubmit",
                        //验证表单中没有无效值时才会自动启动控钮
                        formBind:true,
                        margin:'0 15 0 0'
                       // handler:me.onNewsSubmit
                    },
                    //重置按钮
                    {
                        xtype:"button",
                        action:"groupReset",
                        text:"重  置",
                       handler:function(){
                           me.store.load();
                       }
                    }
                ]
            }
        ] ;


        me.callParent() ;
    }


});
