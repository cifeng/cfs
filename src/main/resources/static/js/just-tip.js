/* 
 * justToolsTip v1.0
 * Simple customizable tooltip with confirm option and 3d effects
 * (c)2015 chemailbox@163.com, qq:977877
 */

(function($) {

    /** just tip **/
    function justTools(elem, options){
        this.elem = elem;
        this.set = options;
        //this.obj = null;
    }
    justTools.prototype = {
        addAnimation: function(){
            switch(this.set.animation){
                case 'none':
                    break;
                case 'fadeIn':
                    this.obj.addClass('animated fadeIn');
                    break;
                case 'flipIn':
                    switch(this.set.gravity){
                        case 'top':
                            this.obj.addClass('animated flipInTop');
                            break;
                        case 'bottom':
                            this.obj.addClass('animated flipInBottom');
                            break;
                        case 'left':
                            this.obj.addClass('animated flipInLeft');
                            break;
                        case 'right':
                            this.obj.addClass('animated flipInRight');
                            break;
                    }
                    break;
                case 'moveInLeft':
                    this.obj.addClass('animated moveLeft');
                    break;
                case 'moveInTop':
                    this.obj.addClass('animated moveTop');
                    break;
                case 'moveInBottom':
                    this.obj.addClass('animated moveBottom');
                    break;
                case 'moveInRight':
                    this.obj.addClass('animated moveRight');
                    break;
            }
        },
        close:function(){
            this.obj.remove();
        },
        setPosition:function(){
            var setPos = {};
            var pos = { x: this.elem.offset().left, y: this.elem.offset().top };
            var wh = { w: this.elem.outerWidth(), h: this.elem.outerHeight() };
            var rightTmp = ( pos.x + wh.w / 2 ) + this.obj.outerWidth() / 2 ;
            var leftTmp = ( pos.x + wh.w / 2 ) - this.obj.outerWidth() / 2 ;
            //console.log(leftTmp)
            switch(this.set.gravity){
                case 'top':
                    if(rightTmp > $(window).width() ){
                        setPos = {
                            x: pos.x + wh.w - this.obj.outerWidth(),
                            y: pos.y - this.obj.outerHeight() - this.set.distance
                        };
                        this.obj.find(".just-" + this.set.gravity).css("left", this.obj.outerWidth() - wh.w/2 + "px")
                    }else if( leftTmp < 0 ){
                        setPos = {
                            x: pos.x,
                            y: pos.y - this.obj.outerHeight() - this.set.distance
                        };
                        this.obj.find(".just-" + this.set.gravity).css("left", wh.w/2 + "px")
                    }else{
                        setPos = {
                            x: pos.x - (this.obj.outerWidth() - wh.w)/2,
                            y: pos.y - this.obj.outerHeight() - this.set.distance
                        };
                    }
                    break;
                case 'bottom':
                    if(rightTmp > $(window).width() ){
                        setPos = {
                            x: pos.x + wh.w - this.obj.outerWidth(),
                            y: pos.y + wh.h + this.set.distance
                        };
                        this.obj.find(".just-" + this.set.gravity).css("left", this.obj.outerWidth() - wh.w/2 + "px")
                    }else if( leftTmp < 0 ){
                        setPos = {
                            x: pos.x,
                            y: pos.y + wh.h + this.set.distance
                        };
                        this.obj.find(".just-" + this.set.gravity).css("left", wh.w/2 + "px")
                    }else{
                        setPos = {
                            x: pos.x - (this.obj.outerWidth() - wh.w)/2,
                            y: pos.y + wh.h + this.set.distance
                        };
                    }
                    break;
                case 'left':
                    setPos = {
                        x: pos.x - this.obj.outerWidth() - this.set.distance,
                        y: pos.y - (this.obj.outerHeight() - wh.h)/2
                    };
                    break;
                case 'right':
                    setPos = {
                        x: pos.x + wh.w + this.set.distance,
                        y: pos.y - (this.obj.outerHeight() - wh.h)/2
                    };
                    break;
            }
            this.obj.css({"left": setPos.x + "px", "top": setPos.y + "px"});
        },
        setEvent:function(){
            var self = this;
            //console.log(self.set.events)
            if(self.set.events =="click" || self.set.events =="onclick"){
                self.obj.on("click", function(e){
                    e.stopPropagation();
                })
                $(document).click(function(){
                    //$(".just-tooltip").remove();
                    self.obj.remove();
                });
            }
           //console.log(self.set.events)
            if(self.set.events =="mouseover" || self.set.events =="onmouseover" || self.set.events =="mouseenter"){
                this.elem.on("mouseout, mouseleave",function(){
                    self.close();
                });
                console.log(1)
            }
        },
        setConfirmEvents:function(){
            var self = this;
            var yes = this.obj.find(".just-yes");
            var no = this.obj.find(".just-no");
            yes.click(function(){
                if(self.set.onYes(self)==true){
                    self.close();
                };
            })
            no.click(function(){
                self.close();
                self.set.onNo(self);
            })
        },
        addConfirm:function(){
            this.obj.append("<div class='just-confirm'><button type='button' class='just-yes'>"
                + this.set.yes +"</button><button type='button' class='just-no'>" + this.set.no +"</button></div>");
            this.setConfirmEvents();
        },
        setContent:function(){
            this.obj = $("<div id=" + this.set.id + " class='just-tooltip " + this.set.theme + "'" +
                "style='width:" + this.set.width + "'><div class='just-con'>" + this.set.contents + "</div>" + "<span class='just-" + this.set.gravity + "'></span></div>");
            $("body").append(this.obj);
            //this.elem.append(this.obj);
            this.setEvent();
            this.addAnimation();
            if(this.set.confirm==true){
                this.addConfirm();
            }
        },
        init:function(){
            //var evt = window.event || arguments.callee.caller.arguments[0];
            //var evt = $.event.fix(event || window.event);
            var e = arguments.callee.caller.caller.caller.caller.caller.arguments[0] || $.event.fix(event || window.event)
            this.set.events = e.type;
            //this.elem.on(this.set.events, function(e){
                var justToolObj = $(".just-tooltip");
                if(justToolObj){
                    justToolObj.remove();
                }
                e.stopPropagation();
                this.setContent();
                this.setPosition();
            //});
            var self = this;
            $(window).resize(function(){
                self.setPosition();
            })
        }
    }
    $.fn.justToolsTip = function(options){
        var defaults = {
            //opacity: 0.5,
            height:"auto",
            width:"auto",
            contents:'',
            gravity: 'top',  //top, left, bottom, right
            theme: '',//className
            distance:10,
            //events: 'mouseover',
            animation: 'none', //none, fadeIn, flipIn, moveInLeft, moveInTop, moveInBottom, moveInRight
            confirm: false,
            yes: '确定',
            no: '取消',
            //delay: 1000,
            onYes: function(){}, //返回ture，关闭tools
            onNo: function(){}
        }
        this.each(function(){
            options = $.extend(defaults, options);
            options.id = new Date().getTime();
            var tooltip = new justTools($(this), options);
            tooltip.init();
        }); 
    }

})(jQuery);
