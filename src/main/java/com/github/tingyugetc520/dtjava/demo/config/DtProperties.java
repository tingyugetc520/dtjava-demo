package com.github.tingyugetc520.dtjava.demo.config;

import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dt")
public class DtProperties {
    private List<AppConfig> appConfigs;

    @Data
    public static class AppConfig {
        /**
         * 钉钉应用的corpId
         */
        private volatile String corpId;
        /**
         * 钉钉应用的agentId
         */
        private volatile Long agentId;
        /**
         * 钉钉应用的appKey
         */
        private volatile String appKey;
        /**
         * 钉钉应用的appSecret
         */
        private volatile String appSecret;

        /**
         * 钉钉应用的aesKey
         */
        private volatile String aesKey;
        /**
         * 钉钉应用的token
         */
        private volatile String token;
        /**
         * 消息推送的加解密需要使用appKey或者corpId，所以需要指定这个属性是什么
         * 当在开发者后台手动配置回调时此属性为appKey
         * 当采用HTTP回调注册API配置回调时此属性为corpId
         */
        private volatile String appKeyOrCorpId;
    }

}
