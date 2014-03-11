
//新闻TabPane面板
Ext.define("Wms.view.framework.log.LogTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.logTabPanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,
    //面板布局需要的组件
    requires: [
        "Wms.view.framework.log.logging.LoggingPanel",
        "Wms.view.framework.log.operatelog.OperateLogPanel"
    ],
    initComponent:function(){
         this.callParent()
    }
});
