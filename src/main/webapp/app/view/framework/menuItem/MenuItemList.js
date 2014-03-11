
//全部新闻数据Grid视图

Ext.define("Wms.view.framework.menuItem.MenuItemList",{
    extend:"Ext.grid.Panel",
    alias:"widget.menuItemlist",


    // 面板默认配置
    overflowY:"auto",
    store: "framework.menuItem.MenuItemListStore",

    initComponent:function(){
        var me=this;
        // 选择模式
        selectCheckBox={selType : 'checkboxmodel'};

        me.tbar=[];

        // 根据登录用户类型userType设置可操作的button
        userType=Wms.curUser.roles;

        if(userType!=0){
            selectCheckBox=null;
        }

        // 头部工具栏，操作按钮
//        me.tbar=me.buttons;
//        console.info(me.buttons);
        // 删除选择控制
        me.selModel=selectCheckBox;

        // 表头，列表标题
        me.columns=[
            // new Ext.grid.RowNumberer(),
            {
                text: "菜单ID",
                sortable: true,
                dataIndex: 'menuId'
            },
            {
                text: "菜单名称",
                sortable: true,
                dataIndex: 'menuName'
            },
            {
                text: "视图别名",
                sortable: true,
                dataIndex: 'menuWName'
            },
            {
                text: "视图请求",
                sortable: true,
                dataIndex: 'menuAction'
            },
            {
                text: "菜单Url",
                sortable: true,
                dataIndex: 'menuUrl'
            },
            {
                text: "创建用户",
                sortable: true,
                dataIndex: 'createUser'
            },
            {
                text: "创建时间",
                sortable: true,
                width : 150,
                dataIndex: 'createDate',
                renderer:function(value){
                    var date=new Date(value*1);
                    return Ext.Date.format(date,'Y-m-d H:i:s')

                }
            },
            {
                text: "桌面菜单图标",
                sortable: true,
                dataIndex: 'menuIconcls'
            },
            {
                text: "开始菜单图标",
                sortable: true,
                dataIndex: 'menuSmallcls'
            },
            {
                text: "优先级",
                sortable: true,
                dataIndex: 'menuPriority'
            },
            {
                text: "菜单状态",
                sortable: true,
                dataIndex: 'menuStatus',
                renderer:function(value){
                    if(value==1){
                        return "启用";
                    }else{
                        return "禁用";
                    }
                }
            },
            {
                text: "开始菜单",
                sortable: true,
                dataIndex: 'menuQuickMenu',
                renderer:function(value){
                    if(value==1){
                        return "显示";
                    }else{
                        return "隐藏";
                    }
                }
            },
            {
                text: "桌面菜单",
                sortable: true,
                dataIndex: 'menuStartMenu',
                renderer:function(value){
                    if(value==1){
                        return "显示";
                    }else{
                        return "隐藏";
                    }
                }
            },
            {
                text: "上级菜单",
                sortable: true,
                dataIndex: 'menuItem',
                renderer:function(value){
                    if(value == null){
                        return "";
                    }
                    return value.menuName;
                }
            }
        ];

        // 底部工具条，分页条，或其它功能
        me.bbar= {
            xtype: 'pagingtoolbar',
            displayInfo: true,
            store: "framework.menuItem.MenuItemListStore",
            emptyMsg:'没有记录'
        };

        // 选中记录后，启用工具按钮。
        me.listeners={
            selectionchange:function(selType,records){
                var isSelect=!(records.length>0);
                if(records.length == 1){
                    this.down("toolbar button[action=editMenuItem]").setDisabled(false);
                    this.down("toolbar button[action=delMenuItem]").setDisabled(false);
                }else if(records.length > 1){
                    this.down("toolbar button[action=editMenuItem]").setDisabled(true);
                    this.down("toolbar button[action=delMenuItem]").setDisabled(true);
                }

            }
        };
        me.callParent();
    }

});
