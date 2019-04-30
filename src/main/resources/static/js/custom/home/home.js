$(function(){
    init();
    $("#logout_id").click(function(){
        window.location.href="/logout";
    });
});
function init(){
    $.ajax({
        url : "/menu/menulist",
        success : function(data) {
            var str="";
            //获取数据遍历得到第一层
            $.each(data.data,function(i,one){
                str+="<li><p><i class='"+one.icon+"'></i>&nbsp;&nbsp;"+one.title+"</p>" ;//拼装第一层
                var str_two="";
                //获取当前菜单下的子菜单
                $.each(one.child,function (y,two) {
                    //拼装第二层
                    str_two+=" <li ><a name='menu_a' data-myid='"+two.id+"' style='width: 100%;height: 100%;display: block'><i class='"+two.icon+"'></i>&nbsp;&nbsp;<input type='hidden' value='"+two.url+"' name='menu_url'  /><input type='hidden' value='"+two.title+"' name='menu_title' />"+two.title+"</a></li>"
                    //再有第三层还可以继续遍历，目前支持3层树
                });
                //将第一层和第二层进行组合拼装
                str+= "<ul>"+str_two+"</ul>" +"</li>"
            });
            //赋值给左树
            $(".navLeft-first").html(str);
            homeEventFn();//给左树标标签加上操作效果
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        }
    });
    //获取用户名
    $.ajax({
        url : "/getloginname",
        success : function(data) {
            if(data.code==200){
                $("#userName_id").text(data.data);
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
        }
    });




}





function homeEventFn(){
    // 阻止iframe的父级刷新页面
    var tabs = $( "#tabs" ).tabs({
        heightStyle : "content",
        //点击上方tab页刷新页面  暂时注掉
        activate: function( event, ui ) {
          /*  var id = ui.newPanel.selector;
            $(id).find("iframe").attr("src",$(id).find("iframe").attr("src"));*/
        }
    });

    // 关闭图标：当点击时移除标签页
    tabs.delegate( "span.ui-icon-close", "click", function() {
        var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
        $( "#" + panelId ).remove();
        tabs.tabs( "refresh" );
    });
    var _num=2;
    $(".navLeft-first a").on("click",function(e){

        var myid=$(this).attr("data-myid");
        var url = $(this).find("[name ='menu_url']").val();
        var title =  $(this).find("[name ='menu_title']").val();
        // $(this).css("background","red");
        if(-1!=$("#"+myid).index()){
            $("#tabs").tabs( "option", "active", $("#"+myid).index() );
            $("#tabs-" +myid).find("iframe").attr("src",url);
            e.stopPropagation();
            return;
        }
        var label = title,
            id = "tabs-" + myid,
            li = "<li id='"+myid+"'><a href='#"+id+"'>"+title+"</a> <span class='ui-icon ui-icon-close' role='presentation'>Remove Tab</span></li>",
            tabContentHtml = "<iframe id='iframe"+id+"' name='iframe"+id+"' frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" scrolling=\"yes\" src='"+url+"'></iframe>";

        tabs.find( ".ui-tabs-nav" ).append( li );
        tabs.append( "<div id='" + id + "' style='height: 800px'><p style='height: 100%'>"+tabContentHtml+"</p></div>" );
        tabs.tabs( "refresh" );
        tabs.tabs( "load", "#"+id );

        $("#tabs").tabs( "option", "active", $("#"+myid).index() );
        e.stopPropagation();
    });

    $(".navLeft-first>li").on("click",function(){
        var index = $(this).index();
        if($(".navLeft-first>li>ul").eq(index).css("display")=="none"){
            $(".navLeft-first>li>ul").eq(index).slideDown();
        }else if($(".navLeft-first>li>ul").eq(index).css("display")=="block"){
            $(".navLeft-first>li>ul").eq(index).slideUp();
        }

    });
}


function addTabsFn(url,title,myid){

    if(-1!=$("#"+myid).index()){
        $("#tabs").tabs( "option", "active", $("#"+myid).index() );
        $("#tabs-" +myid).find("iframe").attr("src",url);
        return;
    }

    var tabs = $( "#tabs" ).tabs({
        heightStyle : "content"
    });
    tabs.delegate( "span.ui-icon-close", "click", function() {
        var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
        $( "#" + panelId ).remove();
        tabs.tabs( "refresh" );
    });
    var id = "tabs-" + myid;
    var li = "<li id='"+myid+"'><a href='#"+id+"'>"+title+"</a> <span class='ui-icon ui-icon-close' role='presentation' style='display: block'>Remove Tab</span></li>";
    var tabContentHtml = "<iframe id='iframe"+id+"' name='iframe"+id+"' frameborder=\"0\" width=\"100%\" height=\"100%\" marginheight=\"0\" marginwidth=\"0\" scrolling=\"yes\" src='"+url+"'></iframe>";
    tabs.find( ".ui-tabs-nav" ).append( li );
    tabs.append( "<div id='" + id + "' style='height: 800px'><p style='height: 100%'>"+tabContentHtml+"</p></div>" );

    tabs.tabs( "refresh" );
    tabs.tabs( "load", "#"+id );
    $("#tabs").tabs( "option", "active", $("#"+myid).index() );
}


function getTabFn(){
    var tabIndex = $("#tabs").tabs('option', 'active');
    var selected = $("#tabs ul>li a").eq(tabIndex).attr('href');
    selected=selected.replace("#","")
    return selected;
}


function assignmentFn(currentTab,targetTab,value,inputName){
    $("#"+currentTab).remove();
    $("li[aria-controls='"+currentTab+"']").remove();
    $("#tabs").tabs( "option", "active",$("li[aria-controls='"+targetTab+"']").index());
    var iframekey="iframe"+targetTab;
    $(window.frames[iframekey].document).find("input[name='"+inputName+"']:visible").val(value);


}