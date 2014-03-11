/**
 * @class Ext.ux.desktop.ShortcutModel
 * @extends Ext.data.Model
 * This model defines the minimal set of fields for desktop shortcuts.
 */
Ext.define('Wms.model._Core_ShortcutModel', {
    extend: 'Ext.data.Model',
    fields: [
       {name:'name'},
       {name:'iconCls'},
       {name:'widgetName'},
       {name:'controller'},
       {name:"isAddToStarMenu"},
       {name:"isAddToQuickStar"},
       {name:"smallIconCls"},
       {name:"tag"},
       {name:"operates"},
       {name:"submenu"}
    ]
});
