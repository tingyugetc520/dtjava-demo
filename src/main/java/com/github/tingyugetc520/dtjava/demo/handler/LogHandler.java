package com.github.tingyugetc520.dtjava.demo.handler;

import java.util.Map;

import com.github.tingyugetc520.ali.dingtalk.api.DtService;
import com.github.tingyugetc520.ali.dingtalk.bean.message.DtEventMessage;
import com.github.tingyugetc520.ali.dingtalk.error.DtErrorException;
import com.github.tingyugetc520.ali.dingtalk.message.DtMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class LogHandler implements DtMessageHandler {

    @Override
    public Boolean handle(DtEventMessage message, Map<String, Object> context, DtService dtService) throws DtErrorException {
        log.info("message:{}", message);
        // 不做任何处理
        return null;
    }
}
