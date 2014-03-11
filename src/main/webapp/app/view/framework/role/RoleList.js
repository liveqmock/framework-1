
//全部新闻数据Grid视图

Ext.define("Wms.view.framework.role.RoleList",{
    extend:"Ext.grid.Panel",
    alias:"widget.rolelist",


    //面板默认配置
    overflowY:"auto",
    store: "framework.role.RoleListStore",

    initComponent:function(){
        var me=this,
        //选择模式
        selectCheckBox={
            selType : 'checkboxmodel'
        };
        //根据登录用户类型userType设置可操作的button
        userType=Wms.curUser.roles;

        if(userType!=0){
            selectCheckBox=null;
        }
        //头部工具栏，操作按钮
      //  me.tbar=buttons;
        me.tbar=[];
        //删除选择控制
        me.selModel=selectCheckBox;

        //表头，列表标题
        me.columns=[
          //  new Ext.grid.RowNumberer(),
            {
                text: "ID",
                width: 50,
                sortable: true,
                dataIndex: 'roleId'
            },
            {
                text: "角色名称",
                width: 160,
                sortable: true,
                dataIndex: 'roleName'
              //  action:'queryRole'
            },
            {
                text: "角色描述",
                width: 160,
                sortable: true,
                dataIndex: 'roleDsc'
            },
            {
                text: "创建者",
                width: 160,
                sortable: true,
                dataIndex: 'createUser'
            },
            {
                text: "创建日期",
                width: 150,
                sortable: true,
                dataIndex: 'createDate',
                renderer:function(value){
                    var date=new Date(value*1);
                    return Ext.Date.format(date,'Y-m-d H:i:s')
                }
            },
            {
                width: 50,
                hidden:true,
                dataIndex: 'parentRoleId',
                hideable: false
            }
        ];
        me.bbar =new Ext.PagingToolbar({
            store: "framework.role.RoleListStore",
            style:{
                border:"none"
            },
            displayInfo: true,
            emptyMsg: "无内容",
            //显示右下角信息
            displayInfo: true,
//            displayMsg: '当前记录 {0} -- {1} 条 共 {2} 条记录',
            emptyMsg: "没有数据显示",
            prevText: "上一页",
            nextText: "下一页",
            refreshText: "刷新",
            lastText: "最后页",
            firstText: "第一页",
            beforePageText: "当前页",
            afterPageText: "共{0}页"
    });

            //选中记录后，启用工具按钮。
        me.listeners={
            selectionchange:function(selType,records){
                var isSelect=!(records.length>0);
                if(this.down("toolbar button[action=delRole]") != undefined)
                	this.down("toolbar button[action=delRole]").setDisabled(isSelect);
                if(this.down("toolbar button[action=editRole]") != undefined)
                	this.down("toolbar button[action=editRole]").setDisabled(isSelect);
                
                if(this.down("toolbar button[action=addRole]") != undefined && !isSelect)
                	this.down("toolbar button[action=addRole]").setText("添加子角色");
                else
                	this.down("toolbar button[action=addRole]").setText("添加角色");

                Ext.getCmp("querySubRoleButton").setDisabled(isSelect);
            }
        }




        me.callParent();
    }

})
