//User数据
Ext.define("Wms.store.framework.config.ConfigListStore", {
	extend : 'Ext.data.Store',
	model : 'Wms.model.framework.config.ConfigListModel',

	// 不允许批量操作
	batchActions : false,
	pageSize:5,//每页显示条数

	proxy : {
		type : "ajax",

		api : {
			// 获取用户数据请求地址
			read : 'config/list.json',
			// 删除
			destroy : 'config/remove.json',
			// 更新
			update : "config/update.json",
			// 添加
			create : "config/add.json"

		},

		reader : {

			type : 'json',

			// 固定数据返回项为 { success:true, Msg:"some text" , data:[ {...},......] }
			root : "dataList",

			messageProperty : "message",
			totalProperty:"recordCount"

		},

		writer : {

			type : "json",

			// 以键值对的方式提交，而不是json对象方式提交数据
			encode : true,

			root : "dataList",

			// 不允许，一个个单独提交
			allowSingle : false,
			
			idProperty:"key"

		}

	}

})
