layui.use(['form', 'layer', 'laydate'], function () {
    var form = layui.form,
        layer = layui.layer,
        laydate = layui.laydate,
        $ = layui.jquery;

    laydate.render({
        elem: '#deliverTime',
        trigger: 'click'
    })

    laydate.render({
        elem: '#receiveTime',
        trigger: 'click'
    })

    form.on('submit(Add-filter)', function (data) {
        $.ajax({
            url: web.rootPath() + "orderInfo/save",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify(data.field),
            dataType: 'json',
            traditional: true,
            success: function (data) {
                layer.msg("操作成功", {
                    icon: 1,
                    success: function () {
                        reloadTb("Save-frame", "#SearchBtn");
                    }
                });
            },
            error: function (e) {
                layer.msg(e.responseJSON.message, {icon: 2});
            }

        });
        return false;
    });

});
