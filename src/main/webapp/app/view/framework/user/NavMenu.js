
//导航菜单
Ext.define("Wms.view.framework.user.NavMenu",{
    extend:"Ext.toolbar.Toolbar",
    alias:"widget.navmenu",

    defaultType:"button",
    style:"padding-left:0",

    //菜单按钮默认样式设置
    defaults: {
        cls:"navMenu"
    },


    cls:"navMenuBox",


    initComponent:function(){

    /*    var
       //新闻中心菜单
            m1= {
                text:"新闻中心",
                menu: [
                    {
                        text: '添加新闻',
                        action:"addMenuItem"
                    },
                    {
                        text: '全部新闻',
                        action:"menuItemList"
                    },
                    '-',
                    {
                        text:"添加新闻分类",
                        action:"addMenuItemType"
                    },
                    {
                        text:"全部新闻分类",
                        action:"menuItemTypeList"
                    }
                ]
            },

        //新闻分类管理菜单
            m2={
                text:"客户中心",
                menu:[
                    {
                        text:"菜单一",
                        action:"addMenuItem"
                    },
                    {
                        text:"菜单二",
                        action:"menuItemList"
                    },
                    {
                        text:"菜单三",
                        action:"otherOption"
                    }
                ]
            },

        //服务管理菜单
            m3={
                text:"服务管理",
                menu:[
                    {
                        text:"菜单一",
                        action:"addMenuItem"
                    },
                    {
                        text:"菜单二",
                        action:"menuItemList"
                    },
                    {
                        text:"菜单三",
                        action:"otherOption"
                    },

                    '-',

                    {
                        text: '设置',
                        menu:[
                            {text:"二级菜单1",action: "addMenuItem"},
                            {text:"二级菜单2",action: "addMenuItem"},
                            {text:"二级菜单3",action: "addMenuItem"},
                            {text:"二级菜单4", action: "addMenuItem"}
                        ]
                    }
                ]
            },

        //流程管理
            m4={
                text:"流程管理",
                menu:[
                    {
                        text:"菜单一",
                        action:"addMenuItem"
                    },
                    {
                        text:"菜单二",
                        action:"menuItemList"
                    },
                    {
                        text:"菜单三",
                        action:"otherOption"
                    },

                    '-',

                    {
                        text: '设置',
                        menu:[
                            {text:"二级菜单1",action: "addMenuItem"},
                            {text:"二级菜单2",action: "addMenuItem"},
                            {text:"二级菜单3",action: "addMenuItem"},
                            {text:"二级菜单4", action: "addMenuItem"}
                        ]
                    }
                ]
            },

        //资源管理
            m5={
                text:"资源管理",
                menu:[
                    {
                        text:"菜单一",
                        action:"addMenuItem"
                    },
                    {
                        text:"菜单二",
                        action:"menuItemList"
                    },
                    {
                        text:"菜单三",
                        action:"otherOption"
                    },

                    '-',

                    {
                        text: '设置',
                        menu:[
                            {text:"二级菜单1",action: "addMenuItem"},
                            {text:"二级菜单2",action: "addMenuItem"},
                            {text:"二级菜单3",action: "addMenuItem"},
                            {text:"二级菜单4", action: "addMenuItem"}
                        ]
                    }
                ]
            },


        //系统管理
            m6={
                text:"系统管理",
                menu:[
                    {
                        text:"菜单一",
                        action:"addMenuItem"
                    },
                    {
                        text:"菜单二",
                        action:"menuItemList"
                    },
                    {
                        text:"菜单三",
                        action:"otherOption"
                    },


                    '-',

                    {
                        text: '设置',
                        menu:[
                            {text:"二级菜单1",action: "addMenuItem"},
                            {text:"二级菜单2",action: "addMenuItem"},
                            {text:"二级菜单3",action: "addMenuItem"},
                            {text:"二级菜单4", action: "addMenuItem"}
                        ]
                    }
                ]
            } ;


        //根据用户权限显示对应的菜单项
        if(Wms.curUser &&   Wms.curUser. roles==0){
            this.items=[  m1,m2,m3,m4,m5,m6 ];
        }else{
            this.items=[  m1,m2,m6 ];
        }*/
        this.callParent();
    }
});
