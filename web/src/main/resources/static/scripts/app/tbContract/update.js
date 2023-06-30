layui.use(['form', 'layer', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery;

    laydate.render({
        elem: '#startDate',
        trigger: 'click'
    })
    laydate.render({
        elem: '#endDate',
        trigger: 'click'
    })
    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "tbContract/update",
            contentType: "application/json",
            type: "put",
            data: JSON.stringify(data.field),
            dataType: 'json',
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Update-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                if (e.responseJSON.errCode === 1003) {
                    layer.msg(e.responseJSON.data.toString(), {icon: 2});
                } else {
                    layer.msg(e.responseJSON.message, {icon: 2});
                }
            }

        })
        return false;
    });

});
