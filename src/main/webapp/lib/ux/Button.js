/**
 * 按钮控制权限
 */
Ext.define('Ext.ux.Button', {
	override : 'Ext.button.Button',
	initComponent : function() {
		//我在视图打开前，将权限付给Wms.menuInfo.operates，如果没有，在按钮渲染钱hidden掉
		var me = this;
		if(typeof(me.authKey)!='undefined'){
			var isTrue = false;
			Ext.each(Wms.menuInfo.operates,function(item){
		    	if(item.opAction == me.authKey){
		    		isTrue =  true;
		    	}
		    })
			if(isTrue){
				me.hidden = false;
			}else{
				me.hidden = true;
			}
		}
		me.callParent(arguments);
	}
});