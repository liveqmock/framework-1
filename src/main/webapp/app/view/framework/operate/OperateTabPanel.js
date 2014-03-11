
//新闻TabPane面板
Ext.define("Wms.view.framework.operate.OperateTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.operatetabpanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,
    initComponent:function(){

         this.items=[
             //新闻列表
             {
                 title:"操作列表",
                 layout:"border",
                 iconCls: 'small_operate',
                 items:[
                     {
                         region:"west",
                         width:220,
                         title:"操作查询",
                         xtype:"search",
                         split:true,
                         collapsible:true
                     },
                     {
                         region:"center",
                         flex:1,
                         xtype:"operatelist"
                     }
                 ]
             }

         ];
         this.callParent()
    }
});
