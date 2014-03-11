/**
 * 表单搜索
 */

Ext.define("Wms.view.framework.config.ConfigSearch",{
    //组件类型。
    extend:"Ext.form.Panel",

    //设置组件引用名称
    alias:"widget.configserach",

    //设置组件ID名，后面可使用Ext.getCmp("idName")快速获取的组件



    //搜索面板配置
    layout: 'form',
    url: 'config/list.json',
    bodyPadding: '5 5 0',

    fieldDefaults: {
        labelAlign: 'top',
        msgTarget: 'side'
    },

    autoScroll:true,

    //初始化组件，组件如果需要使用服务端的某些数据，应该写在这里。
    //如搜索表单中的combo需要加载服务器中的数据。
    initComponent:function(){

        this.items= [
            //标题，关键字
            {
                xtype:"textfield",
                fieldLabel: '键描述   ',
                name: 'keyDesc',
                allowBlank: true,
                emptyText:"请输入搜索关键字"
            },

            //分组
            {
                 xtype:"textfield",
                fieldLabel: '键   ',
                name: 'key',
                allowBlank: true,
                emptyText:"请输入搜索关键字"
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
                        text: '搜  索',
                        handler:function(){
                        	
                            if(this.up('form').getForm().isValid()){
								
                            	 var store=Ext.StoreManager.lookup("framework.config.ConfigListStore");
                                        var searchUrl=store.proxy.api.read;
                                        store.getProxy().extraParams=this.up('form').getForm().getValues();
                                        store.getProxy().api.read=searchUrl;
                                 		store.method='GET';
                                 		//store.load();
                                 		store.loadPage(1);
                                        //console.log(action)
//                                this.up('form').getForm().submit({
//
//                                    success: function(form, action){
//                                        var store=Ext.StoreManager.lookup("framework.config.ConfigListStore");
//                                        store.loadData(action.result.dataList);
//                                        console.log(action)
//
//                                    },
//                                    failure:function(form, action){
//                                        Ext.Msg.alert('Message', action.result.message);
//                                    }
//
//                                 });
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

            //说明内容
//            {
//                xtype:"textexplain"
//            }
        ]


        //必须添加此方法，执行初始化方法，才会有效果。
        this.callParent();
    }



    //如果业务只涉及到组件本身的操作（即事件处理）可直接写在视图中
    //如果业务涉及到多个组件的操作，则应该写在控制器中。

})
