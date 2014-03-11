//用户角色
Ext.define("Wms.store.framework.user.UserRolesStore", {
    //数据使用的是数组类型数据。
//    extend:'Ext.data.ArrayStore',

//    fields:["text"],
//
//    //这里使用了静态数据，直接写在 store内
//    data:[["普通用户"],["系统管理员"]]
    extend: 'Ext.data.Store',
    model: 'Wms.model.framework.role.RoleListModel',
    //不允许批量操作
    batchActions: false,
    autoLoad: true,
    proxy: {
        type: "ajax",
        api: {
            //获取用户数据请求地址
            read: 'member/findRoleByGroupId.json'
            //删除
//            destroy: 'member/delete.json',
//
//            //更新
//            update: "member/update.json",
//
//            //添加
//            create: "member/save.json"
        },
        reader: {
            type: 'json',
            //固定数据返回项为  { success:true, Msg:"some text" , data:[   {...},......]  }
            root: "resultList",
            messageProperty: "msg",

            totalProperty: "totalCount"
        },
        writer: {
            type: "json",
            //以键值对的方式提交，而不是json对象方式提交数据
            encode: true,
            root: "resultList",
            //不允许，一个个单独提交
            allowSingle: false
        }
    }
});