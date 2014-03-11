/**
 * Created by liuguomin on 13-10-12.
 */
//User数据
Ext.define("Wms.store.framework.TreeStore", {
    extend:'Ext.data.TreeStore',
    model:'Wms.model.framework.TreeModel',
    //不允许批量操作
    batchActions: false,
  //  pageSize:2,
    autoRefresh:true,

    proxy: {
        type:"ajax",
            //url: 'role/demoExtTree.json'
        api:{
            //获取角色树数据请求地址
            read: ''

        }
    }

})

