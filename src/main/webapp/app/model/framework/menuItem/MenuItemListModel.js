/**
 * Created with JetBrains WebStorm.
 * User: lenovo
 * Date: 13-9-27
 * Time: 下午12:00
 * To change this template use File | Settings | File Templates.
 */

//全部新闻列表
Ext.define("Wms.model.framework.menuItem.MenuItemListModel",{
    extend:"Ext.data.Model",
    fields:[
        {name:"menuId",type:"string"},
        { name: 'menuName',type:"string", defaultValue:""},
        { name: 'menuWName', type: 'string' ,defaultValue:""},
        { name: 'menuAction', type: 'string' ,defaultValue:""},
        { name: 'createUser', type: 'string' ,defaultValue:""},
        { name: 'createDate', type: 'string' ,defaultValue:""},
        { name: 'menuIconcls', type: 'string' ,defaultValue:""},
        { name: 'menuSmallcls', type: 'string' ,defaultValue:""},
        { name: 'menuPriority', type: 'string' ,defaultValue:""},
        { name: 'menuStatus', type: 'string' ,defaultValue:""},
        { name: 'menuStartMenu', type: 'string' ,defaultValue:""},
        { name: 'menuQuickMenu', type: 'string' ,defaultValue:""},
        { name: 'mainMenuId', type: 'string' ,defaultValue:""},
        { name: 'menuItem'},
        { name: 'menuUrl'}
    ]
});

