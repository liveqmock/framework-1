/**
 * 表单搜索
 */

Ext.define("Wms.view.framework.log.logging.LoggingSearch",{
    extend:"Ext.form.Panel",
    alias:"widget.loggingSearch",
    stores: ["framework.log.LoggingListStore"],
    layout: 'form',
    bodyPadding: '5 5 0',
    fieldDefaults: {
        labelAlign: 'top',
        msgTarget: 'side'
    },
    autoScroll:true,
    initComponent:function(){

        this.items= [
            {
                xtype:"textfield",
                fieldLabel: '用户名',
                name: 'logName'
            },
            {
                xtype:"textfield",
                fieldLabel: 'IP',
                name: 'logIp'
            },
            {
                xtype:"textfield",
                fieldLabel: '信息关键字',
                name: 'logInfo'
            },
            {
                xtype:"textfield",
                fieldLabel: '业务系统',
                name: 'logSiteName'
                //allowBlank: false,//表示必须输入
                //emptyText:"请输入搜索关键字"
            },
            //发布时间设置区域
            {
                xtype:'fieldset',
                title: '记录时间',
                collapsible: true,
                anchor: '100%',
                items:[
                    {
                        xtype:"datefield",
                        anchor: '100%',
                        format : 'Y-m-d',//日期格式
                        fieldLabel:"起始时间",
                        name:"startDate",
                        editable : false
                    },
                    {
                        xtype:"datefield",
                        anchor: '100%',
                        format : 'Y-m-d',//日期格式
                        fieldLabel:"结束时间",
                        name:"endDate" ,
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
                            //这里进行表单验证
                            if(this.up('form').getForm().isValid()){
                                 var listStore=Ext.StoreManager.lookup("framework.log.LoggingListStore");
                                 var searchUrl=listStore.proxy.api.read;

                                 //传参
                                 listStore.getProxy().extraParams=this.up('form').getForm().getValues();

                                 //改变请求的url地址
                                 listStore.getProxy().api.read=searchUrl;
                                 listStore.method='GET';
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
