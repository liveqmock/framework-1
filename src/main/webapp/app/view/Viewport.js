/**
 * 启动应用，创建视图。 一个app只有一个视图 ，称主视图， （Ext.container.Viewport）
 * view文件夹内的所有内容，都称为视图组件，（可以是单组件，或复合组件，如果组件是一个完整界面，也可称为窗口视图）
 */

Ext.define("Wms.view.Viewport",{
    extend:"Ext.container.Viewport",

    //初始化完成，将组件渲染入document.body中
    renderTo:Ext.getBody(),

    //视图布局方式为自动填充容器。
    layout:"fit",



    //布局时，先加载需要用到的视图组件类，此处只需加载一个桌面视图(桌面视图中会具体设置要加载的对应的子视图)。
    requires:[
        "Wms.view._core_desktop.View"
    ],


    //初始化组件，布局开始
    initComponent:function(){
        //console.log("Viewprot.js");
        this.items= [
            {
                xtype:"desktopview"
            }
        ];
        this.callParent();
    }
});