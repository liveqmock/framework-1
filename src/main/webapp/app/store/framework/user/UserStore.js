//User数据
Ext.define("Wms.store.framework.user.UserStore", {
    extend: 'Ext.data.Store',
    model: 'Wms.model.framework.user.UserListModel',
//    //不允许批量操作
//    batchActions: false,
    pageSize: 15,
    proxy: {
        type: "ajax",
        api: {
            //获取用户数据请求地址
            read: 'member/findUser.json',
            //删除
            destroy: 'member/delete.json',
            //更新
            update: "member/update.json",
            //添加
            create: "member/save.json"
        },
        reader: {
            type: "json",
            root: "dataList",
            totalProperty: "recordCount"
        },
        writer: {
            type: "json",
            encode: true,
            root: "dataList"
        }
    }
});


