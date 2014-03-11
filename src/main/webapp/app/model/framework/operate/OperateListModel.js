
  //Operate模型
Ext.define('Wms.model.framework.operate.OperateListModel', {
    extend:'Ext.data.Model',


    fields: [

        { name: "opId",type:"int", defaultValue:"" },

        { name: "opName", defaultValue:"" },

        { name: "createUser", defaultValue:"" },

        { name: "createDate", type:"string", dateFormat: "Y-m-d H:i:s", defaultValue: new Date() },

        { name: "opAction", defaultValue:"" },

/*        { name: "btnId",type: 'string' , defaultValue:"" },*/

        { name: "opIsBtn", type:"string" ,defaultValue:"" },

        { name: "opDesc", defaultValue:"" },

        { name: "opMenuId", type:"string",defaultValue:"" },

        { name: "opUrl" },

        { name: "opPriority" },

        { name: "menuItem" }
    ]
});
