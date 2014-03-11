
  //Operate模型
Ext.define('Wms.model.framework.config.ConfigListModel', {
    extend:'Ext.data.Model',


    fields: [
        "key",

        { name: "key", defaultValue:"a" },

        { name: "value", defaultValue:"a" },

        { name: "keyDesc", defaultValue:"a"},

        { name: "valueDesc", defaultValue:"a" }
    ],

    idProperty: "key"
});
