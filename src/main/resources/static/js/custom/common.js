$(function(){
    $(".menu-content input").blur();
    $(".menu-compile-add").on("click",function(){
        $("<ul class='menu-content-nav'><li><input type='checkbox' name=''></li><li><input type='text' name=''></li><li><input type='text' name=''></li><li><input type='text' name=''></li><li><input type='text' name=''></li><li><input type='text' name=''></li><li><input type='text' name=''></li><li><input type='text' name=''></li></ul>").appendTo($(".menu-content-text"))
    })
    $(".menu-compile-set").on("click",function(){
        $(".menu-content-text input[type='text']").first().focus();
    })
    $(".menu-content-all").on("click",function(){
        // 判定是否全选的时候用prop，不能用attr
        if($(this).prop("checked")){
            $(".menu-content-text input[type='checkbox']").attr("checked","true");
        }else{
            $(".menu-content-text input[type='checkbox']").removeAttr("checked");
        }

    })
    $(".menu-compile-del").on("click",function(){
        $(".menu-content input[type='checkbox']").each(function(index){
            if($(".menu-content-text input[type='checkbox']").eq(index).attr("checked")){
                $(".menu-content-text input[type='checkbox']").eq(index).parent().parent().remove();
            }
        })
    })
    permStyleFn();
});


//下载模板通用方法,页面必须有一个iframe叫excelIFrame
function downTemplateFn(fileName){
    var Max=10;var Min=1;
    var Range = Max - Min;
    var Rand = Math.random();
    var num =(Min + Math.round(Rand * Range));//拼个随机数防止不走后台
    window.open('/files/download/'+fileName+'?num='+num,"excelIFrame");
}

//导出excel公共方法
function exportFn(url){
    var Max=10;var Min=1;
    var Range = Max - Min;
    var Rand = Math.random();
    var num =(Min + Math.round(Rand * Range));//拼个随机数防止不走后台
    window.open(url+'?num='+num,"excelIFrame");
}

function isNumber(obj) {
    return !isNaN(parseFloat(obj)) && isFinite(obj);
}

//修改——转换日期格式(时间戳转换为datetime格式)
function changeDateFormat(cellval) {
    if (cellval != null) {
        if(!isNumber(cellval)&&(cellval+"").indexOf("-")!=-1){
            cellval=Date.parse(cellval.replace(/-/g,"/"))
        }
        var date = new Date(cellval);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hour =  date.getHours() < 10 ? "0" + date.getHours() :  date.getHours();
        var min =  date.getMinutes() < 10 ? "0" + date.getMinutes() :  date.getMinutes();
        var ss=date.getSeconds()<10 ? "0" + date.getSeconds() :  date.getSeconds();
        if(ss=="0"||ss=="00"){
            return date.getFullYear() + "-" + month + "-" + currentDate+" "+hour+":"+min;
        }
        return date.getFullYear() + "-" + month + "-" + currentDate+" "+hour+":"+min+":"+ss;
    }
}

//转换日期格式 yyyy-MM-dd hh:mm
function changeDateByYmdhm(cellval) {
    if (cellval != null) {
        if(!isNumber(cellval)&&(cellval+"").indexOf("-")!=-1){
            cellval=Date.parse(cellval.replace(/-/g,"/"))
        }
        var date = new Date(cellval);
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        var hour =  date.getHours() < 10 ? "0" + date.getHours() :  date.getHours();
        var min =  date.getMinutes() < 10 ? "0" + date.getMinutes() :  date.getMinutes();
        return date.getFullYear() + "-" + month + "-" + currentDate+" "+hour+":"+min;
    }
}


//给jQuery扩展方法，表单数据转成json,用法： $('#form1').formToJson();
$.fn.formToJson = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }

     /*   if(o[this.name]){
            if(isDate(o[this.name])){
                var s=o[this.name];
                o[this.name]= new Date(s);;
            }
        }*/

    });
    return o;
};




/*
function isDate(str){
    str=str.substr(0,10);
    if(str.split("-").length==2){
        try {
            var date = new Date(str);
            return true;
        } catch (e) {
            return false;
        }
    }
    return false;
}*/


//json数据赋值到表单上，json示例：{aaa:bbb},aaa必须在指定的form中必须要有对应的 name和aaa对应，用法：jsonToForm($("#form1"),data.data);
function jsonToForm($form, json) {
    var jsonObj = json;
    if (typeof json === 'string') {
        jsonObj = $.parseJSON(json);
    }

    for (var key in jsonObj) {  //遍历json字符串
        var objtype = jsonTypeFn(jsonObj[key]); // 获取值类型
        if (objtype === "object") { //如果是对象，啥都不错，大多数情况下，会有 xxxId 这样的字段作为外键表的id
        } else if (objtype === "string") { //如果是字符串
            var tagobjs = $("[name=" + key + "]", $form);
            if ($(tagobjs[0]).attr("type") == "radio") {//如果是radio控件
                $.each(tagobjs, function (keyobj,value) {
                    if ($(value).attr("value") == jsonObj[key]) {
                        value.checked = true;
                    }
                });
            }else{
                  $("[name=" + key + "]", $form).val(jsonObj[key]);
            }
        }else if(objtype === "number"){
            var tagobjs = $("[name=" + key + "]", $form);
            if ($(tagobjs[0]).attr("type") == "radio") {//如果是radio控件
                $.each(tagobjs, function (keyobj,value) {
                    if ($(value).attr("value") == jsonObj[key]) {
                        value.checked = true;
                    }
                });
            }else{
                var str=jsonObj[key]+"";
                if(str.length>12){
                    var date=new Date(jsonObj[key]);
                    if(date.getYear()>=0&&date.getYear()<=1017){
                        var s=changeDateFormat(jsonObj[key]);
                        $("[name=" + key + "]", $form).val(s);
                    }
                }else{
                    $("[name=" + key + "]", $form).val(jsonObj[key]);
                }
            }
        } else { //其他的直接赋值
            $("[name=" + key + "]", $form).val(jsonObj[key]);
        }

    }
}
//jsonToForm辅助方法：设置checked
var setCheckedFn = function (name, value) {
    $("[name=" + name + "][val=" + value + "]").attr("checked", "checked");
}
//jsonToForm辅助方法：判断json类型
var jsonTypeFn = function (obj) {
    if (typeof obj === "object") {
        var teststr = JSON.stringify(obj);
        if (teststr[0] == '{' && teststr[teststr.length - 1] == '}') return "class";
        if (teststr[0] == '[' && teststr[teststr.length - 1] == ']') return "array";
    }
    return typeof obj;
}

//表格格式化字段长度方法
function formatterLenFn(value){
    var str="";
    var len=this.len
    if(value&&value.length>len){
        str="<li title='"+value+"' style='word-break: break-all;word-wrap: break-all;'>"+value.substr(0,len)+"..."+"</li>"
    }else{
        str=value;
    }
    return str;
}

function getLenWidth(str) {
    return str.replace(/[\u0391-\uFFE5]/g,"aa").length;  //先把中文替换成两个字节的英文，在计算长度
};


function formatJsonFn(txt,compress/*是否为压缩模式*/){/* 格式化JSON源码(对象转换为JSON文本) */
    var indentChar = '    ';
    if(/^\s*$/.test(txt)){
        //alert('数据为空,无法格式化! ');
        return;
    }
    try{var data=eval('('+txt+')');}
    catch(e){
        //alert('数据源语法错误,格式化失败! 错误信息: '+e.description,'err');
        return;
    };
    var draw=[],last=false,This=this,line=compress?'':'\n',nodeCount=0,maxDepth=0;

    var notify=function(name,value,isLast,indent/*缩进*/,formObj){
        nodeCount++;/*节点计数*/
        for (var i=0,tab='';i<indent;i++ )tab+=indentChar;/* 缩进HTML */
        tab=compress?'':tab;/*压缩模式忽略缩进*/
        maxDepth=++indent;/*缩进递增并记录*/
        if(value&&value.constructor==Array){/*处理数组*/
            draw.push(tab+(formObj?('"'+name+'":'):'')+'['+line);/*缩进'[' 然后换行*/
            for (var i=0;i<value.length;i++)
                notify(i,value[i],i==value.length-1,indent,false);
            draw.push(tab+']'+(isLast?line:(','+line)));/*缩进']'换行,若非尾元素则添加逗号*/
        }else   if(value&&typeof value=='object'){/*处理对象*/
            draw.push(tab+(formObj?('"'+name+'":'):'')+'{'+line);/*缩进'{' 然后换行*/
            var len=0,i=0;
            for(var key in value)len++;
            for(var key in value)notify(key,value[key],++i==len,indent,true);
            draw.push(tab+'}'+(isLast?line:(','+line)));/*缩进'}'换行,若非尾元素则添加逗号*/
        }else{
            if(typeof value=='string')value='"'+value+'"';
            draw.push(tab+(formObj?('"'+name+'":'):'')+value+(isLast?'':',')+line);
        };
    };
    var isLast=true,indent=0;
    notify('',data,isLast,indent,false);
    return draw.join('');
}


//获取URL路径中的参数
function GetUrlStrFn(name)
{
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if(r!=null)return  unescape(r[2]); return null;
}


/**
 * 下面的方法是针对提示信息提取的公共方法，为了防止样式不一致，所以抽象出来了。
 * -------------------------------------------------------------------------------------------------begin
 */

//至少选中一行
function alertEditLineFn(){
    $.alert({title: '提示',content: '只能编辑一行！',buttons:{知道了:{btnClass: 'btn-blue'}}});
}

//至少选中一行
function alertSelectLineFn(){
    $.alert({title: '提示',content: '请至少选中一行！',buttons:{知道了:{btnClass: 'btn-blue'}}});
}
//删除失败
function alertDeleteErrorFn(){
    $.alert({title: '提示',content: '删除失败！',buttons:{关闭:{btnClass: 'btn-blue'}}});
}

//自定义消息
function alertUseMsgFn(msg,callback,autoClose){
    $.alert({title: '提示',content: msg,autoClose:autoClose,buttons:{关闭:{btnClass: 'btn-blue',action:callback}}});
}
//登录失效
function loginInValid(){
    $.confirm({
        title: '登录超时',
        content: '登录超时,点击确定重新登录.',
        autoClose: 'OK|5000',
        buttons: {
            OK: {
                text: '确定',
                action: function () {
                    cfs.toLogin();
                }
            }
        }
    });
}

/**
 * -------------------------------------------------------------------------------------------------end
 */

//弹出处理中请稍后提示框
var lodingDialog;
var  islodingDialog =false;
function showlodingFn(){
    if(islodingDialog){
        return false;
    }else{
        lodingDialog= $.dialog({
            columnClass: 'xsmall',
            closeIcon:false,
            title: false,
            content: '<i class="fa fa-spinner fa-spin fa-2x"></i><span style="font-size: 14px;font-weight: bold;padding-left: 5px;">处理中,请稍后...</span>'
        });
        islodingDialog = true;
    }
    return true;

}
//关闭提示框
function closelodingFn(){
    if(lodingDialog){
        setTimeout(function(){
            lodingDialog.close();
            lodingDialog=null;
            islodingDialog = false;
        },1000);
    }
}

//给嵌入到权限系统的页面加样式
function permStyleFn(){
    try {
        parent.document;
    } catch (e) {
        var shell=$(".shell").html();
        if(!shell){
            //表示是权限系统，需要设置body的内边距
            $("body").css("padding","0px 10px");
        }

    }



}


/*引入工具js*/
document.write("<script language='javascript' src='/js/custom/base.js'></script>");