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
                <div class="layui-col-lg6">
                    <label class="layui-form-label">所属客户</label>
                    <div class="layui-input-block">
                        <select name="custId">
                            <option value="">--请选择--</option>
                            <#list customers as customer>
                                <option value="${customer.id}">${customer.customerName}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">产品名称</label>
                    <div class="layui-input-block">
                        <input type="text" name="prodName" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">产品数量</label>
                    <div class="layui-input-block">
                        <input type="text" name="amounts" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">产品价格</label>
                    <div class="layui-input-block">
                        <input type="text" name="price" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">状态 0 未发货 1 已发货 2 已收货</label>
                    <div class="layui-input-block">
                        <select name="custId">
                            <option value="0">未发货</option>
                            <option value="0">已发货</option>
                            <option value="0">已收获</option>
                        </select>
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">收货人</label>
                    <div class="layui-input-block">
                        <input type="text" name="receiver" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">收货人电话</label>
                    <div class="layui-input-block">
                        <input type="text" name="linkPhone" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">收货地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="address" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物流</label>
                    <div class="layui-input-block">
                        <input type="text" name="logistcs" placeholder="请输入" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">物流单号</label>
                    <div class="layui-input-block">
                        <input type="text" name="logisticsCode" placeholder="请输入" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">发货时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="deliverTime" placeholder="请输入" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-col-lg6">
                    <label class="layui-form-label">收货时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="receiveTime" placeholder="请输入" autocomplete="off"
                               class="layui-input">
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
<script type="text/javascript" src="${request.contextPath}/scripts/app/orderInfo/add.js?_=${randomNum}"></script>
</body>
