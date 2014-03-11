/**
 * 表单搜索
 */

Ext.define("Wms.view.framework.user.MemberSearch",{
    //组件类型。
    extend:"Ext.form.Panel",

    //设置组件引用名称
    alias:"widget.memberserach",

    //设置组件ID名，后面可使用Ext.getCmp("idName")快速获取的组件
   // id:"newsNavMenu",

//	url: 'member/findUser/0.json',


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

        this.items= [
            //标题，关键字
            {
                xtype:"textfield",
                fieldLabel: '用户名   ',
                name: 'userName',
                allowBlank: true,
                emptyText:"请输入搜索关键字"
            },

            //分组
            {
                xtype:"combo",
                fieldLabel: '分组',
                name: 'group.groupId',
                store:"framework.user.UserGroupStore",
                emptyText:'请选择分组',
             	blankText : '请选择分组',
             	valueField:'groupId', 
                displayField:"groupName"
            },
            // 当前页
			/*{
				anchor : '30%',
				xtype:"hiddenfield",
                value:1,
				name : 'page'
			},*/

            //时间设置区域
            {
                xtype:'fieldset',
                title: '创建时间',
                collapsible: true,
                anchor: '100%',
                items:[
                    {
                        xtype:"datefield",
                        id:'starttime',
                        anchor: '100%',
                        fieldLabel:"起始时间",
                        format : 'Y-m-d',//日期格式
                        name:"regDateStart",
                        editable : false
                    },
                    {
                        xtype:"datefield",
                        id:'endtime',
                        anchor: '100%',
                        fieldLabel:"结束时间",
                        format : 'Y-m-d',//日期格式
                        name:"regDateEnd",
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
                        text: '搜  索',
                        // 验证表单中没有无效值时才会自动启动控钮
                        formBind:true,
                        handler:function(){
                            var beginDateTime=this.up('form').getForm().findField('regDateStart').getValue();
                            var endDateTime=this.up('form').getForm().findField('regDateEnd').getValue();
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
								var dateOne = Ext.getCmp('starttime').getValue();
								var dateTwo = Ext.getCmp('endtime').getValue();
								if(Date.parse(dateOne) > Date.parse(dateTwo)){
									Ext.Msg.alert("提示","开始日期不能大于结束日期");
									return;
								}
                                var listStore=Ext.StoreManager.lookup("framework.user.UserStore");
                                var searchUrl=listStore.proxy.api.read;

                                //传参
                                listStore.getProxy().extraParams=this.up('form').getForm().getValues();

                                //改变请求的url地址
                                listStore.getProxy().api.read=searchUrl;
                                listStore.method='GET';
                               // listStore.load();
                                listStore.loadPage(1);

                            }
                        	
                            /*if(this.up('form').getForm().isValid()){

                                //this.up('form').getForm().submit({

                                    //success: function(form, action){
                                        var store=Ext.StoreManager.lookup("framework.user.UserStore");
                                        var searchUrl=store.proxy.api.read;
                                        store.getProxy().extraParams=this.up('form').getForm().getValues();
                                        //改变请求的url地址
                                 		store.getProxy().api.read=searchUrl;
                                 		store.method='GET';
                                        //store.loadData(action.result.dataList);
                                 		store.load();
                                        console.log(action)

//                                    },
//                                    failure:function(form, action){
//                                       //Ext.Msg.alert('Message', "系统异常");
//                                    }

                                 //}
                                // );
                            }*/
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
