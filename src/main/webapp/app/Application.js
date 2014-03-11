/**
 * Ext.application 应用入口类
 */

Ext.define('Wms.Application', {
     extend: 'Ext.app.Application',



     //应用命名空间， MVC中各部分中自定义的类，都需要以此开头。
     name: 'Wms',




     //自动查找view/Viewport.js创建视图。
     autoCreateViewport: true,






     //初始化整个应用时需要用到的   模型，数据，控制器 ，
     // ------------如需要加快桌面显示速度，则只加载桌面控制器，其它控制器在创建弹窗时手动初始化。

     controllers: ["_Core_DesktopController"]


    //注意：在这里添加了控制器，控制器文件下载后就会立即初始化执行init()方法与onLaunch()方法。
    // 因此在这里直接添加的控制器，尽量不要作为弹出窗口的控制器类型使用，如新闻中心。而应该作为一个独立的在桌面上使用的功能。
    // 因为在创建弹窗时，DesktopController中会通过传入的控制器参数，手动调用对应的控制器的init()与onLaunch()方法。

});




