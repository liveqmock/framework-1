/**
 * Created by WangSong on 13-10-14.
 */
//菜单操作
Ext.define("Wms.store.framework.menuItem.MenuItemOpStore",{
    extend:"Ext.data.Store",
    model:"Wms.model.framework.operate.OperateListModel",
    proxy: {
        type: 'ajax',
        url: 'menuItem/edit.json',
        reader: {
            type: 'json',
            root: 'resultList'
        }
    }
});
