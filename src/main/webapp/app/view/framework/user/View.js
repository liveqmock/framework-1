/**
 * Created with JetBrains WebStorm.
 * User: Administrator
 * Date: 13-10-6
 * Time: 下午2:13
 * To change this template use File | Settings | File Templates.
 */

//用户管理主视图
Ext.define("Wms.view.framework.user.View",{
 extend:"Ext.panel.Panel",
    alias:"widget.userview",
    layout:"fit",

    //布局时，需要加载的视图组件
    requires:[
        "Wms.view.framework.user.MemberTabPanel"
    ],

      //初始化视图，布局开始
    initComponent:function(){
        //console.log("初始化布局-----member/View.js");
        

        this.items=[
            //主体区域为 tabpanel面板
            {
                xtype:"membertabpanel",
                flex:1
            }
        ];

        this.callParent();
    }


});
