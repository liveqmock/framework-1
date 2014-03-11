
//新闻TabPane面板
Ext.define("Wms.view.framework.menuItem.MenuItemTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.menuItemtabpanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,


    //面板布局需要的组件
    requires:[
        "Wms.view.framework.menuItem.MenuSearch",
        "Wms.view.framework.menuItem.MenuItemList"
    ],


    initComponent:function(){
         var me = this;
         me.items=[
             //新闻列表
             {
                 title:"菜单列表",
                 layout:"border",
                 iconCls: 'small_menu',
                 items:[
                     {
                         region:"west",
                         width:220,
                         title:"菜单查询",
                         xtype:"menuSearch",
                         split:true,
                         collapsible:true
                     },
                     {
                         region:"center",
                         flex:1,
                         xtype:"menuItemlist"
                     }
                 ]
             }

         ];

        this.callParent()
    }
});
