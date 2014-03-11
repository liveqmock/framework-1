/**
 * 快捷图标数据
 */
Ext.define("Wms.store._Core_ShortcutStore",{
    extend:"Ext.data.Store",

    model:"Wms.model._Core_ShortcutModel",
   // autoLoad:true,

    //在StoreManager中注册，方便管理。如果有需则添加此项，没需求则不加。
    storeId:"shortcutDataList",


    //请求代理对象。
    proxy:{
        type:"ajax",
        url:"menuItem/showMenu.json",

        //读取数据，要求返回的数据类型为json对象，数据格式应为    {  success:true,  message:"",  data:[ {k:v,k2:v2}, ......]   }
        reader:{
            type:"json",
            root:"resultList"
        }
    }

});