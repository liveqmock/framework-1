
//新闻TabPane面板
Ext.define("Wms.view.framework.user.MemberTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.membertabpanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,

    //面板布局需要的组件
    requires:[
        "Wms.view.framework.user.MemberSearch",
        "Wms.view.framework.user.MemberList"
    ],


    initComponent:function(){

         this.items=[
             //新闻列表
             {
                 title:"管理员列表",
                 layout:"border",
                 conCls: 'small_gm',
                 items:[
                     {
                         region:"west",
                         width:220,
                         title:"管理员搜索",
                         xtype:"memberserach",
                         split:true,
                         collapsible:true
                     },
                     {
                         region:"center",
                         flex:1,
                        xtype:"memberlist"
                     }
                 ]
             }

         ]




        this.callParent()
    }
});
