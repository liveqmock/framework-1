Ext.define('Wms.view.framework.util.ActionColumnText', {
    extend: 'Ext.grid.column.Column',
    alias: ['widget.actioncolumntext'],
    alternateClassName: 'Ext.grid.ActionColumnText',
    actionIdRe: new RegExp(Ext.baseCSSPrefix + 'action-col-(\\d+)'),

    /**
     * @cfg {String} altText
     * The alt text to use for the image element.
     */
    altText: '',

    /**
     * @cfg {String} [menuText=<i>Actions</i>]
     * Text to display in this column's menu item if no {@link #text} was specified as a header.
     */
    menuText: '<i>Actions</i>',

    sortable: false,

    //innerCls: Ext.baseCSSPrefix + 'grid-cell-inner-action-col',
    innerCls: '',

    constructor: function(config) {
        var me = this,
            cfg = Ext.apply({}, config),
        // Items may be defined on the prototype
            items = cfg.items || me.items || [me],
            hasGetClass,
            i,
            len;


        me.origRenderer = cfg.renderer || me.renderer;
        me.origScope = cfg.scope || me.scope;

        me.renderer = me.scope = cfg.renderer = cfg.scope = null;

        // This is a Container. Delete the items config to be reinstated after construction.
        cfg.items = null;
        me.callParent([cfg]);

        // Items is an array property of ActionColumns
        me.items = items;

        for (i = 0, len = items.length; i < len; ++i) {
            if (items[i].getClass) {
                hasGetClass = true;
                break;
            }
        }

        // Also need to check for getClass, since it changes how the cell renders
        if (me.origRenderer || hasGetClass) {
            me.hasCustomRenderer = true;
        }
    },

    // Renderer closure iterates through items creating an <img> element for each and tagging with an identifying
    // class name x-action-col-{n}
    defaultRenderer: function(v, meta, record, rowIdx, colIdx, store, view){
        var me = this,
            prefix = Ext.baseCSSPrefix,
            scope = me.origScope || me,
            items = me.items,
            len = items.length,
            i = 0,
            item, ret, disabled, tooltip;

        // Allow a configured renderer to create initial value (And set the other values in the "metadata" argument!)
        // Assign a new variable here, since if we modify "v" it will also modify the arguments collection, meaning
        // we will pass an incorrect value to getClass/getTip
        ret = Ext.isFunction(me.origRenderer) ? me.origRenderer.apply(scope, arguments) || '' : '';

        meta.tdCls += ' ' + Ext.baseCSSPrefix + 'action-col-cell';
        for (; i < len; i++) {
            item = items[i];

            disabled = item.disabled || (item.isDisabled ? item.isDisabled.call(item.scope || scope, view, rowIdx, colIdx, item, record) : false);
            tooltip = disabled ? null : (item.tooltip || (item.getTip ? item.getTip.apply(item.scope || scope, arguments) : null));

            // Only process the item action setup once.


            if (!item.hasActionConfiguration) {

                // Apply our documented default to all items
                item.stopSelection = me.stopSelection;
                item.disable = Ext.Function.bind(me.disableAction, me, [i], 0);
                item.enable = Ext.Function.bind(me.enableAction, me, [i], 0);
                item.hasActionConfiguration = true;

            }

/*            ret += '<img role="button" alt="' + (item.altText || me.altText) + '" src="' + (item.icon || Ext.BLANK_IMAGE_URL) +
                '" class="' + prefix + 'action-col-icon ' + prefix + 'action-col-' + String(i) + ' ' + (disabled ? prefix + 'item-disabled' : ' ') +
                ' ' + (Ext.isFunction(item.getClass) ? item.getClass.apply(item.scope || scope, arguments) : (item.iconCls || me.iconCls || '')) + '"' +
                (tooltip ? ' data-qtip="' + tooltip + '"' : '') + ' />';*/

            ret += '<a  role="button"' + 'href="javascript:void(0);"'+
                '" class="' + prefix + 'action-col-icon ' + prefix + 'action-col-' + String(i) + ' ' + (disabled ? prefix + 'item-disabled' : ' ') +
                ' ' + (Ext.isFunction(item.getClass) ? item.getClass.apply(item.scope || scope, arguments) : (item.iconCls || me.iconCls || '')) + '"' +
                (tooltip ? ' data-qtip="' + tooltip + '"' : '') + ' >'+item.text+'</a>';


        }
        return '<div class="linkBtn">'+ret+'</div>';
    },

    /**
     * Enables this ActionColumn's action at the specified index.
     * @param {Number/Ext.grid.column.Action} index
     * @param {Boolean} [silent=false]
     */
    enableAction: function(index, silent) {
        var me = this;

        if (!index) {
            index = 0;
        } else if (!Ext.isNumber(index)) {
            index = Ext.Array.indexOf(me.items, index);
        }
        me.items[index].disabled = false;
        me.up('tablepanel').el.select('.' + Ext.baseCSSPrefix + 'action-col-' + index).removeCls(me.disabledCls);
        if (!silent) {
            me.fireEvent('enable', me);
        }
    },

    /**
     * Disables this ActionColumn's action at the specified index.
     * @param {Number/Ext.grid.column.Action} index
     * @param {Boolean} [silent=false]
     */
    disableAction: function(index, silent) {
        var me = this;

        if (!index) {
            index = 0;
        } else if (!Ext.isNumber(index)) {
            index = Ext.Array.indexOf(me.items, index);
        }
        me.items[index].disabled = true;
        me.up('tablepanel').el.select('.' + Ext.baseCSSPrefix + 'action-col-' + index).addCls(me.disabledCls);
        if (!silent) {
            me.fireEvent('disable', me);
        }
    },

    destroy: function() {
        delete this.items;
        delete this.renderer;
        return this.callParent(arguments);
    },

    /**
     * @private
     * Process and refire events routed from the GridView's processEvent method.
     * Also fires any configured click handlers. By default, cancels the mousedown event to prevent selection.
     * Returns the event handler's status to allow canceling of GridView's bubbling process.
     */
    processEvent : function(type, view, cell, recordIndex, cellIndex, e, record, row){
        var me = this,
            target = e.getTarget(),
            match,
            item, fn,
            key = type == 'keydown' && e.getKey(),
            disabled;

         //  console.log(target);
        // If the target was not within a cell (ie it's a keydown event from the View), then
        // rely on the selection data injected by View.processUIEvent to grab the
        // first action icon from the selected cell.
        if (key && !Ext.fly(target).findParent(view.getCellSelector())) {
            target = Ext.fly(cell).down('.' + Ext.baseCSSPrefix + 'action-col-icon', true);
        }

        // NOTE: The statement below tests the truthiness of an assignment.
       // console.log( target.className);
      //  console.log( target.className.match(me.actionIdRe));

        if (target && (match = target.className.match(me.actionIdRe))) {
       // if (target ) {
            item = me.items[parseInt(match[1], 10)];

            disabled = item.disabled || (item.isDisabled ? item.isDisabled.call(item.scope || me.origScope || me, view, recordIndex, cellIndex, item, record) : false);
           // console.log(item && !disabled);
            if (item && !disabled) {
                if (type == 'click' || (key == e.ENTER || key == e.SPACE)) {
                    fn = item.handler || me.handler;
                    if (fn) {
                        fn.call(item.scope || me.origScope || me, view, recordIndex, cellIndex, item, e, record, row);
                    }
                } else if (type == 'mousedown' && item.stopSelection !== false) {
                    return false;
                }
            }
        }
        return me.callParent(arguments);
    },

    cascade: function(fn, scope) {
        fn.call(scope||this, this);
    },

    // Private override because this cannot function as a Container, and it has an items property which is an Array, NOT a MixedCollection.
    getRefItems: function() {
        return [];
    }
});