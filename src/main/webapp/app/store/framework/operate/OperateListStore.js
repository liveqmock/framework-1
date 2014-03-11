
//全部新闻数据
Ext.define("Wms.store.framework.operate.OperateListStore",{
    extend:"Ext.data.Store",
    model:"Wms.model.framework.operate.OperateListModel",

    //配置项，
    //每页显示条数
    pageSize:15,

    //采用Url的传统提交方式。
    proxy:{
        type:"ajax",
        api:{
            read:"operate/findOperate.json",
            create:"operate/saveOrUpdateOperate.json",
            update:"operate/saveOrUpdateOperate.json",
            destroy:"operate/del.json",
            getOperate:""
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