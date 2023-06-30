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

<div class="layui-card layui-content">
    <div class="layui-card-body">
        <form class="layui-form" action="" lay-filter="component-form-element">
            <div class="layui-row layui-col-space10 layui-form-item">

                <div class="layui-col-lg6" style="margin-top: 10px">
                    <label class="layui-form-label">消息标题</label>
                    <div class="layui-input-block">
                        <input type="text" name="messageTitle" placeholder="请输入" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6" style="margin-top: 10px">
                    <label class="layui-form-label">消息类型</label>
                    <div class="layui-input-block">
                        <select name="messageType">
                            <option value="1">系统消息</option>
                            <option value="2">用户消息</option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6" style="margin-top: 10px">
                    <label class="layui-form-label">消息接收人</label>
                    <div class="layui-input-block">
                        <select name="receiverId">
                            <option value="">全部</option>
                            <#list users as user>
                                <option value="${user.userId}">${user.username}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6" style="margin-top: 10px">
                    <label class="layui-form-label">消息详情</label>
                    <div class="layui-input-block">
                        <input type="text" name="messageContent" placeholder="请输入" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6" style="margin-top: 10px">
                    <label class="layui-form-label">置顶优先级</label>
                    <div class="layui-input-block">
                        <select name="topPriority">
                            <option value="">--请选择--(优先级越大越靠前)</option>
                            <#list 0..10 as priority>
                                <#if priority == 0>
                                    <option value="${priority}">不置顶</option>
                                <#else>
                                    <option value="${priority}">${priority}</option>
                                </#if>
                            </#list>
                        </select>
                    </div>
                </div>

            </div>
            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-normal" lay-submit lay-filter="Add-filter">新增</button>
                    <button type="reset" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script src="${request.contextPath}/layuiadmin/layui/layui.js"></script>
<script src="${request.contextPath}/layui-extend.js"></script>
<script type="text/javascript" src="${request.contextPath}/scripts/app/messageMgr/add.js?_=${randomNum}"></script>
</body>
