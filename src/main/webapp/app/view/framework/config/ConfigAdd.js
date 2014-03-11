//新闻添加或编辑视图
Ext
		.define(
				"Wms.view.framework.config.ConfigAdd",
				{
					extend : "Ext.form.Panel",
					alias : "widget.configadd",

					id:"configaddForm",

					// 布局 ，垂直排列
					layout : {
						type : "vbox",
						align : 'stretch'
					},
					bodyPadding : 5,
					// items内的子项默认样式。
					defaults : {
						margin : "0 0 10 0"
					},

					// items内表单项，默认配置
					fieldDefaults : {
						msgTarget : 'side',
						labelWidth : 80,
						anchor : "0",
						labelSeparator : "："
					},

					// 表单数据提交处理
					/*
					 * api:{ submit:"_news/edit/inNewsView" },
					 */

					// 当为编辑内容，点击重置，还原为最初的编辑内容，而非空值。
					trackResetOnLoad : true,
					// 提交表单时提示语。
					waitTitle : "提交中，请稍候....",

					// 自定义属性
					required : '<span style="color:red;font-weight:bold" data-qtip="Required">*</span>',

					// 组件初始化
					initComponent : function() {
						var me = this;

						me.items = [
						// 表单区域 ，垂直布局，分成多个块
						// 第一部分----纯表单
						{
							// title:"第一部分",
							// border:true,
							layout : "anchor",
							defaultType : "textfield",
							bodyPadding : '5 10',
							items : [
				         
							// 配置KEY
							{
								anchor : '80%',
								fieldLabel : '配置键   ',
								afterLabelTextTpl : me.required,
								name : 'key',
								allowBlank : false,
								tooltip : '输入配置键',
								maxLength: 50,
								vtype: 'alphanum',
								//disabled:me.flag
								readOnly:me.flag
							},

							// 配置KEYDESC
							{
								anchor : '80%',
								fieldLabel : '配置描述',
								afterLabelTextTpl : me.required,
								name : 'keyDesc',
								allowBlank : false,
								tooltip : '输入配置键描述',
								maxLength: 150
							},
							// 配置VALUE
							{
								anchor : '80%',
								fieldLabel : '配置值   ',
								afterLabelTextTpl : me.required,
								name : 'value',
								allowBlank : false,
								tooltip : '输入配置值',
								xtype:'textarea',
								maxLength: 2000
								//vtype: 'alphanum'
							},

							// 配置值DESC
							{
								anchor : '80%',
								fieldLabel : '配置值描述',
								afterLabelTextTpl : me.required,
								name : 'valueDesc',
								allowBlank : false,
								tooltip : '输入配置值描述',
								maxLength: 100
							} ]
						} ]

						// 提交按钮
						me.dockedItems = [
						// 第五部分------按钮区域
						{
							xtype : "toolbar",
							dock : "top",
							ui : "footer",
							layout : {
								pack : "center"
							},
							// margin:'20 0 10 95',
							items : [
							// 提交按钮
							{
								xtype : "button",
								text : '提  交',
								action : "configSubmit",
								// 验证表单中没有无效值时才会自动启动控钮
								formBind:true,
								margin : '0 15 0 0'
							// handler:me.onNewsSubmit
							},
							// 重置按钮
							{
								xtype : "button",
								action : "configReset",
								text : "重  置",
								handler : function() {
									this.up("form").getForm().reset();
								}
							} ]
						} ];

						me.callParent();
					}

				});
