/**
 * Created by liuguomin on 13-10-12.
 */
Ext.define('Wms.view.framework.util.CheckboxTree', {
    extend: 'Ext.tree.Panel',
    alias:'widget.checkboxtree',

   // id:"checkboxTree",

    useArrows: true,
    rootVisible: false,
    //multiSelect: true,
    //singleExpand: true,
    autoRefresh:true,
    //选中节点是否同时选择所有子节点
    subNodeStatus:true,
    //选中节点是否同时选择所有父节点
    parentNodeStatus:true,
    checklist:[],
    //未选择的
    unchecklist:[],
    //checklist值是否有更改
    isChanged:false,
    /**
     * 检查节点的所有子节点，是否有选中状态
     * @param parentNode
     */
    isChecked:function(node){
      for(var i =0;i<node.childNodes.length;i++){
          var childNode = node.childNodes[i];
          if(childNode.data["checked"]==true)
            return true;
      }
      return false;
    },

    checkAllSubNode:function(node,checked){
        var childNodes = node.childNodes;
        for(var i =0;i<childNodes.length;i++){
            var childNode = childNodes[i];
            childNode.set("checked",checked);
            if(childNode.childNodes.length>0){ //如果子节点还有子节点
                this.checkAllSubNode(childNode,checked);

            }
        }
    },

    checkAllParentNode:function(node,checked){
        var parentNode = node.parentNode;
        if(parentNode!=null){
            if(checked){
                parentNode.set("checked",true);
            }
            else{ //false要检测父类的其他子类是否有勾选状态，如果其他子类有勾选状态，则父类不能取消
                parentNode.set("checked",false);
                for(var i=0;i<parentNode.childNodes.length;i++){
                    if(parentNode.childNodes[i].data["checked"])
                        parentNode.set("checked",true);
                }
            }
            this.checkAllParentNode(parentNode,checked);
        }
    },

    /**
     * 刷新节点
     * @param node
     * @param checked
     * @param subNodeStatus 是否应用子节点
     * @param parentNodeStatus 是否应用父节点
     *
     */
    refreshCheckbox:function(node,checked,subNodeStatus,parentNodeStatus){
        if(subNodeStatus){
            this.checkAllSubNode(node,checked);
        }
        if(parentNodeStatus){
            this.checkAllParentNode(node,checked);
        }
    },
    //获取选中的id集合
    getChecklist:function(){
        this.checklist=[];
        var checkedNode = this.getChecked();
        if(checkedNode!="" && checkedNode.length>0){
            for(var i=0;i<checkedNode.length;i++){
                var n = checkedNode[i];
                if(n.data["inputValue"]!="")
                    this.checklist.push(n.data["inputValue"]);
            }
        }
        return this.checklist;
    },
    /**
     * 获取未选中的id集合
     * @returns {*}
     */
    getUnChecklist:function(){
        this.unchecklist=[];
        var rootnode = this.getRootNode().childNodes[0];
        if((!rootnode.data["checked"]) && rootnode.data["inputValue"]!="")
            this.unchecklist.push(rootnode.data["inputValue"]);
        this.getUnChecklist2(rootnode);
        return this.unchecklist;
    },

    //递归
    getUnChecklist2:function(node){
        if(node!=null){
            if(node.childNodes!=null && node.childNodes.length>0){
                for(var i=0;i<node.childNodes.length;i++){
                    var n = node.childNodes[i];
                    if((!n.data["checked"]) && n.data["inputValue"]!="")
                        this.unchecklist.push(n.data["inputValue"]);
                    this.getUnChecklist2(n);
                }
            }
        }
    },

    initComponent: function() {
        this.addListener({
            checkchange:{
                fn:function(node,checked){
                    this.isChanged = true;
                    var rootnode = this.getRootNode().childNodes[0];
                    //this.refreshCheckbox(rootnode);
                    this.refreshCheckbox(node,checked,this.subNodeStatus,this.parentNodeStatus);
                },
                scope:this
            }

        });
/*        //初始化时加载默认被选中的值
        this.reloadChecklist(this.getChecked());*/

        this.callParent();
    }



});