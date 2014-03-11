
//新闻TabPane面板
Ext.define("Wms.view.framework.log.operatelog.OperateLogPanel",{
    extend:"Ext.panel.Panel",
    alias:"widget.operateLogPanel",
    layout:"fit",
    //面板布局需要的组件
    requires:[
        "Wms.view.framework.log.operatelog.OperateLogSearch",
        "Wms.view.framework.log.operatelog.OperateLogList"
    ],
    initComponent:function(){

         this.items=[
             //登录日志列表
             {
                 layout:"border",
                 items:[
                     {
                         region:"west",
                         width:220,
                         title:"日志查询",
                         xtype:"operateLogSearch",
                         collapsed: false,
                         split:true,
                         collapsible:true
                     },
                     {
                         region:"center",
                         flex:1,
                         xtype:"operateLogList"
                     }
                 ]
             }

         ];
         this.callParent()
    }
});
