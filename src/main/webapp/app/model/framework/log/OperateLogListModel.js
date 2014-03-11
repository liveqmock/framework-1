
  //Operate模型
Ext.define('Wms.model.framework.log.OperateLogListModel', {
    extend:'Ext.data.Model',


    fields: [

        { name: "opId" },

        { name: "opType" },

        { name: "opName" },

        { name: "uid" },
        
        { name: "name"},

        { name: "dateline", type:"string", dateFormat: "Y-m-d H:i:s", defaultValue: new Date() },

        { name: "siteId" },
        
        { name: "siteName"},
        
        { name: "message"}
    ]
});
