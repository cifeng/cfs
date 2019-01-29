(function ( $, window, document) {
    var pluginName = "qlist",

        //默认配置参数  default settings
        defaults = {
            width:600,
            height:300 ,
            leftToRight:function(){},
            rightToLeft:function(){},
            //clickImage:function(element,index){}, //点击图片时回调函数,参数：element：图片元素，index：图片在图片数组中的序号
        };
        var _plugin;
        function Plugin ( element, options,data ) {
            this.element=element;
            this.data=data;
            this.settings = $.extend( {}, defaults, options );
            this._defaults = defaults;
            this._name = pluginName;
            this.init();
            this.initItemEvent();
            this.initBtnEvent();
        };

    Plugin.prototype = {
        init: function () {   //初始化
            var e = this;
            var s="";
            $.each(e.data,function(i,item){
                s+='<span class="item" data-id="'+item.id+'">'+item.name+'</span>';
            });


            var str='<div class="list-body">' +
                '         <div class="item-box left-box" >'
                            +s+
                '         </div>' +
                '         <div class="center-box">' +
                '           <button type="button" class="add-one" title="添加选中项">></button>' +
                '           <button type="button" class="add-all" title="添加全部">>></button>' +
                '           <button type="button" class="remove-one" title="移除选中项"><</button>' +
                '           <button type="button" class="remove-all" title="移除全部"><<</button>' +
                '         </div>' +
                '         <div class="item-box right-box" >' +
                '         </div>' +
                '   </div>';

            $(e.element).html(str);
            $(e.element).addClass("list-select");
            $(e.element).css({"width": this.settings.width, "height": this.settings.height});
            $(e.element).children(".list-body").children("div").height(this.settings.height * 0.9);
            $(e.element).children(".list-body").children(".item-box").css("width", "42%");

        },
        itemClick: function () {
            $(this).parent('.item-box').find('.item').removeClass('selected-item');
            $(this).addClass('selected-item');
        },

        leftMoveRight: function () {
           $(_plugin.element).find('.list-body .right-box').append($(this).removeClass('selected-item'));
            _plugin.initItemEvent();
        },
        rightMoveLeft: function () {
            $(_plugin.element).find('.list-body .left-box').append( $(this).removeClass('selected-item'));
            _plugin.initItemEvent();
        },
        initItemEvent: function () {
            var e = this;
            // 左边列表项单击、双击事件
            $(e.element).find('.list-body .left-box').find('.item').unbind('click');
            $(e.element).find('.list-body .left-box').find('.item').unbind('dblclick');
            $(e.element).find('.list-body .left-box').find('.item').each(function () {
                $(this).on("click", e.itemClick);
                $(this).on('dblclick', e.leftMoveRight);
                //注册对外提供事件 左向右添加时触发
                $(this).on("dblclick",e.settings.leftToRight);
            });

            // 右边列表项单击、双击事件
            $(e.element).find('.list-body .right-box').find('.item').unbind('click');
            $(e.element).find('.list-body .right-box').find('.item').unbind('dblclick');
            $(e.element).find('.list-body .right-box').find('.item').each(function () {
                $(this).on('click', e.itemClick);
                $(this).on('dblclick', e.rightMoveLeft);
                //注册对外提供时间 右向左添加时触发
                $(this).on('dblclick', e.settings.rightToLeft);
            });
        },
        initBtnEvent: function () {
            var e = this;
            var btnBox = $(e.element).find('.list-body .center-box');
            var leftBox = $(e.element).find('.list-body .left-box');
            var rightBox = $(e.element).find('.list-body .right-box');

            // 添加一项
            btnBox.find('.add-one').on('click', function () {
                leftBox.find('.selected-item').trigger('dblclick'); // 触发双击事件
            });
            // 添加所有项
            btnBox.find('.add-all').on('click', function () {
                leftBox.find('.item').trigger('dblclick');
            });
            // 移除一项
            btnBox.find('.remove-one').on('click', function () {
                rightBox.find('.selected-item').trigger('dblclick'); // 触发双击事件
            });
            // 移除所有项
            btnBox.find('.remove-all').on('click', function () {
                rightBox.find('.item').trigger('dblclick');

            });
        },
        getSelected:function(){
            var e = this;
           return $(e).find('.selected-item');
        }

    }

    $.fn[ pluginName ] = function (options,data) {   //向jQuery注册插件
        var e = $(this.selector);
        _plugin= new Plugin(e, options,data);
        return e;
    };







})(jQuery, window, document);

/*

$(function(){

    $.fn.initList = function(){
        var selectTitle = $(this);
        selectTitle.draggable(); // 添加拖拽事件

        /

        /!**
         * 初始化添加、移除、获取值按钮事件
         *!/
        function initBtnEvent(){
            var btnBox = selectTitle.find('.list-body .center-box');
            var leftBox = selectTitle.find('.list-body .left-box');
            var rightBox = selectTitle.find('.list-body .right-box');

            // 添加一项
            btnBox.find('.add-one').on('click', function(){
                leftBox.find('.selected-item').trigger('dblclick'); // 触发双击事件
            });
            // 添加所有项
            btnBox.find('.add-all').on('click', function(){
                leftBox.find('.item').trigger('dblclick');
            });
            // 移除一项
            btnBox.find('.remove-one').on('click', function(){
                rightBox.find('.selected-item').trigger('dblclick'); // 触发双击事件
            });
            // 移除所有项
            btnBox.find('.remove-all').on('click', function(){
                rightBox.find('.item').trigger('dblclick');

            });

            selectTitle.find('.list-footer').find('.selected-val').on('click',getSelectedValue);
        }

        initItemEvent();
        initBtnEvent();
    }




    $('#selectTitle').initList();





});*/
