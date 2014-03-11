//User模型
Ext.define('Wms.model.framework.user.UserListModel', {
    extend: 'Ext.data.Model',
    fields: [
        { name: "uid", type: "int" },
        { name: "role.roleId"},
        { name: "group.groupId"},
        { name: "userName"},
        { name: "email"},
        { name: "role.roleName"},
        { name: "group.groupName" },
        { name: "regIP" },
        { name: "regDate", type: "String"}
    ]
});
