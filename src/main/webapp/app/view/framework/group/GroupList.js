
//全部分组数据Grid视图

Ext.define("Wms.view.framework.group.GroupList",{
    extend:"Ext.grid.Panel",
    alias:"widget.grouplist",


    //面板默认配置
    overflowY:"auto",
    store: "framework.group.GroupStore",

    initComponent:function(){
        var me=this,
            //选择模式
            selectCheckBox={
                selType : 'checkboxmodel'
            };

		me.tbar=[];

        var groupType=Wms.Dictionary.fn("FRAMEWORK_GROUPTYPE").load();
        //根据登录用户类型userType设置可操作的button
        userType=Wms.curUser.roles;

        if(userType!=0){
      //      buttons=['->',btn2,btn3];
            selectCheckBox=null;
        }
        //头部工具栏，操作按钮
      //  me.tbar=buttons;
        
        //删除选择控制
        me.selModel=selectCheckBox;

        //表头，列表标题
        me.columns=[
          //  new Ext.grid.RowNumberer(),
            {
                text: "ID",
                sortable: true,
                dataIndex: 'groupId'
            },
            {
                text: "分组名称",
                sortable: true,
                dataIndex: 'groupName'
            },
            {
                text: "分组类型",
                sortable: true,
                dataIndex: 'groupType',
                renderer:function(value){
                    var megroup="";
                    Ext.each(groupType.data.items,function(obj,index){
                        if(obj.get("id")==value)
                            megroup=obj.get("text");
                    });
                    return megroup;
                }
            },
            {
                text: "分组描述",
                sortable: true,
                dataIndex: 'description'
            },
            {
                text: "创建者",
                sortable: true,
                dataIndex: 'username'
            },
            {
                text: "创建日期",
                width: 150,
                sortable: true,
                dataIndex: 'date',
                renderer:function(value){
                    var date=new Date(value*1);
                    return Ext.Date.format(date,'Y-m-d H:i:s')

                }
            },
            {
                text: "父ID",
                sortable: true,
                dataIndex: 'parentGroupId'
            }
        ];


        //底部工具条，分页条，或其它功能
        me.bbar={
                xtype: 'pagingtoolbar',
                displayInfo: true,
                store: "framework.group.GroupStore",
                emptyMsg: "无内容"
            }
        //选中记录后，启用工具按钮。
        me.listeners={
            selectionchange:function(selType,records){
                var isSelect=!(records.length>0);
                if(this.down("toolbar button[action=editGroup]") != undefined)
                	this.down("toolbar button[action=editGroup]").setDisabled(isSelect);
                if(this.down("toolbar button[action=delGroup]") != undefined)
                	this.down("toolbar button[action=delGroup]").setDisabled(isSelect);
            }
        }

        me.callParent();
    }

})
