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
        url: web.rootPath() + 'tbCustomer/list',
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
            {field: 'customerName', title: '企业名称', minWidth: 100, align: "center"},
            {field: 'legalLeader', title: '法定代表人', minWidth: 100, align: "center"},
            {field: 'registerDate', title: '成立时间', minWidth: 100, align: "center"},
            // {field: 'openStatus', title: '经营状态', minWidth: 100, align: "center"},
            {
                field: 'openStatus', title: '经营状态', minWidth: 100, align: "center", templet: function (customer) {
                    if (customer.openStatus == '0') {
                        return "<button class=\"layui-btn layui-btn-normal layui-btn-xs\">开业</button>";
                    } else if (customer.openStatus == '1') {
                        return "<button class=\"layui-btn layui-btn-danger layui-btn-xs\">注销</button>";
                    } else if (customer.openStatus == '2') {
                        return "<button class=\"layui-btn layui-btn-disabled layui-btn-xs\">破产</button>";
                    }
                }
            },
            {field: 'province', title: '所属地区省份', minWidth: 100, align: "center"},
            {field: 'regCapital', title: '注册资本,(万元)', minWidth: 100, align: "center"},
            {field: 'industry', title: '所属行业', minWidth: 100, align: "center"},
            {field: 'scope', title: '经营范围', minWidth: 100, align: "center"},
            {field: 'regAddr', title: '注册地址', minWidth: 100, align: "center"},
            {field: 'inputTime', title: '录入时间', minWidth: 100, align: "center"},
            {field: 'updateTime', title: '修改时间', minWidth: 100, align: "center"},
            {field: 'username', title: '录入人', minWidth: 100, align: "center"},

            {title: '操作', width: 160, templet: '#List-editBar', fixed: "right", align: "center"}
        ]],

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
            var openStatus = $("#searchForm").find("select[name='openStatus']").val().trim();
            var province = $("#searchForm").find("select[name='province']").val().trim();
            //表格重载
            tableIns.reload({
                where: { //设定异步数据接口的额外参数，任意设
                    parameterName: parameterName,
                    openStatus: openStatus,
                    province: province
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
                content: web.rootPath() + 'tbCustomer/add.html'
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
                    content: web.rootPath() + "tbCustomer/" + data.id + ".html?_"
                });
                break;
            case 'delete':
                var index = layer.confirm('确定要删除?', {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    $.ajax({
                        url: web.rootPath() + "tbCustomer/delete/" + data.id,
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
        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
