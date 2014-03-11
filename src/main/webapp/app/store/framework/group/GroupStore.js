//User数据
Ext.define("Wms.store.framework.group.GroupStore", {
    extend:'Ext.data.Store',
    model:'Wms.model.framework.group.GroupModel',

    //不允许批量操作
    batchActions: false,
    pageSize:15,
    proxy: {
        type:"ajax",

        api:{
           //获取角色数据请求地址
            read: 'group/query.json',
             //删除
            destroy: 'group/remove.json',

            //更新
            update: "group/edit.json",

            //添加
            create: "group/add.json"
        },

        reader: {

            type: 'json',

            //固定数据返回项为  { success:true, Msg:"some text" , data:[   {...},......]  }
            root: "dataList",

            messageProperty: "message",
            totalProperty:"recordCount"

        },

        writer: {

            type: "json",

            //以键值对的方式提交，而不是json对象方式提交数据
            encode: true,

            root: "data",

            //不允许，一个个单独提交
            allowSingle: false

        }

    }

})


