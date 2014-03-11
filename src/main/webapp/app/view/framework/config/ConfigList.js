/**
 * Created with JetBrains WebStorm. User: Administrator Date: 13-10-6 Time:
 * 下午2:13 To change this template use File | Settings | File Templates.
 */

// 操作管理主视图
Ext.define("Wms.view.framework.config.ConfigList", {
	extend : "Ext.grid.Panel",
	alias : "widget.configlist",

	id : "configPanel",
	layout : "fit",

	requires : [ "Ext.ux.CheckColumn" ],

	store : "framework.config.ConfigListStore",

	initComponent : function() {
		var me = this;

//		btn1 = {
//			text : "删除",
//			disabled : true,
//			id : "cofnigdelbutton",
//			action : "delConfig",
//			handler : function() {
//
//			}
//		},
//
//		btn2 = {
//			text : "添加",
//			action : "addConfig",
//			id:    "configaddbutton"
//			handler : function() {
//
//			}
//		},
//
//		btn3 = {
//			text : "编辑",
//			disabled : true,
//			id : "configeditbutton",
//			action : "editConfig",
//			handler : function() {
//
//			}
//		},
		// 操作按钮
//		buttons = [ btn1, "->", btn2, btn3 ], me.tbar = buttons;

		// 底部分页条
		me.bbar = {
			xtype : "pagingtoolbar",
			pageSize : 50,
			displayInfo : true,
			store : me.store
		}
		me.tbar=[];
		// 选择模式
		me.selModel = {
			selType : 'checkboxmodel'
		}

		// Gird列 ----给用户名，email,角色 允许登录 添加 编辑功能
		me.columns = [ {
			text : '键',
			dataIndex : 'key',
			editor : {
				allowBlank : false
			}
		}, {
			text : '键描述',
			dataIndex : 'keyDesc',
			editor : {
				allowBlank : false
			}
		}, {
			text : '值',
			dataIndex : 'value',
			editor : {
				allowBlank : false
			}
		}, {
			text : '值描述',
			dataIndex : 'valueDesc',
			editor : {
				allowBlank : false
			}
		} ]
		//选中记录后，启用工具按钮。
        me.listeners={
            selectionchange:function(selType,records){
                var isSelect=!(records.length>0);
                this.down("toolbar button[action=delConfig]").setDisabled(isSelect);	
                if(records.length==1){
                	this.down("toolbar button[action=editConfig]").setDisabled(false);
                }
                if(records.length>1){
                	this.down("toolbar button[action=editConfig]").setDisabled(true);
                }
            }
        }
		me.callParent();
	}
});
