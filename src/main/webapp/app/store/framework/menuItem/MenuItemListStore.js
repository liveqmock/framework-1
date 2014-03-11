
//全部新闻数据
Ext.define("Wms.store.framework.menuItem.MenuItemListStore",{
   extend:"Ext.data.Store",
    model:"Wms.model.framework.menuItem.MenuItemListModel",

    //配置项，
    // 每页显示条数
    pageSize:15,
    //autoLoad:true,
    //采用Url的传统提交方式。
    proxy:{
    type:"ajax",
        api:{
            read:"menuItem/findMenuItemList.json",
            create:"menuItem/saveOrUpdateMenuItem.json",
            update:"menuItem/saveOrUpdateMenuItem.json",
            destroy:"menuItem/del.json"
        },

        reader:{
            type:"json",
            root:"dataList",
            totalProperty:"recordCount"
      },


        writer: {
            type: "json",
            encode: true,
            root: "results"
        }
    }


    //采用REST方式提交数据----即表征状态转换，四种请求，POST,GET,PUT,DELETE,分别自动对象，新建，获取，更新，删除操作
/*    proxy:{
        type:"rest",
        url:"_data/gridTest.json"
    }*/
});