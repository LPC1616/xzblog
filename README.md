## 前后端分离博客系统——小猪博客

###  前言

我是LPC，一枚C2院校的研究生，未来会在该博客上发布一些技术和读书心得，欢迎关注~~~

博客地址：待发布

### 项目介绍

小猪博客（**XZBlog**），一个前后端分离博客系统。Web端使用 **Vue** + **ElementUi**。后端使用 **SpringBoot** + **Mybatis-plus** 进行开发，使用 **Jwt** + **SpringSecurity** 做登录验证和权限验证，文件支持上传七牛云。

- 后端工程有前台和后台两套系统
- 技术栈（SpringBoot,MybatisPlus,SpringSecurity,EasyExcel,Swagger2,Redis,Echarts,Vue,ElementUI....）

### 项目特点

- 实现前后端分离，统一了响应类和响应枚举，并通过 **Json** 进行数据交互，前端再也不用关注后端技术。
- 引入 **Swagger** 文档支持，方便编写 **API** 接口文档。
- 引入七牛云对象存储，同时支持本地文件存储。
- 采用 **AOP** + 自定义注解 实现接口调用信息的日志记录，便于后期调试排查。

### 未来计划

- 引入**ElasticSearch** 和 **Solr** 作为全文检索服务
- Docker 部署该系统

