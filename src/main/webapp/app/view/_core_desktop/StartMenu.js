/**
 * 开始菜单视图组件
 */

Ext.define('Wms.view._core_desktop.StartMenu', {
    extend: 'Ext.panel.Panel',
    alias:"widget.startmenu",



    //默认配置
    layout:{
        type:"fit",
        align:"stretch"
    },

    ariaRole: 'menu',
    cls: 'x-menu ux-start-menu',
    defaultAlign: 'bl-tl',
    iconCls: 'user',
    title:"",
    floating: true,
    shadow: true,

    width: 300,
    minHeight:350,
    maxHeight:500,


    menu:null,

    initComponent: function() {
        //console.log("1.1.2、初始化开始菜单视图组件 StarMenu.js")
        var me = this;
        var menu = me.menu;

        me.menu ={
            xtype:"menu",
            id:"startLeftMenuList",
            cls: 'ux-start-menu-body',
            border: false,
            floating: false,
            items: menu
        };

        //菜单项
       me.items = [me.menu];


         //创建菜单右侧工具栏
        var toolConfig={
            xtype:"toolbar",
            id:"startRightMenuList",
            dock: 'right',
            layout:{
                align:"stretch"
            },
            width: 100,
            vertical: true,
            cls: 'ux-start-menu-toolbar',
            items: [
/*                {
                    text:'退出',
                    iconCls:'logout',
                    action:"logout"
                }*/
            ],

            //隐藏开始菜单
            listeners: {
                add: function(tb, c) {
                    c.on({
                        click: function() {
                            me.hide();
                        }
                    });
                }
            }
        }

        me.dockedItems =toolConfig;
       // me.addDocked(me.toolbar);

        //注册为一个菜单
        Ext.menu.Manager.register(me);
        me.callParent();
    },




    addMenuItem: function() {
        var cmp = this.menu;
        cmp.add.apply(cmp, arguments);
    },



    addToolItem: function() {
        var cmp = this.toolbar;
        cmp.add.apply(cmp, arguments);
    }
});
