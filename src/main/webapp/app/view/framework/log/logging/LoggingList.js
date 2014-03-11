
//全部新闻数据Grid视图

Ext.define("Wms.view.framework.log.logging.LoggingList",{
    extend:"Ext.grid.Panel",
    alias:"widget.loggingList",
    //面板默认配置
    store: "framework.log.LoggingListStore",

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
                dataIndex: 'logId'
            },
            {
                text: "用户ID",
                sortable: true,
                dataIndex: 'logUid'
            },
            {
                text: "用户名",
                sortable: true,
                dataIndex: 'logName'
            },
            {
                text: "手机",
                sortable: true,
                dataIndex: 'logMobile'
            },
            {
                text: "IP地址",
                sortable: true,
                dataIndex: 'logIp'
            },
            {
                text: "时间",
                width : 150,
                sortable: true,
                dataIndex: 'logDate',
                renderer:function(value){
                    var date=new Date(value*1);
                    return Ext.Date.format(date,'Y-m-d H:i:s')

                }
            },
            {
                text: "业务系统ID",
                sortable: true,
                dataIndex: 'logSiteId'
            },
            {
                text: "业务系统名",
                sortable: true,
                dataIndex: 'logSiteName'
            },
            {
                text: "应用类型",
                sortable: true,
                dataIndex: 'logClientType'
            },
            {
                text: "日志信息",
                sortable: true,
                dataIndex: 'logInfo'
            }
        ];
        //底部工具条，分页条，或其它功能
        me.bbar={
            xtype: 'pagingtoolbar',
            displayInfo: true,
            store: "framework.log.LoggingListStore",
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
