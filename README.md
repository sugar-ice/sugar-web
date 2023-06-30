# 使用docker开发

- 启动mysql和redis，统一mysql和redis版本和数据

```shell
docker compose up -d
```

- 导出sql数据

```shell
python ./export_sql.py
```

# 使用mybatis mpj插件实现mybatis-plus的多表查询

- 依赖

```xml

<dependency>
    <groupId>com.github.yulichang</groupId>
    <artifactId>mybatis-plus-join-boot-starter</artifactId>
    <version>1.4.5</version>
</dependency>
```

- 使用方法

```java
public IPage<TbCustomerWithUser> getCustomerWithUser(LayuiPage layuiPage,MPJLambdaWrapper<TbCustomer> wrapper){
        IPage<TbCustomerWithUser> iPage=tbCustomerMapper.selectJoinPage(new Page<>(layuiPage.getPage(),layuiPage.getLimit()),
        TbCustomerWithUser.class,
        wrapper);
        return iPage;
        }
```

> 传入分页数据和wrapper即可