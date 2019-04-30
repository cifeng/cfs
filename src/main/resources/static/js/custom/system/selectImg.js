$(function(){
    $("i").click(function(){
        var mybtn=$(this);
        showlodingFn();
        setTimeout(function(){
            closelodingFn();
            var s=mybtn.attr("class");
            if(s){
                var targetTab=GetUrlStrFn("tab");
                var inputName=GetUrlStrFn("input");
                var currentTab=parent.getTabFn();
                parent.assignmentFn(currentTab,targetTab,s,inputName);
            }
        },2000);

    });



});