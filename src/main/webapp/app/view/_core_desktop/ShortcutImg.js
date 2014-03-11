
//桌面图标
Ext.define("Wms.view._core_desktop.ShortcutImg",{
    extend:"Ext.view.View",
    alias:"widget.shortcutimg",


   //默认配置项
    overItemCls: 'x-view-over',
    trackOver: true,
    x: 0, y: 0,
    autoRender:true,

    /**
     * 桌面图标
     */
    shortcuts: null,


    /**
     * 图标Item选择器，即每一个图标的class选择器。与shortcutTpl内代码对应
     */
    shortcutItemSelector: 'div.ux-desktop-shortcut',


    //桌面快捷方式模板
    shortcutTpl: [
        '<tpl for=".">',
        '<div class="ux-desktop-shortcut" id="{widgetName}-shortcut">',
        '<div class="ux-desktop-shortcut-icon {iconCls}">',
        '<img src="',Ext.BLANK_IMAGE_URL,'" title="{name}">',
        '</div>',
        '<span class="ux-desktop-shortcut-text">{name}</span>',
        '</div>',
        '</tpl>',
        '<div class="x-clear"></div>'
    ],

    /**
     * 任务栏
     */
    taskbarConfig: null,

    //开始菜单
    windowMenu: null,

    initComponent:function(){
        //console.log("1.2.1、初始化桌面图标视图组件-----ShortcutImg.initComponent()")

        //桌面图标
        var me=this;
        me.itemSelector=me.shortcutItemSelector;
        me.store="_Core_ShortcutStore";

        //桌面快捷图标，静态数据方式
/*        me.store=Ext.create('Ext.data.Store', {
            model: 'Wms.model.ShortcutModel',
            data: [
 {
 "name": '新闻中心',
 "iconCls": 'grid-shortcut',
 "widgetName": 'newsview',
 "controller":"NewsController",
 "isAddToStarMenu":"yes",
 "isAddToQuickStar":"yes",
 "smallIconCls":"bogus"
 },
 {
 "name": 'NewsAddEdit',
 "iconCls": 'grid-shortcut',
 "widgetName": 'newsaddedit',
 "controller":"",
 "isAddToStarMenu":"yes",
 "isAddToQuickStar":"yes",
 "smallIconCls":"icon-grid"
 },
 {
 "name": '用户中心',
 "iconCls": 'accordion-shortcut',
 "widgetName": 'acc-win' ,
 "controller":"",
 "isAddToStarMenu":"yes",
 "isAddToQuickStar":"no" ,
 "smallIconCls":"icon-grid"
 },
 {
 "name": '报表',
 "iconCls": 'cpu-shortcut',
 "widgetName": 'reportform',
 "controller":"",
 "isAddToStarMenu":"yes",
 "isAddToQuickStar":"no",
 "smallIconCls":"icon-grid"
 }
            ]
        });*/



        me.tpl=new Ext.XTemplate(me.shortcutTpl);
        me.callParent();
    }





})