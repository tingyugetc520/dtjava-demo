package com.github.tingyugetc520.dtjava.demo.config;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.api.impl.DtServiceImpl;
import com.github.tingyugetc520.ali.dingtalk.config.impl.DtDefaultConfigImpl;
import com.github.tingyugetc520.ali.dingtalk.message.DtMessageRouter;
import com.github.tingyugetc520.dtjava.demo.handler.ContactUserAddHandler;
import com.github.tingyugetc520.dtjava.demo.handler.LogHandler;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
@Configuration
@EnableConfigurationProperties(DtProperties.class)
public class DtConfiguration {
    private final LogHandler logHandler;
    private final ContactUserAddHandler contactUserAddHandler;

    private final DtProperties properties;

    private final static Map<String, DtMessageRouter> ROUTERS = Maps.newHashMap();
    private static DtMessageRouter router;
    private static Map<String, DtService> dtServices = Maps.newHashMap();

    public static Map<String, DtMessageRouter> getRouters() {
        return ROUTERS;
    }

    public static DtMessageRouter getRouter() {
        return router;
    }

    public static DtService getDtService(String appKey) {
        return dtServices.get(appKey);
    }

    @PostConstruct
    public void init() {
        initServices();
        initRouter();
    }

    private void initServices() {
        dtServices = this.properties.getAppConfigs().stream().map(a -> {
            DtDefaultConfigImpl configStorage = new DtDefaultConfigImpl();
            configStorage.setCorpId(a.getCorpId());
            configStorage.setAgentId(a.getAgentId());
            configStorage.setAppKey(a.getAppKey());
            configStorage.setAppSecret(a.getAppSecret());

            configStorage.setAesKey(a.getAesKey());
            configStorage.setToken(a.getToken());
            configStorage.setAppKeyOrCorpId(a.getAppKeyOrCorpId());

            DtService service = new DtServiceImpl();
            service.setDtConfigStorage(configStorage);
            ROUTERS.put(a.getAppKey(), this.newRouter(service));
            return service;
        }).collect(Collectors.toMap(service -> service.getDtConfigStorage().getAppKey(), a -> a));
    }

    private DtMessageRouter newRouter(DtService dtService) {
        DtMessageRouter newRouter = new DtMessageRouter(dtService);

        // 通讯录用户增加事件
        newRouter.rule().async(false).eventType("user_add_org").handler(this.contactUserAddHandler).end();

        // 默认,记录所有事件的日志（异步执行）
        newRouter.rule().async(true).handler(this.logHandler).end();
        return newRouter;
    }

    private void initRouter() {
        router = new DtMessageRouter();
    }
}
