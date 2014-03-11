/**
 * 任务栏视图组件
 */   
Ext.define('Wms.view._core_desktop.TaskBar', {
    extend: 'Ext.toolbar.Toolbar',
    alias: 'widget.taskbar',

    //这里加载了开始菜单。
    requires: [
        'Wms.view._core_desktop.StartMenu'
    ],

   //默认配置
    cls: 'ux-taskbar',


    //开始菜单文字
    startBtnText: '开始',


   


    initComponent: function () {
        //console.log("1.1.1、初始化任务栏视图组件TaskBar.js")
        var me = this;



        //创建开始菜单
        me.startMenu =Ext.create("Wms.view._core_desktop.StartMenu");


        //创建开始按钮
        me.startBtn={
            xtype:"button",
            cls: 'ux-start-button',
            iconCls: 'ux-start-button-icon',
            menu: me.startMenu,
            text: me.startBtnText
        };
        
        
        

        //创建快速启动栏，
        //console.log("1.1.3、初始化快速启动栏");
        me.quickStart = {
            xtype:"toolbar",
            id:"quickStart",
            maxWidth: 70,
            items: [
                {
                    tooltip: { text: "显示桌面", align: 'bl-tl' },
                    overflowText: "显示桌面",
                    iconCls: "zuomian",
                    widgetName:"newsview",
                    handler:function (){
                    	var item=me.items.items[2].items.items;
                    	for(var i =1;i<item.length;i++){
                    		item[i].win.hide();
                    	}
                    }
                }/*,
                {
                    tooltip: { text: "Test", align: 'bl-tl' },
                    overflowText: "test",
                    iconCls: "accordion",
                    widgetName:"newsaddedit",
                    handler: me.onQuickStartClick
                }*/
            ],
            enableOverflow: true
        };


        //创建窗口工具条
        me.windowBar = new Ext.toolbar.Toolbar(me.getWindowBarConfig());


       //添加入items中
        me.items = [
           //开始菜单
            me.startBtn,

            //快速启动菜单
            me.quickStart,

            //'-',

            //窗口工具条
            me.windowBar
            /*,

            "-"*/

            //时钟设置
            /*  trayItems: [
             { xtype: 'trayclock', flex: 1 }
             ]*/
        ];

        me.callParent();
    },

    //布局后，在这里添加上任务栏窗口右键菜单。
    afterLayout: function () {
        var me = this;
        me.callParent();
        me.windowBar.el.on('contextmenu', me.onButtonContextMenu, me);
    },

    
    /**
     * This method returns the configuration object for the Tray toolbar. A derived
     * class can override this method, call the base version to build the config and
     * then modify the returned object before returning it.
     */
    getTrayConfig: function () {
        var ret = {
            items: this.trayItems
        };
        delete this.trayItems;
        return ret;
    },
    


    getWindowBarConfig: function () {
        return {
            flex: 1,
            cls: 'ux-desktop-windowbar',
            items: [ '&#160;' ],
            layout: { overflowHandler: 'Scroller' }
        };
    },

    getWindowBtnFromEl: function (el) {
        var c = this.windowBar.getChildByElement(el);
        return c || null;
    },




    onButtonContextMenu: function (e) {
        var me = this, t = e.getTarget(), btn = me.getWindowBtnFromEl(t);
        if (btn) {
            e.stopEvent();
            me.windowMenu.theWin = btn.win;
            me.windowMenu.showBy(t);
        }
    },

    onWindowBtnClick: function (btn) {
        var win = btn.win;

        if (win.minimized || win.hidden) {
            btn.disable();
            win.show(null, function() {
                btn.enable();
            });
        } else if (win.active) {
            btn.disable();
            win.on('hide', function() {
                btn.enable();
            }, null, {single: true});
            win.minimize();
        } else {
            win.toFront();
        }
    },



    //添加任务栏窗口
    addTaskButton: function(win) {
        var config = {
            iconCls: win.iconCls,
            enableToggle: true,
            toggleGroup: 'all',
            width: 140,
            margins: '0 2 0 3',
            text: Ext.util.Format.ellipsis(win.title, 20),
            listeners: {
                click: this.onWindowBtnClick,
                scope: this
            },
            win: win
        };

        var cmp = this.windowBar.add(config);
        cmp.toggle(true);
        return cmp;
    },

    removeTaskButton: function (btn) {
        var found, me = this;
        me.windowBar.items.each(function (item) {
            if (item === btn) {
                found = item;
            }
            return !found;
        });
        if (found) {
            me.windowBar.remove(found);
        }
        return found;
    },

    setActiveButton: function(btn) {
        if (btn) {
            btn.toggle(true);
        } else {
            this.windowBar.items.each(function (item) {
                if (item.isButton) {
                    item.toggle(false);
                }
            });
        }
    }
});
