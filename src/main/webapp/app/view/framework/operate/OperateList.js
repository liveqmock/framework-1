
//全部新闻数据Grid视图

Ext.define("Wms.view.framework.operate.OperateList",{
    extend:"Ext.grid.Panel",
    alias:"widget.operatelist",


    //面板默认配置
    overflowY:"auto",
    store: "framework.operate.OperateListStore",

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
                text: "操作ID",
                sortable: true,
                dataIndex: 'opId'
            },
            {
                text: "操作名",
                sortable: true,
                dataIndex: 'opName'
            },
            {
                text: "创建用户",
                sortable: true,
                dataIndex: 'createUser'
            },
            {
                text: "创建时间",
                width : 150,
                sortable: true,
                dataIndex: 'createDate',
                renderer:function(value){
                    var date=new Date(value*1);
                    return Ext.Date.format(date,'Y-m-d H:i:s')

                }
            },
            {
                text: "操作请求",
                sortable: true,
                dataIndex: 'opAction'
            },
            {
                text: "请求URL",
                sortable: true,
                dataIndex: 'opUrl'
            },
/*            {
                text: "按钮ID",
                sortable: true,
                dataIndex: 'btnId'
            },*/
            {
                text: "按钮状态",
                sortable: true,
                dataIndex: 'opIsBtn',
                renderer:function(value){
                    if(value==1){
                        return "正常";
                    }else{
                        return "灰显";
                    }
                }
            },
            {
                text: "对应菜单ID",
                sortable: true,
                dataIndex: 'menuItem',
                renderer:function(value){
                    return value.menuName;
                }
            }
        ];
        //底部工具条，分页条，或其它功能
        me.bbar={
            xtype: 'pagingtoolbar',
            displayInfo: true,
            store: "framework.operate.OperateListStore",
            emptyMsg:'没有记录'
        };


        //选中记录后，启用工具按钮。
        me.listeners={
            selectionchange:function(selType,records){
                var isSelect=!(records.length>0);
                if(records.length == 1){
                    this.down("toolbar button[action=editOperate]").setDisabled(false);
                    this.down("toolbar button[action=delOperate]").setDisabled(false);
                }else{
                    this.down("toolbar button[action=editOperate]").setDisabled(true);
                    if(records.length==0)
                    this.down("toolbar button[action=delOperate]").setDisabled(true);
                }
            }
        };
        me.callParent();
    }

});
