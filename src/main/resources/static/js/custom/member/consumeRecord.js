$(function(){
    initTable();
    initTableByBaseQuestion();
    $("#card_id").focus();


    //初始化时间控件
    $(".create_time_begin,.create_time_end,.start_time,.end_time").click(function(){
        WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm'});
    });

    $("#search_id").click(function(){
        refreshUserListFn(null);
    });

    $("#search_record_id").click(function(){
        var res= $('#userList').bootstrapTable('getSelections');
        if(res.length>0){
            refreshFn(res[0].id);
        }else{
            alertUseMsgFn("请至少选中一行用户数据");
        }

    });

    $('#card_id,#mobile_id,#name_id').bind('keydown',function(event){
        if(event.keyCode == "13") {
            $("#search_id").trigger("click");
        }
    });

    $('#startTime_id,#endTime_id').bind('keydown',function(event){
        if(event.keyCode == "13") {
            $("#search_record_id").trigger("click");
        }
    });


    //添加操作
    $("#save_id").click(function(){
        var res= $('#userList').bootstrapTable('getSelections');
        if(res.length>0){
            $("#userId").val(res[0].id);
            $("#third_info").text("添加消费记录");
            $("#btn_save_id").show();
            $("#btn_edit_id").hide();
            $('#myModal').modal('show');
        }else{
            alertUseMsgFn("请至少选中一行用户数据");
        }


    });
    //添加保存操作
    $("#btn_save_id").click(function(){
        $('#form1').bootstrapValidator('validate');
        if($('#form1').data('bootstrapValidator').isValid()){
            var formData = $('#form1').formToJson();
            cfs.ajaxPostJson("/consume/save",formData,false, function(data){
                if(data.code==200){
                    //重置当前的form表单
                    $("#form1").resetForm();
                    $('#form1').data('bootstrapValidator').resetForm(true);//重置表格
                    $('#myModal').modal('hide');//隐藏该表单
                    var res = $('#userList').bootstrapTable('getSelections');//获取用户
                    //refreshFn(res[0].id);//重新渲染table
                    refreshUserListFn(res[0].id);
                }else{
                    alertUseMsgFn(data.msg);
                }
            },true);
        }
    });

    //修改操作
    $("#edit_id").click(function(){
        var res= $('#consumeRecordList').bootstrapTable('getSelections');
        if(res.length>0){
            //根据id将从后台将数据取出来
            cfs.ajaxPostJson("/consume/querybyid",{id:res[0].id},false, function(data){
                jsonToForm($("#form1"),data.data);
                $("#third_info").text("修改消费记录");
                $("#btn_save_id").hide();
                $("#btn_edit_id").show();
                $("#myModal").modal("show");
            });
        }else{
            alertSelectLineFn();
        }
    });

    //修改保存操作
    $("#btn_edit_id").click(function(){
        $('#form1').bootstrapValidator('validate');
        if($('#form1').data('bootstrapValidator').isValid()){
            var formData = $('#form1').formToJson();
            cfs.ajaxPostJson("/consume/edit",formData,false, function(data){
                if(data.code==200){
                    //重置当前的form表单
                    $("#form1").resetForm();
                    $('#form1').data('bootstrapValidator').resetForm(true);//重置表格
                    //隐藏该表单
                    $('#myModal').modal('hide');
                    var res = $('#userList').bootstrapTable('getSelections');
                    //refreshFn(res[0].id);
                    refreshUserListFn(res[0].id);
                }else{
                    alertUseMsgFn(data.msg);
                }
            },true);
        }
    });

    //删除试题操作
    $("#del_id").click(function(){
        var res= $('#consumeRecordList').bootstrapTable('getSelections');
        if(res.length>=1){
            $.confirm({
                title: '提示',
                content: '是否确定要删除选中的记录,用户剩余金额和剩余次数会重新计算？',
                buttons: {
                    确定: {
                        btnClass: 'btn-red',
                        //回调函数
                        action:function () {
                            var id = res[0].id;
                            cfs.ajaxPostJson("/consume/del",{id:id},false, function(data){
                                if("200" == data.code){
                                    var res = $('#userList').bootstrapTable('getSelections');
                                    refreshUserListFn(res[0].id);
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
    $("#btn_close_base_id,#x_close_base").click(function(){
        //重置当前的form表单
        $("#form1").resetForm();
        $('#form1').data('bootstrapValidator').resetForm(true);//重置表格
        //隐藏该表单
        $('#myModal').modal('hide');
    });

    //给表单验证增加全局样式
    $('#form1,#form3,#form5').bootstrapValidator({
        message: '请输入有效的值',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },fields: {}
    });





});



function initTable(){
    $('#userList').bootstrapTable('destroy');
    var TableInit = function () {
        var table = new Object();
        //初始化Table
        table.Init = function () {
            $('#userList').bootstrapTable({
                url: '/member/list',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: false,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: table.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber:1,                       //初始化加载第一页，默认第一页
                pageSize: 1,                       //每页的记录行数（*）
                pageList: [1,5,10, 25, 50, 100],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 130,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                }
                ,onClickRow:function(row,element){

                    var num=$(element).attr("data-index");
                    $('#userList').bootstrapTable("uncheckAll");
                    $('#userList').bootstrapTable("check",num);
                }
                ,onCheck:function(row){
                        //initTableByBaseQuestion(row.id);
                        refreshFn(row.id);
                    },onLoadSuccess:function(data){
                        //加载完毕后默认选择第一行
                        if(data.rows.length!=0){
                            $('#userList').bootstrapTable("check",0);
                        }else{
                            refreshFn(0);//如果活动数据列表为空则将基础试题数据列表清空
                        }
                        //解决bootstrap table 的radio name冲突，默认它是起一样的名字，所以选中下面的table上面的也会失去选中项
                        $("#userList input[type='radio']").attr("name","aaaa");
                    }
            });
        };

        //得到查询的参数
        table.queryParams = function (params) {
            return   {
                pageSize : 1, //每一页的数据行数，默认是上面设置的10(pageSize)
                pageIndex : 1, //当前页面,默认是上面设置的1(pageNumber)
                "mobile": $("#mobile_id").val(),
                "cardNum": $("#card_id").val(),
                "name": $("#name_id").val()
            };

        };
        return table;
    };

    //1.初始化Table
    var table = new TableInit();
    table.Init();
}





//根据活动列表数据渲染base试题数据列表
function initTableByBaseQuestion() {
    $('#consumeRecordList').bootstrapTable('destroy');
    var TableInitQuesList = function () {
        var table = new Object();
        //初始化Table
        table.Init = function () {
            $('#consumeRecordList').bootstrapTable({
                url: '/consume/list',         //请求后台的URL（*）
                method: 'get',                      //请求方式（*）
                toolbar: '#toolbar',                //工具按钮用哪个容器
                striped: true,                      //是否显示行间隔色
                cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                pagination: true,                   //是否显示分页（*）
                sortable: false,                     //是否启用排序
                sortOrder: "asc",                   //排序方式
                queryParams: table.queryParams,//传递参数（*）
                sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
                pageNumber: 1,                       //初始化加载第一页，默认第一页
                pageSize: 5,                       //每页的记录行数（*）
                pageList: [5,10, 25, 50, 100],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 334,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                uniqueId: "id",                     //每一行的唯一标识，一般为主键列
                cardView: false,                    //是否显示详细视图
                detailView: false,                   //是否显示父子表
                columns: [
                    {radio: true},
                    {field: 'id', title: '消费记录id'},
                    {field: 'userId', title: '用户id',formatter:formatterLenFn,len:40,visible:false},
                    {field: 'balance', title: '消费金额',formatter:formatterLenFn,len:40},
                    {field: 'frequency', title: '使用次数',formatter:formatterLenFn,len:40},
                    {field: 'discount', title: '折扣',
                        formatter:function(value){
                            if(!value){
                                return "无"
                            }else{
                                return value;
                            }
                        }
                    },
                    {
                        field: 'lastTime',title: '消费时间',
                        formatter: function (value) {
                            return changeDateFormat(value)
                        }
                    }
                ],
                responseHandler: function (res) {
                    if(cfs.checkLogin(res)) {
                        return {
                            "total": res.data.total,//总页数
                            "rows": res.data.t   //数据
                        };
                    }
                }

            });
        };

        //得到查询的参数
        table.queryParams = function (params) {
            var res= $('#userList').bootstrapTable('getSelections');
            return {
                pageSize: params.limit, //每一页的数据行数，默认是上面设置的10(pageSize)
                pageIndex: params.offset / params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
                userId: res.length>0?res[0].id:"",
            };
        };
        return table;
    };
    //初始化基础试题信息
    var table = new TableInitQuesList();

    table.Init();

}


//因为是关联表，为了避免页面渲染table出现问题只对table渲染一次，所有采用刷新的方式重新load数据
function refreshFn(userId){
    var startTime = $("#startTime_id").val();
    var endTime = $("#endTime_id").val();
    var opt = {
        url: '/consume/list',
        silent: true,
        query:{
            "userId":userId,
            "startTime": startTime,
            "endTime": endTime
        }
    };

    $("#consumeRecordList").bootstrapTable('refresh', opt);
}



function refreshUserListFn(userId){
    var opt = {
        url: '/member/list',
        silent: true,
        query:{
            "id":userId,
            "mobile": $("#mobile_id").val(),
            "cardNum": $("#card_id").val(),
            "name": $("#name_id").val()
        }
    };

    $("#userList").bootstrapTable('refresh', opt);
}

