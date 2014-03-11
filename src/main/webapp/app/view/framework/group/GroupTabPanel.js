
//新闻TabPane面板
Ext.define("Wms.view.framework.group.GroupTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.grouptabpanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,

    //面板布局需要的组件
    requires:[
        "Wms.view.framework.group.GroupSearch",
        "Wms.view.framework.group.GroupList"
    ],


    initComponent:function(){

         this.items=[
             //新闻列表
             {
                 title:"分组列表",
                 layout:"border",
                 conCls: 'small_group',
                 items:[
                     {
                         region:"west",
                         width:220,
                         title:"分组搜索",
                         xtype:"groupserach",
                         split:true,
                         collapsible:true
                     },
                     {
                         region:"center",
                         flex:1,
                        xtype:"grouplist"
                     }
                 ]
             }

         ]




        this.callParent()
    }
});
