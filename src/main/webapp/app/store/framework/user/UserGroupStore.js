//User数据
Ext.define("Wms.store.framework.user.UserGroupStore", {
    extend: 'Ext.data.Store',
    model: 'Wms.model.framework.user.UserGroupModel',
    //不允许批量操作
    batchActions: false,
    autoLoad: true,
    proxy: {
        type: "ajax",
        api: {
            //获取用户数据请求地址
            read: 'member/findGroup.json'
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
            messageProperty: "message",
            totalProperty: "recordCount"
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


