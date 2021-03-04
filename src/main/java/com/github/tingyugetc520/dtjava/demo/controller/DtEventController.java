package com.github.tingyugetc520.dtjava.demo.controller;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventOutMessage;
import com.github.tingyugetc520.dtjava.demo.config.DtConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/callback/{appKey}")
@Slf4j
public class DtEventController {

    @PostMapping(produces = "application/json; charset=UTF-8")
    public String post(@PathVariable String appKey,
                    @RequestBody String requestBody,
                    @RequestParam("signature") String signature,
                    @RequestParam("timestamp") String timestamp,
                    @RequestParam("nonce") String nonce) {
        log.info("接收回调请求：appKey:{} signature:{}, timestamp:{}, nonce:{}, requestBody:{}", appKey, signature, timestamp, nonce, requestBody);

        final DtService dtService = DtConfiguration.getDtService(appKey);
        DtEventMessage inMessage = DtEventMessage.fromEncryptedJson(requestBody, dtService.getDtConfigStorage(), timestamp, nonce, signature);
        log.info("消息解密后内容为：{} ", inMessage);
        Boolean outMessage = this.route(appKey, inMessage);
        log.info("应用消息处理结果: {}", outMessage);

        outMessage = this.route(dtService, inMessage);
        log.info("统一消息处理结果: {}", outMessage);

        DtEventOutMessage res = DtEventOutMessage.toEncrypted(dtService.getDtConfigStorage(), outMessage);
        log.info("组装回复信息：{}", res);
        return res.toEncryptedJson();
    }

    private Boolean route(String appKey, DtEventMessage message) {
        try {
            return DtConfiguration.getRouters().get(appKey).route(message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }

    private Boolean route(DtService dtService, DtEventMessage message) {
        try {
            return DtConfiguration.getRouter().route(dtService, message);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return false;
    }


}
