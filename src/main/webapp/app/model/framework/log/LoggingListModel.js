
  //Operate模型
Ext.define('Wms.model.framework.log.LoggingListModel', {
    extend:'Ext.data.Model',
    fields: [
        { name: "logId"},

        { name: "logUid"},

        { name: "logName"},

        { name: "logMobile"},

        { name: "logIp"},

        { name: "logDate", type:"string", dateFormat: "Y-m-d H:i:s", defaultValue: new Date()},

        { name: "logSiteId"},

        { name: "logSiteName"},

        { name: "logClientType"},

        { name: "logInfo"}
    ]
});
