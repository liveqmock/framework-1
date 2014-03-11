
  //角色列表模型
Ext.define('Wms.model.framework.group.GroupModel', {
    extend:'Ext.data.Model',


    fields: [
        "groupId",

        { name: "groupName", defaultValue:"" },
        { name: "groupType",type:"string", defaultValue:"" },
        {name: "username", defaultValue: "" },

        { name: "parentGroupId", defaultValue:"" },
        { name: "description", defaultValue:"" },
        { name: "date",  defaultValue: new Date() }

    ],

    //id字段名为userId;
    idProperty: "groupId"
});
