layui.use(['form', 'layer', 'table', 'laytpl', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        laydate = layui.laydate,
        table = layui.table;


    //用户列表
    var tableIns = table.render({
        elem: '#List',
        url: web.rootPath() + 'orderInfo/list',
        cellMinWidth: 95,
        page: true,
        height: "full-" + Math.round(Number($('.layui-card-header').height()) + 44),
        limits: [10, 13, 15, 20, 30, 50, 100, 200],
        limit: 10,
        toolbar: '#List-toolbar',
        id: "ListTable",
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'id', title: 'id', minWidth: 100, align: "center"},
            {field: 'customerName', title: '所属客户', minWidth: 100, align: "center"},
            {field: 'prodName', title: '产品名称', minWidth: 100, align: "center"},
            {field: 'amounts', title: '产品数量', minWidth: 100, align: "center"},
            {field: 'price', title: '产品价格', minWidth: 100, align: "center"},
            {
                field: 'status', title: '状态', minWidth: 100, align: "center", templet: function (customer) {
                    if (customer.status == '0') {
                        return "<button class=\"layui-btn layui-btn-primary layui-btn-xs\">未发货</button>";
                    } else if (customer.status == '1') {
                        return "<button class=\"layui-btn layui-btn-normal layui-btn-xs\">已发货</button>";
                    } else if (customer.status == '2') {
                        return "<button class=\"layui-btn layui-btn-array layui-btn-xs\">已收货</button>";
                    }
                }
            },
            {field: 'receiver', title: '收货人', minWidth: 100, align: "center"},
            {field: 'linkPhone', title: '收货人电话', minWidth: 100, align: "center"},
            {field: 'address', title: '收货地址', minWidth: 100, align: "center"},
            {field: 'logistcs', title: '物流', minWidth: 100, align: "center"},
            {field: 'logisticsCode', title: '物流单号', minWidth: 100, align: "center"},
            {field: 'deliverTime', title: '发货时间', minWidth: 100, align: "center"},
            {field: 'receiveTime', title: '收货时间', minWidth: 100, align: "center"},
            {title: '操作', width: 320, templet: '#List-editBar', fixed: "right", align: "center"}
        ]],
        //渲染完成后的回调函数
        done: function (res, curr, count) {
            //遍历每条数据
            for (var i = 0; i < res.data.length; i++) {
                if (res.data[i].status == 0) {
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="reword"]').addClass('layui-btn-disabled').off('click');
                }
                if (res.data[i].status == 1) {
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="ship"]').addClass('layui-btn-disabled').off('click');
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="update"]').addClass('layui-btn-disabled').off('click');
                }
                if (res.data[i].status == 2) {
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="ship"]').addClass('layui-btn-disabled').off('click');
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="update"]').addClass('layui-btn-disabled').off('click');
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="reword"]').addClass('layui-btn-disabled').off('click');
                }
            }
        }
    });

    //头工具栏事件
    table.on('toolbar(List-toolbar)', function (obj) {
        switch (obj.event) {
            case 'add':
                layer.msg("add");
                break;
            case 'update':
                layer.msg("update");
                break;
            case 'delete':
                layer.msg("delete");
                break;
            case 'export':
                layer.msg("export");
                break;
        }
        ;
    });

    var $ = layui.$, active = {
        reload: function () {
            //获取搜索条件值
            var parameterName = $("#searchForm").find("input[name='parameterName']").val().trim();
            var status = $("#searchForm").find("select[name='status']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName,
                    status: status
                }
            });
        }
    };

    $('#SearchBtn').on('click', function () {
        var type = $(this).data('type');
        active[type] ? active[type].call(this) : '';
    });


    //头工具栏事件
    table.on('toolbar(List-toolbar)', function (obj) {
        if (obj.event == 'add') {
            layer.open({
                id: "Save-frame",
                type: 2,
                area: ['600px', '501px'],
                title: '新增',
                fixed: false,
                maxmin: true,
                content: web.rootPath() + 'orderInfo/add.html'
            });
        }
        ;
    });
    //监听工具条
    table.on('tool(List-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'update':
                layer.open({
                    id: "Update-frame",
                    type: 2,
                    resize: false,
                    area: ['550px', '500px'],
                    title: '修改',
                    fixed: false,
                    maxmin: true,
                    content: web.rootPath() + "orderInfo/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "orderInfo/delete/" + data.id,
                        type: "delete",
                        contentType: "application/json;charset=utf-8",
                        data: JSON.stringify(data.field),
                        dataType: 'json',
                        success: function (data) {
                            layer.msg("操作成功", {
                                icon: 1,
                                success: function () {
                                    $('#SearchBtn').trigger("click");
                                }
                            });
                        },
                        error: function (e) {
                            layer.msg(e.responseJSON.message, {icon: 2});
                        }
                    })
                }, function () {
                });
                break;
            case 'ship':
                data.status = 1
                $.ajax({
                    url: web.rootPath() + "orderInfo/update",
                    contentType: "application/json",
                    type: "put",
                    data: JSON.stringify(data),
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        layer.msg("操作成功", {
                            icon: 1,
                            success: function () {
                                reloadTb("Update-frame", "#SearchBtn");
                            }
                        });
                    },
                    error: function (e) {
                        layer.msg(e.responseJSON.message, {icon: 2});
                    }
                })
                break;
            case 'reword':
                data.status = 2
                $.ajax({
                    url: web.rootPath() + "orderInfo/update",
                    contentType: "application/json",
                    type: "put",
                    data: JSON.stringify(data),
                    dataType: 'json',
                    success: function (data) {
                        console.log(data)
                        layer.msg("操作成功", {
                            icon: 1,
                            success: function () {
                                reloadTb("Update-frame", "#SearchBtn");
                            }
                        });
                    },
                    error: function (e) {
                        layer.msg(e.responseJSON.message, {icon: 2});
                    }
                })
                break;
        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
