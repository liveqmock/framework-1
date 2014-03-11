//编辑器插入图片功能
Ext.define("Ext.ux.multiupload.EditInsertImgWindow", {
    extend: "Ext.window.Window",
    requires: ["Ext.ux.multiupload.Panel"],

    singleton: true,hideMode: 'offsets',  closeAction: 'hide',  closable: true,
    layout: "fit",  modal: true,
    imagePath: "",  resizable: true,  title: '插入图片',
    width: 800, height: 400,

    initComponent: function () {
        var me = this;
        me.picmanager = Ext.create("Ext.ux.multiupload.Panel", { fileFilter:'Images (*.jpg)|*.jpg'});
        me.items = [me.picmanager];

        me.dockedItems=[
            {  xtype:"toolbar",  dock:"bottom",   ui:"footer", layout:{pack:"center"},
                items:[
                    {xtype:"button", text: '确 定',  action:"save",  margin:'0 15 0 0',  handler:me.onInsert  },
                    { xtype:"button", text:"取 消",  handler:me.onCloseWin }
                ]
            }
        ]  ;

        me.callParent(arguments);
    },


    //将图片插入到编辑器中
    onInsert: function () {
     var win=this.up("window");
   //  var list= this.up("window").down("grid").store.data.items;
     var list = this.up("window").down("grid").getSelectionModel().getSelection();

        if(list.length>0){
            var html = "";
            Ext.each(list,function(obj,index){
                html += "<div>"+Ext.String.format("<img src='{0}{1}' >", obj.data["path"], obj.data["filename"])+"</div>";
            });
            console.log(html);
            win.ed.execCommand('mceInsertContent', false, html);
        }
        win.close();
    },

    onCloseWin: function(){
        this.up("toolbar").up("window").close();
    }
})