
//新闻TabPane面板
Ext.define("Wms.view.framework.config.ConfigTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.configtabpanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,


    //面板布局需要的组件
    requires:[
        "Wms.view.framework.config.ConfigList",
        "Wms.view.framework.config.ConfigSearch"
    ],


    initComponent:function(){

         this.items=[
            //主体区域为 configlist面板
            {
            	 title:"配置列表",
                 layout:"border",
                 conCls: 'small_config',
            	items:[
            	 	{
                         region:"west",
                         width:220,
                         title:"配置搜索",
                         xtype:"configserach",
                         split:true,
                         collapsible:true
                     },
                       {
                        region:"center",
                        flex:1,
                        xtype:"configlist"
                       }
                   ]
            }

         ]
        this.callParent()
    }
});
