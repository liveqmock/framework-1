
//全部新闻数据Grid视图

Ext.define("Wms.view.framework.log.operatelog.OperateLogList",{
    extend:"Ext.grid.Panel",
    alias:"widget.operateLogList",


    //面板默认配置
    store: "framework.log.OperateLogListStore",

    initComponent:function(){
        var me=this;

        //选择模式
        var selectCheckBox={
            selType : 'checkboxmodel'
        };

        //头部工具栏，操作按钮
        me.tbar=[];

        //删除选择控制
        me.selModel=selectCheckBox;

        //表头，列表标题
        me.columns=[
            {
                text: "日志ID",
                sortable: true,
                dataIndex: 'opId'
            },
            {
                text: "类型",
                sortable: true,
                dataIndex: 'opName'
            },
            {
                text: "用户ID",
                sortable: true,
                dataIndex: 'uid'
            },
            {
                text: "用户名",
                sortable: true,
                dataIndex: 'name'
            },
            {
                text: "日期",
                width : 150,
                sortable: true,
                dataIndex: 'dateline',
                renderer:function(value){
                    var date=new Date(value*1);
                    return Ext.Date.format(date,'Y-m-d H:i:s')

                }
            },
//            {
//                text: "业务系统ID",
//                sortable: true,
//                dataIndex: 'siteId'
//            },
            {
                text: "业务系统名",
                sortable: true,
                dataIndex: 'siteName'
            },
            {
                text: "信息",
                sortable: true,
                dataIndex: 'message'
            }
        ];
        //底部工具条，分页条，或其它功能
        me.bbar={
            xtype: 'pagingtoolbar',
            displayInfo: true,
            store: "framework.log.OperateLogListStore",
            emptyMsg:'没有记录'
        };


        //选中记录后，启用工具按钮。
//        me.listeners={
//            selectionchange:function(selType,records){
//                var isSelect=!(records.length>0);
//                if(records.length == 1){
//                    this.down("toolbar button[action=editOperate]").setDisabled(false);
//                    this.down("toolbar button[action=delOperate]").setDisabled(false);
//                }else{
//                    this.down("toolbar button[action=editOperate]").setDisabled(true);
//                    if(records.length==0)
//                    this.down("toolbar button[action=delOperate]").setDisabled(true);
//                }
//            }
//        };
        me.callParent();
    }

});
