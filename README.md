# ml-admin 后台管理系统
## 项目介绍
一个基于 Spring Boot、 MyBatis、 JWT、Spring Security、Redis、MySQL、 Vue的前后端分离的后台管理系统
## 实现功能
1. 权限管理: 基于RBAC模型管理用户权限, 支持添加自定义角色和管理菜单
2. 运维管理: 监控服务器的运行情况, 远程部署和管理应用
3. 定时任务: 整合Quartz做定时任务，加入任务日志，任务运行情况一目了然
4. 日志管理: 记录用户操作日志与异常日志, 支持日志导出
5. 其他特性: 支持接口限流, 支持在线用户管理

## 明日任务
1. 完善ip分析, log系统
2. 完善数据库