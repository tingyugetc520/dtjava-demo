# dtjava-demo

#### 基于 `Spring Boot` 构建出`DtJava`的demo程序
DtJava仓库为https://github.com/tingyugetc520/DtJava

## Guide：
1. 复制 `application.properties.sample` 为 `application.properties` 文件
2. 更改配置文件：
```
dt.appConfigs[0].corpId=钉钉应用的corpId
dt.appConfigs[0].agentId=钉钉应用的agentId
dt.appConfigs[0].appKey=钉钉应用的appKey
dt.appConfigs[0].appSecret=钉钉应用的appSecret
dt.appConfigs[0].aesKey=钉钉应用的aesKey
dt.appConfigs[0].token=钉钉应用的token
dt.appConfigs[0].appKeyOrCorpId=钉钉应用的appKeyOrCorpId,手动配置回调地址的为appKey通过接口注册回调地址的为corpId
```
3. 运行Application
4. 配置钉钉应用的事件回调地址：`http://{可外网访问的域名}/event/callback/{appKey}`
6. 根据业务实现各个 `handler` 。
	
