/**
 * Created by liuguomin on 13-10-14.
 * 不要使用id  使用inputValue
 * 使用id 会出现取值 有未定义的情况 node.data["inputValue"]
 */

 //角色列表模型
Ext.define('Wms.model.framework.TreeModel', {
    extend:'Ext.data.TreeModel',
    fields:["text","cls","expanded","leaf","name","inputValue","id"]
});
