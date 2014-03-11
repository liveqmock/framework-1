
//新闻TabPane面板
Ext.define("Wms.view.framework.role.RoleTabPanel",{
    extend:"Ext.tab.Panel",
    alias:"widget.roletabpanel",

    //面板默认配置项
    resizeTabs: true,
    enableTabScroll: true,
    activeTab:0,

    //面板布局需要的组件
    requires:[
        "Wms.view.framework.role.RoleSearch",
        "Wms.view.framework.role.RoleList"
    ],


    initComponent:function(){

         this.items=[
             //新闻列表
             {
                 title:"角色列表",
                 layout:"border",
                 conCls: 'small_role',
                 items:[
                     {
                         region:"west",
                         width:220,
                         title:"角色搜索",
                         xtype:"roleserach",
                         split:true,
                         collapsible:true
                     },
                     {
                         region:"center",
                         flex:1,
                        xtype:"rolelist"
                     }
                 ]
             }

         ]




        this.callParent()
    }
});
