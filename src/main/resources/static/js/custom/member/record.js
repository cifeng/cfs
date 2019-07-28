$(function(){
    initTable();

    //初始化时间控件
    $(".create_time_begin,.create_time_end,.start_time,.end_time").click(function(){
        WdatePicker({el:this,dateFmt:'yyyy-MM-dd HH:mm'});
    });


    $("#search_record_id").click(function(){
        initTable()
    });
    $("#search_record_day").click(function(){
        var myDate = new Date;
        var year = myDate.getFullYear(); //获取当前年
        var mon = myDate.getMonth() + 1; //获取当前月
        if(mon<10){mon="0"+mon;}
        var date = myDate.getDate(); //获取当前日
        if(date<10){
            date = "0"+date;
        }
        var time = year+"-"+mon+"-"+date;
        $("#startTime_id").val(time+" 00:00");
        $("#endTime_id").val(time+" 23:59");
        initTable()
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


//根据活动列表数据渲染base试题数据列表
function initTable() {
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
                pageSize: 10,                       //每页的记录行数（*）
                pageList: [10, 50, 100,500,1000,10000],        //可供选择的每页的行数（*）
                strictSearch: true,
                clickToSelect: true,                //是否启用点击选中行
                height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
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
                        // 计算总额
                        if(res.data.total>0){
                            var sum = 0;
                            var sycs = 0;
                            $.each(res.data.t,function(i,item){
                                sum+=Number(item.balance);
                                sycs+=Number(item.frequency);
                            });
                            var text = "消费金额:"+ sum+ "元";
                            if(sycs>0){
                                text+= "  使用次数:"+sycs+"次";
                            }
                            $(".sum_span").text(text);
                        }


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

            return {
                pageSize: params.limit, //每一页的数据行数，默认是上面设置的10(pageSize)
                pageIndex: params.offset / params.limit + 1, //当前页面,默认是上面设置的1(pageNumber)
                startTime:$("#startTime_id").val(),
                endTime: $("#endTime_id").val()
            };
        };
        return table;
    };
    //初始化基础试题信息
    var table = new TableInitQuesList();

    table.Init();

}



