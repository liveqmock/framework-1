//父菜单下拉框模型
Ext.define("Wms.model.framework.menuItem.MainMenuItemModel",{
   extend:"Ext.data.Model",
   fields:[
       {name:"menuId" , type:"string"},//这里要把服务端接收的int类型转换为string，不然菜单下拉框不会识别
       {name:'menuName',type:"string"}
   ]
});
