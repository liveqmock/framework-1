Ext.define('Ext.ux.multiupload.Panel', {
    extend: 'Ext.grid.Panel',
    requires: [   'Ext.ux.multiupload.Upload' ],
    viewConfig: {  markDirty: false  },   width: 600,
    store: { 
    	fields: ['id', 'name', 'size', 'status', 'progress','filename','path','view'] ,
        reader: {
            type: 'json',
            root: "resultMap",
            messageProperty: "message"
        }
    },
    uploadConfig: {
        uploadUrl: '../../../file/upload/1.json',
        maxFileSize: 4 * 1024 * 1024,
        maxQueueLength: 5 ,
        fileFilter:'Images (*.jpg)|*.jpg'
    },
    initComponent: function () {
        var me = this;
        //复选框
        selectCheckBox={
            selType : 'checkboxmodel'
        };
        me.selModel=selectCheckBox;

        me.addEvents('fileuploadcomplete');

        me.tbar = [{
            xtype: 'uploader',
            uploadConfig: this.uploadConfig,
            listeners: {
                'fileadded': function (source, file) {
                    this.up('grid').store.add({
                        id: file.fileIndex,
                        name: file.fileName,
                        size: file.fileSize,
                        status: 'waiting...',
                        filename:'',
                        path:'',
                        view:'',
                        progress: 0
                    });
                },
                'uploadstart': function (source, file) {
                    var grid = this.up('grid');
                    var record = grid.store.getById(file.fileIndex);

                    if (record) {
                        record.set('status', '上传中...');
                    }
                },
                'uploadprogress': function (source, file) {
                    var grid = this.up('grid');
                    var record = grid.store.getById(file.fileIndex);
                    if (record) {
                        var p = Math.round(file.fileProgress / file.fileSize * 100);
                        record.set('progress', p);
                    }
                },
                'uploaddatacomplete': function (source, data) {
                	var result  = eval('('+data.data+')');
                	console.info(result.success);
                    if(!result.success){
                    	console.info(33333);
                        Ext.Msg.alert("错误",result.message);
                        return;
                    }
                    var grid = this.up('grid');
                    var record = grid.store.getById(data.fileIndex);
                    if (record) {
                        record.set('status', '完成');
                        record.set('filename',result.resultMap.fileName);
                        record.set('path',result.resultMap.path);
                        record.set('view',result.resultMap.path+result.resultMap.fileName);
                    }
                    me.fireEvent('fileuploadcomplete', file.data);
                },
                'uploaderror': function (src, data) {
                	console.info(data);
                    var msg = '上传失败！<br />错误类型: ' + data.errorType;

                    switch (data.errorType) {
                        case 'FileSize':
                            msg = '文件过大: ' + Ext.util.Format.fileSize(data.fileSize) +
                                '，文件大小不能超过： ' + Ext.util.Format.fileSize(data.maxFileSize) + '。';
                            break;

                        case 'QueueLength':
                            msg = '上传文件数量： ' + data.queueLength +
                                '. 超出批量上传最大数量： ' + data.maxQueueLength + '.';
                            break;
                    }

                    Ext.Msg.show({
                        title: '消息',
                        msg: msg,
                        buttons: Ext.Msg.OK,
                        icon: Ext.Msg.ERROR
                    });
                }
            }
        }];

        me.columns = [
            {  header: 'Id',  dataIndex: 'id',  width: 75,
                renderer: function (v) { return v + 1; }
            },
            { hearder:'缩略图',dataIndex:'view',width:100,
                renderer: function(value) {
                    return Ext.String.format("<img src='{0}' width='100' height='100'>", value);
                }
            },
            { header: '名称', dataIndex: 'filename', width: 200},
            { header: '路径', dataIndex: 'path', width: 200 },
            { header: '大小', dataIndex: 'size', renderer: Ext.util.Format.fileSize },
            { header: '状态', dataIndex: 'status' },
            { header: '进度', dataIndex: 'progress', flex:1,
                renderer: function (v) { return v + '%'; }
            }
        ];

        me.callParent(arguments);
    }
});


