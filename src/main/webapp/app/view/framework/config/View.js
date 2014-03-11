/**
 * 新闻中心主视图
 */

Ext.define("Wms.view.framework.config.View",{
    extend:"Ext.panel.Panel",
    alias:"widget.configview",

    //布局时，需要加载的视图组件
    requires:[
        "Wms.view.framework.config.ConfigTabPanel"
    ],


    //自适应容器
    layout:"fit",





    //初始化视图，布局开始
    initComponent:function(){
        //console.log("初始化布局-----_config/View.js");
       
        this.items=[
            //主体区域为 tabpanel面板
            {
                xtype:"configtabpanel",
                flex:1
            }
        ];

        this.callParent();
    }


});
