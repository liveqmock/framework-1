Ext.define('Wms.view.framework.util.TreeComboBox', {
    extend:'Ext.form.field.Picker',
    requires: ['Ext.EventObject','Ext.tree.Panel', 'Ext.data.StoreManager'],
    alternateClassName: ['Ext.form.TreeComboBox',"Ext.form.field.TreeComboBox"],
    alias: ['widget.treecombobox', 'widget.treecombo'],
    triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
    multiSelect: false,
    delimiter: ', ',
    displayField:'text',
    valueField:'id',
    editable:false,
    treeConfig:{
        width:200,height:200,
        useArrows:true,
        rootVisible: false
    },
    defaultListConfig: {
        emptyText: '',
        maxHeight: 300
    },
    ignoreSelection: 0,
    rootVisible: false,
    initComponent: function() {
        var me = this;
        //me.store.setRootNode({"id":"0"});

        //<debug>
        if (!me.store ) {
            Ext.Error.raise('请定义Store.');
        }
        //</debug>

        this.addEvents('select');
        me.bindStore(me.store, true);
        if (!me.displayTpl) {
            me.displayTpl = Ext.create('Ext.XTemplate',
                '<tpl for=".">' +
                    '{[typeof values === "string" ? values : values.' + me.displayField + ']}' +
                    '<tpl if="xindex < xcount">' + me.delimiter + '</tpl>' +
                    '</tpl>'
            );
        } else if (Ext.isString(me.displayTpl)) {
            me.displayTpl = Ext.create('Ext.XTemplate', me.displayTpl);
        }

        me.callParent();

        if (me.store.getCount() > 0) {
            me.setValue(me.value);
        }

    },

    bindStore: function(store, initial) {
        var me = this;
        if (store) {
            me.store = Ext.data.StoreManager.lookup(store);
            if (me.picker) {
                me.picker.bindStore(store);
            }
        }
    },

    createPicker: function() {
        var me = this,
            picker,
            menuCls = Ext.baseCSSPrefix + 'menu',
            opts = Ext.apply({
                selModel: {
                    mode: me.multiSelect ? 'SIMPLE' : 'SINGLE'
                },
                floating: true,
                hidden: true,
                renderTo:Ext.getBody(),
                ownerCt: me.ownerCt,
                cls: me.el.up('.' + menuCls) ? menuCls : '',
                store: me.store,
                focusOnToFront: false
            }, me.treeConfig, me.defaultListConfig);

        picker = me.picker = Ext.create(Ext.tree.Panel, opts);
        me.mon(picker, {
            itemclick: me.onItemClick,
            refresh: me.onListRefresh,
            scope: me
        });

        me.mon(picker.getSelectionModel(), 'selectionchange', me.onListSelectionChange, me);

        return picker;
    },

    onListRefresh: function() {
        this.alignPicker();
        this.syncSelection();
    },

    onItemClick: function(picker, record){
        var me = this,
            lastSelection = me.lastSelection,
            valueField = me.valueField,
            selected;

        if (!me.multiSelect && lastSelection) {
            selected = lastSelection[0];
            if (selected && (record.get(valueField) === selected.get(valueField))) {
                me.collapse();
            }
        }
    },

    onListSelectionChange: function(list, selectedRecords) {
        var me = this,
            isMulti = me.multiSelect,
            hasRecords = selectedRecords.length > 0;
        if (!me.ignoreSelection && me.isExpanded) {
            if (!isMulti) {
                Ext.defer(me.collapse, 1, me);
            }
            if (isMulti || hasRecords) {
                me.setValue(selectedRecords, false);
            }
            if (hasRecords) {
                me.fireEvent('select', me, selectedRecords);
            }
            me.inputEl.focus();
        }
    },


    select: function(r) {
        this.setValue(r, true);
    },

    findRecordByValue: function(value) {
        return this.store.getNodeById(value);
    },

    setValue: function(value, doSelect) {
        var me = this,
            valueNotFoundText = me.valueNotFoundText,
            inputEl = me.inputEl,
            i, len, record,
            models = [],
            displayTplData = [],
            processedValue = [];

        if (me.store.loading) {
            me.value = value;
            return me;
        }

        value = Ext.Array.from(value);

        for (i = 0, len = value.length; i < len; i++) {
            record = value[i];
            if (!record || !record.isModel) {
                record = me.findRecordByValue(record);
            }
            if (record) {
                models.push(record);
                displayTplData.push(record.data);
                processedValue.push(record.get(me.valueField));
            }
            else {
                if (Ext.isDefined(valueNotFoundText)) {
                    displayTplData.push(valueNotFoundText);
                }
                processedValue.push(value[i]);
            }
        }

        me.value = me.multiSelect ? processedValue : processedValue[0];
        if (!Ext.isDefined(me.value)) {
            me.value = null;
        }
        me.displayTplData = displayTplData;
        me.lastSelection = me.valueModels = models;

        if (inputEl && me.emptyText && !Ext.isEmpty(value)) {
            inputEl.removeCls(me.emptyCls);
        }

        me.setRawValue(me.getDisplayValue());
        me.checkChange();

        if (doSelect !== false) {
            me.syncSelection();
        }
        me.applyEmptyText();

        return me;
    },

    getDisplayValue: function() {
        return this.displayTpl.apply(this.displayTplData);
    },

    getValue: function() {
        var me = this,
            picker = me.picker,
            rawValue = me.getRawValue(),
            value = me.value;

        if (me.getDisplayValue() !== rawValue) {
            value = rawValue;
            me.value = me.displayTplData = me.valueModels = null;
            if (picker) {
                me.ignoreSelection++;
                picker.getSelectionModel().deselectAll();
                me.ignoreSelection--;
            }
        }

        return value;
    },

    getSubmitValue: function() {
        return this.getValue();
    },

    isEqual: function(v1, v2) {
        var fromArray = Ext.Array.from,
            i, len;

        v1 = fromArray(v1);
        v2 = fromArray(v2);
        len = v1.length;

        if (len !== v2.length) {
            return false;
        }

        for(i = 0; i < len; i++) {
            if (v2[i] !== v1[i]) {
                return false;
            }
        }

        return true;
    },

    clearValue: function() {
        this.setValue([]);
    },

    syncSelection: function() {
        var me = this,
            ExtArray = Ext.Array,
            picker = me.picker,
            selection, selModel;
        if (picker) {
            selection = [];
            ExtArray.forEach(me.valueModels || [], function(value) {
                if (value && value.isModel && me.store.getNodeById(value.get("id"))) {
                    selection.push(value);
                }
            });

            me.ignoreSelection++;
            selModel = picker.getSelectionModel();
            selModel.deselectAll();
            if (selection.length) {
                selModel.select(selection);
            }
            me.ignoreSelection--;
        }
    },

    alignPicker: function() {
        var me = this,
            picker, isAbove,
            aboveSfx = '-above';

        if (this.isExpanded) {
            picker = me.getPicker();
            if (me.matchFieldWidth) {
                picker.setSize(me.bodyEl.getWidth(), picker.height ? picker.height : picker.maxHeight);
            }
            if (picker.isFloating()) {
                picker.alignTo(me.inputEl, me.pickerAlign, me.pickerOffset);

                isAbove = picker.el.getY() < me.inputEl.getY();
                me.bodyEl[isAbove ? 'addCls' : 'removeCls'](me.openCls + aboveSfx);
                picker.el[isAbove ? 'addCls' : 'removeCls'](picker.baseCls + aboveSfx);
            }
        }
    }
});

