<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>通用后台管理模板系统</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/admin.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/common.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/layuiadmin/style/popup.css" media="all">
</head>
<body>
<script>
</script>
<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form"  action="" lay-filter="component-form-element">
            <input type="hidden" id="id" name="id" value="${id}">
            <div class="layui-row layui-col-space10 layui-form-item">


                <div class="layui-col-lg6">
                        <label class="layui-form-label">id</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="id"
                               value="${obj.id}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息标题</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="messageTitle"
                               value="${obj.messageTitle}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息类型, 1 系统消息、2 个人消息</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="messageType"
                               value="${obj.messageType}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息发布时间</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="publishTime"
                               value="${obj.publishTime}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息发布人</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="publisherId"
                               value="${obj.publisherId}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息接收人，NULL表示所有人</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="receiverId"
                               value="${obj.receiverId}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息简介</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="messageSummary"
                               value="${obj.messageSummary}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息详情</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="messageContent"
                               value="${obj.messageContent}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">消息图片</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="messageImage"
                               value="${obj.messageImage}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-col-lg6">
                        <label class="layui-form-label">置顶优先级，数字越大排的越前面，0表示不置顶</label>
                    <div class="layui-input-block">
                        <input type="text"
                               name="topPriority"
                               value="${obj.topPriority}"
                               autocomplete="off"
                               class="layui-input">
                    </div>
                </div>


                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <button class="layui-btn layui-btn-normal" lay-submit lay-filter="Add-filter">修改</button>
                        <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script src="${request.contextPath}/webjars/jquery/jquery.min.js"></script>
<script>

</script>
<script type="text/javascript" src="${request.contextPath}/scripts/app/message/update.js?_=${randomNum}"></script>
</body>
