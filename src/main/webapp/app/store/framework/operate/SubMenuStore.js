/**
 * Created by WangSong on 13-10-14.
 */
//菜单操作
Ext.define("Wms.store.framework.operate.SubMenuStore",{
    extend:"Ext.data.Store",
    model:"Wms.model.framework.menuItem.MenuItemListModel",
    autoLoad:true,
    proxy: {
        type: 'ajax',
        url: 'operate/querySubMenu.json',
        reader: {
            type: 'json',
            root: 'resultList'
        }
    }
});
