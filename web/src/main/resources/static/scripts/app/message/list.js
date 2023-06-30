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
            {field: 'messageTitle', title: '消息标题', minWidth: 100, align: "center"},
            {
                field: 'messageType', title: '消息类型', minWidth: 100, align: "center", templet: function (data) {
                    var value = data.messageType;
                    var text = '';
                    if (value == '1') {
                        text = '系统消息';
                    } else if (value == '2') {
                        text = '用户消息';
                    }
                    return text;
                }
            },
            {field: 'publishTime', title: '消息发布时间', minWidth: 100, align: "center"},
            {field: 'publisherUsername', title: '消息发布人', minWidth: 100, align: "center"},
            {
                field: 'topPriority', title: '重要性', minWidth: 100, align: "center", templet: function (data) {
                    var value = data.topPriority;
                    var text = '';
                    if (value == '0') {
                        text = '一般';
                    } else {
                        var starCount = parseInt(value); // 将重要性值转换为整数
                        for (var i = 0; i < starCount; i++) {
                            text += '<i class="layui-icon layui-icon-star-fill"></i>'; // 添加图标
                        }
                    }
                    return text;
                }
            },

            {title: '操作', width: 240, templet: '#List-editBar', fixed: "right", align: "center"}
        ]],
        //对返回的数据进行处理
        parseData: function (res) {
            // //遍历每条数据
            // for (var i = 0; i < res.data.length; i++) {
            //     //如果messageType为1，修改为系统消息
            //     if (res.data[i].messageType == 1) {
            //         res.data[i].messageType = "系统消息";
            //     } else {
            //         //否则修改为用户消息
            //         res.data[i].messageType = "用户消息";
            //     }
            // }
            // //返回处理后的数据
            // return res;
        },

        //渲染完成后的回调函数
        done: function (res, curr, count) {
            //遍历每条数据
            for (var i = 0; i < res.data.length; i++) {
                if (res.data[i].isRead == 1) {
                    $('.layui-table').find('tr[data-index="' + i + '"]').css('background-color', '#cccccc');
                    $('.layui-table').find('tr[data-index="' + i + '"]').find('.layui-btn[lay-event="read"]').addClass('layui-btn-disabled').off('click');
                }
            }
        }
    });

    //监听工具条
    table.on('tool(List-toolbar)', function (obj) {
        var data = obj.data;
        switch (obj.event) {
            case 'read':
                $.ajax({
                    url: web.rootPath() + "message/read/" + data.id,
                    type: "post",
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify(data.field),
                    dataType: 'json',
                    success: function (data) {
                        tableIns.reload({});
                    },
                    error: function (e) {
                        layer.msg(e.responseJSON.message, {icon: 2});
                    }
                })
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
                                    tableIns.reload({});
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
                // Retrieve the message content from the data object
                var messageTitle = data.messageTitle;
                var messageType = data.messageType;
                var publishTime = data.publishTime;
                var publisherUsername = data.publisherUsername;
                var topPriority = data.topPriority;
                var messageContent = data.messageContent;

                // Create a new layer to display the message content
                layer.open({
                    type: 1,
                    title: messageTitle,
                    area: ['600px', '400px'],
                    content: '<div style="padding: 20px; font-size: 16px;">' +
                        '<p><strong>消息标题:</strong> ' + messageTitle + '</p>' +
                        '<p><strong>消息类型:</strong> ' + (messageType === '0' ? '系统消息' : '用户消息') + '</p>' +
                        '<p><strong>消息发布时间:</strong> ' + publishTime + '</p>' +
                        '<p><strong>消息发布人:</strong> ' + publisherUsername + '</p>' +
                        '<p><strong>重要性:</strong> ' + (topPriority === '0' ? '一般' : '<i class="layui-icon layui-icon-star-fill"></i>'.repeat(parseInt(topPriority))) + '</p>' +
                        '<p><strong>消息内容:</strong> ' + messageContent + '</p>' +
                        '</div>'
                });
                break;
        }
        ;
    });

    $(window).resize(function () {
        $('div[lay-id="ListTable"]').height(document.body.offsetHeight - Math.round(Number($('.layui-card-header').height()) + 47));
    });
});
