/**
 * 表单搜索
 */

Ext.define("Wms.view.framework.menuItem.MenuSearch",{
    //组件类型。
    extend:"Ext.form.Panel",
    stores: ["framework.menuItem.MenuItemListStore"],
    //设置组件引用名称
    alias:"widget.menuSearch",

    //设置组件ID名，后面可使用Ext.getCmp("idName")快速获取的组件
   // id:"menuItemNavMenu",


    //搜索面板配置
    layout: 'form',


    bodyPadding: '5 5 0',

    fieldDefaults: {
        labelAlign: 'top',
        msgTarget: 'side'
    },

    autoScroll:true,

    //初始化组件，组件如果需要使用服务端的某些数据，应该写在这里。
    //如搜索表单中的combo需要加载服务器中的数据。
    initComponent:function(){
    	 var mainStatus = new Ext.data.SimpleStore({
             fields: ['id', 'text'],
             data: [
                 ['1', '启用'],
                 ['0', '禁用']
             ]
         });

        this.items= [
            //标题，关键字
            {
                xtype:"textfield",
                fieldLabel: '菜单名称',
                name: 'menuName',
                maxLength:50,
                maxLengthText:'最多输入50个字符'
                //allowBlank: false,//表示必须输入
                //emptyText:"请输入搜索关键字"
            },

            //新闻类型
            {
                fieldLabel: '菜单状态',
                xtype:"combo",
                name: 'menuStatus',
                store:mainStatus,
                valueField: 'id',
                displayField: 'text',
                editable: false
            },
            //发布时间设置区域
            {
                xtype:'fieldset',
                title: '创建时间',
                collapsible: true,
                anchor: '100%',
                items:[
                    {
                        xtype:"datefield",
                        anchor: '100%',
                        fieldLabel:"起始时间",
                        name:"startDate",
                        format : 'Y-m-d',//日期格式
                        editable : false
                    },
                    {
                        xtype:"datefield",
                        anchor: '100%',
                        fieldLabel:"结束时间",
                        name:"endDate" ,
                        format : 'Y-m-d',//日期格式
                        editable : false
                    }
                ]
            },

            //按钮区域
            {
                xtype: 'container',
                layout: {
                    type:"hbox",
                    pack: 'center',
                    defaultMargins: {top: 0, right: 5, bottom: 0, left: 5}
                },
                defaults: {
                    hideLabel: true,
                    height:24,
                    width:74
                },
                items: [
                    //提交按钮
                    {
                        xtype:"button",
                        text: '查  询',
                        // 验证表单中没有无效值时才会自动启动控钮
                        formBind:true,
                        handler:function(){
                            var beginDateTime=this.up('form').getForm().findField('startDate').getValue();
                            var endDateTime=this.up('form').getForm().findField('endDate').getValue();
                            if(beginDateTime != null && endDateTime != null){
	                            if(beginDateTime > endDateTime){
	                                Ext.MessageBox.alert({
	                                    title: '提示',
	                                    msg: '起始日期大于结束日期，请重新输入。',
	                                    buttons: Ext.MessageBox.OK,
	                                    icon: Ext.MessageBox.ERROR
	                                });
	                                return false;
	                            }
                            }
                            if(this.up('form').getForm().isValid()){
                                var listStore=Ext.StoreManager.lookup("framework.menuItem.MenuItemListStore");
                                var searchUrl=listStore.proxy.api.read;

                                //传参
                                listStore.getProxy().extraParams=this.up('form').getForm().getValues();

                                //改变请求的url地址
                                listStore.getProxy().api.read=searchUrl;
                                listStore.method='GET';
                                //listStore.load();
                                listStore.loadPage(1);

                            }
                        }
                    },

                    //重置按钮
                    {
                        xtype:"button",
                        text:"重  置",
                        handler:function(){
                            this.up("form").getForm().reset();
                        }
                    }
                ]
            }
        ];

        //必须添加此方法，执行初始化方法，才会有效果。
        this.callParent();
    }



    //如果业务只涉及到组件本身的操作（即事件处理）可直接写在视图中
    //如果业务涉及到多个组件的操作，则应该写在控制器中。

});
