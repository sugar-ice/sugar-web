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
        url: web.rootPath() + 'message/list',
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
            {field: 'messageTitle', title: '消息标题', minWidth: 100, align: "center"},
            {field: 'messageType', title: '消息类型', minWidth: 100, align: "center"},
            {field: 'publishTime', title: '消息发布时间', minWidth: 100, align: "center"},
            {field: 'publisherId', title: '消息发布人', minWidth: 100, align: "center"},

            {title: '操作', width: 160, templet: '#List-editBar', fixed: "right", align: "center"}
        ]],
        //对返回的数据进行处理
        parseData: function (res) {
            //遍历每条数据
            for (var i = 0; i < res.data.length; i++) {
                //如果messageType为1，修改为系统消息
                if (res.data[i].messageType == 1) {
                    res.data[i].messageType = "系统消息";
                } else {
                    //否则修改为用户消息
                    res.data[i].messageType = "用户消息";
                }
            }
            //返回处理后的数据
            return res;
        },

        //渲染完成后的回调函数
        done: function (res, curr, count) {
            //遍历每条数据
            for (var i = 0; i < res.data.length; i++) {
                //如果isRead为0，修改行的背景色为黄色
                if (res.data[i].isRead == 0) {
                    $('.layui-table').find('tr[data-index="' + i + '"]').css('background-color', 'yellow');
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
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName
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
                content: web.rootPath() + 'message/add.html'
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
                    content: web.rootPath() + "message/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "message/delete/" + data.id,
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
            case 'view':
                break;

        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
