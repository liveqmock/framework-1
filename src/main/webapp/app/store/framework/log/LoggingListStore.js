Ext.define("Wms.store.framework.log.LoggingListStore",{
    extend:"Ext.data.Store",
    model:"Wms.model.framework.log.LoggingListModel",

    //配置项，
    //每页显示条数
    pageSize:15,

    //采用Url的传统提交方式。
    proxy:{
        type:"ajax",
        api:{
            read:"log/findLogging.json"
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
});