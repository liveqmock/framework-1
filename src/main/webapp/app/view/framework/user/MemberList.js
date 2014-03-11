
//全部用户数据Grid视图

Ext.define("Wms.view.framework.user.MemberList",{
    extend:"Ext.grid.Panel",
    alias:"widget.memberlist",


    //面板默认配置
    overflowY:"auto",
    store: "framework.user.UserStore",



    initComponent:function(){
        var me=this;
         //创建RowEditing插件
/*        me.rowEditing=Ext.create('Ext.grid.plugin.RowEditing', {
            //单击取消退出编辑
            autoCancel:false,
            saveBtnText: '保存',
            cancelBtnText: '取消',
            errorsText:'错误',
            dirtyText:"确认或取消更改"
        });

        me.plugins=[me.rowEditing];*/
            /* btn1=  {
                 text:"删除",
                 disabled:true,
                 id:"memberdelbutton",
                 action:"delMember",
                 handler:function(){

                 }
             },

            btn2=  {
                text:"添加" ,
                action:"addMember",
                id:"memberaddbutton",
                handler:function(){

                }
            },

            btn3=  {
                text:"编辑" ,
                disabled:true,
                id:"membereditbutton",
                action:"editMember",
                handler:function(){

                }
            },

            //操作按钮
           buttons=[btn1,"->",btn2,btn3],
*/
            //选择模式
            selectCheckBox={
                selType : 'checkboxmodel'
            };


        //根据登录用户类型userType设置可操作的button
       // userType=Wms.curUser.roles;

//        if(userType!=0){
//            buttons=['->',btn2,btn3];
//            selectCheckBox=null;
//        }





        //头部工具栏，操作按钮
        me.tbar=[];

        //删除选择控制
        me.selModel=selectCheckBox;

        //Gird列  ----给用户名，email,角色 允许登录  添加 编辑功能
        me.columns=[
            {
                text: '用户编号', dataIndex: 'uid' ,
                editor: { allowBlank: false}
            },
            {
                text: '用户名', dataIndex: 'userName' ,
                editor: { allowBlank: false}
            },

            {
                text: '电子邮件', dataIndex: 'email' ,
                editor: { allowBlank: false, vtype: 'email' }
            },
            
            {
                text: '分组编号', dataIndex: 'group.groupId' 
            },
            
            {
                text: '分组', dataIndex: 'group.groupName' 
            },
            
            {
                text: '角色编号', dataIndex: 'role.roleId' 
            },

            {
                text: '角色', dataIndex: 'role.roleName'
            },
            {
                text: '注册IP', dataIndex: 'regIP'
            },

            {
                text: '注册时间', dataIndex: 'regDate',
                width : 150,
                renderer:function(value){
                	//var str="1380275436000" ;
                	//Ext.Msg.alert("",value);
            		var date=new Date(value*1);
            		//Ext.Msg.alert("",Ext.Date.format(date,'Y-m-d H:i:s'))
					return Ext.Date.format(date,'Y-m-d H:i:s')
	
                }
            }
        
        ];
  		//底部分页条
        me.bbar={
            xtype: "pagingtoolbar",
            
            displayInfo: true,
            store: "framework.user.UserStore",
   			//displayMsg:'显示第{0}条到{1}条记录，一共{2}条',
   			emptyMsg:'没有记录' 
        }

        //选中记录后，启用工具按钮。
        me.listeners={
            selectionchange:function(selType,records){
                var isSelect=!(records.length>0);
                this.down("toolbar button[action=delMember]").setDisabled(isSelect);	
                if(records.length==1){
                	 this.down("toolbar button[action=editMember]").setDisabled(false);
                }
                if(records.length>1){
                	this.down("toolbar button[action=editMember]").setDisabled(true);
                }
            }
        }




        me.callParent();
    }

})
