
  //角色列表模型
Ext.define('Wms.model.framework.role.RoleListModel', {
    extend:'Ext.data.Model',


    fields: [
        "roleId",

        { name: "roleId", type:"int",defaultValue:"0" },

        { name: "roleName", defaultValue:"" },

        {name: "createUser", defaultValue: "" },

        { name: "roleDsc", defaultValue:"" },

        { name: "createDate",  defaultValue: new Date() },

        { name: "parentRoleId" }
    ],

    //id字段名为userId;
    idProperty: "roleId"
});
