/**
 * 新闻中心主视图
 */

Ext.define("Wms.view.framework.role.View",{
    extend:"Ext.panel.Panel",
    alias:"widget.roleview",

    //布局时，需要加载的视图组件
    requires:[
        "Wms.view.framework.role.RoleTabPanel"
    ],


    //自适应容器
    layout:"fit",





    //初始化视图，布局开始
    initComponent:function(){
        this.items=[
            //主体区域为 tabpanel面板
            {
                xtype:"roletabpanel",
                flex:1
            }
        ];

        this.callParent();
    }


});
