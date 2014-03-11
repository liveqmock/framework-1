/**
 * 桌面主视图
 * 约定：view内每个子文件夹，对应一个桌面图标，即一个完整的功能模块。该子文件夹内，窗口视图（即点击图标显示的主界面）统一使用View.js命名。
 */

Ext.define("Wms.view._core_desktop.View",{
    extend:"Ext.panel.Panel",
    alias:"widget.desktopview",
    id:"wmsDesktop",

    //桌面构成由 桌面图标，任务栏，壁纸，桌面右键菜单三个子组件构成。
    requires:[
        "Wms.view._core_desktop.ShortcutImg",
        "Wms.view._core_desktop.TaskBar"/*,
        "Wms.view.desktop.Wallpaper"*/
    ],



    //桌面默认配置项
    border: false,
    header:false,
    bodyCls:"crm-bottom",
    layout:"fit",


    //当前窗口与非当前窗口样式
    activeWindowCls: 'ux-desktop-active-win',
    inactiveWindowCls: 'ux-desktop-inactive-win',
    lastActiveWindow: null,


    //窗口排列参数
    xTickSize: 1,
    yTickSize: 1,


    app: null,

    //打开窗口集合
    windows:null,




   //任务栏配置对象
    taskbarConfig: null,

    //任务栏对象
    taskbar:null,


    //任务栏窗口右键菜单
    windowMenu: null,





    initComponent:function(){
        var me=this;
        //console.log("1、初始化桌面视图组件-----desktop.View.initComponent()")




        //创建窗口集合
        me.windows = Ext.create("Ext.util.MixedCollection");




        //初始化任务栏
        //console.log("1.1、创建任务栏-----，任务栏包含了开始菜单starMenu，快速启动栏quickStar，窗口工具条windowBar")
        //使用xtype类型的方法，初始化任务栏是懒加载方式，即在实际渲染的时候才会初始化组件对象  ，如果没有涉及到业务操作，建议使用此方式初始化。
        // me.bbar = me.taskbar =  {xtype:"taskbar"}

        //这种方式会立即初始化TaskBar对象，如果此视图内要使用TaskBar对象，应该使用此种方式初始化。
        me.bbar = me.taskbar = Ext.create("Wms.view._core_desktop.TaskBar");






        //桌面图标 ，与壁纸
        //console.log("1.2、初始化桌面图标视图组件")
        me.items=[
            //  { xtype: 'wallpaper', id: me.id+'_wallpaper' },
            {xtype:"shortcutimg"}
        ];


        me.callParent();
    },




    //渲染后添加桌面右键菜单与初始化任务栏窗口右键菜单
    afterRender: function () {
        //console.log("1.3、初始化桌面右键菜单视图组件与任务栏窗口右键菜单视图组件")
        var me = this;
        me.callParent();



        //初始化任务栏窗口右键菜单
        me.windowMenu=Ext.create("Ext.menu.Menu",me.createWindowMenu());
        me.taskbar.windowMenu =me.windowMenu;


        //桌面右键菜单
        me.contextMenu = Ext.create("Ext.menu.Menu",me.createDesktopMenu());
        me.el.on('contextmenu', me.onDesktopMenu, me);
    },






    //创建桌面右键菜单
    createDesktopMenu: function () {
        var me = this, ret = {
            items: me.contextMenuItems || []
        };

        //添加右键菜单分隔符
        if (ret.items.length) {
            ret.items.push('-');
        }

        //添加右键菜单项
        ret.items.push(
            { text: '窗口平铺', handler: me.tileWindows, scope: me, minWindows: 1 },
            { text: '窗口层叠', handler: me.cascadeWindows, scope: me, minWindows: 1 })

        return ret;
    },




    //  创建任务栏窗口右键菜单设置
    createWindowMenu: function () {
        var me = this;
        return {
            defaultAlign: 'br-tr',
            items: [
               { text: '还原', handler: me.onWindowMenuRestore, scope: me },
                { text: '最小化', handler: me.onWindowMenuMinimize, scope: me },
                { text: '最大化', handler: me.onWindowMenuMaximize, scope: me },
                '-',
                { text: '关闭', handler: me.onWindowMenuClose, scope: me }
            ],
            listeners: {
                beforeshow: me.onWindowMenuBeforeShow,
                hide: me.onWindowMenuHide,
                scope: me
            }
        };
    },





    //------------------------------------------------------
    // 弹窗控制
    getActiveWindow: function () {
        var win = null,
            zmgr = this.getDesktopZIndexManager();

        if (zmgr) {
            // We cannot rely on activate/deactive because that fires against non-Window
            // components in the stack.

            zmgr.eachTopDown(function (comp) {
                if (comp.isWindow && !comp.hidden) {
                    win = comp;
                    return false;
                }
                return true;
            });
        }

        return win;
    },

    getDesktopZIndexManager: function () {
        var windows = this.windows;
        // TODO - there has to be a better way to get this...
        return (windows.getCount() && windows.getAt(0).zIndexManager) || null;
    },

    getWindow: function(id) {
        return this.windows.get(id);
    },

    minimizeWindow: function(win) {
        win.minimized = true;
        win.hide();
    },

    restoreWindow: function (win) {
        if (win.isVisible()) {
            win.restore();
            win.toFront();
        } else {
            win.show();
        }
        return win;
    },

    updateActiveWindow: function () {
        var me = this, activeWindow = me.getActiveWindow(), last = me.lastActiveWindow;
        if (activeWindow === last) {
            return;
        }

        if (last) {
            if (last.el.dom) {
                last.addCls(me.inactiveWindowCls);
                last.removeCls(me.activeWindowCls);
            }
            last.active = false;
        }

        me.lastActiveWindow = activeWindow;

        if (activeWindow) {
            activeWindow.addCls(me.activeWindowCls);
            activeWindow.removeCls(me.inactiveWindowCls);
            activeWindow.minimized = false;
            activeWindow.active = true;
        }

        me.taskbar.setActiveButton(activeWindow && activeWindow.taskButton);
    },

    onWindowClose: function(win) {
        var me = this;
        me.windows.remove(win);
       // console.log(me.windows);

        me.taskbar.removeTaskButton(win.taskButton);
        me.updateActiveWindow();
    },






    //------------------------------------------------------
    // 桌面右键菜单处理

    onDesktopMenu: function (e) {
        var me = this, menu = me.contextMenu;
        e.stopEvent();
        if (!menu.rendered) {
            menu.on('beforeshow', me.onDesktopMenuBeforeShow, me);
        }
        menu.showAt(e.getXY());
        menu.doConstrain();
    },

    onDesktopMenuBeforeShow: function (menu) {
        var me = this, count = me.windows.getCount();

        menu.items.each(function (item) {
            var min = item.minWindows || 0;
            item.setDisabled(count < min);
        });
    },


    //窗口排列功能
    tileWindows: function() {
        var me = this, availWidth = me.body.getWidth(true);
        var x = me.xTickSize, y = me.yTickSize, nextY = y;

        me.windows.each(function(win) {
            if (win.isVisible() && !win.maximized) {
                var w = win.el.getWidth();

                // Wrap to next row if we are not at the line start and this Window will
                // go off the end
                if (x > me.xTickSize && x + w > availWidth) {
                    x = me.xTickSize;
                    y = nextY;
                }

                win.setPosition(x, y);
                x += w + me.xTickSize;
                nextY = Math.max(nextY, y + win.el.getHeight() + me.yTickSize);
            }
        });
    },

    //窗口排列功能
    cascadeWindows: function() {
        var x = 0, y = 0,
            zmgr = this.getDesktopZIndexManager();

        zmgr.eachBottomUp(function(win) {
            if (win.isWindow && win.isVisible() && !win.maximized) {
                win.setPosition(x, y);
                x += 20;
                y += 20;
            }
        });
    },





    //------------------------------------------------------
    // 任务栏窗口右键菜单处理

    onWindowMenuBeforeShow: function (menu) {
        var items = menu.items.items, win = menu.theWin;
        items[0].setDisabled(win.maximized !== true && win.hidden !== true); // Restore
        items[1].setDisabled(win.minimized === true); // Minimize
        items[2].setDisabled(win.maximized === true || win.hidden === true); // Maximize
    },

    onWindowMenuClose: function () {
        var me = this, win = me.windowMenu.theWin;

        win.close();
    },

    onWindowMenuHide: function (menu) {
        Ext.defer(function() {
            menu.theWin = null;
        }, 1);
    },

    onWindowMenuMaximize: function () {
        var me = this, win = me.windowMenu.theWin;

        win.maximize();
        win.toFront();
    },

    onWindowMenuMinimize: function () {
        var me = this, win = me.windowMenu.theWin;

        win.minimize();
    },

    onWindowMenuRestore: function () {
        var me = this, win = me.windowMenu.theWin;

        me.restoreWindow(win);
    }


})