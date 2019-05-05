$(function(){
    initTable();//初始化表格
    initSelectFn();//初始化下拉框组件

    //搜索操作
    $("#search_id").bind("click", initTable);
    //添加操作
    $("#save_id").click(function(){
        $("#modal_header_title_info").text("添加用户信息记录");
        $("#btn_save_id").show();
        $("#btn_edit_id").hide();
        $("#form_group_url").hide();//如果是新添加的数据则隐藏路径
        //initSelectFn();
        selectNode(0)
        $('#myModal').modal("show");

    });
    //添加保存操作
    $("#btn_save_id").click(function(){
        $('#form1').bootstrapValidator('validate');
        if($('#form1').data('bootstrapValidator').isValid()) {
            var formData = $('#form1').formToJson();
            cfs.ajaxPostJson("/menu/save",formData,false,function(data){
                if (data.code == 0) {
                    $("#form1").resetForm();
                    $('#form1').data('bootstrapValidator').resetForm(true);//重置表格
                    $('#myModal').modal("hide");
                    initTable();
                } else {
                    alertUseMsgFn("添加失败");
                }
            },true);
        }

    });

    //修改操作
    $("#edit_id").click(function(){
        var res= $('#authorityList').bootstrapTable('getSelections');
        if(res.length>0){
            if(res.length==1) {
                //根据id将从后台将数据取出来
                cfs.ajaxPostJson("/menu/querybyid?id=" + res[0].id, "", false, function (data) {
                    //如果当前父级菜单是根节点则隐藏访问路径，否则显示路径
                    if (data.data.pid == 0) {
                        $("#form_group_url").hide();
                    } else {
                        $("#form_group_url").show();
                    }
                    jsonToForm($("#form1"), data.data);
                    $("#modal_header_title_info").text("修改菜单信息记录");
                    $("#btn_save_id").hide();
                    $("#btn_edit_id").show();
                    //设置下拉树的选中值并将数据的父级id放到隐藏域和显示input上
                    //initSelectFn();
                    selectNode(data.data.pid);
                    $('#myModal').modal("show");
                });
            }else{
                alertEditLineFn();
            }
        }else{
            alertSelectLineFn();
        }
    });

    //根据出入的id设置选中下拉树节点
    function selectNode(id){
        var zTree_Menu = $.fn.zTree.getZTreeObj("treeDemo");
        var node = zTree_Menu.getNodeByParam("id",id);
        $("#pid").val(node.id);
        $("#pid_select").val(node.title);
        zTree_Menu.selectNode(node,true);//指定选中ID的节点
        zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开
    }


    //修改保存操作
    $("#btn_edit_id").click(function(){
        $('#form1').bootstrapValidator('validate');
        if($('#form1').data('bootstrapValidator').isValid()) {
            var formData = $('#form1').formToJson();
            porsche.ajaxPostJson("/menu/edit",formData,false,function(data){
                if (data.code == 0) {
                    $("#form1").resetForm();
                    $('#form1').data('bootstrapValidator').resetForm(true);//重置表格
                    $('#myModal').modal("hide");
                    initTable();
                } else {
                    alertUseMsgFn("修改失败");
                }
            },true);
        }

    });

    //删除关联题操作
    $("#del_id").click(function(){
        var res= $('#authorityList').bootstrapTable('getSelections');
        if(res.length>=1){
            $.confirm({
                title: '提示',
                content: '是否确定要删除选中的记录？',
                buttons: {
                    确定: {
                        btnClass: 'btn-red',
                        //回调函数
                        action:function () {
                            var ids = [];
                            for(var i=0;i<res.length;i++){
                                ids.push(res[i].id);
                            }
                            ids = ids.join(',');
                            porsche.ajaxPostJson("/menu/del",{ids:ids},false, function(data){
                                if("0" == data.code){
                                  initTable();
                                }else{
                                    alertDeleteErrorFn();
                                }
                            },true);
                        }
                    },
                    取消: {
                        btnClass: 'btn-blue'
                    }
                }
            });
        }else{
            alertSelectLineFn();
        }
    });

    //关闭添加框
    $("#btn_close_id,#x_close_id").click(function(){
        //重置当前的form表单
        $("#form1").resetForm();
        $('#form1').data('bootstrapValidator').resetForm(true);//重置表格
        $('#myModal').modal("hide");
    });



    //导出excel
    $("#export_id").click(function(){
        $("#excelForm").attr("action","/menu/export");
        $("#excelForm").submit();
    });

    //给表单验证增加全局样式
    $('#form1').bootstrapValidator({
        message: '请输入有效的值',
        fields: {}
    });



});//jquery初始化方法end

function initSelectFn(){
    var  mydata=[];
    cfs.ajaxPostJson("/menu/querylist",{},false,function(data){
        var result={id:0,title:"根节点",pid:0};
        mydata.push(result);
        $.each(data.data.result,function(i,item){
            item.url="";
            mydata[i+1]=item
        });
        var setting = {
            view: {
                selectedMulti: false
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: "id",    //设置节点唯一标识属性名称
                    pIdKey: "pid"      //设置父节点唯一标识属性名称
                },
                key: {
                    name: "title",//zTree 节点数据保存节点名称的属性名称
                    title: "title"//zTree 节点数据保存节点提示信息的属性名称
                }
            },callback:{
                onClick:function(e,root,item){
                    $("#pid").val(item.id);
                    $("#pid_select").val(item.title);
                    //如果是根节点则隐藏路径，因为不需要设置路径，否则就显示路径input
                    if(item.id==0){
                        $("#form_group_url").hide();
                    }else{
                        $("#form_group_url").show();
                    }
                    hideMenu();
                }
            }
        };
        $.fn.zTree.init($("#treeDemo"), setting, mydata);
    });


}



function initTable(){
    $('#authorityList').bootstrapTable('destroy');
    var TableInit = function () {
        var table = new Object();
        //初始化Table
        table.Init = function () {
            $('#authorityList').bootstrapTable({
                url: '/member/list',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: table.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 720,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [
                    {checkbox: true},
                    {field: 'id',title: '会员ID' ,visible:false},
                    {field: 'username',title: '用户名', visible:false},
                    {field: 'name',title: '姓名'},
                    {field: 'type',title: '账户类型',  visible:false,
                        formatter: function (value, row, index) {
                            if ("1" == value) {
                                return "普通账户"
                            }else if ("5" == value) {
                                return "VIP账户"
                            }else if ("100" == value) {
                                return "管理员"
                            }else if ("101" == value) {
                                return "超级管理员"
                            }else {
                                return value;
                            }
                        }
                    },
                    {field: 'cardNum',title: '卡号'},
                    {field: 'mobile',title: '手机号'},
                    {field: 'balance',title: '余额'},
                    {field: 'frequency',title: '次数'},
                    {
                        field: 'createTime',title: '创建时间',
                        formatter: function (value) {
                            return changeDateFormat(value)
                        }
                    },{
                    field: 'lastTime',title: '最后消费时间',
                    formatter: function (value) {
                            return changeDateFormat(value)
                        }
                    },

                ],

                responseHandler: function(res) {
                    if(cfs.checkLogin(res)) {
                        return {
                            "total": res.data.total,//总页数
                            "rows": res.data.t   //数据
                        };
                    }
                },
            });
        };

        //得到查询的参数
        table.queryParams = function (params) {
            return   {
                pageSize : params.limit, //每一页的数据行数，默认是上面设置的10(pageSize)
                pageIndex : params.offset/params.limit+1, //当前页面,默认是上面设置的1(pageNumber)
                "mobile": $("#search_mobile").val(),
                "cardNum": $("#search_cardNum").val(),
                "name": $("#search_name").val()
            };

        };
        return table;
    };

    //1.初始化Table
    var table = new TableInit();
    table.Init();
}





function showMenu() {
    var pid = $("#pid_select");
    var pidOffset = $("#pid_select").offset();
    $("#treeDemo").css({left:pidOffset.left + "px", top:pidOffset.top + pid.outerHeight() + "px"}).slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}

function hideMenu() {
    $("#treeDemo").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function onBodyDown(event) {
    if(event.target.id.indexOf("treeDemo")==-1){
        hideMenu();
    }
}


//选择图标功能
function selectImgFn(item){
    var value=$(item).parent().parent().find("input[name='answerList']").val();
    var selectedTab=parent.getTabFn();
    //拼接的参数：type为字典类型 1为题库，input 为关闭后需要渲染数据的input的name名称，json为当前需要编辑的json串，tab为当前的tab页类似 tab-1
    var url="/menu/selectimg?input=icon&tab="+encodeURI(selectedTab);
    var title="选择图标";
    var id="1102";
    $("#form1").data("bootstrapValidator").updateStatus("icon","VALIDATED",null);
    parent.addTabsFn(url,title,id);
}
