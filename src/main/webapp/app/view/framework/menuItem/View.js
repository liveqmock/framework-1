/**
 * 新闻中心主视图
 */

Ext.define("Wms.view.framework.menuItem.View",{
    extend:"Ext.panel.Panel",
    alias:"widget.menuitemview",

    //布局时，需要加载的视图组件
    requires:[
        "Wms.view.framework.menuItem.MenuItemTabPanel",
        "Wms.view.framework.menuItem.MenuItemAddEdit",
        "Wms.view.framework.util.CheckboxTree",
        "Wms.view.framework.util.TreeComboBox"
    ],


    //自适应容器
    layout:"fit",

    //初始化视图，布局开始
    initComponent:function(){

        var me =this;

        me.items=[
            //主体区域为 tabpanel面板
            {
                xtype:"menuItemtabpanel",
                flex:1
            }
        ];
        me.tbar={
            xtype:"toolbar",
            defaultType:"button",
            style:"padding-left:0",
            //菜单按钮默认样式设置
            defaults: {
                cls:"navMenu"
            },
            cls:"navMenuBox",
            items:[]
        };


        this.callParent();
    }


});
