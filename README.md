# SkyeStock


stock demo 计划
### 表结构调整：
    1:用户表、账号表
    2：
    
### 包含模块及功能详情
    - 服务端fixserver stock数据接收转发解析服务
    - 客户端fixclient 接收服务端数据并解析给客户端app页面
    - 平台数据管理系统platform

    - 中间件服务：com.skyestock.log
                com.skyestock.file
                com.skyestock.message(mail、sms、微信、【kafka、mq】)

    
    
### 技术实战要求
    1：注册中心
    2：分布式事务、服务集群
    3：redis 集群等实战
    4：nosql、tidb、canal
    5：大数据 flink、scala
    6：xxjob、雪花算法
        链路追踪-skywalking、
        监控-普罗米修斯
        granfna
    
    持续集成：gitlab、docker、Harbor
    
    
    登录session用jwt
    7： docker、git cicd