/**
 * 角色模块工具
 * Created by liuguomin on 13-11-15.
 */
Ext.define('Wms.utils.framework.RoleUtils',{
    //记录当前list列表查看的角色的父角色ID
    queueParentRoleId:new Array(),
    //角色list面板
    addRoleButton:null,

    //添加角色按钮
    setAddRoleButton:function(value){
        this.addRoleButton=value;
    },

    removeAll:function(){
        this.queueParentRoleId= new Array();
        Ext.getCmp("queryParentRoleButton").setDisabled(true);
        if(this.addRoleButton != undefined)
            this.addRoleButton.setText("添加角色");
    },

    //增加
    push:function(value){
        if(this.queueParentRoleId.length==0){ //如果队列长度为0，表示要返回的是第一个角色面板
            var str=Ext.util.Cookies.get("userName");
            var str2 ="YWRtaW4=";
            if(str.indexOf(str2)!=-1){ //用户名为admin
                this.queueParentRoleId.push(0);
            }else
                this.queueParentRoleId.push(value);
        }else
            this.queueParentRoleId.push(value);
        Ext.getCmp("queryParentRoleButton").setDisabled(false);
    },
    //移除第一个,返回移除的数据
    pop:function(){
        var value =this.queueParentRoleId.pop();
        if(this.queueParentRoleId.length==0){
            Ext.getCmp("queryParentRoleButton").setDisabled(true);
            var str=Ext.util.Cookies.get("userName");
            var str2 ="YWRtaW4=";
            if(str.indexOf(str2)!=-1){ //用户名为admin
                if(this.addRoleButton != undefined)
                    this.addRoleButton.setText("添加角色")
            }
        }
        return value;
    },
    //刷新状态
    refresh:function(){
        if(this.queueParentRoleId.length==0){
            Ext.getCmp("queryParentRoleButton").setDisabled(true);
            var str=Ext.util.Cookies.get("userName");
            var str2 ="YWRtaW4=";
            if(str.indexOf(str2)!=-1){ //用户名为admin
                this.addRoleButton.setText("添加角色")
            }
        }if(this.queueParentRoleId.length>0)
            Ext.getCmp("queryParentRoleButton").setDisabled(false);
    }



});